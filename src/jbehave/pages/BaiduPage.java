package jbehave.pages;

import java.util.List;

import jbehave.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Apr 18, 2014 3:58:50 PM
 * 
 */

public class BaiduPage extends BasePage {

	private WebElement searchField;
	private WebElement searchButton;

	public void openHomePage() {
		openUrl("/");
	}

	public void searchText() {
		searchField = findElementBy(By.id("kw1"));
		searchButton = findElementBy(By.id("su1"));
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

}
