package jbehave.base;

import static jbehave.util.SeleniumUtil.configFile;
import static org.jbehave.core.reporters.Format.CONSOLE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.RelativePathCalculator;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.web.selenium.SeleniumConfiguration;
import org.jbehave.web.selenium.SeleniumContext;
import org.jbehave.web.selenium.WebDriverHtmlOutput;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import jbehave.base.annotation.Browsers;
import jbehave.base.ftl.FreemarkerViewGenerator;
import jbehave.base.model.LifeCycleSteps;

/**
 * 
 * @author nianjun
 *
 */

public abstract class BaseRunner extends JUnitStories {

    protected abstract String[] getStepsBasePackages();

    private Configuration config = null;

    @Override
    public InjectableStepsFactory stepsFactory() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan(this.getStepsBasePackages());
        ctx.refresh();

        LifeCycleSteps lcs = new LifeCycleSteps();
        List<BaseStep> ls = new ArrayList<>();
        ls.add(lcs);
        ls.add(new ScreenshotOnFailureSteps());

        SpringStepsFactory factory = new SpringStepsFactory(configuration(), ctx, ls);
        lcs.setFactory(factory);

        return factory;
    }

    @Override
    public void run() throws Throwable {
        Browsers browser = this.getClass().getAnnotation(Browsers.class);
        SeleniumConfig.init(configFile);

        if (browser != null) {
            WebDriverProvider.getInstance().setTestBrowser(browser.value()[0]);
        } else {
            WebDriverProvider.getInstance().setTestBrowser(null);
        }
        List<String> meta = new ArrayList<>();
        String currentMeta = System.getProperty("meta");
        if (StringUtils.isNotBlank(currentMeta)) {
            meta.add("+basic");
            for (String s : currentMeta.split(",")) {
                meta.add("+" + s);
            }
        }
        meta.add("-skip");
        meta.add("-wip");
        Embedder embedder = configuredEmbedder();
        embedder.useMetaFilters(meta);
        EnhancePrintStreamEmbedderMonitor odMonitor = new EnhancePrintStreamEmbedderMonitor();
        embedder.useEmbedderMonitor(odMonitor);
        try {
            embedder.runStoriesAsPaths(storyPaths());
            if (odMonitor.getFailedPaths().size() > 0) {
                System.out.println(
                        "***************************************Begin to re-run the failed stories****************************************");
                StringBuffer sb = new StringBuffer();
                for (String s : odMonitor.getFailedPaths()) {
                    sb = sb.append(s).append(", ");
                }
                System.out.print(sb.toString());
                embedder.runStoriesAsPaths(odMonitor.getFailedPaths());
            }
        } finally {
            embedder.generateCrossReference();
        }
    }

    @Override
    public Configuration configuration() {
        if (config == null) {
            Class<? extends Embeddable> embeddableClass = this.getClass();
            Properties viewResources = new Properties();
            viewResources.put("decorateNonHtml", "true");
            viewResources.put("reports", "ftl/jbehave-reports.ftl");
            ParameterConverters parameterConverters = new ParameterConverters();
            ExamplesTableFactory examplesTableFactory = new ExamplesTableFactory(new LocalizedKeywords(),
                    new LoadFromClasspath(embeddableClass), parameterConverters);
            parameterConverters.addConverters(new ParameterConverters.DateConverter(new SimpleDateFormat("MM-dd-yyyy")),
                    new ParameterConverters.ExamplesTableConverter(examplesTableFactory));
            StoryControls storyControls = new StoryControls();
            storyControls.doSkipScenariosAfterFailure(true);

            config = new SeleniumConfiguration().useSeleniumContext(new SeleniumContext())
                    .useWebDriverProvider(WebDriverProvider.getInstance()).useStepMonitor(new SilentStepMonitor())
                    .useStoryLoader(new LoadFromClasspath(embeddableClass))
                    .useStoryParser(new RegexStoryParser(examplesTableFactory)).useStoryControls(storyControls)
                    .useParameterConverters(parameterConverters)
                    .useStoryReporterBuilder(new StoryReporterBuilder()
                            .withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass)).withDefaultFormats()
                            .withViewResources(viewResources).withFormats(CONSOLE, WebDriverHtmlOutput.WEB_DRIVER_HTML)
                            .withFailureTrace(true).withFailureTraceCompression(true)
                            .withCrossReference(new CrossReference()))
                    .usePathCalculator(new RelativePathCalculator())
                    .useViewGenerator(FreemarkerViewGenerator.getInstance());
            return config;
        } else {
            return config;
        }
    }
}
