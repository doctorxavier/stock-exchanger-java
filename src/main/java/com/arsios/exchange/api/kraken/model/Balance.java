package com.arsios.exchange.api.kraken.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

public class Balance implements Serializable {

	private static final long	serialVersionUID	= -6408508645833951133L;

	@SerializedName("ZEUR")
	private BigDecimal			eur;

	@SerializedName("XXBT")
	private BigDecimal			btc;

	@SerializedName("XLTC")
	private BigDecimal			ltc;

	public BigDecimal getEur() {
		return eur;
	}

	public void setEur(BigDecimal eur) {
		this.eur = eur;
	}

	public BigDecimal getBtc() {
		return btc;
	}

	public void setBtc(BigDecimal btc) {
		this.btc = btc;
	}

	public BigDecimal getLtc() {
		return ltc;
	}

	public void setLtc(BigDecimal ltc) {
		this.ltc = ltc;
	}

}
