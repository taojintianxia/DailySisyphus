/**
 * 
 */
package base;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author hao-chen2
 */
public class SeleniumTestContext extends TestContext {

	private static SeleniumTestContext CONTEXT = new SeleniumTestContext();

	private String seleniumServerURL;

	private String targetBaseURL;

	private SeleniumTestContext() {
	}

	public static SeleniumTestContext getInstance() {
		return CONTEXT;
	}

	public String getSeleniumServerURL() {
		if (StringUtils.isBlank(seleniumServerURL)) {
			seleniumServerURL = SeleniumConfig.getValue(SeleniumConfig.CONFIG_SELENIUM_SERVER_URL);
		}
		return seleniumServerURL;
	}

	public void setSeleniumServerURL(String seleniumServerURL) {
		this.seleniumServerURL = seleniumServerURL;
	}

	public String getTargetBaseURL() {
		if (StringUtils.isBlank(targetBaseURL)) {
			targetBaseURL = SeleniumConfig.getValue(SeleniumConfig.CONFIG_TARGET_BASE_URL);
		}
		return targetBaseURL;
	}

	public void setTargetBaseURL(String targetBaseURL) {
		this.targetBaseURL = targetBaseURL;
	}

}
