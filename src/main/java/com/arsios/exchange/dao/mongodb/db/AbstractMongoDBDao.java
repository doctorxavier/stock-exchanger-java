package com.arsios.exchange.dao.mongodb.db;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.arsios.exchange.dao.IDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.util.JSON;

public abstract class AbstractMongoDBDao<T> implements IDao {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(AbstractMongoDBDao.class);
	private static final Gson	GSON	= new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
	
	private static final DateTimeParser[]	PARSERS	= {
		DateTimeFormat.forPattern("yyyy-MM-dd").getParser(),
		DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").getParser(), 
		DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS").getParser(),
		DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss Z").getParser(), 
		DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS Z").getParser(),
		DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ").getParser(), 
		DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ").getParser(),
		DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssz").getParser(), 
		DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz").getParser(),
	};
	
	private static final DateTimeFormatter DATETIMEFORMATTER = new DateTimeFormatterBuilder().append(null, PARSERS).toFormatter();

	protected Mongo				mongo;

	@Value("${" + MongoDBConfig.HOST + ":localhost}")
	private String				host;

	@Value("${" + MongoDBConfig.PORT + ":27017}")
	private int					port;

	@Value("${" + MongoDBConfig.USERNAME + ":}")
	private String			username;

	@Value("${" + MongoDBConfig.PASSWORD + ":}")
	private String			password;

	@Value("${" + MongoDBConfig.AUTHENTICATION_ENABLED + ":false}")
	private boolean			authenticationEnabled;

	@Value("${" + MongoDBConfig.CONNECTIONS_PER_HOST + ":100}")
	private int					connectionsPerHost;

	@Value("${" + MongoDBConfig.SOCKET_KEEP_ALIVE + ":false}")
	private boolean				socketKeepAlive;

	@Value("${" + MongoDBConfig.DEFAULT_DB_NAME + ":events}")
	private String				defaultDBName;

	@Value("${" + MongoDBConfig.DEFAULT_WRITE_CONCERN + ":acknowledged}")
	private String				writeConcernStr;

	private DBCollection		dbCollection;
	
	private WriteConcern		writeConcern;

	@PostConstruct
	private void init() {
		if ("unacknowledged".equals(writeConcernStr)) {
			writeConcern = WriteConcern.UNACKNOWLEDGED;
		} else {
			writeConcern = WriteConcern.ACKNOWLEDGED;
		}
		MongoClientOptions options = MongoClientOptions.builder().socketKeepAlive(socketKeepAlive).connectionsPerHost(connectionsPerHost).writeConcern(
				writeConcern).build();
		if (!authenticationEnabled) {
			try {
				mongo = new MongoClient(new ServerAddress(host, port), options);
			} catch (UnknownHostException e) {
				LOGGER.error("Can't connect to mongoDB", e);
				return;
			}
		} else {
			try {
				MongoCredential mongoCredential = MongoCredential.createMongoCRCredential(username, defaultDBName, password.toCharArray());
				mongo = new MongoClient(new ServerAddress(host, port), Arrays.asList(mongoCredential), options);
			} catch (UnknownHostException e) {
				LOGGER.error("Can't connect to mongoDB", e);
				return;
			} catch (MongoException e) {
				LOGGER.error("CRITICAL FAILURE: Unable to authenticate. Check username and Password, or use another unauthenticated DB. Not starting MongoDB sink.\n");
				return;
			}
		}

	}

	@PreDestroy
	public void close() {
		if (this.mongo != null) {
			this.mongo.close();
		}
	}

	public DB getDb(String dbName) {
		return mongo.getDB(dbName);
	}
	
	public DBCollection getDbCollection() {
		return dbCollection;
	}

	public void setDbCollection(DBCollection dbCollection) {
		this.dbCollection = dbCollection;
	}

	public int insert(T object) {
		BasicDBObject dbObject = (BasicDBObject) JSON.parse(GSON.toJson(object));

		Date timestamp = new Date();
		if (dbObject.containsField("created")) {
			Object createdObj = dbObject.get("created");
			if (createdObj instanceof String) {
				String created = (String) object;
				try {
					timestamp = DATETIMEFORMATTER.parseDateTime(created).toDate();
					dbObject.removeField("created");
					dbObject.put("created", timestamp);
				} catch (Exception e) {
					LOGGER.error("can't parse date " + created, e);
				}
			}
		} else {
			dbObject.put("created", timestamp);
		}

		try {
			this.dbCollection.insert(dbObject);
		} catch (MongoException e) {
			LOGGER.error("can't insert documents with error: {} , {}", e.getCode(), e.getMessage());
			return -1;
		}
		return 0;
	}
	
	public int update(T object) {
		BasicDBObject dbObject = (BasicDBObject) JSON.parse(GSON.toJson(object));
		try {
			final DBObject doc = new BasicDBObject("$currentDate", new BasicDBObject("timestamp", true))
				.append("$set", dbObject);
			this.dbCollection.update(dbObject, doc, true, false, writeConcern);
		} catch (MongoException e) {
			LOGGER.error("can't insert documents with error: {} , {}", e.getCode(), e.getMessage());
			return -1;
		}
		return 0;
	}

}
