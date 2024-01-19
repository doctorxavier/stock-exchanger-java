package com.arsios.exchange.api.kraken.service;

import java.util.Map;

import com.arsios.exchange.api.kraken.model.AssetPair;
import com.arsios.exchange.api.kraken.model.Ticker;
import com.arsios.exchange.api.kraken.service.impl.KrakenDataException;

public interface IMarketDataService {

	/*
	 *	Input:
	 *
	 *	pair = comma delimited list of asset pairs to get info on
	 *
	 *	Result: array of pair names and their ticker info
	 *
	 *	<pair_name> = pair name
     *		a = ask array(<price>, <lot volume>),
     *		b = bid array(<price>, <lot volume>),
     *		c = last trade closed array(<price>, <lot volume>),
     *		v = volume array(<today>, <last 24 hours>),
     *		p = volume weighted average price array(<today>, <last 24 hours>),
     *		t = number of trades array(<today>, <last 24 hours>),
     *		l = low array(<today>, <last 24 hours>),
     *		h = high array(<today>, <last 24 hours>),
     *		o = today's opening price
	 * 
	 * 	Note: 
	 * 		Today's prices start at 00:00:00 UTC
	 * 
	 */
	Map<String, Ticker> getTicker(Map<String, String> params) throws KrakenDataException;
	
	/*
	 *	Input: 
	 * 
	 *	info = info to retrieve (optional):
	 *    	info = all info (default)
	 *    	leverage = leverage info
	 *    	fees = fees schedule
	 *    	margin = margin info
	 *	pair = comma delimited list of asset pairs to get info on (optional.  default = all)
	 *
	 *	Result: array of pair names and their info
	 *
	 *	<pair_name> = pair name
     *		altname = alternate pair name
     *		aclass_base = asset class of base component
     *		base = asset id of base component
     *		aclass_quote = asset class of quote component
     *		quote = asset id of quote component
     *		lot = volume lot size
     *		pair_decimals = scaling decimal places for pair
     *		lot_decimals = scaling decimal places for volume
     *		lot_multiplier = amount to multiply lot volume by to get currency volume
     *		leverage = array of leverage amounts available
     *		fees = fee schedule array in [volume, percent fee] tuples
     *		fee_volume_currency = volume discount currency
     *		margin_call = margin call level
     *		margin_stop = stop-out/liquidation margin level
	 *
	 */
	Map<String, AssetPair> getAssetPairs() throws KrakenDataException;
	
	Map<String, AssetPair> getAssetPairs(Map<String, String> params) throws KrakenDataException;
}
