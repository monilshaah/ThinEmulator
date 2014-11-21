package com.thinemulator.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.BasicDBObject;
import com.thinemulator.beans.SpringMongoConfig;
import com.thinemulator.beans.UserBean;
import com.thinemulator.utility.Config;
import com.thinemulator.utility.DataUtility;
import com.thinemulator.utility.NotificationUtility;


@Controller
@EnableAutoConfiguration
@ComponentScan
public class UserController extends SpringBootServletInitializer{
	private ApplicationContext ctx;
	private MongoOperations mongoOperation;
	 
	public UserController() {
		this.ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		this.mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
	}

	/*
   public static void main(String[] args){
	   SpringApplication.run(SignupController.class, args);   }
   */
	
   /*@Override
   protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
       return application.sources(Application.class);
   }*/
    
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String renderIndex(Model model) {
		System.out.println("returning index file");
		model.addAttribute("userbean", new UserBean());
		return "index";
	}
	
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public UserBean createUser(@RequestBody final UserBean user) throws Exception{
    	System.out.println("username : "+user.username);
    	System.out.println("password : "+user.password);
    	System.out.println("email : "+user.email);
    	Query searchUserQuery = new Query(Criteria.where("username").is(user.username));
    	UserBean tempUser = mongoOperation.findOne(searchUserQuery, UserBean.class);
    	
    	if (tempUser != null) {
    		 throw new IllegalArgumentException(
		       String.format("A user already exists with the username ", user.username));
    	}
    	else{
    		mongoOperation.insert(user);
    		tempUser = null;
    		tempUser = mongoOperation.findOne(searchUserQuery, UserBean.class);
    		if(tempUser != null){
    			String EMAIL_AUTH_URL = Config.readProperties().getProperty("EMAIL_AUTH_URL");
    			NotificationUtility.sendEmail(DataUtility.getHash(tempUser.toString()), EMAIL_AUTH_URL, user);
    			return tempUser;
    		}
    		else{
    			throw new IllegalArgumentException(
    	                String.format("Problem in inserting userdata ", user.username));
    		
    		}
    	}
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public void Login(@RequestBody final UserBean user) throws Exception{
    	String pwd = DataUtility.getHash(user.password);
    	BasicDBObject query = new BasicDBObject("username", user.username)
        						.append("password", pwd);
    	Query searchUserQuery = new Query(Criteria.where("username").is(user.username).and("password").is(user.password) );
    	UserBean tempUser = mongoOperation.findOne(searchUserQuery, UserBean.class);
    	System.out.println("USER LOGGED IN : "+ tempUser.email);
    }
    
    
    @RequestMapping(value = "/signup/emailauth", method = RequestMethod.PUT)
    @ResponseBody
    public void emailAuth(@RequestBody final User user,
    		@RequestParam(value="hash", defaultValue="000") String hash) throws Exception{
    	String pwd = DataUtility.getHash(user.getPassword());
    	/* TODO 
    	Query searchQuery = new Query(Criteria.where("hashcode").is(hash));
    	Query setQuery = new Query( "$set", new BasicDBObject("password", pwd));
    	mongoOperation.updateFirst(searchUserQuery, setQuery, UserBean.class);    
    	*/
    }
}
