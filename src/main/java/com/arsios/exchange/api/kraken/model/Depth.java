package com.arsios.exchange.api.kraken.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Depth implements Serializable {

	private static final long	serialVersionUID	= -2744239199066531802L;
	
	@SerializedName("XXBTZEUR")
	private PairDepth	btcEur;

	public PairDepth getBtcEur() {
		return btcEur;
	}

	public void setBtcEur(PairDepth btcEur) {
		this.btcEur = btcEur;
	}

	public class PairDepth implements Serializable {

		private static final long		serialVersionUID	= 420255751436064226L;

		private List<List<BigDecimal>>	asks				= new ArrayList<List<BigDecimal>>(0);
		private List<List<BigDecimal>>	bids				= new ArrayList<List<BigDecimal>>(0);

		public List<List<BigDecimal>> getAsks() {
			return asks;
		}

		public void setAsks(List<List<BigDecimal>> asks) {
			this.asks = asks;
		}

		public List<List<BigDecimal>> getBids() {
			return bids;
		}

		public void setBids(List<List<BigDecimal>> bids) {
			this.bids = bids;
		}

	}

}
