package com.arsios.exchange.api.kraken.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ClosedOrders implements Serializable {

	private static final long	serialVersionUID	= 5415548855990545123L;

	private Map<String, Order>	closed				= new HashMap<String, Order>();

	public Map<String, Order> getClosed() {
		return closed;
	}

	public void setClosed(Map<String, Order> closed) {
		this.closed = closed;
	}

}
