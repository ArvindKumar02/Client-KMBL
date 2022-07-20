package com.selenium.elements;

import org.apache.commons.collections4.SetValuedMap;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.selenium.init.BaseElement;

import io.qameta.allure.Step;

public class EditableElement extends BaseElement {

	static Logger log = Logger.getLogger(BaseElement.class);

	public EditableElement(WebElement webElement, String objectName) {
		super(webElement, objectName);

	}

	/**
	 * Set value
	 * 
	 * @param value
	 */

	public void enterJSValue(String value) {
		setValue(getObjectName(), value);
	}

	@Step("Field : {0}, Enter Value : {1}")
	private void enterJSValue(String objName, String value) {

		getBrowser().getJSDriver().executeScript("arguments[0].value='" + value + "';", getWebElement());
		log.info(String.format("Field : %s, Action : Set Value, Value : %s .", objName, value));
	}

	public void setValue(String value) {
		setValue(getObjectName(), value);
	}

	/**
	 * Set value
	 * 
	 * @param value
	 */

	public void setValue(Keys value) {
		setValue(getObjectName(), value);
	}

	public void cleanField() {
		cleanField(getObjectName());
	}

	@Step("Field : (0), Enter Value: {1}")
	private void setValue(String objName, String value) {
//		shouldExist();
		log.info(String.format("Field :%s, Action : Set Value, Value: %s.", objName, value));
		getWebElement().sendKeys(value);
	}
	@Step("Field : (0), Enter Value: {1}")
	private void setValue(String objName, Keys value) {
//		shouldExist();
		log.info(String.format("Field :%s, Action : Set Value, Value: %s.", objName, value));
		getWebElement().sendKeys(value);
	}
	@Step("Field : (0), Clean Enter Value")
	private void cleanField(String objName) {
//		shouldExist();
		log.info(String.format("Field :%s, Action : Clean Value,", objName));
		getWebElement().clear();
	}
	
	public void enterValue(String value) {
		setValue(getObjectName(), value);
	}

	@Step("Field : {0}, Enter Value : {1}")
	private void enterValue(String objName, String value) {
//		shouldExist();
		log.info(String.format("Field :%s, Action : Set Value, Value: %s.", objName, value));
		getWebElement().sendKeys(value);
	}
}
