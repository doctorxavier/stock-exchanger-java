package com.arsios.exchange.api.btcchina.service;

import java.math.BigDecimal;

import com.arsios.exchange.api.btcchina.service.impl.BTCChinaDataException;


public interface IUserTradingService {

	/*
	 * buyOrder2
	 * 
	 * Place a buy order. This method will return order id. 
	 * 
	 * The price in quote currency to buy 1 base currency. Max 2 decimals for BTC/CNY and LTC/CNY markets. 
	 * 4 decimals for LTC/BTC market. Market order is executed by setting price to 'null'.
	 * 
	 * The amount of LTC/BTC to buy. Supports 4 decimal places for BTC and 3 decimal places for LTC. 
	 * 
	 * Request:
	 * 		{"method":"buyOrder2","params":[500,1],"id":1}
	 * 		ex.: {"method":"buyOrder2","params":[null,1],"id":1}
	 * 
	 * Response:
	 * 		Returns order id if order placed successfully. 
	 * 
	 * 		{"result":12345,"id":"1"} 
	 * 
	 */
	
	Long buyOrder(BigDecimal price, BigDecimal amount, String market) throws BTCChinaDataException;
	
	/*
	 * sellOrder2
	 * 
	 * Place a buy order. This method will return order id. 
	 * 
	 * The price in quote currency to buy 1 base currency. Max 2 decimals for BTC/CNY and LTC/CNY markets. 
	 * 4 decimals for LTC/BTC market. Market order is executed by setting price to 'null'.
	 * 
	 * The amount of LTC/BTC to buy. Supports 4 decimal places for BTC and 3 decimal places for LTC. 
	 * 
	 * Request:
	 * 		{"method":"buyOrder2","params":[500,1],"id":1}
	 * 		ex.: {"method":"buyOrder2","params":[null,1],"id":1}
	 * 
	 * Response:
	 * 		Returns order id if order placed successfully. 
	 * 
	 * 		{"result":12345,"id":"1"} 
	 * 
	 */
	
	Long sellOrder(BigDecimal price, BigDecimal amount, String market) throws BTCChinaDataException;
	
}
