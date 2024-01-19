package com.arsios.exchange.api.btcchina.service.impl;

public class BTCChinaDataException extends Exception {

	private static final long	serialVersionUID	= -4123265367871897058L;
	
	public BTCChinaDataException(Throwable e) {
		super(e);
	}

	public BTCChinaDataException(String message) {
		super(message);
	}

	public BTCChinaDataException(String message, Throwable e) {
		super(message, e);
	}

}
