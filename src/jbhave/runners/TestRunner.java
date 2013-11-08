package runners;

import java.util.List;

import org.jbehave.core.embedder.executors.FixedThreadExecutors;
import org.jbehave.core.io.StoryFinder;

import base.BaseRunner;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Feb 11, 2014 1:38:14 PM
 * 
 */

@Locale(language = "en", country = "US")
@Brand(Site.BSD)
public class TestRunner extends BaseRunner {

	public TestRunner() {
		configuredEmbedder().useExecutorService(new FixedThreadExecutors().create(configuredEmbedder().embedderControls().doGenerateViewAfterStories(true).doIgnoreFailureInStories(false).doIgnoreFailureInView(false).doVerboseFailures(true).doVerboseFiltering(true).useStoryTimeoutInSecs(600)));
	}

	@Override
	protected List<String> storyPaths() {
		// Specify story paths as URLs
		String codeLocation = codeLocationFromClass(this.getClass()).getFile();
		return new StoryFinder().findPaths(codeLocation, asList("**/stories/bsd/**/bsd_cpd_file_upload.story"), asList(""));
	}
}
