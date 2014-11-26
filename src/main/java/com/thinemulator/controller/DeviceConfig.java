package com.thinemulator.controller;

import org.springframework.data.annotation.Id;

public class DeviceConfig {

	private String configName;
	private String status = "Running";
	@Id
	public String id;
	public String deviceType;
	public DeviceConfig(String configName, String deviceType) {
		this.configName = configName;
		this.deviceType = deviceType;
	}
	
	public DeviceConfig(String configName, String deviceType, String status) {
		this.configName = configName;
		this.status = status;
		this.deviceType = deviceType;
	}
	
	public DeviceConfig() {
		
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public String getConfigName() {
		return configName;
	}
	
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceType() {
		return deviceType;
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
