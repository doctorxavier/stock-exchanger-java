package com.arsios.exchange.api.kraken.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Trade implements Serializable {

	private static final long	serialVersionUID	= 6157845320315661732L;

	private String				ordertxid;
	private String				pair;
	private BigDecimal			time;
	private String				type;
	private String				ordertype;
	private Float				price;
	private Float				cost;
	private Float				fee;
	private Float				vol;
	private Float				margin;
	private String				misc;

	public String getOrdertxid() {
		return ordertxid;
	}

	public void setOrdertxid(String ordertxid) {
		this.ordertxid = ordertxid;
	}

	public String getPair() {
		return pair;
	}

	public void setPair(String pair) {
		this.pair = pair;
	}

	public BigDecimal getTime() {
		return time;
	}

	public void setTime(BigDecimal time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

	public Float getFee() {
		return fee;
	}

	public void setFee(Float fee) {
		this.fee = fee;
	}

	public Float getVol() {
		return vol;
	}

	public void setVol(Float vol) {
		this.vol = vol;
	}

	public Float getMargin() {
		return margin;
	}

	public void setMargin(Float margin) {
		this.margin = margin;
	}

	public String getMisc() {
		return misc;
	}

	public void setMisc(String misc) {
		this.misc = misc;
	}

}
