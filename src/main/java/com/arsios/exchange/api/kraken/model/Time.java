package com.arsios.exchange.api.kraken.model;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class Time implements Serializable {

	private static final long	serialVersionUID	= -5880344251749134746L;

	@SerializedName("unixtime")
	private Long				timestamp;

	@SerializedName("rfc1123")
	private Date				time;

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long unixtime) {
		this.timestamp = unixtime;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
