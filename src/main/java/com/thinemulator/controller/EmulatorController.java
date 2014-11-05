package com.thinemulator.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thinemulator.ThinEmulatorApp;
import com.thinemulator.adapter.AndroidEmulatorAdapter;
import com.thinemulator.adapter.MongoDBAdapter;
import com.thinemulator.beans.AndroidEmulator;
import com.thinemulator.beans.EmulatorUser;

@RestController
@RequestMapping("/android/{userId}")
public class EmulatorController {
	AndroidEmulatorAdapter androidEmulatorAdapter = new AndroidEmulatorAdapter();
	MongoDBAdapter mongoDBAdapter = new MongoDBAdapter();
	
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "applicaiton/jsons")
	public EmulatorUser getUsers(@PathVariable String userId) {
		System.out.println("***calling DB connection...");
		EmulatorUser user = mongoDBAdapter.getEmulatorUser(userId);
		//TODO check if user = null or not
		System.out.println("***user: "+user);
		return user;
	}
	
	@RequestMapping(value = "/emulators", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String,String>> createUserAndroidEmulator(@PathVariable String userId, @RequestBody @Valid AndroidEmulator newAndroidEmulator, BindingResult validationResult){
		HttpStatus createdStatus;
		Map<String, String> responseBody = new HashMap<String, String>();
		if (validationResult.hasErrors()) {
			List<ObjectError> errorList = validationResult.getAllErrors();
			String errors = null;
			int cnt = 0;
			while(cnt < errorList.size()) {
				errors += errorList.get(cnt).getDefaultMessage()+"; ";
				cnt++;
			}
			createdStatus = HttpStatus.BAD_REQUEST;
			responseBody.put("Errors", errors);
		} else {
			androidEmulatorAdapter.createEmulator(newAndroidEmulator);
			String opStatus = mongoDBAdapter.addEmulator(userId, newAndroidEmulator.getEmulatorName());
			//TODO check status of operation
			if (opStatus.equals("success")) {
				createdStatus = HttpStatus.CREATED;
				responseBody = null;
			} else {
				createdStatus = HttpStatus.OK;
				responseBody.put("Errors",opStatus);
			}
				
		}
		return new ResponseEntity<Map<String,String>>(responseBody, new HttpHeaders(), createdStatus);
	}
	
	@RequestMapping(value = "/emulators", method = RequestMethod.GET, produces = "application/json")
	public Map<String,List<String>> listUserEmulator(@PathVariable String userId) {
		EmulatorUser user = mongoDBAdapter.getEmulatorUser(userId);
		Map<String,List<String>> emulatorList = new HashMap<String, List<String>>();
		emulatorList.put("Emulator List", user.getEmulatorList());
		return emulatorList;
	}
	
	@RequestMapping(value = "/emulators/{emulatorName}", method = RequestMethod.GET)
	public void startUserAndroidEmulator(@PathVariable String emulatorName) {
		//TODO Need to solve emulator naming issue in case of multiple users giving same name. One-way: use emualtorName as userId + emulatorName
		Process emulatorProcess = androidEmulatorAdapter.startEmulator(emulatorName);
		if (emulatorProcess != null) {
			ThinEmulatorApp.runningAndroidEmulator.put(emulatorName, emulatorProcess);
		} else {
			System.out.println("***Emulator not launched!!!");
		}
	}
	
	@RequestMapping(value = "/emulators/{emulatorName}/stop", method = RequestMethod.GET)
	public void stopUserAndroidEmulator(@PathVariable String emulatorName) {
		//TODO Follow startUserAndroidEmulator approach for addressing emulators
		Process emulatorProcess = ThinEmulatorApp.runningAndroidEmulator.get(emulatorName);
		if (emulatorProcess != null) {
			emulatorProcess.destroy();
			ThinEmulatorApp.runningAndroidEmulator.remove(emulatorName);
			System.out.println("***Emulator "+emulatorName+" stopped!!!");
		} else {
			System.out.println("***Emulator not running!!!");
		}
	}
	
	@RequestMapping(value = "/emulators/{emulatorName}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String,String>> removeUserAndroidEmulator(@PathVariable String userId, @PathVariable String emulatorName){
		System.out.println("userId"+userId+"emulatorName:"+emulatorName);
		HttpStatus deleteStatus;
		HashMap<String,String> responseBody = new HashMap<String, String>();
		String opStatus = mongoDBAdapter.removeEmulator(userId, emulatorName);
		//TODO check status of operation
		if (opStatus.equals("success")) {
			deleteStatus = HttpStatus.NO_CONTENT;
			responseBody = null;
		} else {
			deleteStatus = HttpStatus.OK;
			responseBody.put("Errors", opStatus);
		}
		return new ResponseEntity<Map<String,String>>(responseBody, new HttpHeaders(), deleteStatus);
	}
	
}