package com.arsios.exchange.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Ticker implements Serializable {

	private static final long	serialVersionUID	= 2224251267944614922L;

	private Date				created;

	private BigDecimal				ask;

	private BigDecimal				bid;

	private BigDecimal				last;

	private BigDecimal				volume;

	private BigDecimal				avg;

	private BigDecimal				trades;

	private BigDecimal				low;

	private BigDecimal				high;

	private BigDecimal				open;

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public BigDecimal getAsk() {
		return ask;
	}

	public void setAsk(BigDecimal ask) {
		this.ask = ask;
	}

	public BigDecimal getBid() {
		return bid;
	}

	public void setBid(BigDecimal bid) {
		this.bid = bid;
	}

	public BigDecimal getLast() {
		return last;
	}

	public void setLast(BigDecimal last) {
		this.last = last;
	}
	
	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getAvg() {
		return avg;
	}

	public void setAvg(BigDecimal avg) {
		this.avg = avg;
	}

	public BigDecimal getTrades() {
		return trades;
	}

	public void setTrades(BigDecimal trades) {
		this.trades = trades;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.ask, this.bid, this.last, this.volume, this.avg, this.trades, this.low, this.high, this.open);
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		final Ticker ticker = (Ticker) object;
		return Objects.equals(this.ask, ticker.getAsk()) 
				&& Objects.equals(this.avg, ticker.getAvg()) 
				&& Objects.equals(this.bid, ticker.getBid()) 
				&& Objects.equals(this.high, ticker.getHigh()) 
				&& Objects.equals(this.last, ticker.getLast())
				&& Objects.equals(this.low, ticker.getLow()) 
				&& Objects.equals(this.open, ticker.getOpen()) 
				&& Objects.equals(this.trades, ticker.getTrades())
				&& Objects.equals(this.volume, ticker.getVolume());
	}

}
