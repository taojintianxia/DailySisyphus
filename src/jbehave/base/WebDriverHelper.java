package jbehave.base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Feb 19, 2014 10:36:55 AM
 * 
 */

public class WebDriverHelper {

	private WebDriver driver;

	private static volatile WebDriverHelper helper;

	private WebDriverHelper() {
		initialize();
	}

	private void initialize() {
		System.out.println("initialize the webdriver instance");
		// Set IE capabilities
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		switch (Hooks.getTestingBrowser()) {
		case IE32:
			driver = new InternetExplorerDriver(capabilities);
			return;
		case IE64:
			driver = new InternetExplorerDriver(capabilities);
			return;
		case FIREFOX:
			driver = new FirefoxDriver();
			return;
		case CHROME:
			driver = new ChromeDriver(capabilities);
			return;
		default:
			driver = new FirefoxDriver();
		}
		try {
			driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public static WebDriverHelper getInstance() {
		if (helper != null) {
			return helper;
		} else {
			synchronized (WebDriverHelper.class) {
				if (helper == null) {
					helper = new WebDriverHelper();
				}
			}
			return helper;
		}
	}

	public void start() {
		try {
			driver.get(Hooks.getTestingBaseUrl() + "/");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void destroy() {
		try {
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public WebElement findElmenetBy(By by) {
		return driver.findElement(by);
	}

	public void typeTextBoxById(String idLocator, String typeContent) {
		try {
			Capabilities cp = ((RemoteWebDriver) driver).getCapabilities();
			if (cp.getBrowserName().equals("internet explorer")) {
				Thread.sleep(5000);
			}
			driver.findElement(By.id(idLocator)).clear();
			driver.findElement(By.id(idLocator)).sendKeys(typeContent);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void typeText(String text, WebElement element) {
		element.clear();
		element.sendKeys(text);
	}

	public void typeTextBoxByCSS(String cssLocator, String typeContent) {
		try {
			Capabilities cp = ((RemoteWebDriver) driver).getCapabilities();
			if (cp.getBrowserName().equals("internet explorer")) {
				Thread.sleep(5000);
			}
			driver.findElement(By.cssSelector(cssLocator)).clear();
			driver.findElement(By.cssSelector(cssLocator)).sendKeys(typeContent);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void typeTextBoxByXpath(String xpath, String typeContent) {
		try {
			Capabilities cp = ((RemoteWebDriver) driver).getCapabilities();
			if (cp.getBrowserName().equals("internet explorer")) {
				Thread.sleep(5000);
			}
			driver.findElement(By.xpath(xpath)).clear();
			driver.findElement(By.xpath(xpath)).sendKeys(typeContent);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void clickOn(WebElement element) {
		element.click();
	}

	public void clickByCSS(String cssLocator) {
		try {
			Capabilities cp = ((RemoteWebDriver) driver).getCapabilities();
			if (cp.getBrowserName().equals("internet explorer")) {
				Thread.sleep(5000);
			}
			WebElement element = driver.findElement(By.cssSelector(cssLocator));
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void clickById(String idLocator) {
		try {
			Capabilities cp = ((RemoteWebDriver) driver).getCapabilities();
			if (cp.getBrowserName().equals("internet explorer")) {
				Thread.sleep(5000);
			}
			WebElement element = driver.findElement(By.id(idLocator));
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void clickByLinkText(String linkText) {
		try {
			Capabilities cp = ((RemoteWebDriver) driver).getCapabilities();
			if (cp.getBrowserName().equals("internet explorer")) {
				Thread.sleep(5000);
			}
			WebElement element = driver.findElement(By.linkText(linkText));
			element.click();
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void clickByXpath(String xpath) {
		try {
			Capabilities cp = ((RemoteWebDriver) driver).getCapabilities();
			if (cp.getBrowserName().equals("internet explorer")) {
				Thread.sleep(5000);
			}
			WebElement element = driver.findElement(By.xpath(xpath));
			if (cp.getBrowserName().equals("chrome")) {
				try {
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
				} catch (Exception e) {

				}
			}
			element.click();
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void clickByName(String elementName) {
		try {
			Capabilities cp = ((RemoteWebDriver) driver).getCapabilities();
			if (cp.getBrowserName().equals("internet explorer")) {
				Thread.sleep(5000);
			}
			WebElement element = driver.findElement(By.name(elementName));
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void selectTextByElementId(String idLocator, String text) {
		try {
			new Select(driver.findElement(By.id(idLocator))).selectByVisibleText(text);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public String getTextByXpath(String xpath) {
		try {
			WebElement element = driver.findElement(By.xpath(xpath));
			if (element != null)
				return driver.findElement(By.xpath(xpath)).getText().trim();
			else
				throw new RuntimeException("---Failed to locate the element: " + xpath);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	public String getValueByXpath(String xpath) {
		try {
			WebElement element = driver.findElement(By.xpath(xpath));
			if (element != null)
				return driver.findElement(By.xpath(xpath)).getAttribute("value");
			else
				throw new RuntimeException("---Failed to locate the element: " + xpath);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public boolean isTextPresentOnPage(String content) {
		try {
			WebElement element = driver.findElement(By.xpath("//*[contains(.,'" + content + "')]"));
			if (element != null)
				return true;
			else
				throw new NoSuchElementException("---Failed to locate the element with content: " + content);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void openURL(String url) {
		driver.get(SeleniumUtil.getTargetUrl() + url);
	}

}
