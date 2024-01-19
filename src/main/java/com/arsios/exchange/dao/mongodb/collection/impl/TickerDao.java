package com.arsios.exchange.dao.mongodb.collection.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.arsios.exchange.dao.mongodb.collection.ITickerDao;
import com.arsios.exchange.dao.mongodb.db.AbstractMongoDBDao;
import com.arsios.exchange.model.Ticker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

@Repository
public class TickerDao extends AbstractMongoDBDao<Ticker> implements ITickerDao {
	
	private static final Logger	LOGGER	= LoggerFactory.getLogger(TickerDao.class);

	private static final Gson	GSON	= new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
	
	private String dbName;
	private String dbCollectionName;

	protected TickerDao() {
		
	}
	
	@Autowired
	public TickerDao(@Value("test")String dbName, @Value("ticker")String dbCollectionName) {
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
	
	public int insert(Ticker ticker) {
		return super.insert(ticker);
	}
	
	public List<Ticker> getTickers(Date dategte, byte sort) {
		return getTickers(dategte, null, (byte) sort, (short) 0);
	}
	
	public List<Ticker> getTickers(Date dategte, Date datelt, byte sort) {
		return getTickers(dategte, datelt, sort, (short) 0);
	}
	
	public List<Ticker> getTickers(Date dategte, Date datelt, byte sort, short limit) {
		List<Ticker> tickers = null;
		try {
			final List<DBObject> pipeline = new ArrayList<DBObject>(0);
			
			DBObject fields = new BasicDBObject("_id", 0);
			fields.put("ask", 1);
			fields.put("bid", 1);
			fields.put("last", 1);
			fields.put("volume", 1);
			fields.put("avg", 1);
			fields.put("trades", 1);
			fields.put("low", 1);
			fields.put("high", 1);
			fields.put("open", 1);
			fields.put("created", 1);
			
			pipeline.add(new BasicDBObject("$project", fields));
			
			if (dategte != null || datelt != null) {
				BasicDBObject match = null;
				
				if (dategte != null) {
					match = new BasicDBObject("$gte", dategte);
					if (datelt != null) {
						match.append("$lt", datelt);
					}
				} else {
					match = new BasicDBObject("$lt", datelt);
				}
	
				if (match != null) {
					pipeline.add(new BasicDBObject("$match", new BasicDBObject("created", match)));
				}
			}
			
			if (sort != 0) {
				pipeline.add(new BasicDBObject("$sort", new BasicDBObject("created", sort)));
			}
			if (limit != 0) {
				pipeline.add(new BasicDBObject("$limit", limit));
			}
			
			final AggregationOutput output = this.getDbCollection().aggregate(pipeline);
			
			tickers = new ArrayList<Ticker>(0);
			
			for (final DBObject dbObject : output.results()) {
				if (dbObject instanceof BasicDBObject) {
					Date created = (Date) dbObject.get("created");
					dbObject.removeField("created");
					Ticker ticker = GSON.fromJson(dbObject.toString(), Ticker.class);
					ticker.setCreated(created);
					tickers.add(ticker);
				}
			}

		} catch (MongoException e) {
			LOGGER.error("can't retrieve documents with error: {} , {}", e.getCode(), e.getMessage());
		}
		return tickers;
	}
	
}
