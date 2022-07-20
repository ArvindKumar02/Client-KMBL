package com.testng.listerners;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.selenium.init.Browser;
import com.selenium.init.BrowserManager;
import com.selenium.init.Page;

public class ResultsTestListeners implements ITestListener{

	static Logger log =Logger.getLogger(ResultsTestListeners.class);
	
	WebDriver driver;
	ExtentReports extent;
	ExtentSparkReporter spark;
	ExtentTest test;
	Browser browser;
	Page page;
	
	@Override
	public void onTestStart(ITestResult result) {
		

	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		this.browser = BrowserManager.getBrowser();
		BrowserManager.getBrowser().takeScreenShot("Test Pass");
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		this.browser = BrowserManager.getBrowser();
		BrowserManager.getBrowser().takeScreenShot("Test Fail");
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
