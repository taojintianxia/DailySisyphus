package jbehave.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jbehave.base.BasePage;


/**
 * @author nianjun
 * 
 */

public class BaiduDemoPage extends BasePage {

    private WebElement searchField;
    private WebElement searchButton;

    public void openHomePage() {
        openUrl("/");
    }

    public void searchText() {
        searchField = findElementBy(By.id("kw"));
        searchButton = findElementBy(By.id("su"));
        typeTextBox(searchField, "Jbehave");
        clickOn(searchButton);
    }

    public boolean isSearchResultAppear() {
        return findElementBy(By.id("content_left")) != null;
    }

    public void nevigateLinkRandomly() {
        List<WebElement> links = findElementsBy(By.cssSelector(".t a"));
        clickOn(links.get(random.nextInt(links.size())));
    }

    public void iSeeTheContentOfTheLink() {
        System.out.println("running here");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
