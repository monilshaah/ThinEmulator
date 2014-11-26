package com.thinemulator;

import java.util.HashMap;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ThinEmulatorApp {
	public static HashMap<String, Process> runningAndroidEmulator = new HashMap<String, Process>();
	
	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		return factory.createMultipartConfig();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ThinEmulatorApp.class, args);
	}
}
