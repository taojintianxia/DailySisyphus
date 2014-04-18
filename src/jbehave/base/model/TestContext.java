/**
 * 
 */
package jbehave.base.model;

public class TestContext {

	protected String site;

	protected String language;

	protected String country;

	/**
	 * sqw, sqm, prd etc
	 */
	protected String env;

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

}
