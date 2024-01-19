package com.arsios.exchange.api.kraken.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TradesHistory implements Serializable {

	private static final long	serialVersionUID	= 751435899131415698L;

	private Map<String, Trade>		trades				= new HashMap<String, Trade>(0);
	private Integer				count;

	public Map<String, Trade> getTrades() {
		return trades;
	}

	public void setTrades(Map<String, Trade> trades) {
		this.trades = trades;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
