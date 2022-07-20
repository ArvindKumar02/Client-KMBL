package com.selenium.elements;

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import com.selenium.init.BaseElement;

import io.qameta.allure.Step;

public class ClickableElement extends BaseElement {

	static Logger log = Logger.getLogger(BaseElement.class);

	public ClickableElement(WebElement webElement, String objectName) {

		super(webElement, objectName);

	}

	/**
	 * Click on the Object
	 */
	public void click() {

		click(getObjectName(), Clicktype.NORMAL);

	}

	/**
	 * Click on the Object
	 */

	public void click(ClickType clickType) {

		click(getObjectName(), clickType);
	}

	/**
	 * @param objectName
	 */

	@Step("Field : {0}, Action : Click")
	private void click(String objectName , ClickType clickType) {
		if(!objectName.contains("CheckBox Button"))
			shouldBeClikable();
		
		log.info(String.format("Field: %s, Action : Click, Click Type : %s", getObjectName(),clickType);
		if (clickType == Clicktype.NORMAL) {
			getWebElement().click();
		}else if (clickType == Clicktype.ACTION){
			getBrowser().getActionDriver().moveToElement(getWebElement()).click().perform();
		}else if (clickType == Clicktype.JAVASCRIPT){
			getBrowser().getJSDriver().executeScript("arguments[0].click();",getWebElement());
		}else if (clickType == Clicktype.DIMENSION){
			Dimension elementDimension = getWebElement().getSize();
			Point elementLocation = getWebElement().getHeight();
			int width = elementDimension.getWidth();
			int Height = elementDimension.getHeight();
			int XCoordinate = elementLocation.getX()+(width /2);
			int YCoordinate = elementLocation.getY()+(Height /2);
			log.debug(String.format("Dimension Click. X: %s,Y: %s", XCoordinate,YCoordinate));
			getBrowser().getActionDriver().moveBYOffset(XCoordinate,YCoordinate).click().perform();
			
		}
	}
	
	/**
	 * Verify the object Should Be clickable.
	 */
	public void shouldBeClickable() {
		log.info(String.format("Field : %s, Verification :Field Should be Clickable."), getObjectName());
		shouldBeEnabled();
		shouldBeClickable();
	}

}
