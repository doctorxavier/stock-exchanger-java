package com.arsios.exchange.runtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.arsios.exchange.api.kraken.client.listener.KrakenAPIListener;

//CHECKSTYLE:OFF
@ComponentScan(basePackages = {
		"com.arsios.exchange.api.kraken.service", 
		"com.arsios.exchange.api.kraken.client", 
		"com.arsios.exchange.api.kraken.config",
		"com.arsios.exchange.service.kraken",
		"com.arsios.exchange.dao",
		"com.arsios.exchange.controller",
		"com.arsios.exchange.kraken"
	})
//CHECKSTYLE:ON
@PropertySources({
	@PropertySource(value = "classpath:config-${APP_ENV:default}.properties"),
    @PropertySource(value = "file:config-${APP_ENV:default}.properties", ignoreResourceNotFound = true)
})
public final class StockExchangerRuntime {
	
	private static final Logger	LOGGER	= LoggerFactory.getLogger(StockExchangerRuntime.class);

	@Bean
	@Lazy(false)
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(StockExchangerRuntime.class)) {
			
			KrakenAPIListener krakenAPIListener = context.getBean(KrakenAPIListener.class);
			Thread krakenAPIThread = new Thread(krakenAPIListener);
			krakenAPIThread.start();

			//CHECKSTYLE:OFF
			while (krakenAPIThread.isAlive()) { }
			//CHECKSTYLE:ON
			LOGGER.info("Finished.");
		}
	}
	
}
