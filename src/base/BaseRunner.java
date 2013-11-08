package base;

import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.STATS;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

public abstract class BaseRunner extends JUnitStories {

	protected abstract String[] getStepsBasePackages();

	@Override
	public InjectableStepsFactory stepsFactory() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.scan(this.getStepsBasePackages());
		ctx.refresh();

		LifeCycleSteps lcs = new LifeCycleSteps();
		List<BaseStep> ls = new ArrayList<BaseStep>();
		ls.add(lcs);

		ODSpringStepsFactory factory = new ODSpringStepsFactory(configuration(), ctx, ls);
		lcs.setFactory(factory);

		return factory;
	}

	@Override
	public void run() throws Throwable {
		Brand brand = this.getClass().getAnnotation(Brand.class);
		Locale locale = this.getClass().getAnnotation(Locale.class);
		Browsers browser = this.getClass().getAnnotation(Browsers.class);
		Env env = this.getClass().getAnnotation(Env.class);

		String currentBrand = System.getProperty(TestConstants.BRAND);
		String currentLocale = System.getProperty("meta.locale.country");
		String currentEnv = System.getProperty("env");
		if (!isRunnerSkipped(brand, locale, currentBrand, currentLocale)) {
			// load values form properties
			StringBuilder bundleName = new StringBuilder(100);
			bundleName.append("selenium-").append(brand.value().name().toLowerCase()).append("-").append(locale.language()).append("_").append(locale.country());
			if (StringUtils.isNotBlank(currentEnv)) {
				bundleName.append("-").append(currentEnv);
			} else if (env != null) {
				bundleName.append("-").append(env.env().toLowerCase());
			}
			SeleniumConfig.init(bundleName.toString());
			if (browser != null) {
				ODWebDriverProvider.getInstance().setTestBrowser(browser.value()[0]);
			} else {
				ODWebDriverProvider.getInstance().setTestBrowser(null);
			}
			List<String> meta = new ArrayList<String>();
			// String currentMeta = System.getProperty("meta");
			// if (StringUtils.isNotBlank(currentMeta)) {
			// meta.add("+basic");
			// for (String s : currentMeta.split(",")) {
			// meta.add("+" + s);
			// }
			// }
			meta.add("-skip");
			meta.add("-manual");
			meta.add("-wip");
			Embedder embedder = configuredEmbedder();
			embedder.useMetaFilters(meta);
			// embedder.embedderControls().useStoryTimeoutInSecs(600)
			// .useThreads(10)
			// .doIgnoreFailureInStories(true)
			// .doIgnoreFailureInView(true)
			// .doVerboseFailures(true)
			// .doVerboseFiltering(true);
			ODPrintStreamEmbedderMonitor odMonitor = new ODPrintStreamEmbedderMonitor();
			embedder.useEmbedderMonitor(odMonitor);
			try {
				embedder.runStoriesAsPaths(storyPaths());
				if (odMonitor.getFailedPaths().size() > 0) {
					System.out.println("***************************************Begin to re-run the failed stories****************************************");
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
	}

	private boolean isRunnerSkipped(Brand brand, Locale locale, String currentBrand, String currentLocale) {
		if (null == brand || null == locale || null == brand.value() || null == locale.country() || null == locale.language()) {
			throw new IllegalArgumentException("@Brand or @Locale are not well configured in Runner");
		}

		if (StringUtils.isBlank(currentLocale) && StringUtils.isBlank(currentBrand)) {
			return false;
		}

		if (currentLocale.equalsIgnoreCase(locale.language() + "_" + locale.country()) && currentBrand.equalsIgnoreCase(brand.value().name())) {
			return false;
		} else {
			return true;
		}
	}

	private Configuration config = null;

	@Override
	public Configuration configuration() {
		if (config == null) {
			Class<? extends Embeddable> embeddableClass = this.getClass();
			Properties viewResources = new Properties();
			viewResources.put("decorateNonHtml", "true");
			viewResources.put("reports", "ftl/od-jbehave-reports.ftl");
			ParameterConverters parameterConverters = new ParameterConverters();
			ExamplesTableFactory examplesTableFactory = new ExamplesTableFactory(new LocalizedKeywords(), new LoadFromClasspath(embeddableClass), parameterConverters);
			parameterConverters.addConverters(new ParameterConverters.DateConverter(new SimpleDateFormat("MM-dd-yyyy")), new ParameterConverters.ExamplesTableConverter(examplesTableFactory));
			StoryControls storyControls = new StoryControls();
			storyControls.doSkipScenariosAfterFailure(true);
			config = new SeleniumConfiguration().useSeleniumContext(new SeleniumContext()).useWebDriverProvider(ODWebDriverProvider.getInstance()).useStepMonitor(new SilentStepMonitor()).useStoryLoader(new LoadFromClasspath(embeddableClass)).useStoryParser(new RegexStoryParser(examplesTableFactory)).useStoryControls(storyControls).useParameterConverters(parameterConverters).useStoryReporterBuilder(new StoryReporterBuilder().withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass)).withDefaultFormats().withViewResources(viewResources).withFormats(CONSOLE, TXT, HTML, XML, STATS).withFailureTrace(true).withFailureTraceCompression(true).withCrossReference(new CrossReference())).usePathCalculator(new RelativePathCalculator()).useViewGenerator(FreemarkerODViewGenerator.getInstance());
			return config;
		} else {
			return config;
		}
	}
}
