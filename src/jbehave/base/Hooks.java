package jbehave.base;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Feb 19, 2014 10:37:50 AM
 * 
 */

public class Hooks {

	public enum Browser {
		FIREFOX,
		IE32,
		IE64,
		CHROME
	}

	private static String baseUrl = SeleniumUtil.getTargetUrl();
	private static String testingBrowser = SeleniumUtil.getBrowser();

	public static String getTestingBaseUrl() {
		return baseUrl;
	}

	public static Browser getTestingBrowser() {
		if (testingBrowser.equals("CHROME"))
			System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		if (testingBrowser.equals("IE32"))
			System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer32.exe");
		if (testingBrowser.equals("IE64"))
			System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer64.exe");
		return Browser.valueOf(Browser.class, System.getProperty("browser", testingBrowser));
	}

	public static void start() {
		try {
			PageContainer.getInstance().lanuchTesting();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void stop() {
		try {
			PageContainer.getInstance().finishTesting();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<String> verifyItemFormater(List<String> tableHeader) {
		List<String> verifyItems = new ArrayList<String>();
		for (int i = 0; i < tableHeader.size(); i++)
			verifyItems.add(tableHeader.get(i).trim().replaceAll(" ", "_"));
		return verifyItems;
	}

	public static String giveUniqueIdAfter(String origin) {
		UUID uuid = UUID.randomUUID();
		StringBuffer converted = new StringBuffer();
		converted.append(origin);
		converted.append(uuid.toString().replace("-", ""));
		return converted.toString();
	}

	public static String giveUniqueIdBefore(String origin) {
		UUID uuid = UUID.randomUUID();
		StringBuffer converted = new StringBuffer();
		converted.append(uuid.toString().replace("-", ""));
		converted.append(origin);
		return converted.toString();
	}

	public static String cutCurrencySymbol(String origin) {
		return String.valueOf(origin.toCharArray(), 1, origin.toCharArray().length - 1);
	}
}
