package com.arsios.exchange.api.kraken.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDescr implements Serializable {

	private static final long	serialVersionUID	= 8075892553097465433L;

	private String				pair;
	private String				type;
	private String				ordertype;
	private BigDecimal			price;
	private BigDecimal			price2;
	private String				leverage;
	private String				position;
	private String				order;
	private String				close;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}

	public String getPair() {
		return pair;
	}

	public void setPair(String pair) {
		this.pair = pair;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPrice2() {
		return price2;
	}

	public void setPrice2(BigDecimal price2) {
		this.price2 = price2;
	}

	public String getLeverage() {
		return leverage;
	}

	public void setLeverage(String leverage) {
		this.leverage = leverage;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
