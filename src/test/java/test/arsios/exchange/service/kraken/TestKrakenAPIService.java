package test.arsios.exchange.service.kraken;

import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.arsios.exchange.dao.mongodb.collection.ITickerDao;
import com.arsios.exchange.model.Ticker;
import com.arsios.exchange.service.StockExchangeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestKrakenAPIService.class})
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
public class TestKrakenAPIService {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(TestKrakenAPIService.class);
	private static final Gson	GSON	= new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

	@Resource(name = "KrakenAPIService")
	private StockExchangeService	krakenAPIService;

	@Resource
	private ITickerDao			tickerDao;
	
	@Autowired
	private Environment env;

	@Bean
	@Lazy(false)
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Test
	@Ignore
	public void testGetTicker() {
		try {
			Ticker ticker = krakenAPIService.getTicker();
			//tickerDao.insert(ticker);
			LOGGER.info(GSON.toJson(ticker));
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
		}
	}
	
	@Test
	@Ignore
	public void testTickerEquals() {
		try {
			final int delay = 1000;
			Ticker currTicker = krakenAPIService.getTicker();
			LOGGER.info(GSON.toJson(currTicker));
			
			Ticker lastTicker = krakenAPIService.getTicker();
			LOGGER.info(GSON.toJson(lastTicker));
			
			LOGGER.info(String.valueOf(Objects.equals(currTicker, lastTicker)));
			
			Thread.sleep(delay);
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
		}
	}
	
	@Test
	public void test() {
		LOGGER.info("test");
	}

}
