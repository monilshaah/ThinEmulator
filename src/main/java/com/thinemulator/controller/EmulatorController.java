package com.thinemulator.controller;
 
import java.util.ArrayList;
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
import com.thinemulator.beans.UserBean;

/**
 * Emulator Controller Class
 * All responses are in JSON format only.
 * 
 */
@RestController
@RequestMapping("/users/{userId}")
public class EmulatorController {
 	AndroidEmulatorAdapter androidEmulatorAdapter = new AndroidEmulatorAdapter();
 	MongoDBAdapter mongoDBAdapter = new MongoDBAdapter();
 	
 	/**
 	 * Get user profile
 	 * @param userId
 	 * @return userBean as JSON
 	 */
 	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
 	public UserBean getUsers(@PathVariable String userId) {
 		System.out.println("***calling DB connection...");
 		UserBean user = mongoDBAdapter.getEmulatorUser(userId);
 		//TODO check if user = null or not
 		System.out.println("***user: "+user);
 		return user;
 	}
 	
 	/**
 	 * Create user configured emulator and add it to user emulator list
 	 * @param userId
 	 * @param newDeviceConfig
 	 * @param validationResult
 	 * @return operation status and errors if any as JSON
 	 */
 	@RequestMapping(value = "/emulators", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
 	public ResponseEntity<Map<String,String>> createUserAndroidEmulator(@PathVariable String userId, @RequestBody DeviceConfig newDeviceConfig, BindingResult validationResult) {
 		HttpStatus createdStatus;
 		Map<String, String> responseBody = new HashMap<String, String>();
 		if (validationResult.hasErrors()) {
 			List<ObjectError> errorList = validationResult.getAllErrors();
 			String errors = "";
 			int cnt = 0;
 			while(cnt < errorList.size()) {
 				errors += errorList.get(cnt).getDefaultMessage()+"; ";
 				cnt++;
 			}
 			createdStatus = HttpStatus.BAD_REQUEST;
 			responseBody.put("success", "false");
 			responseBody.put("errors", errors);
 		} else {
 			AndroidEmulator newAndroidEmulator = mongoDBAdapter.getDevice(newDeviceConfig.getDeviceType());
 			androidEmulatorAdapter.createEmulator(newDeviceConfig.getDeviceName(),newAndroidEmulator.getEmulatorTargetId(),newAndroidEmulator.getEmulatorDeviceId());
 			String opStatus = mongoDBAdapter.addEmulator(userId, newDeviceConfig);
 			//TODO check status of operation
 			if (opStatus.equals("success")) {
 				createdStatus = HttpStatus.CREATED;
 				responseBody.put("success","true");
 			} else {
 				createdStatus = HttpStatus.OK;
 				responseBody.put("success", "false");
 				responseBody.put("errors", opStatus);
 			}
 		}
 		return new ResponseEntity<Map<String,String>>(responseBody, new HttpHeaders(), createdStatus);
 	}
 	
 	/**
 	 * Get list of user configured emulator lists
 	 * @param userId
 	 * @return emulator list as JSON
 	 */
 	@RequestMapping(value = "/emulators", method = RequestMethod.GET, produces = "application/json")
 	public Map<String, ArrayList<DeviceConfig>> listUserEmulator(@PathVariable String userId) {
 		UserBean user = mongoDBAdapter.getEmulatorUser(userId);
 		Map<String, ArrayList<DeviceConfig>> emulatorList = new HashMap<String, ArrayList<DeviceConfig>>();
 		emulatorList.put("EmulatorList", user.getEmulatorList());
 		return emulatorList;
 	}
 	
 	/**
 	 * Start the user selected emulator
 	 * @param emulatorName
 	 * @return status of emulator
 	 */
 	@RequestMapping(value = "/emulators/{emulatorName}", method = RequestMethod.GET)
 	public ResponseEntity<Map<String,String>> startUserAndroidEmulator(@PathVariable String emulatorName) {
 		//TODO Need to solve emulator naming issue in case of multiple users giving same name. One-way: use emualtorName as userId + emulatorName
 		Map<String, String> responseBody = new HashMap<String, String>();
 		Process emulatorProcess = androidEmulatorAdapter.startEmulator(emulatorName);
 		if (emulatorProcess != null) {
 			ThinEmulatorApp.runningAndroidEmulator.put(emulatorName, emulatorProcess);
 			responseBody.put("success", "true");
 		} else {
 			System.out.println("***Emulator not launched!!!");
 			responseBody.put("success", "false");
 			responseBody.put("errors", "emulator is not launched");
 		}
 		return new ResponseEntity<Map<String,String>>(responseBody, new HttpHeaders(), HttpStatus.OK);
 	}
 	
 	/**
 	 * Stop emulators which has been started previously
 	 * @param emulatorName
 	 * @return operation status and errors if any as JSON
 	 */
 	@RequestMapping(value = "/emulators/{emulatorName}", method = RequestMethod.PUT)
 	public ResponseEntity<Map<String,String>> stopUserAndroidEmulator(@PathVariable String emulatorName) {
 		Map<String, String> responseBody = new HashMap<String, String>();
 		//TODO Follow startUserAndroidEmulator approach for addressing emulators
 		//TODO Stop multiple instance of same emulator
 		Process emulatorProcess = ThinEmulatorApp.runningAndroidEmulator.get(emulatorName);
 		if (emulatorProcess != null) {
 			emulatorProcess.destroy();
 			ThinEmulatorApp.runningAndroidEmulator.remove(emulatorName);
 			responseBody.put("success", "true");
 			System.out.println("***Emulator "+emulatorName+" stopped!!!");
 		} else {
 			responseBody.put("success", "false");
 			responseBody.put("errors", "emulator is not running");
 			System.out.println("***Emulator not running!!!");
 		}
 		return new ResponseEntity<Map<String,String>>(responseBody, new HttpHeaders(), HttpStatus.OK);
 	}
 	
 	/**
 	 * Delete user emulator
 	 * @param userId
 	 * @param emulatorName
 	 * @return operation status and errors if any as JSON
 	 */
 	@RequestMapping(value = "/emulators/{emulatorName}", method = RequestMethod.DELETE)
 	public ResponseEntity<Map<String,String>> removeUserAndroidEmulator(@PathVariable String userId, @PathVariable String emulatorName){
 		System.out.println("userId"+userId+"emulatorName:"+emulatorName);
 		HttpStatus deleteStatus;
 		HashMap<String,String> responseBody = new HashMap<String, String>();
 		String opStatus = mongoDBAdapter.removeEmulator(userId, emulatorName);
 		//TODO check status of operation
 		if (opStatus.equals("success")) {
 			androidEmulatorAdapter.deleteEmulator(emulatorName);
 			deleteStatus = HttpStatus.NO_CONTENT;
 			responseBody.put("success", "true");
 		} else {
 			deleteStatus = HttpStatus.OK;
 			responseBody.put("success", "false");
 			responseBody.put("errors", opStatus);
 		}
 		//TODO check status of command
 		return new ResponseEntity<Map<String,String>>(responseBody, new HttpHeaders(), deleteStatus);
 	}
 	
}

