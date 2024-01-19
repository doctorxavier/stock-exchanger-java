package com.arsios.exchange.api.btcchina.client.listener.callback;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.arsios.exchange.btcchina.BTCChinaExchangerConfig;
import com.arsios.exchange.controller.StockExchanger;
import com.arsios.exchange.dao.mongodb.collection.IOrderDao;
import com.arsios.exchange.dao.mongodb.collection.ITickerDao;
import com.arsios.exchange.model.Order;
import com.arsios.exchange.model.Ticker;
import com.arsios.exchange.runtime.StockExchangerConfigure;
import com.arsios.exchange.service.DataException;
import com.arsios.exchange.service.StockExchangeService;
import com.arsios.exchange.utils.Utilities;
import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
public class BTCChinaAPICallback implements Emitter.Listener {

	private static final Logger			LOGGER			= LoggerFactory.getLogger(BTCChinaAPICallback.class);

	private static final Gson			GSON			= new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
	private static final BigDecimal		ONEHUNDERT		= new BigDecimal(100);
	private static final MathContext	MATH_CONTEXT	= MathContext.DECIMAL128;
	
	@Value("${" + BTCChinaExchangerConfig.CHANGE_TOLERANCE + ":.10}")
	private BigDecimal changeTolerance;
	
	@Value("${" + BTCChinaExchangerConfig.DEBUG + ":false}")
	private Boolean debug;
	
	@Value("${" + BTCChinaExchangerConfig.AMOUNT_PRECISION + ":8}")
	private Integer amountPrecision;
	
	@Value("${" + BTCChinaExchangerConfig.PRICE_PRECISION + ":8}")
	private Integer pricePrecision;
	
	@Value("${" + BTCChinaExchangerConfig.MINIMAL_OPERATION + ":.01}")
	private BigDecimal minOp;
	
	@Value("${" + BTCChinaExchangerConfig.STOP_LOSS_TOLERANCE + ":5}")
	private BigDecimal stopLoss;
	
	@Value("${" + BTCChinaExchangerConfig.FEE + ":.2}")
	private BigDecimal fee;
	
	@Value("${" + BTCChinaExchangerConfig.ORDER_AMOUNT + ":10}")
	private BigDecimal orderAmount;
	
	@Value("${" + BTCChinaExchangerConfig.PROFIT + ":.25}")
	private BigDecimal profit;
	
	@Resource
	private ITickerDao			tickerDao;
	
	@Resource
	private IOrderDao			orderDao;
	
	@Resource
	private StockExchanger	stockExchanger;
	
	@Resource(name = "BTCChinaAPIService")
	private StockExchangeService stockExchangeService;
	
	private Ticker lastTicker;
	
	private BigDecimal askDiff;
	
	private BigDecimal bidDiff;
	
	private BigDecimal changeTolerancePercentage;
	
	@PostConstruct
	public void init() {
		
		lastTicker = null;
		
		StockExchangerConfigure configure = new StockExchangerConfigure();
		configure.setAmountPrecision(amountPrecision);
		configure.setMinOp(minOp);
		configure.setOrderAmount(orderAmount);
		configure.setPricePrecision(pricePrecision);
		configure.setProfit(profit);
		configure.setStopLoss(stopLoss);
		configure.setDebug(debug);
		
		changeTolerancePercentage = changeTolerance.divide(ONEHUNDERT, MATH_CONTEXT);
		
		tickerDao.setDb("btcchina");
		orderDao.setDb("btcchina");
		
		try {
			stockExchanger.init(stockExchangeService, configure);
		} catch (DataException e) {
			LOGGER.info("Error initializing stock exchanger.", e);
			return;
		}
		
		tickerDao.setDb("btcchina");
	}

	@Override
	public void call(Object... args) {
		
		if (args.length > 0 && args[0] instanceof JSONObject) {
			Map<String, com.arsios.exchange.api.btcchina.model.Ticker> tickerMap = 
					GSON.fromJson(args[0].toString(), new TypeToken<Map<String, com.arsios.exchange.api.btcchina.model.Ticker>>() { }.getType());
			
			if (tickerMap.containsKey("ticker")) {
				com.arsios.exchange.api.btcchina.model.Ticker btcChinaTicker = tickerMap.get("ticker");
				Ticker ticker = new Ticker();
				
				ticker.setAsk(btcChinaTicker.getSell());
				ticker.setAvg(btcChinaTicker.getVwap());
				ticker.setBid(btcChinaTicker.getBuy());
				ticker.setHigh(btcChinaTicker.getHigh());
				ticker.setLast(btcChinaTicker.getLast());
				ticker.setLow(btcChinaTicker.getLow());
				ticker.setOpen(btcChinaTicker.getOpen());
				ticker.setTrades(BigDecimal.ZERO);
				ticker.setVolume(btcChinaTicker.getVol());
				ticker.setCreated(new Date(TimeUnit.SECONDS.toMillis(btcChinaTicker.getDate())));
				
				if (lastTicker != null) {
					askDiff = ticker.getAsk().subtract(lastTicker.getAsk()).abs();
					bidDiff = ticker.getBid().subtract(lastTicker.getBid()).abs();

					if (askDiff.compareTo(BigDecimal.valueOf(Utilities.formatBigDecimal(changeTolerancePercentage.multiply(ticker.getAsk(), MATH_CONTEXT), this.pricePrecision))) > 0 
							|| bidDiff.compareTo(BigDecimal.valueOf(Utilities.formatBigDecimal(changeTolerancePercentage.multiply(ticker.getBid(), MATH_CONTEXT), this.pricePrecision))) > 0) {
						tickerDao.insert(ticker);
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug(GSON.toJson(ticker));
						}
						Order order = null;
						try {
							order = stockExchanger.execute(ticker);
						} catch (DataException e) {
							LOGGER.error(ExceptionUtils.getMessage(e), e);
						}
						if (order != null) {
							orderDao.insert(order);
						}
					}
				}
				
				lastTicker = ticker;
			}
		}
	}

}
