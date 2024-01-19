package com.arsios.exchange.service.kraken;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arsios.exchange.api.kraken.model.AddOrderResult;
import com.arsios.exchange.api.kraken.model.TradeVolume;
import com.arsios.exchange.api.kraken.service.IMarketDataService;
import com.arsios.exchange.api.kraken.service.IUserDataService;
import com.arsios.exchange.api.kraken.service.IUserTradingService;
import com.arsios.exchange.api.kraken.service.impl.KrakenDataException;
import com.arsios.exchange.model.Balance;
import com.arsios.exchange.model.Order;
import com.arsios.exchange.model.Ticker;
import com.arsios.exchange.service.DataException;
import com.arsios.exchange.service.StockExchangeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service("KrakenAPIService")
public class KrakenAPIService implements StockExchangeService {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(KrakenAPIService.class);
	private static final Gson	GSON	= new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
	
	@Resource(name = "KrakenMarketDataService")
	private IMarketDataService	marketDataService;

	@Resource(name = "KrakenUserDataService")
	private IUserDataService	userDataService;

	@Resource(name = "KrakenUserTradingService")
	private IUserTradingService	userTradingService;
	
	private String pair;
	
	@PostConstruct
	public void init() {
		this.pair = "XXBTZEUR";
	}
	
	public Balance getBalance() throws DataException {
		com.arsios.exchange.api.kraken.model.Balance krakenBalance;
		try {
			krakenBalance = userDataService.getBalance();
		} catch (KrakenDataException e) {
			throw new DataException(e.getMessage(), e);
		}
		
		if (krakenBalance != null) {
			Balance balance = new Balance();
			
			balance.setValue(krakenBalance.getBtc());
			balance.setCurrency(krakenBalance.getEur());
			
			return balance;
		} else {
			throw new DataException("Balance data is null.");
		}
	}
	
	public BigDecimal getFee() throws DataException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("pair", this.pair);
		
		TradeVolume tradeVolume;
		try {
			tradeVolume = userDataService.getTradeVolume(params);
		} catch (KrakenDataException e) {
			throw new DataException(e.getMessage(), e);
		}
		if (tradeVolume != null && tradeVolume.getFees() != null) {
			Map<String, com.arsios.exchange.api.kraken.model.Fee> fees = tradeVolume.getFees();
			if (fees != null && fees.containsKey(this.pair)) {
				com.arsios.exchange.api.kraken.model.Fee fee = fees.get(this.pair);
				if (fee.getFee() != null) {
					return fee.getFee();
				} else {
					throw new DataException("Can't retrieve current fee, fee float value is null.");
				}
			} else {
				throw new DataException("Can't retrieve current fee, pair not exists in result.");
			}
		} else {
			throw new DataException("Can't retrieve current fee, trade data is null.");
		}
	}
	
	public Ticker getTicker() throws DataException {

		Map<String, String> params = new HashMap<String, String>();
		params.put("pair", this.pair);
		
		Map<String, com.arsios.exchange.api.kraken.model.Ticker> krakenTickers;
		try {
			krakenTickers = marketDataService.getTicker(params);
		} catch (KrakenDataException e) {
			throw new DataException(e.getMessage(), e);
		}
		
		if (krakenTickers != null && krakenTickers.containsKey(this.pair)) {
			com.arsios.exchange.api.kraken.model.Ticker krakenTicker = krakenTickers.get(this.pair);
			if (krakenTicker != null) {
				Ticker ticker = new Ticker();

				if (krakenTicker.getAsk() != null && krakenTicker.getAsk().size() == 2) {
					ticker.setAsk(krakenTicker.getAsk().get(0));
				} else {
					throw new DataException("Wrong ask data.");
				}

				if (krakenTicker.getBid() != null && krakenTicker.getBid().size() == 2) {
					ticker.setBid(krakenTicker.getBid().get(0));
				} else {
					throw new DataException("Wrong bid data.");
				}

				if (krakenTicker.getHighPrice() != null && krakenTicker.getHighPrice().size() == 2) {
					ticker.setHigh(krakenTicker.getHighPrice().get(0));
				} else {
					throw new DataException("Wrong high price data.");
				}

				if (krakenTicker.getLastTradeClosed() != null && krakenTicker.getLastTradeClosed().size() == 2) {
					ticker.setLast(krakenTicker.getLastTradeClosed().get(0));
				} else {
					throw new DataException("Wrong last trade data.");
				}

				if (krakenTicker.getLowPrice() != null && krakenTicker.getLowPrice().size() == 2) {
					ticker.setLow(krakenTicker.getLowPrice().get(0));
				} else {
					throw new DataException("Wrong low price data.");
				}
				
				if (krakenTicker.getVolWeightedAveragePrice() != null && krakenTicker.getVolWeightedAveragePrice().size() == 2) {
					ticker.setAvg(krakenTicker.getVolWeightedAveragePrice().get(0));
				} else {
					throw new DataException("Wrong avg data.");
				}

				if (krakenTicker.getNumTrades() != null && krakenTicker.getNumTrades().size() == 2) {
					ticker.setTrades(krakenTicker.getNumTrades().get(0));
				} else {
					throw new DataException("Wrong trades data.");
				}

				if (krakenTicker.getVolume() != null && krakenTicker.getVolume().size() == 2) {
					ticker.setVolume(krakenTicker.getVolume().get(0));
				} else {
					throw new DataException("Wrong volume data.");
				}

				ticker.setOpen(krakenTicker.getOpeningPrice());
				
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(GSON.toJson(ticker));
				}
				
				return ticker;

			} else {
				throw new DataException("Ticker data is null.");
			}
		} else {
			throw new DataException("Pair " + this.pair + "not exists.");
		}
	}
	
	public Order makeBid(BigDecimal amount) throws DataException {
		return this.makeOrder(amount, Order.BID);
	}
	
	public Order makeAsk(BigDecimal amount) throws DataException {
		return this.makeOrder(amount, Order.ASK);
	}
	
	private Order makeOrder(BigDecimal amount, Character type) throws DataException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("pair", this.pair);
		params.put("type",  Order.TYPE.get(type));
		params.put("ordertype", "market");
		params.put("volume", amount.toPlainString());
		
		AddOrderResult addOrderResult;
		try {
			addOrderResult = userTradingService.addOrder(params);
		} catch (KrakenDataException e) {
			throw new DataException(e.getMessage(), e);
		}
		if (addOrderResult == null) {
			throw new DataException("Error doing order.");
		}
		if (addOrderResult.getTxid().size() == 0 || addOrderResult.getTxid().size() > 1) {
			throw new DataException("Error doing order, many ids in result.");
		}
		
		Order order = new Order();
		
		order.setDescription(addOrderResult.getDescr().getOrder());
		order.setId(addOrderResult.getTxid().get(0));
		
		order.setType(type);
		order.setPair(this.pair);
		
		return order;
	}
	
	public Order getOrder(String txid) throws DataException  {
		Map<String, String> params = new HashMap<String, String>();
		params.put("txid", txid);
		
		Map<String, com.arsios.exchange.api.kraken.model.Order> orders;
		try {
			orders = userDataService.queryOrders(params);
		} catch (KrakenDataException e) {
			throw new DataException(e.getMessage(), e);
		}
		
		if (orders.containsKey(txid)) {
			com.arsios.exchange.api.kraken.model.Order orderResult = orders.get(txid);
			Order order = new Order();
			order.setAmount(orderResult.getVol());
			order.setType(Order.TYPE_S.get(orderResult.getDescr().getType()));
			if (order.getType() == Order.BID) {
				order.setPrice(orderResult.getPrice().add(orderResult.getFee()));
				order.setAsk(orderResult.getPrice());
				order.setCost(orderResult.getCost().add(orderResult.getFee()));
			} else if (order.getType() == Order.ASK) {
				order.setPrice(orderResult.getPrice().subtract(orderResult.getFee()));
				order.setBid(orderResult.getPrice());
				order.setCost(orderResult.getCost().subtract(orderResult.getFee()));
			}
			order.setFee(orderResult.getFee());
			order.setDescription(orderResult.getDescr().getOrder());
			order.setId(txid);
			order.setPair(orderResult.getDescr().getPair());
			
			return order;
		}
		
		return null;
	}

}
