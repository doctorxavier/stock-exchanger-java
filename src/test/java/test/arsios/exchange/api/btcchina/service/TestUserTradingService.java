package test.arsios.exchange.api.btcchina.service;

import java.math.BigDecimal;
import java.math.MathContext;

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

import com.arsios.exchange.api.btcchina.client.IBTCChinaAPIClient;
import com.arsios.exchange.api.btcchina.service.IUserTradingService;
import com.arsios.exchange.api.btcchina.service.impl.BTCChinaDataException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestUserTradingService.class})
//CHECKSTYLE:OFF
@ComponentScan(basePackages = {
		"com.arsios.exchange.api.btcchina.client",
		"com.arsios.exchange.api.btcchina.service"
	})
//CHECKSTYLE:ON
@PropertySources({
	@PropertySource(value = "classpath:config-${APP_ENV:default}.properties"),
	@PropertySource(value = "file:config-${APP_ENV:default}.properties", ignoreResourceNotFound = true)
})
public class TestUserTradingService {
	private static final Logger	LOGGER	= LoggerFactory.getLogger(TestUserTradingService.class);
	private static final Gson	GSON	= new GsonBuilder().create();
	
	@Resource
	private IBTCChinaAPIClient	bTCChinaAPIClient;
	
	@Resource(name = "BTCChinaUserTradingService")
	private IUserTradingService	userTradingService;
	
	@Bean
	@Lazy(false)
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Test
	@Ignore
	public void sellOrder() {
		MathContext mc = new MathContext(1);
		BigDecimal amount = new BigDecimal(Float.valueOf("0.01"), mc);
		
		Long txid;
		try {
			txid = userTradingService.sellOrder(null, amount, null);
			LOGGER.info(GSON.toJson(txid));
		} catch (BTCChinaDataException e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
		}

	}
	
	@Test
	@Ignore
	public void buyOrder() {
		MathContext mc = new MathContext(1);
		BigDecimal amount = new BigDecimal(Float.valueOf("0.01"), mc);
		
		Long txid;
		try {
			txid = userTradingService.buyOrder(null, amount, null);
			LOGGER.info(GSON.toJson(txid));
		} catch (BTCChinaDataException e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
		}
	}
	
}
