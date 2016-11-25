package jbehave.base;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author nianjun
 *
 */

public class SeleniumConfig {

    public static final String CONFIG_SELENIUM_SERVER_URL = "selenium.server.url";

    public static final String CONFIG_TARGET_BASE_URL = "target.base.url";

    public static final String CONFIG_SELENIUM_ZAP_PROXY_HOST = "zapProxyHost";

    private static Properties props = new Properties();

    public static void init(String configFile) {
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(configFile));
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SeleniumConfig() {}

    public static String getValue(String key) {
        return props.getProperty(key);
    }

}
