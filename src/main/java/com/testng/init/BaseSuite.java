package com.testng.init;

import java.io.File;
import java.io.IOException;
import java.lang.module.Configuration;
import org.apache.log4j.Logger;
import com.selenium.init.Browser;
import com.selenium.init.BrowserManager;
import com.selenium.misc.Loggerline;
import com.selenium.utils.DirectoryUtils;
import com.selenium.utils.FileUtils;

@listners(CustomListerners.class)
public class BaseSuite {

	protected Browser browser;
	protected Configuration config;
	protected String deviceType;

	static Logger log = Logger.getLogger(BaseSuite.class);

	/**
	 * Setup Environment, browser, TimOuts and Listners
	 * 
	 * @throws Exception
	 */

	@BeforeMethod(alwaysRun = true, description = "Application SetUp")
	@Parameters({ "browser", "device" })
	public void beforeSuite(@Optional("chrome") String browserString, @Optional("desktop") String deviceType) {
		Loggerline.initilizeLogger();
		Configuration.getInstance().setBrowser(browserString);
		config = Configuration.getInstance();
		this.deviceType = deviceType;
		loadHomePage();

	}

	/**
	 * LogOut of the APplication and close the Browser
	 * 
	 * @throws Exception
	 */

	@AfterMethod(alwaysRun = true, description = "Close Application")
	public void tearDown() {

//		File file = new File(DirectoryUtils.getAllureReportDir()+ File.separator+"environment Properties");
//		file.getParentFile().mkdirs();
//		FileUtils.writePropertiesFile(config.getEnvPropperties(), file.getAbsolutePath());

		BrowserManager.closeBrowser();

	}

	public void loadHomePage() {

		browser = BrowserManager.getBrowser();
		browser.setPageLoadTimeOut(config.getPageLoadTimeOut);
		browser.setImplicitTimeout(config.getImplicitWait);
		browser.setExplicitTimeout(config.getExplicitTimeout);

//		 App app = new App();
//		 App.openApp();

	}

	public void run(IHookCallBack callback, ITestResult testResult) {
		callback.runTestMethod(testResult);
		if (testResult.getThrowable() != null) {
			log.info("Test Case:" + testResult.getMethod().getMethodName() + "Failed , Reason :"
					+ testResult.getThowable().getCause().getMessage());
			captureScreen(testResult.getMethod().getMethodName(), testResult.getThowable().getCause().getMessage());
		}

	}

	/**
	 * Attach Image when test fails
	 * 
	 * @param methodName
	 * @param description
	 * @return
	 * @throws IOException
	 */
	@Attachment(value = "Failur in test - {0} - {1}", type = "Image/png")
	public byte[] takeScreenShot(String methodName, String description) {
		return browser.takeScreenShot();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * Capture and Attach Image when test fails
	 * 
	 * @param methodName
	 * @param description
	 * @return
	 * @throws IOException
	 */

	@Attachment(value = "Reason - {0} - {1}", type = "Image/png")
	public byte[] captureScreen(String description, String value) {
		return browser.takeScreenShot();
	}

	public String getScreenShot(String name) throws Exception {
		return browser.getScreenshot(name);
	}

	public String getScreenShotAsRobot(String name) throws Exception {
		return browser.getScreenShotAsRobot(name);
	}

}
