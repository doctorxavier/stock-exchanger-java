package com.arsios.exchange.api.kraken.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CancelOrderResult implements Serializable {

	private static final long	serialVersionUID	= -998544831139657417L;

	private Integer				count;
	private List<String>		pending				= new ArrayList<String>(0);

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<String> getPending() {
		return pending;
	}

	public void setPending(List<String> pending) {
		this.pending = pending;
	}

}
