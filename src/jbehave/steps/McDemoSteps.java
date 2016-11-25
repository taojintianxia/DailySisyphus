package jbehave.steps;

import static junit.framework.Assert.assertTrue;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.stereotype.Component;

import jbehave.base.BaseStep;
import jbehave.pages.McDemoPage;

/**
 * @author nianjun
 * 
 */

@Component
public class McDemoSteps extends BaseStep {

    private McDemoPage mcDemoPage;

    @Given("我访问了MC的站点")
    public void iAccessMcSite() {
        mcDemoPage = new McDemoPage();
        mcDemoPage.openHomePage();
    }

    @When("我访问了MC的登录页面")
    public void iAccessMcLoginPage() {
        mcDemoPage.openLoginPage();
    }

    @Then("我应该看到MC的登录页面")
    public void iShouldSeeMcLoginPage() {
        assertTrue(mcDemoPage.isInLoginPage());
    }

    @When("我使用用户名密码登录MC")
    public void iLoginByUserNameandPassword() {
        mcDemoPage.login();
    }

    @Then("我应该看到MC的首页")
    public void iShouldSeeMcHomePage() {
        assertTrue(mcDemoPage.isInHomePage());
    }

    @When("脂肪についての王笑います")
    public void ridiculeMussleWong() throws InterruptedException {
        Thread.sleep(2000);
    }

    @Then("당신은 어떤 문제가 막대 안")
    public void justAtest() {
        System.out.println("应该没问题吧");
    }

    @When("I navigate to \"Data Management Platform\"")
    public void navigateToDataManagementPlatform() {
        mcDemoPage.navigateToDataManagementPlatformPage();
    }

    @Then("I should see \"Data Management Platform\" page")
    public void iShouldSeeDataManagementPlatformPage() {
        assertTrue(mcDemoPage.isInDataManagementPlantformPage());
    }

}
