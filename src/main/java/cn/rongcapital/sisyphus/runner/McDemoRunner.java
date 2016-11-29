package cn.rongcapital.sisyphus.runner;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

import java.util.List;

import org.jbehave.core.embedder.executors.FixedThreadExecutors;
import org.jbehave.core.io.StoryFinder;

import cn.rongcapital.sisyphus.base.BaseRunner;
import cn.rongcapital.sisyphus.base.annotation.Browsers;
import cn.rongcapital.sisyphus.base.model.Browser;

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
        return new StoryFinder().findPaths(codeLocation, asList("**/resources/stories/**/mc_demo.story"), asList(""));
    }

    @Override
    protected String[] getStepsBasePackages() {
        return new String[] {"cn.rongcapital.sisyphus.steps"};
    }
}
