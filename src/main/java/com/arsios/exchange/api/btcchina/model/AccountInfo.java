package com.arsios.exchange.api.btcchina.model;

import java.io.Serializable;

public class AccountInfo implements Serializable {

	private static final long	serialVersionUID	= 4086137781224358428L;

	private Balance				balance;

	public Balance getBalance() {
		return balance;
	}

	public void setBalance(Balance balance) {
		this.balance = balance;
	}

}
