package test.arsios.exchange.api.kraken.client.listener;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.arsios.exchange.controller.StockExchanger;
import com.arsios.exchange.dao.mongodb.collection.IStatusDao;
import com.arsios.exchange.dao.mongodb.collection.ITickerDao;
import com.arsios.exchange.kraken.KrakenExchangerConfig;
import com.arsios.exchange.model.Balance;
import com.arsios.exchange.model.Order;
import com.arsios.exchange.model.Ticker;
import com.arsios.exchange.runtime.StockExchangerConfigure;
import com.arsios.exchange.service.DataException;
import com.arsios.exchange.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestKrakenAPIListener.class})
//CHECKSTYLE:OFF
@ComponentScan(basePackages = {
		"com.arsios.exchange.api.kraken.service", 
		"com.arsios.exchange.api.kraken.client", 
		"com.arsios.exchange.api.kraken.config",
		"com.arsios.exchange.service.kraken",
		"com.arsios.exchange.controller",
		"com.arsios.exchange.dao"
	})
//CHECKSTYLE:ON
@PropertySources({
	@PropertySource(value = "classpath:config-${APP_ENV:default}.properties"),
	@PropertySource(value = "file:config-${APP_ENV:default}.properties", ignoreResourceNotFound = true)
})
public class TestKrakenAPIListener {
	
	private static final Logger		LOGGER		= LoggerFactory.getLogger(TestKrakenAPIListener.class);
	private static final Gson		GSON		= new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
	private static final BigDecimal ONEHUNDRED = new BigDecimal(100);
	private static final MathContext MATH_CONTEXT = MathContext.DECIMAL128;
	
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
	
	@Resource
	private ITickerDao			tickerDao;
	
	@Resource
	private StockExchanger	stockExchanger;
	
	@Resource
	private IStatusDao			statusDao;
	
	@Bean
	@Lazy(false)
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Test
	public void test() {
		
		long start = 0L;
		long elapsed = 0L;
		long startTotal = 0L;
		
		final int days = 30;
		
		StockExchangerConfigure configure = new StockExchangerConfigure();
		configure.setAmountPrecision(amountPrecision);
		configure.setFee(fee);
		configure.setMinOp(minOp);
		configure.setOrderAmount(orderAmount);
		configure.setPricePrecision(pricePrecision);
		configure.setProfit(profit);
		configure.setStopLoss(stopLoss);
		configure.setDebug(debug);
		configure.setBalance(new Balance());
		
		tickerDao.setDb("kraken");
		
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		
		LOGGER.info("minimal_operation: {}, stop_loss_tolerance: {}, fee: {}"
				+ ", order_amount: {}, debug: {}", minOp, stopLoss, fee, orderAmount, debug);
		
		Date dategte = null;
		Date datelt = null;
		try {
			// dategte = simpleDateFormat.parse("2014-09-09T00:00:00.000+0200");
			datelt = simpleDateFormat.parse("2014-09-12T00:00:00.000+0200");
		} catch (ParseException e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
			return;
		}
		
		BigDecimal startBalance = BigDecimal.ZERO;
		BigDecimal maxBalance = BigDecimal.ZERO;
		BigDecimal effectiveProfit = BigDecimal.ZERO;
		BigDecimal effectiveOrderAmount = BigDecimal.ZERO;
		BigDecimal effectiveBtc = BigDecimal.ZERO;
		BigDecimal effectiveEur = BigDecimal.ZERO;
		BigDecimal maxAmortization = BigDecimal.ZERO;
		
		List<Ticker> tickers = tickerDao.getTickers(dategte, datelt, (byte) 1);
		
		Order initialOrder;
		Order lastOrder;
		
		BigDecimal initialBalance = null;
		BigDecimal amortization = null;
		
		try {
			stockExchanger.init(null, configure);
		} catch (DataException e) {
			LOGGER.info("Error initializing stock exchanger.");
			return;
		}
		
		//CHECKSTYLE:OFF
		Double sdprofit = 0.0;
		Double edprofit = 5.0;
		Double idprofit = 1.0;

		Double sdorderAmount = 0.0;
		Double edorderAmount = 80.0;
		Double idorderAmount = 20.0;

		Double sdvalue = 0.0;
		Double edvalue = 5.0;
		Double idvalue = 0.5;

		Double sdcurrency = 0.0;
		Double edcurrency = 10000.0;
		Double idcurrency = 1000.0;
		//CHECKSTYLE:ON
		
		Double iprofit = (edprofit - sdprofit) / idprofit;
		Double iorderAmount = (edorderAmount - sdorderAmount) / idorderAmount;
		Double ivalue = (edvalue - sdvalue) / idvalue;
		Double icurrency = (edcurrency - sdcurrency) / idcurrency;
		
		Double iterations = iprofit * iorderAmount * ivalue * icurrency;
		//Double iterations = ivalue * icurrency;
		
		Integer counter = 0;
		
		startTotal = System.currentTimeMillis();
		
		try {
			//CHECKSTYLE:OFF
			// for (Double dprofit = sdprofit; dprofit < edprofit; dprofit += idprofit) {
				
				// for (Double dorderAmount = sdorderAmount; dorderAmount < edorderAmount; dorderAmount += idorderAmount) {
						
					// for (Double dvalue = sdvalue; dvalue < edvalue; dvalue += idvalue) {
						
						// for (Double dcurrency = sdcurrency; dcurrency < edcurrency; dcurrency += idcurrency) {
							
							//CHECKSTYLE:OFF
							//configure.setProfit(BigDecimal.valueOf(dprofit));
							//configure.setOrderAmount(BigDecimal.valueOf(dorderAmount));
							//configure.getBalance().setValue(BigDecimal.valueOf(dvalue));
							//configure.getBalance().setCurrency(BigDecimal.valueOf(dcurrency));
							
							configure.setProfit(BigDecimal.valueOf(0.1));
							configure.setOrderAmount(new BigDecimal(40));
							configure.getBalance().setValue(new BigDecimal(2));
							configure.getBalance().setCurrency(new BigDecimal(740));
							
							LOGGER.info("Start trade :" + GSON.toJson(configure));
							//CHECKSTYLE:ON
							
							stockExchanger.reset(configure);
							
							initialBalance = configure.getBalance().getCurrency().add(configure.getBalance().getValue().multiply(tickers.get(0).getAsk(), MATH_CONTEXT));
							start = System.currentTimeMillis();
							
							initialOrder = null;
							lastOrder = null;
					
							for (int d = 0; d < days; d++) {
								if (tickers != null) {
									Ticker lastTicker = null;
									for (Ticker ticker : tickers) {
										if (lastTicker != null) {
											Order order = stockExchanger.execute(ticker);
											if (order != null) {
												if (initialOrder == null) {
													initialOrder = order;
												}
												lastOrder = order;
												order.setCreated(ticker.getCreated());
												
												if (LOGGER.isDebugEnabled()) {
													LOGGER.debug(GSON.toJson(ticker));
													LOGGER.debug(GSON.toJson(order));
												}
											}
										}
										lastTicker = ticker;
									}
								}				
							}
							
							if (initialOrder != null && lastOrder != null) {
								
								LOGGER.info("First ticker: ask: " + tickers.get(0).getAsk() + ", bid: " + tickers.get(0).getBid());
								LOGGER.info("First order: eur: " + initialOrder.getCurrency() + ", btc: " + initialOrder.getValue() + ", balance: " + initialOrder.getBalance());
								
								LOGGER.info("Last ticker: ask: " + tickers.get(tickers.size() - 1).getAsk() + ", bid: " + tickers.get(tickers.size() - 1).getBid());
								LOGGER.info("Last order: eur: " + lastOrder.getCurrency() + ", btc: " + lastOrder.getValue() + ", balance: " + lastOrder.getBalance());

								amortization = lastOrder.getBalance().divide(initialBalance, MATH_CONTEXT).subtract(BigDecimal.ONE).multiply(ONEHUNDRED, MATH_CONTEXT);
								
								if (lastOrder.getBalance().divide(initialBalance, MATH_CONTEXT).compareTo(maxAmortization) > 0) {
									startBalance = initialBalance;
									maxBalance = lastOrder.getBalance();
									effectiveProfit = configure.getProfit();
									effectiveOrderAmount = configure.getOrderAmount();
									effectiveBtc = configure.getBalance().getValue();
									effectiveEur = configure.getBalance().getCurrency();
									maxAmortization = lastOrder.getBalance().divide(initialBalance, MATH_CONTEXT);
								}
								
								LOGGER.info("Amortization: " + Utilities.formatBigDecimal(amortization, 2) + "%, initial balance: " + Utilities.formatBigDecimal(initialBalance, 2) + ", balance: " + Utilities.formatBigDecimal(lastOrder.getBalance(), 2));
								
								elapsed = System.currentTimeMillis() - start;
								LOGGER.info("Time elapsed: " + Utilities.parseMilliseconds(elapsed));
							}
							
							//CHECKSTYLE:OFF
							counter++;
							LOGGER.info("Progress: " + new BigDecimal((counter / iterations) * 100.0, new MathContext(2)) + " %, iteration: " + counter + ", iterations: " + Utilities.formatBigDecimal(new BigDecimal(iterations), 2) + ", max profit: " + maxAmortization.subtract(BigDecimal.ONE).multiply(ONEHUNDRED, new MathContext(8, RoundingMode.DOWN)));
							//CHECKSTYLE:ON
						// }
					// }
				// }
			// }
			
			//CHECKSTYLE:OFF		
			LOGGER.info("Start balance: " + Utilities.formatBigDecimal(startBalance, 2) + ", end balance: " + Utilities.formatBigDecimal(maxBalance, 2) + ", amortization: " + maxAmortization.subtract(BigDecimal.ONE).multiply(ONEHUNDRED, new MathContext(8, RoundingMode.DOWN)) + ", profit: " 
					+ effectiveProfit + ", orderAmountPercentage: " + effectiveOrderAmount + ", eur: " + effectiveEur + ", btc: " + effectiveBtc);
			//CHECKSTYLE:ON		
			
			elapsed = System.currentTimeMillis() - startTotal;
			LOGGER.info("Total time elapsed: " + Utilities.parseMilliseconds(elapsed));
		} catch (DataException e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
		}
	}
}
