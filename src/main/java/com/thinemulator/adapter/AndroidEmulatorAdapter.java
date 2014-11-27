package com.thinemulator.adapter;

import java.io.IOException;
import java.util.Properties;

import com.thinemulator.beans.AndroidEmulator;
import com.thinemulator.utility.Config;

public class AndroidEmulatorAdapter {
	static Properties properties = Config.readProperties();
	public static final String ANDRIOD_SDK_ADDRESS = properties.getProperty("ANDRIOD_SDK_ADDRESS");
	public static final String ANDROID_ADDRESS = properties.getProperty("ANDROID_ADDRESS").concat(" ");
	public static final String EMULATOR_ADDRESS = properties.getProperty("EMULATOR_ADDRESS").concat(" ");
	public static final String ANDROID_ADB_PATH = properties.getProperty("ANDROID_ADB_PATH").concat(" ");
	public static final String CREATE_ANDROID = properties.getProperty("CREATE_ANDROID").concat(" ");
	public static final String DELETE_ANDROID = properties.getProperty("DELETE_ANDROID").concat(" ");
	public static final String DEVICE_DEFINITION = " ".concat(properties.getProperty("DEVICE_DEFINITION")).concat(" ");
	public static final String ANDROID_AVD = properties.getProperty("ANDROID_AVD").concat(" ");
	public static final String CREATE_ANDROID_TARGET = " ".concat(properties.getProperty("CREATE_ANDROID_TARGET")).concat(" ");
	public static final String CREATE_ANDROID_PATH = properties.getProperty("CREATE_ANDROID_PATH");
	public static final String WAIT_FOR_ADB = properties.getProperty("WAIT_FOR_ADB");
	public static final String GET_DEVICES = properties.getProperty("GET_DEVICES");
	public static final String DEVICE_STATUS = properties.getProperty("DEVICE_STATUS");
	public static final String GET_DEVICE_STATUS_COMMAND = properties.getProperty("GET_DEVICE_STATUS_COMMAND");
	public static final String INSTALL_COMMAND = properties.getProperty("INSTALL_COMMAND");
	public static final String ANDROID_APK_FILE = properties.getProperty("ANDROID_APK_FILE");
	public static final String START_ACTIVITY_VIEW = properties.getProperty("START_ACTIVITY_VIEW");

	

	/**
	 * emulatorName : name the emulator which you want to create
	 * targetId : give target id, use command [android list targets] and [./android list targets == for linux/mac ] 
	 * deviceId : give device id, use command [android list device] and [./android list device == for linux/mac ]
	 * @param emulatorName
	 * @param targetId
	 * @throws IOException
	 */
	//public void createEmulator(AndroidEmulator newEmulator) {
	public void createEmulator(String deviceName, String targetId, String deviceId) {
		Runtime runTime = Runtime.getRuntime();
		try {
		System.out.println(ANDRIOD_SDK_ADDRESS.concat(ANDROID_ADDRESS).concat(CREATE_ANDROID).concat(ANDROID_AVD).concat(deviceName).concat(CREATE_ANDROID_TARGET).concat(targetId).concat(DEVICE_DEFINITION).concat(deviceId));
		Process launchEmulatorProcess = runTime.exec(ANDRIOD_SDK_ADDRESS.concat(ANDROID_ADDRESS).concat(CREATE_ANDROID).concat(ANDROID_AVD).concat(deviceName).concat(CREATE_ANDROID_TARGET).concat(targetId).concat(DEVICE_DEFINITION).concat(deviceId));
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
