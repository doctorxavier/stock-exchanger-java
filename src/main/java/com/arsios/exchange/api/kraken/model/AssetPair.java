package com.arsios.exchange.api.kraken.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class AssetPair implements Serializable {

	private static final long		serialVersionUID	= 5383850946813566571L;

	private String					altname;

	@SerializedName("aclass_base")
	private String					aclassBase;
	private String					base;

	@SerializedName("aclass_quote")
	private String					aclassQuote;
	private String					currency;
	private String					quote;
	private String					lot;

	@SerializedName("pair_decimals")
	private Integer					pairDecimals;

	@SerializedName("lot_decimals")
	private Integer					lotDecimals;

	@SerializedName("lot_multiplier")
	private Integer					lotMultiplier;
	private List<String>			leverage			= new ArrayList<String>(0);
	private List<List<BigDecimal>>	fees				= new ArrayList<List<BigDecimal>>(0);

	@SerializedName("fee_volume_currency")
	private String					feeVolumeCurrency;

	@SerializedName("margin_call")
	private Integer					marginCall;

	@SerializedName("margin_stop")
	private Integer					marginStop;

	public String getAltname() {
		return altname;
	}

	public void setAltname(String altname) {
		this.altname = altname;
	}

	public String getAclassBase() {
		return aclassBase;
	}

	public void setAclassBase(String aclassBase) {
		this.aclassBase = aclassBase;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getAclassQuote() {
		return aclassQuote;
	}

	public void setAclassQuote(String aclassQuote) {
		this.aclassQuote = aclassQuote;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public Integer getPairDecimals() {
		return pairDecimals;
	}

	public void setPairDecimals(Integer pairDecimals) {
		this.pairDecimals = pairDecimals;
	}

	public Integer getLotDecimals() {
		return lotDecimals;
	}

	public void setLotDecimals(Integer lotDecimals) {
		this.lotDecimals = lotDecimals;
	}

	public Integer getLotMultiplier() {
		return lotMultiplier;
	}

	public void setLotMultiplier(Integer lotMultiplier) {
		this.lotMultiplier = lotMultiplier;
	}

	public List<String> getLeverage() {
		return leverage;
	}

	public void setLeverage(List<String> leverage) {
		this.leverage = leverage;
	}

	public List<List<BigDecimal>> getFees() {
		return fees;
	}

	public void setFees(List<List<BigDecimal>> fees) {
		this.fees = fees;
	}

	public String getFeeVolumeCurrency() {
		return feeVolumeCurrency;
	}

	public void setFeeVolumeCurrency(String feeVolumeCurrency) {
		this.feeVolumeCurrency = feeVolumeCurrency;
	}

	public Integer getMarginCall() {
		return marginCall;
	}

	public void setMarginCall(Integer marginCall) {
		this.marginCall = marginCall;
	}

	public Integer getMarginStop() {
		return marginStop;
	}

	public void setMarginStop(Integer marginStop) {
		this.marginStop = marginStop;
	}

}
