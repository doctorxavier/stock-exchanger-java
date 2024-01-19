package com.arsios.exchange.api.btcchina.model;

import java.io.Serializable;

public class Error implements Serializable {

	private static final long	serialVersionUID	= -6586514891226180382L;

	private Integer				code;
	private String				message;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
