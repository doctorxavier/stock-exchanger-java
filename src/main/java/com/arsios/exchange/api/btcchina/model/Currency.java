package com.arsios.exchange.api.btcchina.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

public class Currency implements Serializable {

	private static final long	serialVersionUID	= 5772791241194389355L;

	private String				currency;
	private String				symbol;
	private BigDecimal			amount;

	@SerializedName("amount_integer")
	private Integer				amountInteger;

	@SerializedName("amount_decimal")
	private Integer				amountDecimal;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getAmountInteger() {
		return amountInteger;
	}

	public void setAmountInteger(Integer amountInteger) {
		this.amountInteger = amountInteger;
	}

	public Integer getAmountDecimal() {
		return amountDecimal;
	}

	public void setAmountDecimal(Integer amountDecimal) {
		this.amountDecimal = amountDecimal;
	}

}
