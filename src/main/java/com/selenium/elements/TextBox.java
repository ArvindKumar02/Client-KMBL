package com.selenium.elements;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.selenium.init.BaseElement;

public class TextBox extends EditableElement {

	static Logger log = Logger.getLogger(BaseElement.class);
	
	public TextBox(WebElement webElement, String objectName) {
		super(webElement, objectName+"Text Box");
		
	}

}
