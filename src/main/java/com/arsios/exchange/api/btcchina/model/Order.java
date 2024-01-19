package com.arsios.exchange.api.btcchina.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

public class Order implements Serializable {

	private static final long	serialVersionUID	= 5291911245887966438L;
	
	private Long id;
	private String type;
	private BigDecimal price;
	private String currency;
	private BigDecimal amount;
	
	@SerializedName("amount_original")
	private BigDecimal amountOriginal;
	private Long date;
	private String status;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getAmountOriginal() {
		return amountOriginal;
	}
	
	public void setAmountOriginal(BigDecimal amountOriginal) {
		this.amountOriginal = amountOriginal;
	}
	
	public Long getDate() {
		return date;
	}
	
	public void setDate(Long date) {
		this.date = date;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
