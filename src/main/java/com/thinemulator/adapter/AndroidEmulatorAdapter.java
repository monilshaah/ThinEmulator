package com.thinemulator.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.thinemulator.beans.AndroidEmulator;

public class AndroidEmulatorAdapter {
	public static final String ANDROID_ADDRESS = "/Users/ramnivasindani/Downloads/adt-bundle-mac-x86_64-20140702/sdk/tools/android";
	public static final String EMULATOR_ADDRESS = "/Users/ramnivasindani/Downloads/adt-bundle-mac-x86_64-20140702/sdk/tools/emulator";
	public static final String ANDROID_ADB_PATH = "/Users/ramnivasindani/Downloads/adt-bundle-mac-x86_64-20140702/sdk/platform-tools/adb";
	public static final String CREATE_ANDROID = " -s create ";
	public static final String ECHO_NO = "echo no | ";
	public static final String CREATE_ANDROID_AVD = "avd -n ";
	public static final String CREATE_ANDROID_TARGET = " -t ";
	public static final String CREATE_ANDROID_PATH = " -p /Users/Monil/.android/avd";
	public static final String WAIT_FOR_ADB = "wait-for-device";
	public static final String GET_DEVICES = "devices";
	public static final String DEVICE_STATUS = "device";
	public static final String GET_DEVICE_STATUS_COMMAND = "get-state"; //"status-window";//
	public static final String INSTALL_COMMAND = "install";
	public static final String ANDROID_APK_FILE = "/Users/Monil/Documents/Study/273/Project/RemindMe.apk";
	public static final String START_ACTIVITY_VIEW = "shell am start \"intent:#Intent;scheme=customapp;package=com.batteryalert.home.Home\"";

	/**
	 * emulatorName : name the emulator which you want to create
	 * targetId : give target id, use command [android list targets] and [./android list targets == for linux/mac ] 
	 * 
	 * @param emulatorName
	 * @param targetId
	 * @throws IOException
	 */
	public void createEmulator(AndroidEmulator newEmulator) {
		Runtime runTime = Runtime.getRuntime();
		try {
		System.out.println(ANDROID_ADDRESS.concat(" ").concat(CREATE_ANDROID).concat(CREATE_ANDROID_AVD).concat(newEmulator.getEmulatorName()).concat(CREATE_ANDROID_TARGET).concat(newEmulator.getEmulatorTargetId()));
		Process launchEmulatorProcess = runTime.exec(ECHO_NO.concat(ANDROID_ADDRESS.concat(" ").concat(CREATE_ANDROID).concat(CREATE_ANDROID_AVD).concat(newEmulator.getEmulatorName()).concat(CREATE_ANDROID_TARGET).concat(newEmulator.getEmulatorTargetId())));
		 InputStream stderr = launchEmulatorProcess.getErrorStream();
         InputStreamReader isr = new InputStreamReader(stderr);
         BufferedReader br = new BufferedReader(isr);
         String line = null;
         System.out.println("<ERROR>");
         while ( (line = br.readLine()) != null)
             System.out.println(line);
         System.out.println("</ERROR>");
		//System.out.println("Command error : "+ launchEmulatorProcess.waitFor());
		//Process createEmulator = runTime.exec("createemulator.sh");
		} catch(IOException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * start pre-configured emulator.
	 * update to return emulator running instance id for further use.
	 * @param emulatorName
	 */
	public Process startEmulator(String emulatorName) {
		Runtime runTime = Runtime.getRuntime();
		try {
		//Process androidTargetVersions = runTime.exec(ANDROID_ADDRESS.concat(" ").concat(emulatorName));
			
		Process launchEmulatorProcess = runTime.exec(EMULATOR_ADDRESS.concat(" -avd ").concat(emulatorName));
		System.out.println(EMULATOR_ADDRESS.concat(" -avd ").concat(emulatorName));
		InputStream stderr = launchEmulatorProcess.getErrorStream();
        InputStreamReader isr = new InputStreamReader(stderr);
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        System.out.println("<ERROR>");
        while ( (line = br.readLine()) != null)
            System.out.println(line);
        System.out.println("</ERROR>");
		return launchEmulatorProcess;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * install APK on emulator.
	 * Needs to update to work with emulator device id.
	 * @param emulatorName
	 * @param APKPath
	 * @throws IOException
	 */
	public void installAPK(String emulatorName, String APKPath) throws IOException {
		Runtime runTime = Runtime.getRuntime();
		Process installAPKOnEmulator = runTime.exec(ANDROID_ADB_PATH+" "+WAIT_FOR_ADB+" "+INSTALL_COMMAND+" "+ANDROID_APK_FILE);
	}
}
