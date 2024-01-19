package com.arsios.exchange.api.kraken.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class TradeBalance implements Serializable {

	private static final long	serialVersionUID	= 2804067206124532208L;
	
	@SerializedName("tb")
	private Float	tradeBalance;
	
	@SerializedName("m")
	private Float	initalMargin;
	
	@SerializedName("n")
	private Float	unPositions;
	
	@SerializedName("c")
	private Float	costOpenPositions;
	
	@SerializedName("v")
	private Float	avgUnPositions;
	
	@SerializedName("e")
	private Float	equity;
	
	@SerializedName("mf")
	private Float	freeMargin;
	
	@SerializedName("ml")
	private Float	marginLevel;

	public Float getTradeBalance() {
		return tradeBalance;
	}

	public void setTradeBalance(Float tradeBalance) {
		this.tradeBalance = tradeBalance;
	}

	public Float getInitalMargin() {
		return initalMargin;
	}

	public void setInitalMargin(Float initalMargin) {
		this.initalMargin = initalMargin;
	}

	public Float getUnPositions() {
		return unPositions;
	}

	public void setUnPositions(Float unPositions) {
		this.unPositions = unPositions;
	}

	public Float getCostOpenPositions() {
		return costOpenPositions;
	}

	public void setCostOpenPositions(Float costOpenPositions) {
		this.costOpenPositions = costOpenPositions;
	}

	public Float getAvgUnPositions() {
		return avgUnPositions;
	}

	public void setAvgUnPositions(Float avgUnPositions) {
		this.avgUnPositions = avgUnPositions;
	}

	public Float getEquity() {
		return equity;
	}

	public void setEquity(Float equity) {
		this.equity = equity;
	}

	public Float getFreeMargin() {
		return freeMargin;
	}

	public void setFreeMargin(Float freeMargin) {
		this.freeMargin = freeMargin;
	}

	public Float getMarginLevel() {
		return marginLevel;
	}

	public void setMarginLevel(Float marginLevel) {
		this.marginLevel = marginLevel;
	}

}
