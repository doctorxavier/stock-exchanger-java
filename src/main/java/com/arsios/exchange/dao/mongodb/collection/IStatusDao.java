package com.arsios.exchange.dao.mongodb.collection;

import com.arsios.exchange.model.Status;

public interface IStatusDao {

	int insert(Status status);
	
	void setDb(String dbName);
	
}
