package com.arsios.exchange.api.kraken.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultWrapper<T> implements Serializable {

	private static final long	serialVersionUID	= -2444042226082472115L;

	private List<String>		error				= new ArrayList<String>(0);
	private T					result;

	public List<String> getError() {
		return error;
	}

	public void setError(List<String> error) {
		this.error = error;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

}
