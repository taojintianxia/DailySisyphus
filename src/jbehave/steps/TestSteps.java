package jbehave.steps;

import jbehave.base.BaseStep;
import jbehave.pages.BaiduPage;

import org.jbehave.core.annotations.Given;

/**
 *
 *
 * @author Kane.Sun
 * @version Apr 18, 2014 3:50:08 PM
 * 
 */

public class TestSteps extends BaseStep {

	private BaiduPage baiduPage;

	@Given("I access Baidu site")
	public void iAccessBaiduSite() {
		baiduPage = new BaiduPage();
		baiduPage.openHomePage();
	}

}
