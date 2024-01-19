package com.arsios.exchange.api.kraken.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TradeVolume implements Serializable {

	private static final long	serialVersionUID	= 4117662908704117427L;

	private String				currency;
	private Float				volume;
	private Map<String, Fee>	fees				= new HashMap<String, Fee>();

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Float getVolume() {
		return volume;
	}

	public void setVolume(Float volume) {
		this.volume = volume;
	}

	public Map<String, Fee> getFees() {
		return fees;
	}

	public void setFees(Map<String, Fee> fees) {
		this.fees = fees;
	}

}
