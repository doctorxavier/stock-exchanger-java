package com.arsios.exchange.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable {

	public static final Character	BID					= 'b';
	public static final Character	ASK					= 'a';
	
	public static final String	BID_S					= "buy";
	public static final String	ASK_S					= "sell";
	
	public static final String	BID_N					= "bid";
	public static final String	ASK_N					= "ask";
	
	public static final Map<Character, String> TYPE = new HashMap<Character, String>() {
		private static final long	serialVersionUID	= -2387405940047479908L; 
		{
            put(BID, "buy");
            put(ASK, "sell");
        }
    };
    
    public static final Map<String, Character> TYPE_S = new HashMap<String, Character>() {
		private static final long	serialVersionUID	= 2562800892113202664L; 
		{
            put(BID_S, 'b');
            put(ASK_S, 'a');
        }
    };
    
    public static final Map<String, Character> TYPE_N = new HashMap<String, Character>() {
		private static final long	serialVersionUID	= 3016980356104263398L;
		{
            put(BID_N, 'b');
            put(ASK_N, 'a');
        }
    };

	private static final long					serialVersionUID	= -1861763007120236876L;

	private String								id;
	private Character							type;
	private BigDecimal							amount;
	private BigDecimal							cost;
	private BigDecimal							lask;
	private BigDecimal							ask;
	private BigDecimal							lbid;
	private BigDecimal							bid;
	private BigDecimal							price;
	private String								description;
	private BigDecimal							fee;
	private BigDecimal							mfee;
	private String								pair;
	private BigDecimal							value;
	private BigDecimal							currency;
	private BigDecimal							balance;
	private Date								created;
	private String 								status;
	private String								exchanger;
	
	private Integer								iteration;

	public Integer getIteration() {
		return iteration;
	}

	public void setIteration(Integer iteration) {
		this.iteration = iteration;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getCurrency() {
		return currency;
	}

	public void setCurrency(BigDecimal currency) {
		this.currency = currency;
	}

	public String getExchanger() {
		return exchanger;
	}

	public void setExchanger(String exchanger) {
		this.exchanger = exchanger;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public BigDecimal getLask() {
		return lask;
	}

	public void setLask(BigDecimal lask) {
		this.lask = lask;
	}

	public BigDecimal getLbid() {
		return lbid;
	}

	public void setLbid(BigDecimal lbid) {
		this.lbid = lbid;
	}

	public BigDecimal getMfee() {
		return mfee;
	}

	public void setMfee(BigDecimal mfee) {
		this.mfee = mfee;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAsk() {
		return ask;
	}

	public void setAsk(BigDecimal ask) {
		this.ask = ask;
	}

	public BigDecimal getBid() {
		return bid;
	}

	public void setBid(BigDecimal bid) {
		this.bid = bid;
	}

	public Character getType() {
		return type;
	}

	public void setType(Character type) {
		this.type = type;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getPair() {
		return pair;
	}

	public void setPair(String pair) {
		this.pair = pair;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

}
