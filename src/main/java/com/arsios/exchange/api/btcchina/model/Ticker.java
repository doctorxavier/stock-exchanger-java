package com.arsios.exchange.api.btcchina.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

public class Ticker implements Serializable {

	private static final long	serialVersionUID	= 6743951735930262194L;

	private Long				date;
	private String				market;
	private BigDecimal			high;
	private BigDecimal			vol;
	private BigDecimal			last;
	private BigDecimal			low;
	private BigDecimal			buy;
	private BigDecimal			sell;
	private BigDecimal			vwap;
	private BigDecimal			open;

	@SerializedName("prev_close")
	private BigDecimal			prevClose;

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getVol() {
		return vol;
	}

	public void setVol(BigDecimal vol) {
		this.vol = vol;
	}

	public BigDecimal getLast() {
		return last;
	}

	public void setLast(BigDecimal last) {
		this.last = last;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getBuy() {
		return buy;
	}

	public void setBuy(BigDecimal buy) {
		this.buy = buy;
	}

	public BigDecimal getSell() {
		return sell;
	}

	public void setSell(BigDecimal sell) {
		this.sell = sell;
	}

	public BigDecimal getVwap() {
		return vwap;
	}

	public void setVwap(BigDecimal vwap) {
		this.vwap = vwap;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getPrevClose() {
		return prevClose;
	}

	public void setPrevClose(BigDecimal prevClose) {
		this.prevClose = prevClose;
	}

}
