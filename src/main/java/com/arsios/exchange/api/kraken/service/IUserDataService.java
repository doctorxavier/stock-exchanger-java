package com.arsios.exchange.api.kraken.service;

import java.util.Map;

import com.arsios.exchange.api.kraken.model.Balance;
import com.arsios.exchange.api.kraken.model.Order;
import com.arsios.exchange.api.kraken.model.TradeVolume;
import com.arsios.exchange.api.kraken.service.impl.KrakenDataException;

public interface IUserDataService {

	/*
	 * 
	 * Result: array of asset names and balance amount
	 * 
	 */
	Balance getBalance() throws KrakenDataException;
	
	/*
	 *	Input:
	 * 
	 * 	trades = whether or not to include trades in output (optional.  default = false)
	 * 	userref = restrict results to given user reference id (optional)
	 * 	txid = comma delimited list of transaction ids to query info about (20 maximum)
	 * 
	 * 	Result: associative array of orders info
	 * 
	 * 	<order_txid> = order info.  See Get open orders/Get closed orders
	 * 
	 */
	Map<String, Order> queryOrders(Map<String, String> params) throws KrakenDataException;
	
	/*
	 *	Input:
	 *
	 *	pair = comma delimited list of asset pairs to get fee info on (optional)
	 *
	 *	Result: associative array
	 *
	 *	currency = volume currency
	 *	volume = current discount volume
	 *	fees = array of asset pairs and fee tier info (if requested)
	 *    	fee = current fee in percent
	 *    	minfee = minimum fee for pair (if not fixed fee)
	 *    	maxfee = maximum fee for pair (if not fixed fee)
	 *    	nextfee = next tier's fee for pair (if not fixed fee.  nil if at lowest fee tier)
	 *    	nextvolume = volume level of next tier (if not fixed fee.  nil if at lowest fee tier)
	 *    	tiervolume = volume level of current tier (if not fixed fee.  nil if at lowest fee tier)
	 * 
	 */
	TradeVolume getTradeVolume() throws KrakenDataException;
	
	TradeVolume getTradeVolume(Map<String, String> params) throws KrakenDataException;
	
}
