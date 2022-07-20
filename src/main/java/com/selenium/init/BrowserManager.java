package com.selenium.init;

import freemarker.template.Configuration;

public class BrowserManager {
	private static ThreadLocal<Browser> ThreadDriver = new ThreadLocal<Browser>();

	public synchronized static Browser getBrowser() {
		Browser browser = BrowserManager.ThreadDriver.get();
		
		if (browser == null) {
			browser = new Browser() 
					
			// set System variable to configuration.properties file
			String browserName =System.getProperty("browse");
			/*if (!browserName.equalsIgnoreCase(""))){System.out.println("test");*/
			if(browserName!=null && !(browserName.equalsIgnoreCase(""))) {
				Configuration.getInstanse().setBrowser(browserName);}
			try {
				browser.launchBrowser(Configuration.getInstanse().setBrowser(browserName));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			ThreadDriver.set(browser);
			
		}
		return browser;
	}

	public static void closeBrowser() {
		getBrowser().closeBrowser();
		BrowserManager.ThreadDriver.set(null);
	}

}
