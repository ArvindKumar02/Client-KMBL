package com.selenium.elements;

import org.openqa.selenium.WebElement;

public class Button extends ClickableElement {

	public Button(WebElement webElement, String objectName) {
		
		super(webElement, objectName + "Button");
	}

}
