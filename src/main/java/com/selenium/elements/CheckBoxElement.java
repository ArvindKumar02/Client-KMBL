package com.selenium.elements;

import org.openqa.selenium.WebElement;

public class CheckBoxElement extends ClickableElement {

	public CheckBoxElement(WebElement webElement, String objectName) {

		super(webElement, objectName + "CheckBoxElement");
	}
}
