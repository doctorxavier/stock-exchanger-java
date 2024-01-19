package com.arsios.exchange.api.kraken.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class OHLC implements Serializable {

	private static final long	serialVersionUID	= -8812462071987532859L;
	
	@SerializedName("XXBTZEUR")
	private List<List<BigDecimal>> btcEur = new ArrayList<List<BigDecimal>>(0);
	private Long last;
	
	public List<List<BigDecimal>> getBtcEur() {
		return btcEur;
	}
	
	public void setBtcEur(List<List<BigDecimal>> btcEur) {
		this.btcEur = btcEur;
	}
	
	public Long getLast() {
		return last;
	}
	
	public void setLast(Long last) {
		this.last = last;
	}
	
}
