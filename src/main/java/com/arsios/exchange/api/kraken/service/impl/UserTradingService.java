package com.arsios.exchange.api.kraken.service.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arsios.exchange.api.kraken.client.IKrakenAPIClient;
import com.arsios.exchange.api.kraken.model.AddOrderResult;
import com.arsios.exchange.api.kraken.model.CancelOrderResult;
import com.arsios.exchange.api.kraken.model.ResultWrapper;
import com.arsios.exchange.api.kraken.service.IUserTradingService;
import com.arsios.exchange.api.kraken.utils.ResultWrapperChecker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Service("KrakenUserTradingService")
public class UserTradingService implements IUserTradingService {

	private static final Logger	LOGGER				= LoggerFactory.getLogger(UserTradingService.class);

	private static final Gson	GSON				= new GsonBuilder().serializeNulls().setDateFormat("E, d MMM yy HH:mm:ss Z").create();
	private static final Type	ADD_ORDER_TYPE		= new TypeToken<ResultWrapper<AddOrderResult>>() { }.getType();
	private static final Type	CANCEL_ORDER_TYPE	= new TypeToken<ResultWrapper<CancelOrderResult>>() { }.getType();
	
	private static final ResultWrapperChecker<AddOrderResult>		ADD_ORDER_CHECKER		= new ResultWrapperChecker<AddOrderResult>();
	private static final ResultWrapperChecker<CancelOrderResult>	CANCEL_ORDER_CHECKER	= new ResultWrapperChecker<CancelOrderResult>();
	
	@Resource
	private IKrakenAPIClient	krakenAPIClient;

	public AddOrderResult addOrder(Map<String, String> params) throws KrakenDataException {
		ResultWrapper<AddOrderResult> resultWrapper;
		String result;
		try {
			result = krakenAPIClient.getPrivate("AddOrder", params);
		} catch (IOException e) {
			throw new KrakenDataException(e);
		}
		if (result != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(result);
			}
			resultWrapper = GSON.fromJson(result, ADD_ORDER_TYPE);
			return ADD_ORDER_CHECKER.checkErrors(resultWrapper);
		}
		return null;
	}

	public CancelOrderResult cancelOrder(Map<String, String> params) throws KrakenDataException {
		ResultWrapper<CancelOrderResult> resultWrapper;
		String result;
		try {
			result = krakenAPIClient.getPrivate("CancelOrder", params);
		} catch (IOException e) {
			throw new KrakenDataException(e);
		}
		if (result != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(result);
			}
			resultWrapper = GSON.fromJson(result, CANCEL_ORDER_TYPE);
			return CANCEL_ORDER_CHECKER.checkErrors(resultWrapper);
		}
		return null;
	}

}
