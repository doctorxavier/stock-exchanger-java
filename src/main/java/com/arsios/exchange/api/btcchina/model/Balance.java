package com.arsios.exchange.api.btcchina.model;

import java.io.Serializable;

public class Balance implements Serializable {

	private static final long	serialVersionUID	= 7167064376939745532L;
	private Currency			btc;
	private Currency			cny;

	public Currency getBtc() {
		return btc;
	}

	public void setBtc(Currency btc) {
		this.btc = btc;
	}

	public Currency getCny() {
		return cny;
	}

	public void setCny(Currency cny) {
		this.cny = cny;
	}

}
