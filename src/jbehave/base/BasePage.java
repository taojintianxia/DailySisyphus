package jbehave.base;


/**
 * 
 * 
 * @author Kane.Sun
 * @version Feb 19, 2014 11:08:36 AM
 * 
 */

public class BasePage {
	protected WebDriverHelper helper;

	protected BasePage() {
		this.helper = WebDriverHelper.getInstance();
	}
}
