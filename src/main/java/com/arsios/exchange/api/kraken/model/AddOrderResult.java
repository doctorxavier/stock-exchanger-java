package com.arsios.exchange.api.kraken.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddOrderResult implements Serializable {

	private static final long	serialVersionUID	= -4568209355767116708L;

	private OrderDescr			descr;
	private List<String>		txid				= new ArrayList<String>(0);

	public OrderDescr getDescr() {
		return descr;
	}

	public void setDescr(OrderDescr descr) {
		this.descr = descr;
	}

	public List<String> getTxid() {
		return txid;
	}

	public void setTxid(List<String> txid) {
		this.txid = txid;
	}

}
