package com.arsios.exchange.dao.mongodb.collection.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.arsios.exchange.dao.mongodb.collection.IStatusDao;
import com.arsios.exchange.dao.mongodb.db.AbstractMongoDBDao;
import com.arsios.exchange.model.Status;

@Repository
public class StatusDao extends AbstractMongoDBDao<Status> implements IStatusDao {
	
	private String dbName;
	private String dbCollectionName;

	protected StatusDao() {
		
	}
	
	@Autowired
	public StatusDao(@Value("test")String dbName, @Value("status2")String dbCollectionName) {
		this.dbName = dbName;
		this.dbCollectionName = dbCollectionName;
	}
	
	@PostConstruct
	private void init() {
		this.setDbCollection(this.getDb(this.dbName).getCollection(this.dbCollectionName));
	}
	
	public void setDb(String dbName) {
		this.setDbCollection(this.getDb(dbName).getCollection(this.dbCollectionName));
	}
	
	public int insert(Status status) {
		return super.insert(status);
	}

}
