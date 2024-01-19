package test.arsios.exchange.api.btcchina.client;

import java.util.ArrayList;
import java.util.List;

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

import com.arsios.exchange.api.btcchina.client.IBTCChinaAPIClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBTCChinaAPIClient.class})
//CHECKSTYLE:OFF
@ComponentScan(basePackages = {
		"com.arsios.exchange.api.btcchina.client"
	})
//CHECKSTYLE:ON
@PropertySources({
	@PropertySource(value = "classpath:config-${APP_ENV:default}.properties"),
	@PropertySource(value = "file:config-${APP_ENV:default}.properties", ignoreResourceNotFound = true)
})
public class TestBTCChinaAPIClient {
	
	private static final Logger	LOGGER	= LoggerFactory.getLogger(TestBTCChinaAPIClient.class);
	
	@Resource
	private IBTCChinaAPIClient	bTCChinaAPIClient;
	
	@Bean
	@Lazy(false)
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Test
	public void execute() {
		try {
			List<Object> params = new ArrayList<Object>(0);
			params.add("profile");
			
			String result = bTCChinaAPIClient.execute("getAccountInfo", params);
			if (result != null) {
				LOGGER.info(result);
			}
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
		}
	}
	
}
