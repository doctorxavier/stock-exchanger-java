package com.arsios.exchange.api.kraken.service.impl;

public class KrakenDataException extends Exception {

	private static final long	serialVersionUID	= 3946200677692114626L;

	public KrakenDataException(Throwable e) {
		super(e);
	}
	
	public KrakenDataException(String message) {
		super(message);
	}
	
	public KrakenDataException(String message, Throwable e) {
		super(message, e);
	}
	
}
