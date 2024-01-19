package test.arsios.exchange.dao.mongodb.collection;

import java.math.BigDecimal;

import javax.annotation.Resource;

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

import com.arsios.exchange.dao.mongodb.collection.IStatusDao;
import com.arsios.exchange.model.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestStatusDao.class})
//CHECKSTYLE:OFF
@ComponentScan(basePackages = { 
		"com.arsios.exchange.api.kraken.config", 
		"com.arsios.exchange.dao"
	})
//CHECKSTYLE:ON
@PropertySources({
	@PropertySource(value = "classpath:config-${APP_ENV:default}.properties"),
	@PropertySource(value = "file:config-${APP_ENV:default}.properties", ignoreResourceNotFound = true)
})
public class TestStatusDao {
	
	private static final Logger	LOGGER	= LoggerFactory.getLogger(TestStatusDao.class);
	private static final Gson	GSON	= new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

	@Resource
	private IStatusDao			statusDao;
	
	@Bean
	@Lazy(false)
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Test
	public void testInsert() {
		//CHECKSTYLE:OFF
		Status status = new Status();
		status.setAmount(new BigDecimal(0.11));
		status.setCharge(new BigDecimal(1.12));
		status.setCurrency(new BigDecimal(2.13));
		status.setProfit(new BigDecimal(3.14));
		status.setValue(new BigDecimal(4.15));
		//CHECKSTYLE:ON
		
		statusDao.setDb("stockexchanger");
		statusDao.insert(status);
	}
	
}
