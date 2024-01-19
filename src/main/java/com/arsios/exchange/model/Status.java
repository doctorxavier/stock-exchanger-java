package com.arsios.exchange.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Status implements Serializable {

	private static final long	serialVersionUID	= -8245891346457121693L;

	private BigDecimal			profit;
	private BigDecimal			initBalance;
	private BigDecimal			endBalance;
	private BigDecimal			amount;
	private BigDecimal			currency;
	private BigDecimal			value;
	private BigDecimal			charge;
	private Integer				orders;

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public BigDecimal getInitBalance() {
		return initBalance;
	}

	public void setInitBalance(BigDecimal initBalance) {
		this.initBalance = initBalance;
	}

	public BigDecimal getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getCurrency() {
		return currency;
	}

	public void setCurrency(BigDecimal currency) {
		this.currency = currency;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getCharge() {
		return charge;
	}

	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}

}
