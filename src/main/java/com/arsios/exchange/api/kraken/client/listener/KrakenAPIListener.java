package com.arsios.exchange.api.kraken.client.listener;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.annotation.Resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.arsios.exchange.controller.StockExchanger;
import com.arsios.exchange.dao.mongodb.collection.IOrderDao;
import com.arsios.exchange.dao.mongodb.collection.ITickerDao;
import com.arsios.exchange.kraken.KrakenExchangerConfig;
import com.arsios.exchange.model.Order;
import com.arsios.exchange.model.Ticker;
import com.arsios.exchange.runtime.StockExchangerConfigure;
import com.arsios.exchange.service.DataException;
import com.arsios.exchange.service.StockExchangeService;
import com.arsios.exchange.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class KrakenAPIListener implements Runnable {
	
	private static final Logger			LOGGER			= LoggerFactory.getLogger(KrakenAPIListener.class);
	private static final Gson			GSON			= new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
	private static final BigDecimal		ONEHUNDERT		= new BigDecimal(100);
	private static final MathContext	MATH_CONTEXT	= MathContext.DECIMAL128;
	
	@Value("${" + KrakenExchangerConfig.CHANGE_TOLERANCE + ":.10}")
	private BigDecimal changeTolerance;
	
	@Value("${" + KrakenExchangerConfig.DEBUG + ":false}")
	private Boolean debug;
	
	@Value("${" + KrakenExchangerConfig.AMOUNT_PRECISION + ":8}")
	private Integer amountPrecision;
	
	@Value("${" + KrakenExchangerConfig.PRICE_PRECISION + ":8}")
	private Integer pricePrecision;
	
	@Value("${" + KrakenExchangerConfig.MINIMAL_OPERATION + ":.01}")
	private BigDecimal minOp;
	
	@Value("${" + KrakenExchangerConfig.STOP_LOSS_TOLERANCE + ":5}")
	private BigDecimal stopLoss;
	
	@Value("${" + KrakenExchangerConfig.FEE + ":.2}")
	private BigDecimal fee;
	
	@Value("${" + KrakenExchangerConfig.ORDER_AMOUNT + ":10}")
	private BigDecimal orderAmount;
	
	@Value("${" + KrakenExchangerConfig.PROFIT + ":.25}")
	private BigDecimal profit;
	
	@Value("${" + KrakenExchangerConfig.ITERATION_DELAY + ":1000}")
	private Integer iterationDelay;
	
	@Resource
	private ITickerDao			tickerDao;
	
	@Resource
	private IOrderDao			orderDao;
	
	@Resource
	private StockExchanger	stockExchanger;
	
	@Resource(name = "KrakenAPIService")
	private StockExchangeService stockExchangeService;

	@Override
	public void run() {
		
		BigDecimal changeTolerancePercentage = changeTolerance.divide(ONEHUNDERT, MATH_CONTEXT);
		
		StockExchangerConfigure configure = new StockExchangerConfigure();
		configure.setAmountPrecision(amountPrecision);
		configure.setMinOp(minOp);
		configure.setOrderAmount(orderAmount);
		configure.setPricePrecision(pricePrecision);
		configure.setProfit(profit);
		configure.setStopLoss(stopLoss);
		configure.setDebug(debug);
		
		tickerDao.setDb("kraken");
		orderDao.setDb("kraken");
		
		try {
			stockExchanger.init(stockExchangeService ,configure);
		} catch (DataException e) {
			LOGGER.info("Error initializing stock exchanger.", e);
			return;
		}
		
		Ticker lastTicker = null;
		BigDecimal askDiff;
		BigDecimal bidDiff;
		
		while (!Thread.currentThread().isInterrupted()) {
			try {
				Ticker ticker = stockExchangeService.getTicker();
				
				if (lastTicker != null) {
					askDiff = ticker.getAsk().subtract(lastTicker.getAsk()).abs();
					bidDiff = ticker.getBid().subtract(lastTicker.getBid()).abs();

					if (askDiff.compareTo(BigDecimal.valueOf(Utilities.formatBigDecimal(changeTolerancePercentage.multiply(ticker.getAsk(), MATH_CONTEXT), this.pricePrecision))) > 0 
							|| bidDiff.compareTo(BigDecimal.valueOf(Utilities.formatBigDecimal(changeTolerancePercentage.multiply(ticker.getBid(), MATH_CONTEXT), this.pricePrecision))) > 0) {
						tickerDao.insert(ticker);
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug(GSON.toJson(ticker));
						}
						Order order = stockExchanger.execute(ticker);
						if (order != null) {
							orderDao.insert(order);
						}
					}
				}
				
				lastTicker = ticker;
			} catch (DataException e) {
				LOGGER.info(ExceptionUtils.getMessage(e));
				break;
			}
			try {
				Thread.sleep(iterationDelay);
			} catch (InterruptedException e) {
				LOGGER.info("Thread interrupted.");
				break;
			}
		}
		LOGGER.info("Finished.");
	}

}
