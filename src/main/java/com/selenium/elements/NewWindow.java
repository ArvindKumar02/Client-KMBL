package com.selenium.elements;

import java.awt.Window;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class NewWindow extends EditableElement{

	static Logger log = Logger.getLogger(NewWindow.class);
	
	private Select select;
	
	public NewWindow(WebElement webElement, String objectName) {
		super(webElement, objectName+"Dropdown");
//		select = new Window(webElement)
		
	}
	/**
	 * Select Value From Dropdown
	 * 
	 * @param VisibleText
	 */
	
	public void selectByVisibleText(String visibleText) {
		selectByVisibleText(getObjectName(),visibleText);
	}
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
