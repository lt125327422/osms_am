package com.itecheasy.common.util;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyFileLoader {
	private static Logger log = Logger.getLogger(PropertyFileLoader.class);
		
	private Properties properties = null;

	public PropertyFileLoader(String filename) {
		properties = loadFile(filename);
		log.info(filename + " loaded.");
		
	}
	
	public Enumeration<Object> getAllKeys() {
		return properties.keys();
	}
	
	public String get(Object key) {
		return (String)properties.get(key);
	}

	private static Properties loadFile(String filename) {
		Properties prop = new Properties();
		try {
			InputStream inputStream = PropertyFileLoader.class.getClassLoader()
					.getResourceAsStream(filename);
			prop.load(inputStream);
			inputStream.close();
		} catch (Exception e) {
			log.error("can not read config file " + filename, e);
		}
		return prop;
	}
}
