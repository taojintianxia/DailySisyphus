package jbehave.steps;

import jbehave.base.BaseStep;
import jbehave.pages.BaiduPage;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author Kane.Sun
 * @version Apr 18, 2014 3:50:08 PM
 * 
 */
@Component
@Scope("prototype")
public class TestSteps extends BaseStep {

	private BaiduPage baiduPage;

	@Given("I access Baidu site")
	public void iAccessBaiduSite() {
		baiduPage = new BaiduPage();
		baiduPage.openHomePage();
	}

	@When("I search text on Baidu")
	public void iSearchTextOnBaidu() {

	}

	@Then("I should see the search result on baidu")
	public void iShouldSeeTheSearchResultOnBaidu() {

	}

	@When("I nevigate a result link on baidu randomly")
	public void iNevigateAResultLinkOnBaiduRandomly() {

	}
}
