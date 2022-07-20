package com.selenium.init;

import java.awt.AWTException;
import java.awt.Desktop.Action;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

public abstract class Page {

	static Logger log = Logger.getLogger(Page.class);

	public Browser browser;

	SoftAssert softAssert = new SoftAssert();

	public Page() {
		this.browser = BrowserManager.getBrowser();
		PageFactory.initElements(browser, getDriver), this);
	}

	public void sendKey(Keys key) {
		browser.getActionDriver().sendKeys(key).perform();
	}

	public void navigateBack() {
		browser.back();
	}

	public void waitForTheElemenToDisappear(WebElement ele) {
		waitForAjaxRequestToLoad();
		waitForPaageToLoad();
		this.browser.getWebDriverWait().until(ExpectedConditions.invisibilityOf(ele));
	}

	public void waitforElement() {

	}

	/**
	 * Wait Till Ajax Request is Completed
	 */

	public void waitForAjaxRequestToLoad() {
		log.info("Waiting for Ajax Call to Finish.....");
		SleepUtils.sleep(1);

		this.browser.getWebDriverWait().until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				try {
					return ((Long) ((JavascriptExecutor) webDriver).executeScript("return jQuery.active")) == 0;
				} catch (Exception e) {
					return false;
				}
			}

			@Override
			public String toString() {
				return "Ajax call Complete";
			}
		});
	}

	/*
	 * public void waitForThePageToLoad() {log.info("Waiting for the Page to Load")}
	 * SleepUtils.sleep(1); this.browser.getWebDriverWait().until(new
	 * ExpectedCondition<Boolean>(){public Boolean apply(WebDriver webDriver) { try
	 * (return ((String)(JavaScriptExecutor)webDriver).
	 * executeScript("return document.readyState")).equals("Complete"); }catch
	 * (Exception e){return false;}}
	 * 
	 * @Override public String toString(){ (return "Page Load Complete"); } });}
	 * 
	 */

	/**
	 * @author arvind
	 * @description Wait for Loader Icon to hide.
	 * @return void
	 * @param "none"
	 */

	public void waitForPageToLoad() {
		log.info("Waiting for Ajax Call to Finish.....");
		SleepUtils.sleep(1);

		this.browser.getWebDriverWaitsp().until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				try {
					return ((Boolean) ((JavascriptExecutor) webDriver)
							.executeScript("return $(document.querySelectorAll('.loader)).is('hidden')"));
				} catch (Exception e) {
					return false;
				}
			}

			@Override
			public String toString() {
				return "Page Load Complete";
			}
		});
	}

	public String verifyAndAcceptAlert() throws AWTException, Exception {
//		((FluentWait<WebDriver>) browser.getDriver().until(ExpectedConditions.alertIsPresent());
		Alert alert = browser.getDriver().switchTo().alert();
//		Assert.assetEquals(alert.getText(), message , "Alert Message Doesn't Match");
		String alertMsg = alert.getText();
		alert.accept();
		return alertMsg;
	}

	public void scrollToELement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) browser.getDriver();
		// Now Scroll to this Element
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public Action HoverToELement(WebElement ele) {
		// this.browser.getActionDriver.moveToElement(target);
		Actions action = new Actions(browser.getDriver());
		action.moveToElement(ele).perform();
		return action.build();
	}

	/*
	 * Function to Validate broken images
	 */
	@Step("Verify Broken Images")
	public int validateBrokenImages() {
		int brokenImages = 0;
		List<WebElement> allImg = browser.getDriver().findElements(By.tagName("img"));
		try {
			for (WebElement imageElement : allImg) {
				if (imageElement != null) {
					HttpClient httpClient = HttpClientBuilder.create().build();
					String a = imageElement.getAttribute("src");

					if (a != null && !a.equalsIgnoreCase(""))
						;
					{
						HttpGet httpRequest = new HttpGet(a);
						HttpResponse response = httpClient.execute(httpRequest);
						log.info("Images :" + a);
						if (response.getStatusLine().getStatusCode() != 200) {
							brokenImages++;
							log.info("Broken Images " + brokenImages + ":" + imageElement.getAttribute("alt"));
						}
					}
				} else {
					brokenImages++;
					log.info("Broken Images " + brokenImages + ":" + imageElement.getAttribute("alt"));
				}
			}
		} catch (Exception e) {
			log.error("Exception - ValidateBrokenImage: " + e);
		}
		return brokenImages;
	}

	/*
	 * Function to Validate broken Links
	 */
	@Step("Verify Broken Links")
	public int validateBrokenLinks() {
		int brokenImages = 0;
		List<WebElement> allImg = browser.getDriver().findElements(By.tagName("a"));
		try {
			for (WebElement imageElement : allImg) {
				if (imageElement != null) {
					HttpClient httpClient = HttpClientBuilder.create().build();
					String a = imageElement.getAttribute("href");
					if (a != null && !a.equalsIgnoreCase(""))
						;
					{
						HttpGet httpRequest = new HttpGet(a);
						HttpResponse response = httpClient.execute(httpRequest);
						log.info("Links :" + a);
						if (response.getStatusLine().getStatusCode() != 200) {
							brokenImages++;
							log.info("Broken Images " + brokenImages + ":" + imageElement.getAttribute("alt"));
						}
					}
				} else {
//					brokenImages++;
//					log.info("Broken Images "+brokenImages+":"+ imageElement.getAttribute("alt"));
				}
			}
		} catch (Exception e) {
			log.error("Exception - ValidateBrokenLink: " + e);
		}
		return brokenImages;
	}

	/**
	 * Capture full Screen and Attach Image when Required
	 * 
	 * @param description
	 * @return
	 * @throws IOException
	 * 
	 */

	@Attachment(value = "Reason - {0}", type = "image/png")
	public byte[] takeScreenShot(String description) {
		return browser.takeScreenShot();
	}

	public void assertAll() {
		softAssert.assertAll();
	}

	public void scrollToTop() {
		JavascriptExecutor js = (JavascriptExecutor) browser.getDriver();
		js.executeScript("window.scrollTo(100,0)");
		SleepUtils.sleep(1);
	}

	public void scrollToDown() {
		JavascriptExecutor js = (JavascriptExecutor) browser.getDriver();
		js.executeScript("window.scrollTo(0,100)");
		SleepUtils.sleep(1);
	}

	public void refreshPage() {
		browser.refresh();
		waitForPageToLoad();
	}

	public void refreshPage_() {
		browser.refresh();
//		waitForPageToLoad();
	}

	public void getPage(String url) {
		browser.getDriver().get(url);
		waitForPageToLoad();
		browser.refresh();
	}

	public void getPage_(String url) {
		browser.getDriver().get(url);
//		waitForPageToLoad();
//		browser.refresh(); 
	}

	public void waitForLoadingToComplete() {
		List<WebElement> ele;
		SleepUtils.sleep(1);
		ele = this.browser.getDriver().findElements(By.cssSelector(".loading-div"));
		if (ele.size() > 0) {
			this.browser.getWebDriverWaitsp().until(
					ExpectedConditions.invisibilityOf(browser.getDriver().findElement(By.cssSelector(".loading-div"))));
		}
	}

	public void waitForSingPadToLoad() {
		List<WebElement> ele;
		SleepUtils.sleep(1);
		ele = this.browser.getDriver().findElements(By.cssSelector(".ieco-signin-pad"));
		if (ele.size() > 0) {
			this.browser.getWebDriverWaitsp().until(ExpectedConditions
					.invisibilityOf(browser.getDriver().findElement(By.cssSelector(".ieco-signin-pad"))));
		}
	}

	public void waitForElementToLoad(WebElement ele) {
		waitForAjaxRequestLoad();
		this.browser.getWebDriverWait().until(ExpectedConditions.visibilityOf(ele));
	}

	@Step("Actual Text : {0} ,EXpected Text : {1}")
	public void compareText(String actual, String expected) {
		softAssert.assertEquals(actual, expected, "Text are not Equal");

	}

	public void waitForElementToLoad1(WebElement ele) {
		waitForAjaxRequestToLoad();
		this.browser.getWebDriverWait().until(ExpectedConditions.visibilityOf(ele));
	}

	public void scrollCalender(int ele, int val) {
		JavascriptExecutor js = (JavascriptExecutor) browser.getDriver();
		js.executeScript("document.querySelectorAll('.vf-revlover-show.selectContainer')[" + ele + "]"
				+ ".setAttribute('style','transform: translate3d(0px," + val + "px,0px);')");
		SleepUtils.sleep(1);
	}

	/**
	 * Sets the value on a horizontal angular.js slider
	 * 
	 * @param value the drsired value
	 * 
	 */

	public void setHValue(WebElement slider, double value) {
		double minValue = Double.parseDouble(slider.getAttribute("aria-valuemin"));
		double maxValue = Double.parseDouble(slider.getAttribute("aria-valuemax"));
		int sliderH = slider.getSize().height;
		int sliderW = slider.getSize().width;
		System.out.println(sliderH);
		System.out.println(sliderW);
		Actions action = new Actions(browser.getDriver());
		action.moveToElement(slider, (int) (value + sliderW / (maxValue - minValue)), sliderH / 2).click().build()
				.perform();
	}

	public void switchToChildWindow() {
		this.browser.switchToChildWindow();
	}

	public void parentWindow() {
		this.browser.switchToParentWindow();
	}

	public static void copyFile(String src, String dest) {
		Path result = null;
		try {
			result = Files.copy(Paths.get(src), Paths.get(dest));
		} catch (IOException e) {
			log.info("Exception while moving file: " + e.getMessage());
		}
		if (result != null) {
			log.info("File Moved Successfully");
		} else {
			log.info("File Movement Failed");
		}
	}

}
