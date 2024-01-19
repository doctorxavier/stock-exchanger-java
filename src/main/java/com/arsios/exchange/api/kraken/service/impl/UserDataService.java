package com.arsios.exchange.api.kraken.service.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arsios.exchange.api.kraken.client.IKrakenAPIClient;
import com.arsios.exchange.api.kraken.model.Balance;
import com.arsios.exchange.api.kraken.model.Order;
import com.arsios.exchange.api.kraken.model.ResultWrapper;
import com.arsios.exchange.api.kraken.model.TradeVolume;
import com.arsios.exchange.api.kraken.service.IUserDataService;
import com.arsios.exchange.api.kraken.utils.ResultWrapperChecker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Service("KrakenUserDataService")
public class UserDataService implements IUserDataService {

	private static final Logger LOGGER	= LoggerFactory.getLogger(UserDataService.class);
	private static final Gson	GSON	= new GsonBuilder().serializeNulls().setDateFormat("E, d MMM yy HH:mm:ss Z").create();
	
	private static final Type	BALANCE_TYPE		= new TypeToken<ResultWrapper<Balance>>() { }.getType();
	private static final Type	QUERY_ORDERS_TYPE	= new TypeToken<ResultWrapper<Map<String, Order>>>() { }.getType();
	private static final Type	TRADE_VOLUME_TYPE	= new TypeToken<ResultWrapper<TradeVolume>>() { }.getType();

	private static final ResultWrapperChecker<Balance>				BALANCE_CHECKER			= new ResultWrapperChecker<Balance>();
	private static final ResultWrapperChecker<Map<String, Order>> 	QUERY_ORDERS_CHECKER	= new ResultWrapperChecker<Map<String, Order>>();
	private static final ResultWrapperChecker<TradeVolume>			TRADE_VOLUME_CHECKER	= new ResultWrapperChecker<TradeVolume>();
	
	@Resource
	private IKrakenAPIClient	krakenAPIClient;

	public Balance getBalance() throws KrakenDataException {
		ResultWrapper<Balance> resultWrapper;
		String result;
		try {
			result = krakenAPIClient.getPrivate("Balance");
		} catch (IOException e) {
			throw new KrakenDataException(e);
		}
		if (result != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(result);
			}
			resultWrapper = GSON.fromJson(result, BALANCE_TYPE);
			return BALANCE_CHECKER.checkErrors(resultWrapper);
		}
		return null;
	}

	public Map<String, Order> queryOrders(Map<String, String> params) throws KrakenDataException {
		ResultWrapper<Map<String, Order>> resultWrapper;
		String result;
		try {
			result = krakenAPIClient.getPrivate("QueryOrders", params);
		} catch (IOException e) {
			throw new KrakenDataException(e);
		}
		if (result != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(result);
			}
			resultWrapper = GSON.fromJson(result, QUERY_ORDERS_TYPE);
			return QUERY_ORDERS_CHECKER.checkErrors(resultWrapper);
		}
		return null;
	}
	
	public TradeVolume getTradeVolume() throws KrakenDataException {
		return getTradeVolume(new HashMap<String, String>());
	}

	public TradeVolume getTradeVolume(Map<String, String> params) throws KrakenDataException {
		ResultWrapper<TradeVolume> resultWrapper;
		String result;
		try {
			result = krakenAPIClient.getPrivate("TradeVolume", params);
		} catch (IOException e) {
			throw new KrakenDataException(e);
		}
		if (result != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(result);
			}
			resultWrapper = GSON.fromJson(result, TRADE_VOLUME_TYPE);
			return TRADE_VOLUME_CHECKER.checkErrors(resultWrapper);
		}
		return null;
	}

}
