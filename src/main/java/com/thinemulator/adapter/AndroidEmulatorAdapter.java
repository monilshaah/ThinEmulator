package com.thinemulator.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.thinemulator.beans.AndroidEmulator;
import com.thinemulator.utility.Config;

public class AndroidEmulatorAdapter {
	public static final String ANDRIOD_SDK_ADDRESS = Config.readProperties().getProperty("ANDRIOD_SDK_ADDRESS");
	public static final String ANDROID_ADDRESS = Config.readProperties().getProperty("ANDROID_ADDRESS");
	public static final String EMULATOR_ADDRESS = Config.readProperties().getProperty("EMULATOR_ADDRESS");
	public static final String ANDROID_ADB_PATH = Config.readProperties().getProperty("ANDROID_ADB_PATH");
	public static final String CREATE_ANDROID = Config.readProperties().getProperty("CREATE_ANDROID");
	public static final String DELETE_ANDROID = Config.readProperties().getProperty("DELETE_ANDROID");
	public static final String DEVICE_DEFINITION = Config.readProperties().getProperty("DEVICE_DEFINITION");
	public static final String ANDROID_AVD = Config.readProperties().getProperty("ANDROID_AVD");
	public static final String CREATE_ANDROID_TARGET = Config.readProperties().getProperty("CREATE_ANDROID_TARGET");
	public static final String CREATE_ANDROID_PATH = Config.readProperties().getProperty("CREATE_ANDROID_PATH");
	public static final String WAIT_FOR_ADB = Config.readProperties().getProperty("WAIT_FOR_ADB");
	public static final String GET_DEVICES = Config.readProperties().getProperty("GET_DEVICES");
	public static final String DEVICE_STATUS = Config.readProperties().getProperty("DEVICE_STATUS");
	public static final String GET_DEVICE_STATUS_COMMAND = Config.readProperties().getProperty("GET_DEVICE_STATUS_COMMAND");
	public static final String INSTALL_COMMAND = Config.readProperties().getProperty("INSTALL_COMMAND");
	public static final String ANDROID_APK_FILE = Config.readProperties().getProperty("ANDROID_APK_FILE");
	public static final String START_ACTIVITY_VIEW = Config.readProperties().getProperty("START_ACTIVITY_VIEW");

	/**
	 * emulatorName : name the emulator which you want to create
	 * targetId : give target id, use command [android list targets] and [./android list targets == for linux/mac ] 
	 * deviceId : give device id, use command [android list device] and [./android list device == for linux/mac ]
	 * @param emulatorName
	 * @param targetId
	 * @throws IOException
	 */
	public void createEmulator(AndroidEmulator newEmulator) {
		Runtime runTime = Runtime.getRuntime();
		try {
		System.out.println(ANDRIOD_SDK_ADDRESS.concat(ANDROID_ADDRESS).concat(CREATE_ANDROID).concat(ANDROID_AVD).concat(newEmulator.getEmulatorName()).concat(CREATE_ANDROID_TARGET).concat(newEmulator.getEmulatorTargetId()).concat(DEVICE_DEFINITION).concat(newEmulator.getEmulatorDeviceId()));
		Process launchEmulatorProcess = runTime.exec(ANDRIOD_SDK_ADDRESS.concat(ANDROID_ADDRESS).concat(CREATE_ANDROID).concat(ANDROID_AVD).concat(newEmulator.getEmulatorName()).concat(CREATE_ANDROID_TARGET).concat(newEmulator.getEmulatorTargetId()).concat(DEVICE_DEFINITION).concat(newEmulator.getEmulatorDeviceId()));
		/* InputStream stderr = launchEmulatorProcess.getErrorStream();
         InputStreamReader isr = new InputStreamReader(stderr);
         BufferedReader br = new BufferedReader(isr);
         String line = null;
         System.out.println("<ERROR>");
         while ( (line = br.readLine()) != null)
             System.out.println(line);
         System.out.println("</ERROR>");*/
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
		Process launchEmulatorProcess = runTime.exec(ANDRIOD_SDK_ADDRESS.concat(EMULATOR_ADDRESS).concat(" -avd ").concat(emulatorName));
		System.out.println(ANDRIOD_SDK_ADDRESS.concat(EMULATOR_ADDRESS).concat(" -avd ").concat(emulatorName));
		/*InputStream stderr = launchEmulatorProcess.getErrorStream();
        InputStreamReader isr = new InputStreamReader(stderr);
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        System.out.println("<ERROR>");
        while ( (line = br.readLine()) != null)
            System.out.println(line);
        System.out.println("</ERROR>");*/
		return launchEmulatorProcess;
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteEmulator(String emulatorName) {
		Runtime runTime = Runtime.getRuntime();
		try {
		System.out.println(ANDRIOD_SDK_ADDRESS.concat(ANDROID_ADDRESS).concat(DELETE_ANDROID).concat(ANDROID_AVD).concat(emulatorName));
		Process launchEmulatorProcess = runTime.exec(ANDRIOD_SDK_ADDRESS.concat(ANDROID_ADDRESS).concat(DELETE_ANDROID).concat(ANDROID_AVD).concat(emulatorName));
		} catch(IOException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
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
