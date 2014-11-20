package com.thinemulator.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Properties properties;

    public static Properties readProperties() {
        if (null == properties) {
            try {
                properties = readPropertyValues();
            } catch (IOException e) {
                System.out.println("ERROR reading configurations"); // Just fail if can't read properties
            }
        }
        return properties;
    }

    private static Properties readPropertyValues() throws IOException {
        Properties properties = new Properties();
        String userDir = System.getProperty("user.dir");
		InputStream inputProperties = new FileInputStream(userDir + "/ThinEmulator.properties");
        properties.load(inputProperties);
        return properties;
    }
}

