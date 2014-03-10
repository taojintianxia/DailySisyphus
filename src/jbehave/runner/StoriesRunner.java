package jbehave.runner;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import jbehave.steps.BeforeAfterSteps;
import jbehave.steps.SearchSteps;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterConverters;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Feb 19, 2014 10:11:59 AM
 * 
 */

public class StoriesRunner extends JUnitStories {

	private final CrossReference xref = new CrossReference();

	public StoriesRunner() {
		configuredEmbedder().embedderControls().doGenerateViewAfterStories(true).doIgnoreFailureInStories(false).doIgnoreFailureInView(false).doVerboseFailures(true).doVerboseFiltering(true).useThreads(1).useStoryTimeoutInSecs(300);
		// configuredEmbedder().useEmbedderControls(new PropertyBasedEmbedderControls());
	}

	@Override
	public Configuration configuration() {
		Class<? extends Embeddable> embeddableClass = this.getClass();
		Properties viewResources = new Properties();
		viewResources.put("decorateNonHtml", "true");
		viewResources.put("reports", "ftl/jbehave-reports-with-totals.ftl");
		// Start from default ParameterConverters instance
		ParameterConverters parameterConverters = new ParameterConverters();
		// factory to allow parameter conversion and loading from external resources (used by StoryParser too)
		ExamplesTableFactory examplesTableFactory = new ExamplesTableFactory(new LocalizedKeywords(), new LoadFromClasspath(embeddableClass), parameterConverters);
		// add custom converters
		parameterConverters.addConverters(new ParameterConverters.DateConverter(new SimpleDateFormat("MM-dd-yyyy")), new ParameterConverters.ExamplesTableConverter(examplesTableFactory));
		return new MostUsefulConfiguration().useStoryLoader(new LoadFromClasspath(embeddableClass)).useStoryParser(new RegexStoryParser(examplesTableFactory)).useStoryReporterBuilder(new StoryReporterBuilder().withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass)).withDefaultFormats().withViewResources(viewResources).withFormats(CONSOLE, TXT, HTML, XML).withFailureTrace(true).withFailureTraceCompression(true).withCrossReference(xref)).useParameterConverters(parameterConverters).useStepMonitor(xref.getStepMonitor());
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		return new InstanceStepsFactory(configuration(), new BeforeAfterSteps(), new SearchSteps());
	}

	@Override
	protected List<String> storyPaths() {
		// Specify story paths as URLs
		String codeLocation = codeLocationFromClass(this.getClass()).getFile();
		return new StoryFinder().findPaths(codeLocation, asList("**/*.story"), asList(""));
	}

}
