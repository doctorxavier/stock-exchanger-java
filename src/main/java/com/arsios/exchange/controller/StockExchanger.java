package com.arsios.exchange.controller;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.arsios.exchange.dao.mongodb.collection.ITickerDao;
import com.arsios.exchange.model.Balance;
import com.arsios.exchange.model.Order;
import com.arsios.exchange.model.Ticker;
import com.arsios.exchange.runtime.StockExchangerConfigure;
import com.arsios.exchange.service.DataException;
import com.arsios.exchange.service.StockExchangeService;
import com.arsios.exchange.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class StockExchanger {
	
	private static final Logger		LOGGER		= LoggerFactory.getLogger(StockExchanger.class);
	private static final Gson		GSON		= new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
	private static final BigDecimal ONEHUNDERT = new BigDecimal(100);
	private static final BigDecimal MAX_VALUE = new BigDecimal(Double.MAX_VALUE);
	private static final MathContext MATH_CONTEXT = MathContext.DECIMAL128;
	
	private StockExchangerConfigure configure;
	
	@Resource
	private ITickerDao			tickerDao;
	
	private BigDecimal feePercentage;
	private BigDecimal mfee;
	private BigDecimal nmfee;
	
	private BigDecimal profitPercentage;
	private BigDecimal pprofit;
	private BigDecimal nprofit;
	
	private BigDecimal orderAmountPercentage;
	
	private BigDecimal initialBalance;
	
	private BigDecimal lastBid;
	private BigDecimal lastAsk;
	
	private BigDecimal firstBid;
	private BigDecimal firstAsk;
	
	private BigDecimal stopLossPercentage;
	
	private Balance balance = new Balance();
	
	private Ticker lastTicker;
	
	private StockExchangeService stockExchangeService;
	
	public void init(StockExchangeService stockExchangeService, StockExchangerConfigure configure) throws DataException {
		this.stockExchangeService = stockExchangeService;
		this.configure = configure;
		
		if (configure.getFee() == null) {
			configure.setFee(stockExchangeService.getFee());
		}
		
		feePercentage = configure.getFee().divide(ONEHUNDERT, MATH_CONTEXT);
		mfee = BigDecimal.ONE.add(feePercentage);
		nmfee = BigDecimal.ONE.subtract(feePercentage);
		
		reset(configure);
	}
	
	public void reset(StockExchangerConfigure configure) throws DataException {
		lastTicker = null;
		
		if (configure.getBalance() != null) {
			balance = configure.getBalance();
		} else {
			balance = stockExchangeService.getBalance();
		}
		
		profitPercentage = configure.getProfit().divide(ONEHUNDERT, MATH_CONTEXT);
		pprofit = BigDecimal.ONE.add(profitPercentage.divide(ONEHUNDERT, MATH_CONTEXT)).add(feePercentage);
		nprofit = BigDecimal.ONE.subtract(profitPercentage.divide(ONEHUNDERT, MATH_CONTEXT)).subtract(feePercentage);
		
		orderAmountPercentage = BigDecimal.ONE.subtract(configure.getOrderAmount().divide(ONEHUNDERT, MATH_CONTEXT));
		
		initialBalance = null;
		
		lastBid = null;
		lastAsk = null;
		
		firstBid = null;
		firstAsk = null;
		
		stopLossPercentage = BigDecimal.ONE.subtract(configure.getStopLoss().divide(ONEHUNDERT, MATH_CONTEXT));
	}
	
	public void resetFee() {
		
		feePercentage = configure.getFee().divide(ONEHUNDERT, MATH_CONTEXT);
		mfee = BigDecimal.ONE.add(feePercentage);
		nmfee = BigDecimal.ONE.subtract(feePercentage);
		
		profitPercentage = configure.getProfit().divide(ONEHUNDERT, MATH_CONTEXT);
		pprofit = BigDecimal.ONE.add(profitPercentage.divide(ONEHUNDERT, MATH_CONTEXT)).add(feePercentage);
		nprofit = BigDecimal.ONE.subtract(profitPercentage.divide(ONEHUNDERT, MATH_CONTEXT)).subtract(feePercentage);
		
	}

	public Order execute(Ticker ticker) throws DataException {
		
		Order order = null;
		if (lastTicker != null) {
			
			BigDecimal amount = BigDecimal.ZERO;
			BigDecimal currBalance = BigDecimal.ZERO;
			
			BigDecimal mAsk = BigDecimal.valueOf(Utilities.formatBigDecimal(ticker.getAsk().multiply(mfee, MATH_CONTEXT).multiply(pprofit, MATH_CONTEXT), configure.getPricePrecision()));
			if (lastBid.compareTo(mAsk) > 0 && firstAsk.compareTo(mAsk) > 0) {
				//Bid Order
				amount = BigDecimal.valueOf(Utilities.formatBigDecimal(balance.getCurrency().multiply(orderAmountPercentage, MATH_CONTEXT).divide(ticker.getAsk(), MATH_CONTEXT).multiply(nmfee, MATH_CONTEXT), configure.getAmountPrecision()));
				if (amount.compareTo(configure.getMinOp()) > 0 && balance.getCurrency().divide(ticker.getAsk(), MATH_CONTEXT).compareTo(amount) > 0) {
					order = makeOrder(balance, amount, lastTicker.getAsk(), ticker.getAsk(), lastTicker.getBid(), ticker.getBid(), configure.getFee(), mfee, Order.BID);
					if (firstBid.compareTo(BigDecimal.ZERO) == 0) {
						firstBid = ticker.getAsk();
					}
					firstAsk = MAX_VALUE;
					lastAsk = BigDecimal.ZERO;
					lastBid = ticker.getAsk();
					currBalance = balance.getCurrency().add(balance.getValue().multiply(ticker.getAsk(), MATH_CONTEXT));
					if (initialBalance.multiply(stopLossPercentage, MATH_CONTEXT).compareTo(currBalance) > 0) {
						throw new DataException("Stop loss. Initial balance: " + initialBalance + ", balance: " + currBalance);
					}
					
					if (balance.getCurrency().compareTo(BigDecimal.ZERO) < 0) {
						throw new DataException("BTC balance negative.");
					}
					if (balance.getCurrency().compareTo(BigDecimal.ZERO) < 0) {
						throw new DataException("EUR Balance negative.");
					}
				}
			}
	
			BigDecimal mBid = BigDecimal.valueOf(Utilities.formatBigDecimal(ticker.getBid().multiply(nmfee, MATH_CONTEXT).multiply(nprofit, MATH_CONTEXT), configure.getPricePrecision()));
			if (lastAsk.compareTo(mBid) < 0 && firstBid.compareTo(mBid) < 0) {
				//Ask Order	
				amount = BigDecimal.valueOf(Utilities.formatBigDecimal(balance.getValue().multiply(orderAmountPercentage, MATH_CONTEXT).multiply(nmfee, MATH_CONTEXT), configure.getAmountPrecision()));
				if (amount.compareTo(configure.getMinOp()) > 0 && balance.getValue().compareTo(amount) > 0) {
					order = makeOrder(balance, amount, lastTicker.getAsk(), ticker.getAsk(), lastTicker.getBid(), ticker.getBid(), configure.getFee(), nmfee, Order.ASK);
					if (firstAsk.compareTo(MAX_VALUE) == 0) {
						firstAsk = ticker.getBid();
					}
					firstBid = BigDecimal.ZERO;
					lastBid = MAX_VALUE;
					lastAsk = ticker.getBid();
					currBalance = balance.getCurrency().add(balance.getValue().multiply(ticker.getAsk(), MATH_CONTEXT));
					if (initialBalance.multiply(stopLossPercentage, MATH_CONTEXT).compareTo(currBalance) > 0) {
						throw new DataException("Stop loss. Initial balance: " + initialBalance + ", balance: " + currBalance);
					}
					
					if (balance.getCurrency().compareTo(BigDecimal.ZERO) < 0) {
						throw new DataException("BTC balance negative.");
					}
					if (balance.getCurrency().compareTo(BigDecimal.ZERO) < 0) {
						throw new DataException("EUR Balance negative.");
					}
				}
			}
			
		} else {
			if (ticker.getAvg().compareTo(ticker.getLast()) > 0) {
				firstBid = lastBid = ticker.getAsk();
				firstAsk = MAX_VALUE;
				lastAsk = BigDecimal.ZERO;
			} else {
				firstAsk = lastAsk = ticker.getBid();
				firstBid = BigDecimal.ZERO;
				lastBid = MAX_VALUE;
			}
			initialBalance = balance.getCurrency().add(balance.getValue().multiply(ticker.getAsk(), MATH_CONTEXT));
		}
		lastTicker = ticker;
		
		return order;
	}
	
	public Order makeOrder(Balance balance, BigDecimal amount, BigDecimal lask, BigDecimal ask, BigDecimal lbid, BigDecimal bid, BigDecimal fee, BigDecimal mfee, Character type) throws DataException {
		Order order = new Order();
		
		BigDecimal price = BigDecimal.ZERO;
		BigDecimal cost = BigDecimal.ZERO;
		
		BigDecimal remoteFee = BigDecimal.ZERO;

		if (type.compareTo(Order.ASK) == 0) {
			
			price = BigDecimal.valueOf(Utilities.formatBigDecimal(bid.multiply(mfee, MATH_CONTEXT), configure.getPricePrecision()));
			cost = BigDecimal.valueOf(Utilities.formatBigDecimal(amount.multiply(price, MATH_CONTEXT), configure.getPricePrecision()));
			
			if (!configure.isDebug()) {
				order = stockExchangeService.makeAsk(amount);
			} else {
				order.setType(Order.ASK);
			}
			
			balance.setValue(balance.getValue().subtract(amount));
			balance.setCurrency(balance.getCurrency().add(cost));
		} else if (type.compareTo(Order.BID) == 0) {
			price = BigDecimal.valueOf(Utilities.formatBigDecimal(ask.multiply(mfee, MATH_CONTEXT), configure.getPricePrecision()));
			cost = BigDecimal.valueOf(Utilities.formatBigDecimal(amount.multiply(price, MATH_CONTEXT), configure.getPricePrecision()));
			
			if (!configure.isDebug()) {
				order = stockExchangeService.makeBid(amount);
			} else {
				order.setType(Order.BID);
			}
			
			balance.setValue(balance.getValue().add(amount));
			balance.setCurrency(balance.getCurrency().subtract(cost));
		} else {
			throw new DataException("The order is null.");
		}
		
		order.setBalance(BigDecimal.valueOf(Utilities.formatBigDecimal(balance.getCurrency().add(balance.getValue().multiply(ask, MATH_CONTEXT)), configure.getPricePrecision())));
		
		order.setAmount(amount);
		order.setCost(cost);
		order.setLask(lask);
		order.setAsk(ask);
		order.setLbid(lbid);
		order.setBid(bid);
		order.setPrice(price);
		order.setFee(fee);
		order.setMfee(mfee);
		order.setValue(balance.getValue());
		order.setCurrency(balance.getCurrency());
		
		order.setType(type);
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(GSON.toJson(order));
			
			if (!configure.isDebug()) {
				Order remoteOrder = stockExchangeService.getOrder(order.getId());
				Balance remoteBalance = stockExchangeService.getBalance();
				
				LOGGER.debug(GSON.toJson(remoteOrder));
				LOGGER.debug(GSON.toJson(remoteBalance));
			}
		}
		
		if (!configure.isDebug()) {
			remoteFee = stockExchangeService.getFee();
			if (fee.compareTo(remoteFee) != 0) {
				configure.setFee(remoteFee);
				resetFee();
			}
		}
		
		return order;
	}

}
