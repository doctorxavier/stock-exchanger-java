package com.arsios.exchange.service;

public class DataException extends Exception {

	private static final long	serialVersionUID	= -7089387292940710733L;

	public DataException(String message) {
		super(message);
	}

	public DataException(String message, Throwable e) {
		super(message, e);
	}
	
}
