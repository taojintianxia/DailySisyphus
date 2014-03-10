package jbehave.base;

import jbehave.pages.BaiduPage;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Feb 19, 2014 10:33:09 AM
 * 
 */

public class PageContainer {

	private static volatile PageContainer pageContainer;
	private WebDriverHelper helper;
	private BaiduPage baiduPage;

	private PageContainer() {
	}

	public PageContainer(WebDriverHelper helper) {
		this.helper = helper;
		baiduPage = new BaiduPage();
	}

	public static PageContainer getInstance() {
		if (pageContainer != null) {
			return pageContainer;
		} else {
			synchronized (PageContainer.class) {
				if (pageContainer == null) {
					pageContainer = new PageContainer(WebDriverHelper.getInstance());
				}
			}
			return pageContainer;
		}
	}

	public void lanuchTesting() {
		helper.start();
	}

	public void finishTesting() {
		helper.destroy();
	}

	public BaiduPage getBaiduPage() {
		return this.baiduPage;
	}
}
