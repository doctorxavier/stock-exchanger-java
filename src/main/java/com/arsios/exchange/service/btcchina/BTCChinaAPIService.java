package com.arsios.exchange.service.btcchina;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arsios.exchange.api.btcchina.service.IUserDataService;
import com.arsios.exchange.api.btcchina.service.IUserTradingService;
import com.arsios.exchange.api.btcchina.service.impl.BTCChinaDataException;
import com.arsios.exchange.model.Balance;
import com.arsios.exchange.model.Order;
import com.arsios.exchange.model.Ticker;
import com.arsios.exchange.service.DataException;
import com.arsios.exchange.service.StockExchangeService;

@Service("BTCChinaAPIService")
public class BTCChinaAPIService implements StockExchangeService {
	
	private static final Logger	LOGGER	= LoggerFactory.getLogger(BTCChinaAPIService.class);
	
	@Resource(name = "BTCChinaUserDataService")
	private IUserDataService	userDataService;

	@Resource(name = "BTCChinaUserTradingService")
	private IUserTradingService	userTradingService;
	
	public Balance getBalance() throws DataException {
		com.arsios.exchange.api.btcchina.model.Balance btcchinaBalance;
		try {
			btcchinaBalance = userDataService.getBalance();
		} catch (BTCChinaDataException e) {
			throw new DataException(e.getMessage(), e);
		}
		
		if (btcchinaBalance != null) {
			Balance balance = new Balance();
			
			balance.setValue(btcchinaBalance.getBtc().getAmount());
			balance.setCurrency(btcchinaBalance.getCny().getAmount());
			
			return balance;
		} else {
			throw new DataException("Balance data is null.");
		}
	}
	
	public Order makeBid(BigDecimal amount) throws DataException {
		com.arsios.exchange.api.btcchina.model.Order btcchinaOrder;
		try {
			Long txid = userTradingService.buyOrder(null, amount, null);
			btcchinaOrder = userDataService.getOrder(txid);
		} catch (BTCChinaDataException e) {
			throw new DataException(e.getMessage(), e);
		}
		
		if (btcchinaOrder != null) {
			Order order = new Order();
			
			if (btcchinaOrder.getAmount().compareTo(btcchinaOrder.getAmountOriginal()) < 0) {
				LOGGER.warn("This order has been partially filled.");
			}
	
			order.setId(btcchinaOrder.getId().toString());
			order.setAmount(btcchinaOrder.getAmountOriginal());
			order.setAsk(btcchinaOrder.getPrice());
			order.setCreated(new Date(TimeUnit.SECONDS.toMillis(btcchinaOrder.getDate())));
			order.setStatus(btcchinaOrder.getStatus());
			order.setType(Order.TYPE_N.get(btcchinaOrder.getType()));
			
			return order;
		} else {
			throw new DataException("Error doing order. The order has not found.");
		}
	}
	
	public Order makeAsk(BigDecimal amount) throws DataException {
		Long txid;
		try {
			txid = userTradingService.sellOrder(null, amount, null);
		} catch (BTCChinaDataException e) {
			throw new DataException(e.getMessage(), e);
		}
		Order order = getOrder(txid.toString());
		if (order == null) {
			throw new DataException("Error doing order. The order has not found.");
		}
		return order;
	}

	@Override
	public Ticker getTicker() throws DataException {
		throw new DataException("Not implemented.");
	}

	@Override
	public BigDecimal getFee() throws DataException {
		return BigDecimal.ZERO;
	}

	@Override
	public Order getOrder(String txid) throws DataException {
		com.arsios.exchange.api.btcchina.model.Order btcchinaOrder = null;
		try {
			btcchinaOrder = userDataService.getOrder(Long.valueOf(txid));
			if (btcchinaOrder != null) {
				Order order = new Order();
				
				if (btcchinaOrder.getAmount().compareTo(btcchinaOrder.getAmountOriginal()) < 0) {
					LOGGER.warn("This order has been partially filled.");
				}
		
				order.setId(btcchinaOrder.getId().toString());
				order.setAmount(btcchinaOrder.getAmountOriginal());
				order.setBid(btcchinaOrder.getPrice());
				order.setCreated(new Date(TimeUnit.SECONDS.toMillis(btcchinaOrder.getDate())));
				order.setStatus(btcchinaOrder.getStatus());
				order.setType(Order.TYPE_N.get(btcchinaOrder.getType()));
				
				return order;
			}
		} catch (NumberFormatException e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
		} catch (BTCChinaDataException e) {
			throw new DataException("Error quering order.");
		}
		return null;
	}

}
