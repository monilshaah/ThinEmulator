package com.thinemulator.controller;

import org.springframework.data.annotation.Id;

public class DeviceConfig {

	private String configName;
	private String status;
	@Id
	public String id;
	public DeviceConfig(String configName, String status) {
		this.configName = configName;
		this.status = status;
	}
	
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public String getConfigName() {
		return configName;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getId() {
		return id;
	}
	
}
