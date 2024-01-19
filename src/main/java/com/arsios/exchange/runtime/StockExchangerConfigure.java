package com.arsios.exchange.runtime;

import java.io.Serializable;
import java.math.BigDecimal;

import com.arsios.exchange.model.Balance;

public class StockExchangerConfigure implements Serializable {

	private static final long	serialVersionUID	= -911252806318865116L;

	private Integer				amountPrecision;
	private Integer				pricePrecision;
	private BigDecimal			fee;
	private BigDecimal			minOp;
	private BigDecimal			stopLoss;
	private BigDecimal			profit;
	private BigDecimal			orderAmount;
	private Boolean				debug;

	private Balance				balance;

	public Balance getBalance() {
		return balance;
	}

	public void setBalance(Balance balance) {
		this.balance = balance;
	}

	public Boolean isDebug() {
		return debug;
	}

	public void setDebug(Boolean debug) {
		this.debug = debug;
	}

	public Integer getAmountPrecision() {
		return amountPrecision;
	}

	public void setAmountPrecision(Integer amountPrecision) {
		this.amountPrecision = amountPrecision;
	}

	public Integer getPricePrecision() {
		return pricePrecision;
	}

	public void setPricePrecision(Integer pricePrecision) {
		this.pricePrecision = pricePrecision;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getMinOp() {
		return minOp;
	}

	public void setMinOp(BigDecimal minOp) {
		this.minOp = minOp;
	}

	public BigDecimal getStopLoss() {
		return stopLoss;
	}

	public void setStopLoss(BigDecimal stopLoss) {
		this.stopLoss = stopLoss;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

}
