package com.thinemulator.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    
	
	@RequestMapping(value={"","/", "/index"}, method = RequestMethod.GET)
	public String renderIndex(Model model) {
		System.out.println("returning index file");
		model.addAttribute("userbean", new UserBean());
		return "index";
	}
	
	@RequestMapping(value="/welcome", method = RequestMethod.GET)
	public String renderSuccessSignup(Model model) {
		return "signupsuccess";
	}
	
	@RequestMapping(value="/home/{username}", method = RequestMethod.GET)
	public String renderHome(@PathVariable String username, Model model) {
		Query searchUserQuery = new Query(Criteria.where("username").is(username));
		UserBean user  = mongoOperation.findOne(searchUserQuery, UserBean.class);
		System.out.println(user.toString());
		model.addAttribute("user", user);
		return "home";
	}
	@RequestMapping(value="/loadnewdeviceform", method = RequestMethod.GET)
	public String renderLoadNewDeviceForm(Model model) {
		List<Devices> devices = new ArrayList<Devices>();
		devices.add(new Devices("Nexus-4","Nexus-4"));
		devices.add(new Devices("Nexus-5","Nexus-5"));
		devices.add(new Devices("Nexus-7","7"));
		model.addAttribute("models", devices);
		return "home :: listdeviceoptions";
	}
	
	@RequestMapping(value="/loadconfigureddevices", method = RequestMethod.GET)
	public String renderExistingConfigs(Model model) {
		List<DeviceConfig> devices = new ArrayList<DeviceConfig>();
		devices.add(new DeviceConfig("First Application","Running"));
		devices.add(new DeviceConfig("Second Application testing","Running"));
		devices.add(new DeviceConfig("Third Application test case suit automation under test","In-Progress"));
		model.addAttribute("models", devices);
		return "home :: usersavedconfigs";
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
    
    
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
                stream.write(bytes);
                stream.close();
                System.out.println( "You successfully uploaded " + name + " into " + name + "-uploaded !");
                return "You successfully uploaded "+name;
            } catch (Exception e) {
            	 System.out.println( "You failed to upload " + name + " => " + e.getMessage());
            	 return "ailed to upload " + name ;
            }
        } else {
        	 System.out.println("You failed to upload " + name + " because the file was empty.");
        	 return "ailed to upload " + name ;
        }
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody String Login(@RequestBody final UserBean user) throws Exception{
    	String pwd = DataUtility.getHash(user.password);
    	BasicDBObject query = new BasicDBObject("username", user.username)
        						.append("password", pwd);
    	Query searchUserQuery = new Query(Criteria.where("username").is(user.username).and("password").is(user.password) );
    	UserBean tempUser = mongoOperation.findOne(searchUserQuery, UserBean.class);
    	System.out.println("USER LOGGED IN : "+tempUser);
    	
    	if(null!=tempUser) {
    		String successfulLoginResponse = tempUser.toString();
    		return "{\"success\":\"true\","
    				+ successfulLoginResponse
    				+"}";
    	} else {
    		return "{\"success\":\"false\"}";
    	}
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
