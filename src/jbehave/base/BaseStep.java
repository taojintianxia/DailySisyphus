package jbehave.base;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Feb 19, 2014 11:09:03 AM
 * 
 */

public class BaseStep {
	protected PageContainer pageContainer;

	protected BaseStep() {
		this.pageContainer = PageContainer.getInstance();
	}
}
