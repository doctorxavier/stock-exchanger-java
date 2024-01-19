package com.arsios.exchange.dao.mongodb.collection;

import com.arsios.exchange.model.Order;

public interface IOrderDao {

	int insert(Order order);
	
	void setDb(String dbName);

}
