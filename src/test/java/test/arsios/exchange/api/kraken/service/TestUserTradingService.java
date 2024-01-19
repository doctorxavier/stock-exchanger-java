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

import com.arsios.exchange.api.kraken.model.AddOrderResult;
import com.arsios.exchange.api.kraken.model.CancelOrderResult;
import com.arsios.exchange.api.kraken.model.ResultWrapper;
import com.arsios.exchange.api.kraken.service.IUserTradingService;
import com.arsios.exchange.api.kraken.service.impl.KrakenDataException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestUserTradingService.class})
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
public class TestUserTradingService {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(TestUserTradingService.class);
	private static final Gson	GSON	= new GsonBuilder().serializeNulls().setDateFormat("E, d MMM yy HH:mm:ss Z").create();

	@Resource(name = "KrakenUserTradingService")
	private IUserTradingService	userTradingService;

	@Bean
	@Lazy(false)
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Test
	@Ignore
	public void addOrder() {
		try {
			ResultWrapper<AddOrderResult> resultWrapper = new ResultWrapper<AddOrderResult>();

			Map<String, String> params = new HashMap<String, String>();
			params.put("pair", "XXBTZEUR");
			params.put("type", "sell");
			params.put("ordertype", "market");
			params.put("volume", "0.01");

			AddOrderResult addOrderResult = userTradingService.addOrder(params);
			resultWrapper.setResult(addOrderResult);

			LOGGER.info(GSON.toJson(resultWrapper));
		} catch (KrakenDataException e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
		}
	}

	@Test
	@Ignore
	public void cancelOrder() {
		try {
			ResultWrapper<CancelOrderResult> resultWrapper = new ResultWrapper<CancelOrderResult>();

			Map<String, String> params = new HashMap<String, String>();
			params.put("txid", "OTEOXF-KOLBF-OE3ZIQ");

			CancelOrderResult cancelOrderResult = userTradingService.cancelOrder(params);
			resultWrapper.setResult(cancelOrderResult);

			LOGGER.info(GSON.toJson(resultWrapper));
		} catch (KrakenDataException e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
		}
	}

}
