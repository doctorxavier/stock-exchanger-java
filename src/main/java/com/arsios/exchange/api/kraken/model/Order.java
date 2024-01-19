package com.arsios.exchange.api.kraken.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Order implements Serializable {

	private static final long	serialVersionUID	= 6658280585539719873L;

	private String				refid;
	private String				userref;
	private String				status;
	private String				reason;
	private BigDecimal			opentm;
	private BigDecimal			closetm;
	private BigDecimal			starttm;
	private BigDecimal			expiretm;
	private OrderDescr			descr;
	private BigDecimal			vol;

	@SerializedName("vol_exec")
	private BigDecimal			volExec;
	private BigDecimal			cost;
	private BigDecimal			fee;
	private BigDecimal			price;
	private BigDecimal			stopprice;
	private BigDecimal			limitprice;
	private String				misc;
	private String				oflags;
	private List<String>		trades				= new ArrayList<String>(0);

	public BigDecimal getStopprice() {
		return stopprice;
	}

	public void setStopprice(BigDecimal stopprice) {
		this.stopprice = stopprice;
	}

	public BigDecimal getLimitprice() {
		return limitprice;
	}

	public void setLimitprice(BigDecimal limitprice) {
		this.limitprice = limitprice;
	}

	public String getRefid() {
		return refid;
	}

	public void setRefid(String refid) {
		this.refid = refid;
	}

	public String getUserref() {
		return userref;
	}

	public void setUserref(String userref) {
		this.userref = userref;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public BigDecimal getOpentm() {
		return opentm;
	}

	public void setOpentm(BigDecimal opentm) {
		this.opentm = opentm;
	}

	public BigDecimal getClosetm() {
		return closetm;
	}

	public void setClosetm(BigDecimal closetm) {
		this.closetm = closetm;
	}

	public BigDecimal getStarttm() {
		return starttm;
	}

	public void setStarttm(BigDecimal starttm) {
		this.starttm = starttm;
	}

	public BigDecimal getExpiretm() {
		return expiretm;
	}

	public void setExpiretm(BigDecimal expiretm) {
		this.expiretm = expiretm;
	}

	public OrderDescr getDescr() {
		return descr;
	}

	public void setDescr(OrderDescr descr) {
		this.descr = descr;
	}

	public BigDecimal getVol() {
		return vol;
	}

	public void setVol(BigDecimal vol) {
		this.vol = vol;
	}

	public BigDecimal getVolExec() {
		return volExec;
	}

	public void setVolExec(BigDecimal volExec) {
		this.volExec = volExec;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getMisc() {
		return misc;
	}

	public void setMisc(String misc) {
		this.misc = misc;
	}

	public String getOflags() {
		return oflags;
	}

	public void setOflags(String oflags) {
		this.oflags = oflags;
	}

	public List<String> getTrades() {
		return trades;
	}

	public void setTrades(List<String> trades) {
		this.trades = trades;
	}

}
