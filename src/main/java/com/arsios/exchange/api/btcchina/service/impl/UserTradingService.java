package com.arsios.exchange.api.btcchina.service.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arsios.exchange.api.btcchina.client.IBTCChinaAPIClient;
import com.arsios.exchange.api.btcchina.model.ResultWrapper;
import com.arsios.exchange.api.btcchina.service.IUserTradingService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Service("BTCChinaUserTradingService")
public class UserTradingService implements IUserTradingService {
	
	private static final Logger	LOGGER		= LoggerFactory.getLogger(UserTradingService.class);
	private static final Gson	GSON		= new GsonBuilder().create();
	private static final Type	ORDER_TYPE	= new TypeToken<ResultWrapper<Long>>() { }.getType();

	@Resource
	private IBTCChinaAPIClient	bTCChinaAPIClient;
	
	@Override
	public Long buyOrder(BigDecimal price, BigDecimal amount, String market) throws BTCChinaDataException {
		ResultWrapper<Long> resultWrapper = new ResultWrapper<Long>();
		List<Object> params = new ArrayList<Object>(0);
		params.add(price);
		params.add(amount);
		params.add(market);
		
		String result = null;
		
		try {
			result = bTCChinaAPIClient.execute("buyOrder2", params);
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
			return resultWrapper.getResult();
		}
		return null;
	}

	@Override
	public Long sellOrder(BigDecimal price, BigDecimal amount, String market) throws BTCChinaDataException {
		ResultWrapper<Long> resultWrapper = new ResultWrapper<Long>();
		List<Object> params = new ArrayList<Object>(0);
		params.add(price);
		params.add(amount);
		params.add(market);

		String result;
		try {
			result = bTCChinaAPIClient.execute("sellOrder2", params);
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
			return resultWrapper.getResult();
		}
		return null;
	}

}
