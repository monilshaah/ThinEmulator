/**
 * Spring MongoDB configuration file
 * 
 */

package com.thinemulator.beans;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;



@Configuration
public class SpringMongoConfig {
	
	@Bean
	public MongoTemplate mongoTemplate() throws IOException {
		Properties properties = new Properties();
		String userDir = System.getProperty("user.dir");
		InputStream inputProperties = new FileInputStream(userDir + "/ThinEmulator.properties");
		properties.load(inputProperties);
		String uri =  properties.getProperty("MongoDBConnectionString");
		MongoClientURI mongoClientUri = new MongoClientURI(uri);
		SimpleMongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(new MongoClient(mongoClientUri), properties.getProperty("DatabaseName"));
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
		return mongoTemplate;
	}
}
 