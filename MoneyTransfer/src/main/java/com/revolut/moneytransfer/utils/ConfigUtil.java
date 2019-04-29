package com.revolut.moneytransfer.utils;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public final class ConfigUtil {

	private static Logger LOGGER = Logger.getLogger(ConfigUtil.class);

	private ConfigUtil() {

	}

	private static String fileName = System.getProperty("application.properties");
	private static ResourceBundle resourceBundle;
	static {

		if (fileName == null)
			fileName = "application";

		try {
			resourceBundle = ResourceBundle.getBundle(fileName);
		} catch (Exception e) {
			LOGGER.error("Exception while loading config file.", e);
		}

	}

	public static String getValue(String key) {
		String value = resourceBundle.getString(key);
		return value;
	}

}
