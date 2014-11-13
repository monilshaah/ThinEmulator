package com.thinemulator.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.thinemulator.adapter.AnnotationConfigApplicationContext;
import com.thinemulator.adapter.ApplicationContext;
import com.thinemulator.adapter.MongoOperations;
import com.thinemulator.beans.EmulatorUser;
import com.thinemulator.beans.SpringMongoConfig;
import com.thinemulator.beans.UserBean;
import com.thinemulator.utility.*;


@Controller
@EnableAutoConfiguration
@ComponentScan
public class UserController extends SpringBootServletInitializer{
	private ApplicationContext ctx;
	private MongoOperations mongoOperation;
	 
	public MongoDBAdapter() {
		this.ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		this.mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
	}

	/*
   public static void main(String[] args){
	   SpringApplication.run(SignupController.class, args);   }
   */
	
   @Override
   protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
       return application.sources(Application.class);
   }
    
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public User createUser(@RequestBody final User user) throws Exception{
    	Query searchUserQuery = new Query(Criteria.where("username").is(user.username));
    	UserBean tempUser = mongoOperation.findOne(searchUserQuery, UserBean.class);
    	
    	if (tempUser == null) {
    		 throw new IllegalArgumentException(
		       String.format("A user already exists with the username ", user.username));
    	}
    	else{
    		mongoOperation.insert(user);
    		tempUser = null;
    		tempUser = mongoOperation.findOne(searchUserQuery, UserBean.class);
    		if(tempUser != null){
    			NotificationUtility.sendEmail(tempUser.hashcode, "http://localhost:8080/signup/emailauth?hash=");
    			return tempUser;
    		}
    		else{
    			throw new IllegalArgumentException(
    	                String.format("Problem in inserting userdata ", user.username));
    		
    		}
    	}
    }
    
    @RequestMapping(value = "/signup/emailauth", method = RequestMethod.PUT)
    @ResponseBody
    public void emailAuth(@RequestBody final User user,
    		@RequestParam(value="hash", defaultValue="000") String hash) throws Exception{
    	String pwd = getHash(user.password);
    	/* TODO 
    	Query searchQuery = new Query(Criteria.where("hashcode").is(hash));
    	Query setQuery = new Query( "$set", new BasicDBObject("password", pwd));
    	mongoOperation.updateFirst(searchUserQuery, setQuery, UserBean.class);    
    	*/
    }
}
