package com.arsios.exchange.api.kraken.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Ticker implements Serializable {

	private static final long	serialVersionUID		= 6531605959062237254L;

	@SerializedName("a")
	private List<BigDecimal>	ask						= new ArrayList<BigDecimal>(0);

	@SerializedName("b")
	private List<BigDecimal>	bid						= new ArrayList<BigDecimal>(0);

	@SerializedName("c")
	private List<BigDecimal>	lastTradeClosed			= new ArrayList<BigDecimal>(0);

	@SerializedName("v")
	private List<BigDecimal>	volume					= new ArrayList<BigDecimal>(0);

	@SerializedName("p")
	private List<BigDecimal>	volWeightedAveragePrice	= new ArrayList<BigDecimal>(0);

	@SerializedName("t")
	private List<BigDecimal>	numTrades				= new ArrayList<BigDecimal>(0);

	@SerializedName("l")
	private List<BigDecimal>	lowPrice				= new ArrayList<BigDecimal>(0);

	@SerializedName("h")
	private List<BigDecimal>	highPrice				= new ArrayList<BigDecimal>(0);

	@SerializedName("o")
	private BigDecimal			openingPrice;

	public List<BigDecimal> getAsk() {
		return ask;
	}

	public void setAsk(List<BigDecimal> ask) {
		this.ask = ask;
	}

	public List<BigDecimal> getBid() {
		return bid;
	}

	public void setBid(List<BigDecimal> bid) {
		this.bid = bid;
	}

	public List<BigDecimal> getLastTradeClosed() {
		return lastTradeClosed;
	}

	public void setLastTradeClosed(List<BigDecimal> lastTradeClosed) {
		this.lastTradeClosed = lastTradeClosed;
	}

	public List<BigDecimal> getVolume() {
		return volume;
	}

	public void setVolume(List<BigDecimal> volume) {
		this.volume = volume;
	}

	public List<BigDecimal> getVolWeightedAveragePrice() {
		return volWeightedAveragePrice;
	}

	public void setVolWeightedAveragePrice(List<BigDecimal> volWeightedAveragePrice) {
		this.volWeightedAveragePrice = volWeightedAveragePrice;
	}

	public List<BigDecimal> getNumTrades() {
		return numTrades;
	}

	public void setNumTrades(List<BigDecimal> numTrades) {
		this.numTrades = numTrades;
	}

	public List<BigDecimal> getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(List<BigDecimal> lowPrice) {
		this.lowPrice = lowPrice;
	}

	public List<BigDecimal> getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(List<BigDecimal> highPrice) {
		this.highPrice = highPrice;
	}

	public BigDecimal getOpeningPrice() {
		return openingPrice;
	}

	public void setOpeningPrice(BigDecimal openingPrice) {
		this.openingPrice = openingPrice;
	}

}
