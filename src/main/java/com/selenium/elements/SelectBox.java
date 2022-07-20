package com.selenium.elements;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.selenium.init.BaseElement;

import io.qameta.allure.Step;

public class SelectBox extends EditableElement {

	static Logger log = Logger.getLogger(BaseElement.class);
	
	private Select select;
	
	public SelectBox(WebElement webElement, String objectName) {
		super(webElement, objectName+ "Dropdown");
		select = new Select(webElement);
	}
	
	/**
	 * Select Value From Dropdown
	 * 
	 * @param visibleText
	 */
	
	public void selectByVisibleText(String visibleText) {
		selectByVisibleText(getObjectName(),visibleText);
	}
	@Step("Select Value. Field : {0}, Value: {1}")
	private void selectByVisibleText(String objectName, String visibleText) {
		shouldExist();
		try {
			log.info(String.format("Select Value.Field: %s, Value: %s.", objectName, visibleText));
			select.selectByVisibleText(visibleText);
			
		} catch (NoSuchElementException e) {
			Assert.fail(visibleText+"Option not available in Dropdown : "+getObjectName());
		}
		
	}
	
	
	
}
