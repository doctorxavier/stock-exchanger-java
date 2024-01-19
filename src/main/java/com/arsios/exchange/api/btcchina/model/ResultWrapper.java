package com.arsios.exchange.api.btcchina.model;

import java.io.Serializable;

public class ResultWrapper<T> implements Serializable {

	private static final long	serialVersionUID	= -6700599825310150789L;

	private Error				error;
	private T					result;
	private Long				id;

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

}
