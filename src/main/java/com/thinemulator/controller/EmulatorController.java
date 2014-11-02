package com.thinemulator.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thinemulator.ThinEmulatorApp;
import com.thinemulator.adapter.AndroidEmulatorAdapter;
import com.thinemulator.beans.AndroidEmulator;

@RestController
@RequestMapping("/android")
public class EmulatorController {
	AndroidEmulatorAdapter androidEmulatorAdapter = new AndroidEmulatorAdapter();
	
	@RequestMapping(value = "/emulator", method = RequestMethod.POST, consumes = "application/json")
	public void createAndroidEmulator(@RequestBody AndroidEmulator newAndroidEmulator){
		androidEmulatorAdapter.createEmulator(newAndroidEmulator);
	}
	
	@RequestMapping(value = "/emulator/{emulatorName}", method = RequestMethod.GET, consumes = "application/json")
	public void startAndroidEmulator(@PathVariable String emulatorName) {
		Process emulatorProcess = androidEmulatorAdapter.startEmulator(emulatorName);
		if (emulatorProcess != null) {
			// TODO Use concatenated userName and emulatorName as key to HashMap
			ThinEmulatorApp.runningAndroidEmulator.put(emulatorName, emulatorProcess);
		} else {
			System.out.println("***Emulator not launched!!!");
		}
	}
	
	@RequestMapping(value = "/emulator/{emulatorName}/stop", method = RequestMethod.GET, consumes = "application/json")
	public void stopAndroidEmulator(@PathVariable String emulatorName) {
		Process emulatorProcess = ThinEmulatorApp.runningAndroidEmulator.get(emulatorName);
		if (emulatorProcess != null) {
			emulatorProcess.destroy();
			ThinEmulatorApp.runningAndroidEmulator.remove(emulatorName);
			System.out.println("***Emulator "+emulatorName+" stopped!!!");
		} else {
			System.out.println("***Emulator not running!!!");
		}
	}
}