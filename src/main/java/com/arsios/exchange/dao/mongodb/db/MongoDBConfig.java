package com.arsios.exchange.dao.mongodb.db;

import org.springframework.context.annotation.Configuration;

//CHECKSTYLE:OFF
@Configuration
//CHECKSTYLE:ON
public class MongoDBConfig {

	public static final String HOST = "mongodb.host";
	public static final String PORT = "mongodb.port";
	public static final String DEFAULT_DB_NAME = "mongodb.defaultDBName";
	public static final String USERNAME = "mongodb.username";
	public static final String PASSWORD = "mongodb.password";
	public static final String CONNECTIONS_PER_HOST = "mongodb.conectionsPerHost";
	public static final String AUTHENTICATION_ENABLED = "mongodb.authenticationEnabled";
	public static final String SOCKET_KEEP_ALIVE = "mongodb.socketKeepAlive";
	public static final String DEFAULT_WRITE_CONCERN = "mongodb.writeConcern";

}
