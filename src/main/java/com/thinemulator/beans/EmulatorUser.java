package com.thinemulator.beans;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class EmulatorUser {
	@Id
	private String userId;
	
	private List<String> emulatorList;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<String> getEmulatorList() {
		return emulatorList;
	}
	public void setEmulatorList(List<String> emulatorList) {
		this.emulatorList = emulatorList;
	} 
	
	@Override
	public String toString() {
		return (this.getUserId()+" "+this.getEmulatorList());
	}

}
