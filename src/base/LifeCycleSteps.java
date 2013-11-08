package base;

import javax.inject.Scope;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStory;

@Component
@Scope("prototype")
public class LifeCycleSteps extends BaseStep {

	@BeforeStory
	public void initStory() {
		ODWebDriverProvider.getInstance().initialize();
	}

	@AfterStory
	public void destoryStory() {
		ODWebDriverProvider.getInstance().end();
		factory.clearThreadCache();
	}

	@AfterStories
	public void destoryStories() {
		ODWebDriverProvider.getInstance().destory();
	}

	private ODSpringStepsFactory factory;

	public ODSpringStepsFactory getFactory() {
		return factory;
	}

	public void setFactory(ODSpringStepsFactory factory) {
		this.factory = factory;
	}

}
