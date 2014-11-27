package com.thinemulator.controller;

import org.springframework.data.annotation.Id;

public class DeviceConfig {

	private String deviceName;
	private String status = "Running";
	private String deviceType;
	
	public DeviceConfig(String configName, String deviceType) {
		this.deviceName = configName;
		this.deviceType = deviceType;
	}
	
	public DeviceConfig(String configName, String deviceType, String status) {
		this.deviceName = configName;
		this.status = status;
		this.deviceType = deviceType;
	}
	
	public DeviceConfig() {
		
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceName() {
		return deviceName;
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
	
}
