package com.example.calculator.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private static final Properties properties = new Properties();
	
	public static void loadProperties(String env) {
		try {
			FileInputStream fis = new FileInputStream("src/test/resources/config/" + env + ".properties");
			properties.load(fis);
		} catch (IOException e) {
			throw new RuntimeException("Unable to load environment config: " + env, e);
		}
	}
	
	public static String get(String key) {
		return properties.getProperty(key);
	}
}
