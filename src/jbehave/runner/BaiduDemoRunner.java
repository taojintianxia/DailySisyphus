package jbehave.runner;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

import java.util.List;

import jbehave.base.BaseRunner;

import org.jbehave.core.embedder.executors.FixedThreadExecutors;
import org.jbehave.core.io.StoryFinder;

public class BaiduDemoRunner extends BaseRunner {

	public BaiduDemoRunner() {
		configuredEmbedder().useExecutorService(new FixedThreadExecutors().create(configuredEmbedder().embedderControls().doGenerateViewAfterStories(true).doIgnoreFailureInStories(false).doIgnoreFailureInView(false).doVerboseFailures(true).doVerboseFiltering(true).useStoryTimeoutInSecs(600)));
	}

	@Override
	protected List<String> storyPaths() {
		// Specify story paths as URLs
		String codeLocation = codeLocationFromClass(this.getClass()).getFile();
		return new StoryFinder().findPaths(codeLocation, asList("**/stories/**/baidu_search.story"), asList(""));
	}

	@Override
	protected String[] getStepsBasePackages() {
		return new String[] { "jbehave.steps" };
	}
}
