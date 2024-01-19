package com.arsios.exchange.api.kraken.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Asset implements Serializable {

	private static final long	serialVersionUID	= 3689815420997537129L;

	private String				aclass;
	private String				altname;
	private Integer				decimals;
	
	@SerializedName("display_decimals")
	private Integer				displayDecimals;

	public String getAclass() {
		return aclass;
	}

	public void setAclass(String aclass) {
		this.aclass = aclass;
	}

	public String getAltname() {
		return altname;
	}

	public void setAltname(String altname) {
		this.altname = altname;
	}

	public Integer getDecimals() {
		return decimals;
	}

	public void setDecimals(Integer decimals) {
		this.decimals = decimals;
	}

	public Integer getDisplayDecimals() {
		return displayDecimals;
	}

	public void setDisplayDecimals(Integer displayDecimals) {
		this.displayDecimals = displayDecimals;
	}

}
