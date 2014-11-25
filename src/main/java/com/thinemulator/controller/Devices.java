package com.thinemulator.controller;

public class Devices {
	private String name;
	private String id;
	
	public Devices(String name, String id){
		this.name = name;
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
}
