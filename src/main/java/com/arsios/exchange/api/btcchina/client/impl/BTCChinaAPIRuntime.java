package com.arsios.exchange.api.btcchina.client.impl;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.arsios.exchange.api.btcchina.client.listener.BTCChinaAPIListener;

//CHECKSTYLE:OFF
@ComponentScan(basePackages = {
		"com.arsios.exchange.api.btcchina.service", 
		"com.arsios.exchange.api.btcchina.client", 
		"com.arsios.exchange.api.btcchina.config",
		"com.arsios.exchange.service.btcchina",
		"com.arsios.exchange.dao",
		"com.arsios.exchange.controller",
		"com.arsios.exchange.btcchina"
	})
//CHECKSTYLE:ON
@PropertySources({
	@PropertySource(value = "classpath:config-${APP_ENV:default}.properties"),
	@PropertySource(value = "file:config-${APP_ENV:default}.properties", ignoreResourceNotFound = true)
})
public class BTCChinaAPIRuntime {
	
	private static final Logger		LOGGER	= LoggerFactory.getLogger(BTCChinaAPIRuntime.class);
	
	private static final int DELAY = 60000;
	
	@Bean
	@Lazy(false)
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BTCChinaAPIRuntime.class)) {
			boolean delay = false;
			
			BTCChinaAPIListener bTCChinaAPIClient = context.getBean(BTCChinaAPIListener.class);
			Thread bTCChinaAPIClientThread = new Thread(bTCChinaAPIClient);
			bTCChinaAPIClientThread.start();
			
			if (delay) {
				try {
					bTCChinaAPIClientThread.join(DELAY);
				} catch (InterruptedException e) {
					LOGGER.error(ExceptionUtils.getMessage(e));
				} catch (Exception e) {
					LOGGER.error(ExceptionUtils.getMessage(e));
				}
			}
			
			//CHECKSTYLE:OFF
			if (bTCChinaAPIClientThread.isAlive() && delay) {
				LOGGER.info("Interrupt thread.");
				bTCChinaAPIClientThread.interrupt();
				while (bTCChinaAPIClientThread.isAlive() 
						&& !bTCChinaAPIClientThread.isInterrupted()) { }
			} else {
				while (bTCChinaAPIClientThread.isAlive() 
						&& !bTCChinaAPIClientThread.isInterrupted()) { }
			}
			//CHECKSTYLE:ON
			LOGGER.info("Terminated.");
		}
	}
}
