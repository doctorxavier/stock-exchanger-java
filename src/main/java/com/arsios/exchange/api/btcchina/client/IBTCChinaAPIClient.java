package com.arsios.exchange.api.btcchina.client;

import java.io.IOException;
import java.util.List;


public interface IBTCChinaAPIClient {
	
	String execute(String method) throws IOException;
	
	String execute(String method, List<Object> params) throws IOException;
	
}
