package jbehave.steps;

import static org.junit.Assert.assertTrue;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jbehave.base.BaseStep;
import jbehave.pages.BaiduDemoPage;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Apr 18, 2014 3:50:08 PM
 * 
 */
@Component
@Scope("prototype")
public class BaiduDemoSteps extends BaseStep {

    private BaiduDemoPage baiduPage;

    @Given("我访问了百度的首页")
    public void iAccessBaiduSite() {
        baiduPage = new BaiduDemoPage();
        baiduPage.openHomePage();
    }

    @When("I search text on Baidu")
    public void iSearchTextOnBaidu() {
        baiduPage.searchText();
    }

    @Then("I should see the search result on baidu")
    public void iShouldSeeTheSearchResultOnBaidu() {
        assertTrue(baiduPage.isSearchResultAppear());
    }

    @When("I navigate a result link on baidu randomly")
    public void iNevigateAResultLinkOnBaiduRandomly() {
        baiduPage.nevigateLinkRandomly();
    }
}
