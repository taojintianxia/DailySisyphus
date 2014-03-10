package jbehave.steps;

import jbehave.base.BasePage;
import jbehave.base.PageContainer;
import junit.framework.Assert;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Feb 19, 2014 10:16:53 AM
 * 
 */

public class SearchSteps extends BasePage {
	@Given("I access Baidu site")
	public void iAccessBaiduSite() throws Exception {
		PageContainer.getInstance().getBaiduPage().nevigateToHomePage();
	}

	@When("I search text on Baidu")
	public void iSearchTextOnBaidu() {
		PageContainer.getInstance().getBaiduPage().searchSpecificTextOnPage();
	}

	@Then("I should see the search result")
	public void iShouldSeeTheSearchResult() {
		Assert.assertTrue(PageContainer.getInstance().getBaiduPage().isExpectedResultShowUp());
	}

	@When("I nevigate a result link randomly")
	public void iNevigateAResultLinkRandomly() {
		PageContainer.getInstance().getBaiduPage().searchSpecificTextOnPage();
	}
}
