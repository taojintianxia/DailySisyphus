package jbehave.pages;

import jbehave.base.BasePage;

/**
 *
 *
 * @author Kane.Sun
 * @version Apr 18, 2014 3:58:50 PM
 * 
 */

public class BaiduPage extends BasePage {

	public void openHomePage() {
		openUrl("/");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
