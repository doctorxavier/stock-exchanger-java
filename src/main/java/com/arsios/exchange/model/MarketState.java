package com.arsios.exchange.model;

import java.math.BigDecimal;

public class MarketState {

	private BigDecimal	fee;
	private BigDecimal	pfee;
	private BigDecimal	mfee;
	private BigDecimal	nmfee;

	private BigDecimal	profit;
	private BigDecimal	pprofit;
	private BigDecimal	mprofit;
	private BigDecimal	nmprofit;

	private BigDecimal	order;
	private BigDecimal	porder;

	private BigDecimal	fask;
	private BigDecimal	fbid;

	private BigDecimal	ask;
	private BigDecimal	lask;

	private BigDecimal	bid;
	private BigDecimal	lbid;

	private Balance		balance;

	private BigDecimal	minOp;

	public BigDecimal getMinOp() {
		return minOp;
	}

	public void setMinOp(BigDecimal minOp) {
		this.minOp = minOp;
	}

	public Balance getBalance() {
		return balance;
	}

	public void setBalance(Balance balance) {
		this.balance = balance;
	}

	public BigDecimal getOrder() {
		return order;
	}

	public void setOrder(BigDecimal order) {
		this.order = order;
	}

	public BigDecimal getPorder() {
		return porder;
	}

	public void setPorder(BigDecimal porder) {
		this.porder = porder;
	}

	public BigDecimal getPfee() {
		return pfee;
	}

	public void setPfee(BigDecimal pfee) {
		this.pfee = pfee;
	}

	public BigDecimal getPprofit() {
		return pprofit;
	}

	public void setPprofit(BigDecimal pprofit) {
		this.pprofit = pprofit;
	}

	public BigDecimal getFask() {
		return fask;
	}

	public void setFask(BigDecimal fask) {
		this.fask = fask;
	}

	public BigDecimal getFbid() {
		return fbid;
	}

	public void setFbid(BigDecimal fbid) {
		this.fbid = fbid;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public BigDecimal getMprofit() {
		return mprofit;
	}

	public void setMprofit(BigDecimal mprofit) {
		this.mprofit = mprofit;
	}

	public BigDecimal getNmprofit() {
		return nmprofit;
	}

	public void setNmprofit(BigDecimal nmprofit) {
		this.nmprofit = nmprofit;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getMfee() {
		return mfee;
	}

	public void setMfee(BigDecimal mfee) {
		this.mfee = mfee;
	}

	public BigDecimal getNmfee() {
		return nmfee;
	}

	public void setNmfee(BigDecimal nmfee) {
		this.nmfee = nmfee;
	}

	public BigDecimal getAsk() {
		return ask;
	}

	public void setAsk(BigDecimal ask) {
		this.ask = ask;
	}

	public BigDecimal getLask() {
		return lask;
	}

	public void setLask(BigDecimal lask) {
		this.lask = lask;
	}

	public BigDecimal getBid() {
		return bid;
	}

	public void setBid(BigDecimal bid) {
		this.bid = bid;
	}

	public BigDecimal getLbid() {
		return lbid;
	}

	public void setLbid(BigDecimal lbid) {
		this.lbid = lbid;
	}

}
