package test.arsios.exchange.api.btcchina.model;

import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arsios.exchange.api.btcchina.model.Ticker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class TestTicker {
	
	private static final Logger	LOGGER				= LoggerFactory.getLogger(TestTicker.class);
	
	private static final Gson	GSON				= new GsonBuilder().serializeNulls().create();

	@Test
	public void test() {
		
		String json = "{\"ticker\":{\"date\":1410558201,\"market\":\"btccny\",\"high\":2929.99,\"vol\":13988.1168,\"last\":2921.33,\"low\":2886,\"buy\":2921.33,\"sell\":2921.91,\"vwap\":2913.5,\"open\":2927.64,\"prev_close\":2928.84}}";
		
		Map<String, Ticker> ticker = GSON.fromJson(json, new TypeToken<Map<String, Ticker>>() { }.getType());
		
		LOGGER.info(GSON.toJson(ticker.get("ticker")));

	}
	
}
