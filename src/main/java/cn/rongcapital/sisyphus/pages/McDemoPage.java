package cn.rongcapital.sisyphus.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import cn.rongcapital.sisyphus.base.BasePage;

/**
 * @author nianjun
 * 
 */

public class McDemoPage extends BasePage {

    public void openHomePage() {
        openUrl("/");
    }

    public void openLoginPage() {
        openUrl("/html/signin/login.html");
    }

    public boolean isInLoginPage() {
        WebElement userName = findElementBy(By.id("userName"));
        WebElement passWord = findElementBy(By.id("passWord"));
        WebElement loginButtion = findElementBy(By.id("submit"));

        return userName != null && passWord != null && loginButtion != null;
    }

    public void login() {
        WebElement userName = findElementBy(By.id("userName"));
        WebElement passWord = findElementBy(By.id("passWord"));
        WebElement loginButtion = findElementBy(By.id("submit"));

        typeTextBox(userName, "user1");
        typeTextBox(passWord, "1");

        clickOn(loginButtion);
    }

    public boolean isInHomePage() {
        WebElement loginPageMenu = findElementBy(By.id("home"));
        return loginPageMenu.getAttribute("class").equals("topmenu-btn cur");
    }

    public void navigateToDataManagementPlatformPage() {
        WebElement navigationBar = findElementBy(By.id("dmp"));
        clickOn(navigationBar);
    }

    public boolean isInDataManagementPlantformPage() {
        // 相同变量,应该提取出来
        WebElement navigationBar = findElementBy(By.id("dmp"));
        return navigationBar.getAttribute("class").equals("topmenu-btn cur");
    }

}
