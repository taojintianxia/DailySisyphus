package jbehave.base;

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
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
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
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import jbehave.base.model.Browser;

/**
 * 
 * @author nianjun
 *
 */

final public class WebDriverProvider extends DelegatingWebDriverProvider {

    private static final Log logger = LogFactory.getLog(WebDriverProvider.class);

    // singleton
    private WebDriverProvider() {}

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

    // get the seleniumDriver
    public DefaultSelenium getSeleniumDriver() {
        WebDriverBackedSelenium selenium =
                new WebDriverBackedSelenium(this.get(), SeleniumTestContext.getInstance().getTargetBaseURL());
        return selenium;
    }

    // Destroy & quit all web drivers in provider
    private Set<WebDriver> drivers = new HashSet<>();

    public void destory() {
        for (WebDriver driver : drivers) {
            driver.quit();
        }
        delegate = new ThreadLocal<>();
        drivers = new HashSet<>();
    }

    @Override
    public synchronized void initialize() {
        Browser browser = targetTestBrowser;
        if (browser == null) {
            browser = Browser.valueOf(Browser.class, System.getProperty("browser", "firefox").toUpperCase());
        }
        WebDriver webDriver = createDriver(browser);
        delegate.set(webDriver);
        drivers.add(webDriver);
        try {
            webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS).setScriptTimeout(30, TimeUnit.SECONDS)
                    .implicitlyWait(20, TimeUnit.SECONDS);
            webDriver.manage().window().maximize();
        } catch (Exception e) {
            LOGGER.error("WebDriverProvider initialize()", e); //$NON-NLS-1$
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
            case MOBILE:
                return new FirefoxDriver(capabilities);
            case CHROME:
                System.setProperty("webdriver.chrome.driver", "src/main/resources/browsers/chromedriver");
                return new ChromeDriver(capabilities);
            case IE6:
                System.setProperty("webdriver.ie.driver", "src/test/resources/browsers/IEDriverServer32.exe");
                return new InternetExplorerDriver(capabilities);
            case IE7:
                System.setProperty("webdriver.ie.driver", "src/test/resources/browsers/IEDriverServer32.exe");
                return new InternetExplorerDriver(capabilities);
            case IE8:
                System.setProperty("webdriver.ie.driver", "src/test/resources/browsers/IEDriverServer32.exe");
                return new InternetExplorerDriver(capabilities);
            case IE9:
                System.setProperty("webdriver.ie.driver", "src/test/resources/browsers/IEDriverServer32.exe");
                return new InternetExplorerDriver(capabilities);
            case NOCOOKIE:
                return new FirefoxDriver(capabilities);
            case NOJS:
                return new FirefoxDriver(capabilities);
            default:
                return new FirefoxDriver(capabilities);
        }
    }

    private DesiredCapabilities getBrowserCapabilities(Browser browser) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (browser.getName().equals("internet explorer")) {
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        } else if (Browser.CHROME == browser) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments(Arrays.asList("allow-running-insecure-content", "ignore-certificate-errors"));
            options.addArguments("--start-maximized");
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        } else {
            FirefoxProfile profile = new FirefoxProfile();
            if (Browser.MOBILE == browser) {
                profile.setPreference("general.useragent.override",
                        "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");
                profile.setAcceptUntrustedCertificates(true);
            }
            if (Browser.NOCOOKIE == browser) {
                profile.setPreference("network.cookie.cookieBehavior", 2);
            }
            profile.setEnableNativeEvents(true);
            capabilities.setCapability(FirefoxDriver.PROFILE, profile);
            // Setting ZAP Proxy for PEN-TESTING
            if (StringUtils.isNotEmpty(SeleniumConfig.getValue(SeleniumConfig.CONFIG_SELENIUM_ZAP_PROXY_HOST))) {
                String zapProxy = SeleniumConfig.getValue(SeleniumConfig.CONFIG_SELENIUM_ZAP_PROXY_HOST);
                capabilities.setCapability(CapabilityType.PROXY, new Proxy().setHttpProxy(zapProxy));
                if (logger.isDebugEnabled()) {
                    logger.debug("ZAPProxy:=  " + zapProxy);
                }
                logger.info("************+ZAPPROXY: " + zapProxy + " ****************");
            }
        }
        if (Browser.NOJS == browser) {
            capabilities.setJavascriptEnabled(false);
        } else {
            capabilities.setJavascriptEnabled(true);
        }
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
