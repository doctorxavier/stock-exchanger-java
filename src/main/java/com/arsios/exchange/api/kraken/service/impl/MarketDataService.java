package com.arsios.exchange.api.kraken.service.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arsios.exchange.api.kraken.client.IKrakenAPIClient;
import com.arsios.exchange.api.kraken.model.AssetPair;
import com.arsios.exchange.api.kraken.model.ResultWrapper;
import com.arsios.exchange.api.kraken.model.Ticker;
import com.arsios.exchange.api.kraken.service.IMarketDataService;
import com.arsios.exchange.api.kraken.utils.ResultWrapperChecker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Service("KrakenMarketDataService")
public class MarketDataService implements IMarketDataService {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(MarketDataService.class);
	private static final Gson	GSON	= new GsonBuilder().serializeNulls().setDateFormat("E, d MMM yy HH:mm:ss Z").create();
	
	private static final Type	ASSET_TICKER_TYPE	= new TypeToken<ResultWrapper<Map<String, Ticker>>>() { }.getType();
	private static final Type	ASSET_PAIRS_TYPE	= new TypeToken<ResultWrapper<Map<String, AssetPair>>>() { }.getType();

	private static final ResultWrapperChecker<Map<String, Ticker>>		ASSET_TICKER_CHECKER	= new ResultWrapperChecker<Map<String, Ticker>>();
	private static final ResultWrapperChecker<Map<String, AssetPair>>	ASSET_PAIRS_CHECKER		= new ResultWrapperChecker<Map<String, AssetPair>>();

	@Resource
	private IKrakenAPIClient	krakenAPIClient;

	public Map<String, AssetPair> getAssetPairs() throws KrakenDataException {
		return getAssetPairs(null);
	}

	public Map<String, AssetPair> getAssetPairs(Map<String, String> params) throws KrakenDataException {
		ResultWrapper<Map<String, AssetPair>> resultWrapper;
		String result;
		try {
			result = krakenAPIClient.getPublic("AssetPairs", params);
		} catch (IOException e) {
			throw new KrakenDataException(e);
		}
		if (result != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(result);
			}
			resultWrapper = GSON.fromJson(result, ASSET_PAIRS_TYPE);
			return ASSET_PAIRS_CHECKER.checkErrors(resultWrapper);
		}
		return null;
	}

	public Map<String, Ticker> getTicker(Map<String, String> params) throws KrakenDataException {
		ResultWrapper<Map<String, Ticker>> resultWrapper;

		String result = null;

		try {
			result = krakenAPIClient.getPublic("Ticker", params);
		} catch (IOException e) {
			throw new KrakenDataException(e);
		}

		if (result != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(result);
			}
			resultWrapper = GSON.fromJson(result, ASSET_TICKER_TYPE);
			return ASSET_TICKER_CHECKER.checkErrors(resultWrapper);
		}
		return null;
	}

}
