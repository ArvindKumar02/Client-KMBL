package com.selenium.init;

import java.util.logging.Logger;

import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Browser;

import io.qameta.allure.Step;

public abstract class BaseElement {

	static Logger log = Logger.getLogger(BaseElement.class);

	private Browser browser;
	private WebElement webElement;
	private String objectName;

	public BaseElement(WebElement element, String objectName) {

		this.browser = BrowserManager.getBrowser();
		this.setWebElement(element);
		this.setObjectName(objectName);
	}

	public WebElement getWebElement() {
		return webElement;
	}

	public void setWebElement(WebElement webElement) {
		this.webElement = webElement;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public Browser getBrowser() {
		return browser;
	}

	public void shouldExist() {
		log.info(String.format("Field : %S , Verification : Field Should Exist on the Page.", getObjectName()));
		try {
			webElement.getText();// dummy call

		} catch (NoSuchElementException | NullPointerException e) {
			String failureMessage = String.format(
					"Field : %S , Expected  : Field Should Exist on the Page.Actual : Field Doesn't exist on the Page.",
					getObjectName());

			log.error(failureMessage);
			throw new AssertionError(failureMessage, new Throwable(failureMessage));
		}
	}

	/**
	 * Verify Text
	 * 
	 * @param text
	 */

	public void textShouldBe(String text) {
		textShouldBe(getObjectName(), text.trim());
	}

	@Step("(0)Should contain exact text : (1)")

	private void textShouldBe(String objectName, String text) {
		log.info(String.format("Field : %S , Verification : Field Should Exist on the Page.", getObjectName(), text));

		Assert.assertEquals(getText().trim().toLowerCase(), text.toLowerCase(),
				"Text Doesn't Match for The Field : " + objectName + "");
	}

	/**
	 * Verify Text
	 * 
	 * @param text
	 */

	public void textShouldContain(String text) {

		textShouldContain(getObjectName(), text);
	}

	@Step("(0)Should contain exact text : (1)")
	private void textShouldContain() {

	private void textShouldContain(String objectName, String text) {
		log.info(String.format("Field : %S , Verification : Field Should Exist on the Page.", getObjectName(), text));

		if (getText().toLowerCase().contains(text.toLowerCase())) {
			Assert.assertTrue(true, "Text Doesn't Match for The Field : " + objectName + "");
		} else {
			Assert.assertTrue(false, "Text Doesn't Match for The Field : " + objectName + "");
		}
	}

	/**
	 * Verify Text
	 * 
	 * @param text
	 */

	public void textShouldBe(String text, boolean ignorecase) {
		textShouldBe(getObjectName(), text.trim());
	}

	@Step("(0)Should contain exact text : (1)")
	private void textShouldBe(String objectName, String text, boolean ignorecase) {
		log.info(String.format("Field : %S , Verification : Field Should Exist on the Page.", getObjectName(), text));

		Assert.assertEquals(getText().trim().toLowerCase(), text.toLowerCase(),
				"Text Doesn't Match for The Field : " + objectName + "");
	}

	@Step("(0)Should contain exact text : (1)")
	private void textShouldContain(String objectName, String text, boolean ignorecase) {
		log.info(String.format("Field : %S , Verification : Field Should Exist on the Page.", getObjectName(), text));

		Assert.assertEquals(getText().trim().toLowerCase(), text.toLowerCase(),
				"Text Doesn't Match for The Field : " + objectName + "");
	}

	protected boolean isEnabled() {
//			ShouldExist();
		return getWebElement().isEnabled();
	}

	protected boolean isSelected() {
		ShouldExist();
		return getWebElement().isSelected();
	}

	public void shouldBeDisable() {
		shouldBeDisable(getObjectName());
	}
	
	@Step("Field : {0}Should be enabled.")
	private void shouldBeDisable(String objectName) {
		log.info(String.format("Field : %S , Verification : Field Should be Diabled.", objectName));
		Assert.assertFalse(isEnabled(),objectName+"Not Disabled.");
	}
	
	public void shouldBeEnabled() {
		shouldBeDisable(getObjectName());
	}
	
	@Step("Field : {0}Should be enabled.")
	private void shouldBeEnabled(String objectName) {
		log.info(String.format("Field : %S , Verification : Field Should be Enabled.", objectName));
		Assert.assertFalse(isEnabled(),objectName+"Not Enabled.");
	}
	
	/**
	 * Element Should be Visible
	 */
	
	public void shouldBeVisible() {
		shouldBeVisible(getObjectName());
	}
	
	@Step("Field : {0}Should be Visible.")
	private void shouldBeVisible(String objectName) {
		log.info(String.format("Field : %S , Verification : Field Should be Visible.", objectName));
		Assert.assertFalse(isDisplayed(),objectName+"Not Visible.");
	}
	
	public void shouldNotBeVisible() {
		shouldNotBeVisible(getObjectName());
	}
	
	@Step("Field : {0}Should be Visible.")
	private void shouldNotBeVisible(String objectName) {
		log.info(String.format("Field : %S , Verification : Field Should be not Visible.", objectName));
		Assert.assertFalse(isDisplayed(),objectName+"Not Visible.");
	}
	
	/**
	 * @return element text
	 */
	
	public String getText() {
//		shouldExist();
		return getWebElement().getText();
	}
	
	/**
	 * @return element text
	 */
	
	public String getText(String atr) {
		shouldExist();
		return getWebElement().getAttribute(atr);
	}
	
	/**
	 * Check if Object is Displayed
	 * 
	 * @return
	 */
	
	public boolean isDisplayed() {
		shouldExist();
		boolean isPass;
		try {
			isPass = getWebElement().isDisplayed();
		} catch (Exception e) {
			isPass=false;
		}
		return isPass;
		}
	}

}
