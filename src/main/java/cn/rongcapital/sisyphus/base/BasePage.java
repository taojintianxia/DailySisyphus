package cn.rongcapital.sisyphus.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.thoughtworks.selenium.DefaultSelenium;

/**
 * 
 * @author nianjun
 *
 */

public abstract class BasePage {

	protected final WebDriverProvider driverProvider = WebDriverProvider.getInstance();

	protected final WebDriver webDriver = driverProvider.get();

	protected final DefaultSelenium selenium = driverProvider.getSeleniumDriver();

	protected static final Log LOGGER = LogFactory.getLog(BasePage.class);

	private static final long TIME_OUT = 60;

	protected static final Random random = new Random();

	// --- !!!notice : All these following methods must be COMMON AND BASIC!!!
	// --- openBrowser
	protected void openUrl(String url) {
		if (getCurrentUrl().contains(";jsessionid=")) {
			String currentURL = getCurrentUrl();
			String parameter = "";
			String jsessionId = new String(currentURL.substring(currentURL.indexOf(";jsessionid=")));
			if (url.contains("?")) {
				parameter = url.substring(url.indexOf("?"), url.length());
				url = new String(url.substring(0, url.indexOf("?")));
			}
			if (jsessionId.contains("?")) {
				jsessionId = new String(jsessionId.substring(0, jsessionId.indexOf("?")));
			}

			url = url + jsessionId + parameter;
		}
		if (url.startsWith("http")) {
			webDriver.get(url);
		} else {
			webDriver.get(SeleniumTestContext.getInstance().getTargetBaseURL() + url);
		}
	}

	protected String getCurrentUrl() {
		return webDriver.getCurrentUrl();
	}

	protected String getTargetBaseUrl() {
		return SeleniumTestContext.getInstance().getTargetBaseURL();
	}

	/**
	 * @param give the locator in format of "id=","css=","name=","xpath=",etc...
	 *            or give the id/xpath directly
	 * @return boolean
	 */
	protected boolean isElementPresentBySelenium(String locator) {
		return selenium.isElementPresent(locator);
	}

	protected boolean isElementVisibleBySelenium(String locator) {
		return selenium.isVisible(locator);
	}

	protected boolean isCheckBoxChecked(String locator) {
		return selenium.isChecked(locator);
	}

	// --- basic operation
	protected void typeTextBox(WebElement element, String typeContent) {
		if (element != null) {
			element.clear();
			element.sendKeys(typeContent);
		} else {
			throw new AssertionError("Failed to locate the element: " + element);
		}
	}

	/**
	 * @param By class
	 * 
	 * @return
	 */
	protected void typeTextBoxBy(By byElement, String typeContent) {
		typeTextBox(findElementBy(byElement), typeContent);
	}

	protected void clickOn(WebElement element) {
		if (element != null) {
			Capabilities cp = ((HasCapabilities) webDriver).getCapabilities();
			if (cp.getBrowserName().equals("chrome")) {
				((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
			}
			element.click();
		} else {
			throw new AssertionError("Failed to locate the element: " + element);
		}
	}

	/**
	 * @param By class
	 * @return
	 */
	protected void clickBy(By byElement) {
		clickOn(findElementBy(byElement));
	}

	protected void refreshPage() throws Exception {
		webDriver.navigate().refresh();
	}

	// --- findElement
	protected WebElement findElementById(String idLocator) {
		return webDriver.findElement(By.id(idLocator));
	}

	protected WebElement findElementByXpath(String xpath) {
		return webDriver.findElement(By.xpath(xpath));
	}

	protected WebElement findElementByCSS(String cssLocator) {
		return webDriver.findElement(By.cssSelector(cssLocator));
	}

	protected WebElement findElementByLinkText(String linkText) {
		return webDriver.findElement(By.linkText(linkText));
	}

	protected WebElement findElementByName(String name) {
		return webDriver.findElement(By.name(name));
	}

	protected WebElement findElementBy(By by) {
		return webDriver.findElement(by);
	}

	// --- findChildElement
	protected WebElement findChildElementById(WebElement parent, String idLocator) {
		return parent.findElement(By.id(idLocator));
	}

	protected WebElement findChildElementByXpath(WebElement parent, String Xpath) {
		return parent.findElement(By.xpath(Xpath));
	}

	protected WebElement findChildElementByLinkText(WebElement parent, String linkText) {
		return parent.findElement(By.linkText(linkText));
	}

	protected WebElement findChildElementByName(WebElement parent, String name) {
		return parent.findElement(By.name(name));
	}

	protected WebElement findChildElementByCSS(WebElement parent, String cssLocator) {
		return parent.findElement(By.cssSelector(cssLocator));
	}

	protected WebElement findChildElementBy(WebElement parent, By by) {
		return parent.findElement(by);
	}

	// --- findElements
	protected List<WebElement> findElementsByXpath(String by) {
		return webDriver.findElements(By.xpath(by));
	}

	protected List<WebElement> findElementsByCSS(String cssSelector) {
		return webDriver.findElements(By.cssSelector(cssSelector));
	}

	protected List<WebElement> findElementsByName(String name) {
		return webDriver.findElements(By.name(name));
	}

	protected List<WebElement> findElementsByLinkText(String linkText) {
		return webDriver.findElements(By.linkText(linkText));
	}

	protected List<WebElement> findElementsBy(By by) {
		return webDriver.findElements(by);
	}

	// --- findChildrenElements
	protected List<WebElement> findChildrenElements(WebElement parent, By by) {
		return parent.findElements(by);
	}

	// --- findElement No Exception
	protected boolean isElementPresent(By by) {
		try {
			webDriver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			LOGGER.error("isElementPresent(By)", e); //$NON-NLS-1$
			return false;
		}
	}

	protected boolean isElementVisiable(By by) {
		try {
			return webDriver.findElement(by).isDisplayed();
		} catch (NoSuchElementException e) {
			LOGGER.error("isElementVisiable(By)", e); //$NON-NLS-1$
			return false;
		}
	}

	// --- findElementWait
	protected WebElement findElementByLinkTextWait(String linkText) {
		return waitForVisibilityOfElementLocated(By.linkText(linkText));
	}

	protected WebElement findElementByCSSWait(String cssSelector) {
		return waitForVisibilityOfElementLocated(By.cssSelector(cssSelector));
	}

	protected WebElement findElementByIdWait(String id) {
		return waitForVisibilityOfElementLocated(By.id(id));
	}

	protected WebElement findElementByXpathWait(String xpath) {
		return waitForVisibilityOfElementLocated(By.xpath(xpath));
	}

	protected WebElement findElementByNameWait(String name) {
		return waitForVisibilityOfElementLocated(By.name(name));
	}

	// --- select in List
	protected void selectOptionInListByText(WebElement element, String text) {
		if (element != null) {
			new Select(element).selectByVisibleText(text);
		} else {
			throw new AssertionError("Failed to locate the element: " + element);
		}
	}

	protected void selectOptionInListByIndex(WebElement element, int index) {
		if (element != null) {
			new Select(element).selectByIndex(index);
		} else {
			throw new AssertionError("Failed to locate the element: " + element);
		}
	}

	protected void selectOptionInListByValue(WebElement element, String value) {
		if (element != null) {
			new Select(element).selectByValue(value);
		} else {
			throw new AssertionError("Failed to locate the element: " + element);
		}
	}

	protected List<WebElement> getSelectedOptions(WebElement element) {
		if (element != null) {
			return new Select(element).getAllSelectedOptions();
		} else {
			throw new AssertionError("Failed to locate the element: " + element);
		}
	}

	protected List<String> getValueOfOptionsInSelectList(WebElement element) {
		if (element != null) {
			List<String> valueList = new ArrayList<>();
			List<WebElement> optionList = new Select(element).getOptions();
			for (int i = 0; i < optionList.size(); i++) {
				valueList.add(this.getValue(optionList.get(i)));
			}
			return valueList;
		} else {
			throw new AssertionError("Failed to locate the element: " + element);
		}
	}

	protected List<String> getTextOfOptionsInSelectList(WebElement element) {
		if (element != null) {
			List<String> textList = new ArrayList<>();
			List<WebElement> optionList = new Select(element).getOptions();
			for (int i = 0; i < optionList.size(); i++) {
				textList.add(this.getText(optionList.get(i)));
			}
			return textList;
		} else {
			throw new AssertionError("Failed to locate the element: " + element);
		}
	}

	// --- element operation
	protected String getText(WebElement element) {
		if (element != null) {
			return element.getText().trim();
		} else {
			throw new AssertionError("Failed to locate the element: " + element);
		}
	}

	protected String getValue(WebElement element) {
		if (element != null) {
			return element.getAttribute("value");
		} else {
			throw new AssertionError("Failed to locate the element: " + element);
		}
	}

	// --- assert test present
	protected boolean isTextPresentOnPage(String content) {
		boolean flag = true;
		try {
			webDriver.findElement(By.xpath("//*[contains(.,'" + content + "')]"));
		} catch (NoSuchElementException e) {
			flag = false;
			LOGGER.error("isTextPresentOnPage(String)", e); //$NON-NLS-1$		
		}
		return flag;
	}

	protected boolean isPageLoaded(By verifyItem, String verifyText) {
		return isTextPresentInElement(verifyItem, verifyText);
	}

	protected boolean isTextPresentInElement(By elementLocator, String text) {
		WebDriverWait wait = new WebDriverWait(webDriver, TIME_OUT);
		return wait.until(ExpectedConditions.textToBePresentInElementLocated(elementLocator, text));
	}

	// --- wait for
	protected WebElement waitForVisibilityOfElementLocated(By elementLocator) {
		WebDriverWait wait = new WebDriverWait(webDriver, TIME_OUT);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
	}

	protected WebElement waitForPresenceOfElementLocated(By by) {
		WebDriverWait wait = new WebDriverWait(webDriver, TIME_OUT);
		return wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	protected List<WebElement> waitForPresenceOfElementsLocated(By by) {
		WebDriverWait wait = new WebDriverWait(webDriver, TIME_OUT);
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
	}

	protected WebElement waitForExpectedConditions(Function<? super WebDriver, WebElement> f) {
		WebDriverWait wait = new WebDriverWait(webDriver, TIME_OUT);
		return wait.until(f);
	}

	// --- Alert relation
	protected boolean isAlertPrecent(String alertMessage) {
		WebDriverWait wait = new WebDriverWait(webDriver, TIME_OUT);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		return alert.getText().equals(alertMessage);
	}

	protected void acceptAlert(String alertMessage) {
		WebDriverWait wait = new WebDriverWait(webDriver, TIME_OUT);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		if (alert.getText().contains(alertMessage)) {
			alert.accept();
		}
	}

	protected Alert getAlert() {
		WebDriverWait wait = new WebDriverWait(webDriver, TIME_OUT);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		return alert;
	}

	// --- mouse action
	protected void mouseHoverOn(WebElement element) {
		Actions actions = new Actions(webDriver);
		actions.moveToElement(element).build().perform();
	}

	protected void doubleClick(WebElement element) {
		Actions actions = new Actions(webDriver);
		actions.doubleClick(element).build().perform();
	}

	// --- window operations
	protected String getWindowHandle() {
		return webDriver.getWindowHandle();
	}

	protected Set<String> getWindowHandles() {
		return webDriver.getWindowHandles();
	}

	// --- switchTo
	protected void redirectFrame(int index) {
		webDriver.switchTo().frame(index);
	}

	protected void redirectFrame(WebElement element) {
		webDriver.switchTo().frame(element);
	}

	protected void switchToMainWindow(String window) throws Exception {
		webDriver.switchTo().window(window);
	}

	protected void focusOnNewWindow() throws Exception {
		for (String winHandle : webDriver.getWindowHandles()) {
			webDriver.switchTo().window(winHandle);
		}
	}

	// --- cookie operation
	protected Cookie getCookie(String key) {
		return webDriver.manage().getCookieNamed(key);
	}

	protected Set<Cookie> getAllCookies() {
		return webDriver.manage().getCookies();
	}

	protected void deleteCookie(String key) {
		webDriver.manage().deleteCookieNamed(key);
	}

	protected void deleteAllCookies() {
		webDriver.manage().deleteAllCookies();
	}

	protected void setCookie(String key, String value) {
		webDriver.manage().addCookie(new Cookie(key, value));
	}

	// --- specific
	protected void seleniumWaitForPageToLoad(long timeoutMillisecond) {
		selenium.waitForPageToLoad("" + timeoutMillisecond);
	}

	protected boolean waitForAjaxCall(int milliSeconds) {
		final String javaScript = "return (window.jQuery != null) && (jQuery.active === 0)";
		boolean jscondition = false;
		try {
			jscondition = (Boolean) ((JavascriptExecutor) webDriver).executeScript(javaScript);
			Thread.sleep(milliSeconds);
			return jscondition;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	protected void executeSpecificJS(String targetJS) {
		((JavascriptExecutor) webDriver).executeScript(targetJS);
	}

}
