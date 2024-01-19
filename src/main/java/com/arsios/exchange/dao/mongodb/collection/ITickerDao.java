package com.arsios.exchange.dao.mongodb.collection;

import java.util.Date;
import java.util.List;

import com.arsios.exchange.model.Ticker;

public interface ITickerDao {

	int insert(Ticker ticker);
	
	List<Ticker> getTickers(Date dategte, byte sort);
	
	List<Ticker> getTickers(Date dategte, Date datelt, byte sort);
	
	List<Ticker> getTickers(Date dategte, Date datelt, byte sort, short limit);
	
	void setDb(String dbName);
	
}
