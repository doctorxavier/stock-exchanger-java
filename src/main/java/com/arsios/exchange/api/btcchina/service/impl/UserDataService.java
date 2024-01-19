package com.arsios.exchange.api.btcchina.service.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arsios.exchange.api.btcchina.client.IBTCChinaAPIClient;
import com.arsios.exchange.api.btcchina.model.AccountInfo;
import com.arsios.exchange.api.btcchina.model.Balance;
import com.arsios.exchange.api.btcchina.model.Order;
import com.arsios.exchange.api.btcchina.model.ResultWrapper;
import com.arsios.exchange.api.btcchina.service.IUserDataService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Service("BTCChinaUserDataService")
public class UserDataService implements IUserDataService {
	
	private static final Logger	LOGGER			= LoggerFactory.getLogger(UserDataService.class);
	private static final Gson	GSON			= new GsonBuilder().create();

	private static final Type	ACCOUNT_INFO_TYPE	= new TypeToken<ResultWrapper<AccountInfo>>() { }.getType();
	private static final Type	ORDER_TYPE			= new TypeToken<ResultWrapper<Map<String, Order>>>() { }.getType();
	
	@Resource
	private IBTCChinaAPIClient	bTCChinaAPIClient;
	
	public Balance getBalance() throws BTCChinaDataException {
		List<Object> params = new ArrayList<Object>(0);
		params.add("balance");
		
		ResultWrapper<AccountInfo> resultWrapper = new ResultWrapper<AccountInfo>();
		
		String result = null;

		try {
			result = bTCChinaAPIClient.execute("getAccountInfo", params);
		} catch (IOException e) {
			throw new BTCChinaDataException(e);
		}

		if (result != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(result);
			}
			resultWrapper = GSON.fromJson(result, ACCOUNT_INFO_TYPE);
			
			if (resultWrapper.getError() != null) {
				throw new BTCChinaDataException(GSON.toJson(resultWrapper.getError()));
			}
			if (resultWrapper.getResult() != null) {
				return resultWrapper.getResult().getBalance();
			}
		}
		return null;
	}
	
	public Order getOrder(Long txid)  throws BTCChinaDataException {
		
		List<Object> params = new ArrayList<Object>(0);
		params.add(txid);
		params.add(null);
		params.add(true);
		
		ResultWrapper<Map<String,Order>> resultWrapper = new ResultWrapper<Map<String,Order>>();

		String result = null; 
				
		try {
			result = bTCChinaAPIClient.execute("getOrder", params);
		} catch (IOException e) {
			throw new BTCChinaDataException(e);
		}

		if (result != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(result);
			}
			resultWrapper = GSON.fromJson(result, ORDER_TYPE);
			
			if (resultWrapper.getError() != null) {
				throw new BTCChinaDataException(GSON.toJson(resultWrapper.getError()));
			}
			
			if (resultWrapper.getResult() != null && resultWrapper.getResult().containsKey("order")) {
				return resultWrapper.getResult().get("order");
			}
		}
		return null;
	}
	
}
