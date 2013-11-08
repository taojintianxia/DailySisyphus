package steps;

import org.jbehave.core.annotations.When;

/**
 *
 *
 * @author Kane.Sun
 * @version Nov 5, 2013 11:36:04 AM
 * 
 */

public class BaiduStep {

	@When("open the target link")
	public void openTheTargetLink(){
		openUrl("baidu.com");
	}
}
