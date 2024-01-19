package test.arsios.exchange.api.btcchina.service;

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

import com.arsios.exchange.api.btcchina.model.Balance;
import com.arsios.exchange.api.btcchina.model.Order;
import com.arsios.exchange.api.btcchina.service.IUserDataService;
import com.arsios.exchange.api.btcchina.service.impl.BTCChinaDataException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestUserDataService.class})
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
public class TestUserDataService {
	
	private static final Logger	LOGGER	= LoggerFactory.getLogger(TestUserDataService.class);
	private static final Gson	GSON	= new GsonBuilder().create();
	
	@Resource(name = "BTCChinaUserDataService")
	private IUserDataService	userDataService;
	
	@Bean
	@Lazy(false)
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Test
	@Ignore
	public void getBalance() {
		Balance balance;
		try {
			balance = userDataService.getBalance();
			LOGGER.info(GSON.toJson(balance));
		} catch (BTCChinaDataException e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
		}
	}

	@Test
	public void getOrder() {
		Order order;
		try {
			order = userDataService.getOrder(Long.valueOf("33214976"));
			LOGGER.info(GSON.toJson(order));
		} catch (NumberFormatException | BTCChinaDataException e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
		}
	}
	
}
