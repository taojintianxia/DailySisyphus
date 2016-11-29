package cn.rongcapital.sisyphus.base.model;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.rongcapital.sisyphus.base.BaseStep;
import cn.rongcapital.sisyphus.base.SpringStepsFactory;
import cn.rongcapital.sisyphus.base.WebDriverProvider;

/**
 * @author nianjun
 * 
 */

@Component
@Scope("prototype")
public class LifeCycleSteps extends BaseStep {

    @BeforeStory
    public void initStory() {
        WebDriverProvider.getInstance().initialize();
    }

    @AfterStory
    public void destoryStory() {
        WebDriverProvider.getInstance().end();
        factory.clearThreadCache();
    }

    @AfterStories
    public void destoryStories() {
        WebDriverProvider.getInstance().destory();
    }

    private SpringStepsFactory factory;

    public SpringStepsFactory getFactory() {
        return factory;
    }

    public void setFactory(SpringStepsFactory factory) {
        this.factory = factory;
    }

}
