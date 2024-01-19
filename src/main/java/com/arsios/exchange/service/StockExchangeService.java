package com.arsios.exchange.service;

import java.math.BigDecimal;

import com.arsios.exchange.model.Balance;
import com.arsios.exchange.model.Order;
import com.arsios.exchange.model.Ticker;


public interface StockExchangeService {
	
	Ticker getTicker() throws DataException;

	Balance getBalance() throws DataException;
	
	BigDecimal getFee() throws DataException;
	
	Order makeBid(BigDecimal amount) throws DataException;
	
	Order makeAsk(BigDecimal amount) throws DataException;
	
	Order getOrder(String txid) throws DataException;
	
}
