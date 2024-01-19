package test.arsios.exchange.dao.mongodb.collection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import com.arsios.exchange.dao.mongodb.collection.ITickerDao;
import com.arsios.exchange.model.Ticker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestTickerDao.class})
//CHECKSTYLE:OFF
@ComponentScan(basePackages = {
		"com.arsios.exchange.api.kraken.service", 
		"com.arsios.exchange.api.kraken.client", 
		"com.arsios.exchange.api.kraken.config",
		"com.arsios.exchange.service.kraken", 
		"com.arsios.exchange.dao"
	})
//CHECKSTYLE:ON
@PropertySources({
	@PropertySource(value = "classpath:config-${APP_ENV:default}.properties"),
	@PropertySource(value = "file:config-${APP_ENV:default}.properties", ignoreResourceNotFound = true)
})
public class TestTickerDao {
	
	private static final Logger	LOGGER	= LoggerFactory.getLogger(TestTickerDao.class);
	private static final Gson	GSON	= new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

	@Resource
	private ITickerDao			tickerDao;
	
	@Bean
	@Lazy(false)
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Test
	@Ignore
	public void test() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		
		try {
			Date dategte = simpleDateFormat.parse("2014-09-10T03:00:00.000+0200");
			Date datelt = simpleDateFormat.parse("2014-09-10T04:00:00.000+0200");
			
			List<Ticker> tickers = tickerDao.getTickers(dategte, datelt, (byte) 0, (short) 0);
			if (tickers != null) {
				for (Ticker ticker : tickers) {
					LOGGER.info(GSON.toJson(ticker));
				}
			}
		} catch (ParseException e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
		}
	}

}
