package com.arsios.exchange.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Balance implements Serializable {

	private static final long	serialVersionUID	= 2833842139992776146L;

	private BigDecimal			value;

	private BigDecimal			currency;

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getCurrency() {
		return currency;
	}

	public void setCurrency(BigDecimal currency) {
		this.currency = currency;
	}

}
