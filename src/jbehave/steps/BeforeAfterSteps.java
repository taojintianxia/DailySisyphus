package jbehave.steps;

import jbehave.base.Hooks;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Feb 19, 2014 11:27:14 AM
 * 
 */

public class BeforeAfterSteps {
	@BeforeScenario
	public void beforeScenario() throws Exception {
		System.out.println("---(Before Scenario)---");
		Hooks.start();
	}

	@AfterScenario
	public void afterScenario() throws Exception {
		System.out.println("---(After Scenario)---");
		Hooks.stop();
	}
}
