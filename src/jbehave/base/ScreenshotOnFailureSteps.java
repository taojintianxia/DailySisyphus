package jbehave.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.core.failures.PendingStepFound;
import org.jbehave.core.failures.UUIDExceptionWrapper;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.web.selenium.RemoteWebDriverProvider;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 
 * @author nianjun
 *
 */

@Component
@Scope("prototype")
public class ScreenshotOnFailureSteps extends BaseStep {

    protected final StoryReporterBuilder reporterBuilder = new StoryReporterBuilder();
    protected final String screenshotPathPattern = "{0}/screenshots/failed-scenario-{1}.png";

    @AfterScenario(uponOutcome = Outcome.FAILURE)
    public void afterScenarioFailure(UUIDExceptionWrapper uuidWrappedFailure) throws Exception {
        if (uuidWrappedFailure instanceof PendingStepFound) {
            return; // we don't take screen-shots for Pending Steps
        }
        String screenshotPath = screenshotPath(uuidWrappedFailure.getUUID());
        String currentUrl = "[unknown page title]";
        try {
            currentUrl = WebDriverProvider.getInstance().get().getCurrentUrl();
        } catch (Exception e) {}
        boolean savedIt = false;
        try {
            if (WebDriverProvider.getInstance().get() instanceof RemoteWebDriver) {
                WebDriver driver = new Augmenter().augment(WebDriverProvider.getInstance().get());
                byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                File file = new File(screenshotPath);
                file.getParentFile().mkdirs();
                try {
                    file.createNewFile();
                    IOUtils.write(bytes, new FileOutputStream(file));
                } catch (IOException e) {
                    throw new RuntimeException("Can't save file", e);
                }
                savedIt = true;
            } else {
                savedIt = WebDriverProvider.getInstance().saveScreenshotTo(screenshotPath);
            }
        } catch (RemoteWebDriverProvider.SauceLabsJobHasEnded e) {
            System.err.println("Screenshot of page '" + currentUrl
                    + "' has **NOT** been saved. The SauceLabs job has ended, possibly timing out on their end.");
            return;
        } catch (Exception e) {
            System.out.println("Screenshot of page '" + currentUrl + ". Will try again. Cause: " + e.getMessage());
            // Try it again. WebDriver (on SauceLabs at least?) has blank-page
            // and zero length files issues.
            try {
                if (WebDriverProvider.getInstance().get() instanceof RemoteWebDriver) {
                    WebDriver driver = new Augmenter().augment(WebDriverProvider.getInstance().get());
                    byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    File file = new File(screenshotPath);
                    file.getParentFile().mkdirs();
                    try {
                        file.createNewFile();
                        IOUtils.write(bytes, new FileOutputStream(file));
                    } catch (IOException e1) {
                        throw new RuntimeException("Can't save file", e1);
                    }
                    savedIt = true;
                } else {
                    savedIt = WebDriverProvider.getInstance().saveScreenshotTo(screenshotPath);
                }
            } catch (Exception e1) {
                System.err
                        .println("Screenshot of page '" + currentUrl + "' has **NOT** been saved to '" + screenshotPath
                                + "' because error '" + e.getMessage() + "' encountered. Stack trace follows:");
                e.printStackTrace();
                return;
            }
        }
        if (savedIt) {
            System.out.println("Screenshot of page '" + currentUrl + "' has been saved to '" + screenshotPath
                    + "' with " + new File(screenshotPath).length() + " bytes");
        } else {
            System.err.println("Screenshot of page '" + currentUrl
                    + "' has **NOT** been saved. If there is no error, perhaps the WebDriver type you are using is not compatible with taking screenshots");
        }
    }

    protected String screenshotPath(UUID uuid) {
        return MessageFormat.format(screenshotPathPattern, reporterBuilder.outputDirectory(), uuid);
    }

}
