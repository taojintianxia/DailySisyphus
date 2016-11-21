package jbehave.runner;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

import java.util.List;

import org.jbehave.core.embedder.executors.FixedThreadExecutors;
import org.jbehave.core.io.StoryFinder;

import jbehave.base.BaseRunner;
import jbehave.base.annotation.Browsers;
import jbehave.base.model.Browser;

@Browsers(Browser.CHROME)
public class McDemoRunner extends BaseRunner {

    public McDemoRunner() {
        configuredEmbedder()
                .useExecutorService(new FixedThreadExecutors().create(configuredEmbedder().embedderControls()
                        .doGenerateViewAfterStories(true).doIgnoreFailureInStories(false).doIgnoreFailureInView(false)
                        .doVerboseFailures(true).doVerboseFiltering(true).useStoryTimeoutInSecs(600)));
    }

    @Override
    protected List<String> storyPaths() {
        // Specify story paths as URLs
        String codeLocation = codeLocationFromClass(this.getClass()).getFile();
        return new StoryFinder().findPaths(codeLocation, asList("**/stories/**/mc_demo.story"), asList(""));
    }

    @Override
    protected String[] getStepsBasePackages() {
        return new String[] {"jbehave.steps"};
    }
}
