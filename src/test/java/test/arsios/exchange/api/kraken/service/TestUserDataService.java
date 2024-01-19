package test.arsios.exchange.api.kraken.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.arsios.exchange.api.kraken.model.Balance;
import com.arsios.exchange.api.kraken.model.Order;
import com.arsios.exchange.api.kraken.model.ResultWrapper;
import com.arsios.exchange.api.kraken.model.TradeVolume;
import com.arsios.exchange.api.kraken.service.IUserDataService;
import com.arsios.exchange.api.kraken.service.impl.KrakenDataException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestUserDataService.class})
//CHECKSTYLE:OFF
@ComponentScan(basePackages = {
		"com.arsios.exchange.api.kraken.service", 
		"com.arsios.exchange.api.kraken.client", 
		"com.arsios.exchange.api.kraken.config",
		"com.arsios.exchange.service.kraken", 
		"com.arsios.exchange.dao",
		"com.arsios.exchange.bot"
	})
//CHECKSTYLE:ON
@PropertySources({
	@PropertySource(value = "classpath:config-${APP_ENV:default}.properties"),
	@PropertySource(value = "file:config-${APP_ENV:default}.properties", ignoreResourceNotFound = true)
})
public class TestUserDataService {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(TestUserDataService.class);
	private static final Gson	GSON	= new GsonBuilder().serializeNulls().setDateFormat("E, d MMM yy HH:mm:ss Z").create();
	// private static final Gson	GSON	= new GsonBuilder().serializeNulls().setLongSerializationPolicy(LongSerializationPolicy.STRING).serializeSpecialFloatingPointValues().setDateFormat("E, d MMM yy HH:mm:ss Z").create();

	@Resource(name = "KrakenUserDataService")
	private IUserDataService	userDataService;

	@Bean
	@Lazy(false)
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	// {"error":[],"result":{"ZEUR":"0.0000","XXBT":"0.2547466800","XLTC":"0.0000000000"}}
	@Test
	public void getBalance() {
		try {
			ResultWrapper<Balance> resultWrapper = new ResultWrapper<Balance>();
			Balance balance = userDataService.getBalance();
			resultWrapper.setResult(balance);

			LOGGER.info(GSON.toJson(resultWrapper));
		} catch (KrakenDataException e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
		}
	}
	
	@Test
	@Ignore
	public void queryOrders() {
		try {
			ResultWrapper<Map<String, Order>> resultWrapper = new ResultWrapper<Map<String, Order>>();
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("txid", "OO2OG6-JE5FQ-EBOGVI");
			//params.put("trades", "true");
			
			Map<String, Order> orders = userDataService.queryOrders(params);
			resultWrapper.setResult(orders);
			
			LOGGER.info(GSON.toJson(resultWrapper));
		} catch (KrakenDataException e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
		}
	}
	
	@Test
	@Ignore
	public void getTradeVolume() {
		ResultWrapper<TradeVolume> resultWrapper = new ResultWrapper<TradeVolume>();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("pair", "XXBTZEUR");
		
		TradeVolume tradeVolume;
		try {
			tradeVolume = userDataService.getTradeVolume(params);
			resultWrapper.setResult(tradeVolume);
			
			LOGGER.info(GSON.toJson(resultWrapper));
		} catch (KrakenDataException e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
		}
	}
	
}
