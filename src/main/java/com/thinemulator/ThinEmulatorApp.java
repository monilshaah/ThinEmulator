package com.thinemulator;

import java.util.HashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ThinEmulatorApp {
	public static HashMap<String, Process> runningAndroidEmulator = new HashMap<String, Process>();
	
	public static void main(String[] args) {
		SpringApplication.run(ThinEmulatorApp.class, args);
	}
}
