package com.arsios.exchange.api.kraken.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class OpenOrders implements Serializable {

	private static final long	serialVersionUID	= 1964827700354194206L;

	private Map<String, Order>	open				= new HashMap<String, Order>();

	public Map<String, Order> getOpen() {
		return open;
	}

	public void setOpen(Map<String, Order> open) {
		this.open = open;
	}

}
