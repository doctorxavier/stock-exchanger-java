package com.arsios.exchange.api.btcchina.service;

import com.arsios.exchange.api.btcchina.model.Balance;
import com.arsios.exchange.api.btcchina.model.Order;
import com.arsios.exchange.api.btcchina.service.impl.BTCChinaDataException;


public interface IUserDataService {
	
	/* 
	 * getAccountInfo
	 * 
	 * Get stored account information and user balance. 
	 * 
	 * Request:
	 * 		Could be “all”,”balance”,”frozen”or “profile”, default to “all”.
	 * 
	 *  	{"method":"getAccountInfo","params":["balance"],"id":1}
	 *  	ex.:	{"method":"getAccountInfo","params":["balance"],"id":1}
	 *  
	 *  Response:
	 *  	Returns objects profile, balance and frozen
	 *  
	 *		{
	 *		"result": {
	 *			"profile": {
	 *		    	"username": "btc",
	 *		      	"trade_password_enabled": true,
	 *		      	"otp_enabled": true,
	 *		      	"trade_fee": 0,
	 *		      	"trade_fee_cnyltc": 0,
	 *		      	"trade_fee_btcltc": 0,
	 *		      	"daily_btc_limit": 10,
	 *		      	"daily_ltc_limit": 300,
	 *		      	"btc_deposit_address": "123myZyM9jBYGw5EB3wWmfgJ4Mvqnu7gEu",
	 *		      	"btc_withdrawal_address": "123GzXJnfugniyy7ZDw3hSjkm4tHPHzHba",
	 *		      	"ltc_deposit_address": "L12ysdcsNS3ZksRrVWMSoHjJgcm5VQn2Tc",
	 *		      	"ltc_withdrawal_address": "L23GzXJnfugniyy7ZDw3hSjkm4tHPHzHba",
	 *		      	"api_key_permission": 3
	 *		   	},
	 *		    "balance": {
	 *		      	"btc": {
	 *		        	"currency": "BTC",
	 *		        	"symbol": "\u0e3f",
	 *		        	"amount": "100.00000000",
	 *		        	"amount_integer": "10000000000",
	 *		       	 	"amount_decimal": 8
	 *		      	},
	 *		      	"ltc": {
	 *		        	"currency": "LTC",
	 *		        	"symbol": "\u0141",
	 *		        	"amount": "0.00000000",
	 *		        	"amount_integer": "0",
	 *		        	"amount_decimal": 8
	 *		      	},
	 *		      	"cny": {
	 *		        	"currency": "CNY",
	 *		        	"symbol": "\u00a5",
	 *		        	"amount": "50000.00000",
	 *		        	"amount_integer": "5000000000",
	 *		        	"amount_decimal": 5
	 *		      	}
	 *		    },
	 *		    "frozen": {
	 *		      	"btc": {
	 *		        	"currency": "BTC",
	 *		        	"symbol": "\u0e3f",
	 *		        	"amount": "0.00000000",
	 *		        	"amount_integer": "0",
	 *		        	"amount_decimal": 8
	 *		      	},
	 *		      	"ltc": {
	 *		        	"currency": "LTC",
	 *		        	"symbol": "\u0141",
	 *		        	"amount": "0.00000000",
	 *		        	"amount_integer": "0",
	 *		        	"amount_decimal": 8
	 *		      	},
	 *		      	"cny": {
	 *		        	"currency": "CNY",
	 *		        	"symbol": "\u00a5",
	 *		        	"amount": "0.00000",
	 *		        	"amount_integer": "0",
	 *		        	"amount_decimal": 5
	 *		      	}
	 *		    }
	 *		},
	 *		"id": "1"
	 *		}
	 */
	Balance getBalance() throws BTCChinaDataException;
	
	/*
	 * getOrder
	 * 
	 * Get an order, including its status. When “withdetail” parameter is set to true, all the trade details for this order will be included in the response. 
	 * 
	 * Request:
	 * 		{"method":"getOrder","params":[2],"id":1}
	 * 		ex.: {"method":"getOrder","params":[2,"BTCCNY",true],"id":1}
	 * 
	 * Response:
	 * 
	 * 		{
	 * 		"result":{
	 *  		"order":{
	 *  		"id":2,
	 *  		"type":"ask",
	 *  		"price":"46.84",
	 *  		"currency":"CNY",
	 *  		"amount":"0.00000000",
	 *  		"amount_original":"3.18400000",
	 *  		"date":1406860694,
	 *  		"status":"closed",
	 *  		"details":[{
	 *       		"dateline":"1406860696",
	 *         		"price":"46.84",
	 *         		"amount":3.184}]
	 *   	}},
	 *  	"id":"1"
	 *  	}
	 *
	 */
	 
	Order getOrder(Long txid) throws BTCChinaDataException;
	
}
