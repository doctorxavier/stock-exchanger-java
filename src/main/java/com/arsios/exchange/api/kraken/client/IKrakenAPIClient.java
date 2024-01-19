package com.arsios.exchange.api.kraken.client;

import java.io.IOException;
import java.util.Map;

public interface IKrakenAPIClient {

	String getPublic(String method) throws IOException;
	
	String getPublic(String method, Map<String, String> params) throws IOException;
	
	String getPrivate(String method) throws IOException;
	
	String getPrivate(String method, Map<String, String> params) throws IOException;
	
}
