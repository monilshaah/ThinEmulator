package com.thinemulator.controller;

public class Devices {
	private String emulatorTargetId;
	private String id;
	private String emulatorDeviceId;
	
	public Devices(String emulatorTargetId, String id, String emulatorDeviceId){
		this.emulatorTargetId = emulatorTargetId;
		this.id = id;
		this.emulatorDeviceId = emulatorDeviceId;
	}
	
	public void setEmulatorTargetId(String emulatorTargetId) {
		this.emulatorTargetId = emulatorTargetId;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public void setEmulatorDeviceId(String emulatorDeviceId){
		this.emulatorDeviceId = emulatorDeviceId;
	}
	public String getEmulatorTargetId() {
		return emulatorTargetId;
	}
	
	public String getId() {
		return id;
	}
	
	public String getEmulatorDeviceId() {
		return emulatorDeviceId;
	}
}
