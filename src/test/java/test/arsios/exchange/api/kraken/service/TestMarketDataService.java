package test.arsios.exchange.api.kraken.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
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

import com.arsios.exchange.api.kraken.model.ResultWrapper;
import com.arsios.exchange.api.kraken.model.Ticker;
import com.arsios.exchange.api.kraken.service.IMarketDataService;
import com.arsios.exchange.api.kraken.service.impl.KrakenDataException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestMarketDataService.class})
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
public class TestMarketDataService {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(TestMarketDataService.class);
	private static final Gson	GSON	= new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

	@Resource(name = "KrakenMarketDataService")
	private IMarketDataService		marketDataService;

	@Bean
	@Lazy(false)
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Test
	public void getTicker() {
		
		ResultWrapper<Map<String, Ticker>> resultWrapper = new ResultWrapper<Map<String, Ticker>>();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("pair", "XXBTZEUR");
		
		Map<String, Ticker> tickers;
		try {
			tickers = marketDataService.getTicker(params);
			resultWrapper.setResult(tickers);
			
			LOGGER.info(GSON.toJson(resultWrapper));
		} catch (KrakenDataException e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
		}
	}
	
}
