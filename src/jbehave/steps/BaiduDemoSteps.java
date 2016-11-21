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

    @When("我在百度首页搜索了个关键字")
    public void iSearchTextOnBaidu() {
        baiduPage.searchText();
    }

    @Then("我应该看到搜索结果并仔细看了一会")
    public void iShouldSeeTheSearchResultOnBaidu() throws InterruptedException {
        assertTrue(baiduPage.isSearchResultAppear());
        Thread.sleep(2000);
    }

    @When("我胡乱点击了一个链接")
    public void iNevigateAResultLinkOnBaiduRandomly() {
        baiduPage.nevigateLinkRandomly();
    }

    @Then("我看到了链接的具体内容")
    public void iSeeTheContentOfTheLink() {
        baiduPage.nevigateLinkRandomly();
    }
}
