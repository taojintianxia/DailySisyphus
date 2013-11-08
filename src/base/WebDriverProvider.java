package base;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbehave.web.selenium.DelegatingWebDriverProvider;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.thoughtworks.selenium.DefaultSelenium;

final public class WebDriverProvider extends DelegatingWebDriverProvider {

	// --singleton
	private WebDriverProvider() {
	}

	private final static WebDriverProvider INSTANCE = new WebDriverProvider();

	public static WebDriverProvider getInstance() {
		return INSTANCE;
	}

	// --logger
	private final Log LOGGER = LogFactory.getLog(WebDriverProvider.class);

	// create needed parameter
	private Browser targetTestBrowser = null;

	public void setTestBrowser(Browser browser) {
		this.targetTestBrowser = browser;
	}

	// create needed parameter
	private String baseUrl = null;

	public void setBaseUrl(String url) {
		this.baseUrl = url;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	// get the seleniumDriver
	public DefaultSelenium getSeleniumDriver() {
		WebDriverBackedSelenium selenium = new WebDriverBackedSelenium(this.get(), baseUrl);
		return selenium;
	}

	// Destroy & quit all web drivers in provider
	private Set<WebDriver> drivers = new HashSet<WebDriver>();

	public void destory() {
		for (WebDriver driver : drivers) {
			driver.quit();
		}
		delegate = new ThreadLocal<WebDriver>();
		drivers = new HashSet<WebDriver>();
	}

	public synchronized void initialize() {
		Browser browser = targetTestBrowser;
		if (browser == null) {
			browser = Browser.valueOf(Browser.class, System.getProperty("browser", "firefox").toUpperCase());
		}
		WebDriver webDriver = createDriver(browser);
		delegate.set(webDriver);
		drivers.add(webDriver);
		try {
			webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS).setScriptTimeout(30, TimeUnit.SECONDS).implicitlyWait(20, TimeUnit.SECONDS);
		} catch (Exception e) {
			LOGGER.error("ODWebDriverProvider initialize()", e); //$NON-NLS-1$
			throw new RuntimeException(e);
		}
	}

	// create the web driver
	private WebDriver createDriver(Browser browser) {
		SeleniumTestContext context = SeleniumTestContext.getInstance();
		if (StringUtils.isNotBlank(context.getSeleniumServerURL())) {
			try {
				URL seleniumServer = new URL(context.getSeleniumServerURL());
				return getRemoteWebDriver(seleniumServer, getBrowserCapabilities(browser));
			} catch (MalformedURLException e) {
				throw new RuntimeException("Invalid url for Selenium Server: " + context.getSeleniumServerURL());
			}
		} else {
			return getLocalWebDriver(browser, getBrowserCapabilities(browser));
		}
	}

	private WebDriver getRemoteWebDriver(URL seleniumServer, Capabilities capabilities) {
		return new RemoteWebDriver(seleniumServer, capabilities);
	}

	private WebDriver getLocalWebDriver(Browser browser, DesiredCapabilities capabilities) {
		String htmlUnit = System.getProperty("htmlunit");
		if (StringUtils.isNotBlank(htmlUnit) && "true".equalsIgnoreCase(htmlUnit)) {
			return new HtmlUnitDriver(capabilities);
		}

		switch (browser) {
		case FIREFOX:
			return new FirefoxDriver(capabilities);
		case CHROME:
			System.setProperty("webdriver.chrome.driver", "src/test/resources/browsers/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments(Arrays.asList("allow-running-insecure-content", "ignore-certificate-errors"));
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			return new ChromeDriver(capabilities);
		case IE6:
			System.setProperty("webdriver.ie.driver", "src/test/resources/browsers/IEDriverServer32.exe");
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			return new InternetExplorerDriver(capabilities);
		case IE7:
			System.setProperty("webdriver.ie.driver", "src/test/resources/browsers/IEDriverServer32.exe");
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			return new InternetExplorerDriver(capabilities);
		case IE8:
			System.setProperty("webdriver.ie.driver", "src/test/resources/browsers/IEDriverServer32.exe");
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			return new InternetExplorerDriver(capabilities);
		case IE9:
			System.setProperty("webdriver.ie.driver", "src/test/resources/browsers/IEDriverServer32.exe");
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			return new InternetExplorerDriver(capabilities);
		case NOCOOKIE:
			return new FirefoxDriver(capabilities);
		default:
			return new FirefoxDriver(capabilities);
		}
	}

	private DesiredCapabilities getBrowserCapabilities(Browser browser) {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		FirefoxProfile profile = new FirefoxProfile();
		if (Browser.NOCOOKIE == browser) {
			profile.setPreference("network.cookie.cookieBehavior", 2);
		}
		profile.setEnableNativeEvents(true);
		capabilities.setCapability(FirefoxDriver.PROFILE, profile);
		capabilities.setJavascriptEnabled(true);
		capabilities.setBrowserName(browser.getName());
		if (StringUtils.isNotEmpty(browser.getVersion())) {
			capabilities.setVersion(browser.getVersion());
		}
		capabilities.setPlatform(Platform.ANY);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setCapability("trustAllSSLCertificates", true);
		return capabilities;
	}

}
