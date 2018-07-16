package com.itecheasy.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class DeployProperties {

	private static Logger logger = Logger.getLogger(DeployProperties.class);

	private static final String PROPERTIE_FILE_NAME = "deploy_config.properties";

	private static long lastModified = 0L;

	private Properties properties = null;

	private static DeployProperties instance = new DeployProperties();;

	private File file = null;

	public static DeployProperties getInstance() {
		return instance;
	}

	private DeployProperties() {
		try {
			Resource resource = new ClassPathResource(PROPERTIE_FILE_NAME);
			file = resource.getFile();
			lastModified = file.lastModified();
			if (lastModified == 0) {
				logger.error(PROPERTIE_FILE_NAME + " file does not exist!");
			}
			properties = new Properties();
			properties.load(resource.getInputStream());

		} catch (IOException e) {
			logger.error("can not read config file " + PROPERTIE_FILE_NAME);
		}
		logger.info(PROPERTIE_FILE_NAME + " loaded.");
	}

	public final String getProperty(String key) {
		return getProperty(key, StringUtils.EMPTY);
	}

	public final String getProperty(String key, String defaultValue) {
		long newTime = file.lastModified();
		if (newTime == 0) {
			return defaultValue;
		} else if (newTime > lastModified) {
			try {
				properties.clear();
				Resource resource = new ClassPathResource(PROPERTIE_FILE_NAME);
				properties.load(new FileInputStream(resource.getFile()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		lastModified = newTime;
		return properties.getProperty(key) == null ? defaultValue : properties.getProperty(key);
	}

	public String getAccessKeyId() {
		return getProperty("amazon.accessKeyId");
	}

	public String getSecretAccessKey() {
		return getProperty("amazon.secretAccessKey");
	}

	public String getAppName() {
		return getProperty("amazon.appName");
	}

	public String getServiceURL() {
		return getProperty("amazon.serviceURL");
	}

	public String getAppVersion() {
		return getProperty("amazon.appVersion");
	}

	public String getTempFile() {
		return getProperty("temp_file");
	}

	public String getAmazonSellerID() {
		return getProperty("amazon.SellerID");
	}

	public String getAmazonMarketplaceID() {
		return getProperty("amazon.MarketplaceID");
	}
}
