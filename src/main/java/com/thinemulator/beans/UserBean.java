package com.thinemulator.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.thinemulator.controller.DeviceConfig;

@Document(collection = "users")
public class UserBean implements Serializable{

	 /**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -1299234524292338425L;
		ArrayList<DeviceConfig> emulatorList = new ArrayList<DeviceConfig>();
		
		@NotBlank
	    @Size(max = 64)
		@Id
	     public String username;
	    
	    @Size(max = 64)
	    public String password;

	    @NotBlank
	    @Size(max = 64)
	    public String email;
	    
	    public void setUsername(String username) {
	    	this.username = username;
	    }
	    
	    public String getUsername() {
	    	return this.username;
	    }
	    
	    public void setEmail(String email) {
	    	this.email = email;
	    }
	    
	    public String getEmail() {
	    	return this.email;
	    }
	    
	    public void setPassword(String password) {
	    	this.password = password;
	    }
	    
	    public String getPassword() {
	    	return this.password;
	    }
	    
	    public void setEmulatorList(DeviceConfig deviceConfig){
	    	emulatorList.add(deviceConfig);
	    }
	    
	    public ArrayList<DeviceConfig> getEmulatorList() {
	    	return emulatorList;
	    }
	    
	    @Override
	    public String toString() {
	    	return "\"username\":\""+username+"\","
	    			+"\"email\":\""+email+"\""
	    			;
	    }
	    
}
