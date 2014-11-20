package com.thinemulator.utility;

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
                System.exit(-10); // Just fail if can't read properties
            }
        }
        return properties;
    }

    private static Properties readPropertyValues() throws IOException {
        Properties properties = new Properties();
        String propFileName = "ThinEmulator.properties";

        InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(propFileName);
        properties.load(inputStream);
        if (null == inputStream) {
            throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath");
        }

        return properties;
    }
}

