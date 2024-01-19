package com.arsios.exchange.api.kraken.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Fee implements Serializable {

	private static final long	serialVersionUID	= 1515077760285576006L;

	private BigDecimal			fee;
	private BigDecimal			minfee;
	private BigDecimal			maxfee;
	private BigDecimal			nextfee;
	private BigDecimal			nextvolume;
	private BigDecimal			tiervolume;

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getMinfee() {
		return minfee;
	}

	public void setMinfee(BigDecimal minfee) {
		this.minfee = minfee;
	}

	public BigDecimal getMaxfee() {
		return maxfee;
	}

	public void setMaxfee(BigDecimal maxfee) {
		this.maxfee = maxfee;
	}

	public BigDecimal getNextfee() {
		return nextfee;
	}

	public void setNextfee(BigDecimal nextfee) {
		this.nextfee = nextfee;
	}

	public BigDecimal getNextvolume() {
		return nextvolume;
	}

	public void setNextvolume(BigDecimal nextvolume) {
		this.nextvolume = nextvolume;
	}

	public BigDecimal getTiervolume() {
		return tiervolume;
	}

	public void setTiervolume(BigDecimal tiervolume) {
		this.tiervolume = tiervolume;
	}

}
