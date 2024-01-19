package com.arsios.exchange.dao.mongodb.collection.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.arsios.exchange.dao.mongodb.collection.IOrderDao;
import com.arsios.exchange.dao.mongodb.db.AbstractMongoDBDao;
import com.arsios.exchange.model.Order;

@Repository
public class OrderDao extends AbstractMongoDBDao<Order> implements IOrderDao {
	
	private String dbName;
	private String dbCollectionName;

	protected OrderDao() {
		
	}
	
	@Autowired
	public OrderDao(@Value("kraken")String dbName, @Value("order2")String dbCollectionName) {
		this.dbName = dbName;
		this.dbCollectionName = dbCollectionName;
	}
	
	public void setDb(String dbName) {
		this.setDbCollection(this.getDb(dbName).getCollection(this.dbCollectionName));
	}
	
	@PostConstruct
	private void init() {
		this.setDbCollection(this.getDb(this.dbName).getCollection(this.dbCollectionName));
	}
	
	public int insert(Order order) {
		return super.insert(order);
	}
	
}
