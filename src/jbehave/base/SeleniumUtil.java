package jbehave.base;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Feb 19, 2014 10:50:50 AM
 * 
 */

public class SeleniumUtil {

	private final static String configFile = "src/jbehave/resources/config.properties";

	private static Properties loadProperties() {
		InputStream in = null;
		Properties properties = new Properties();
		try {
			in = new BufferedInputStream(new FileInputStream(configFile));
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return properties;
	}

	public static String getTargetUrl() {
		return loadProperties().get("TargetUrl").toString();
	}

	public static String getBrowser() {
		return loadProperties().get("Browser").toString();
	}
}
