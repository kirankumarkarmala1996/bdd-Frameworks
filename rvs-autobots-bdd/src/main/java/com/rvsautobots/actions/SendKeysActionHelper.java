package com.rvsautobots.actions;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.rvsautobots.exceptions.AutomationException;

public class SendKeysActionHelper {
	UtilityActionHelper uah = new UtilityActionHelper();
	/**
	 * @Author AutobotsBDD Cucumber Team
	 * @Description :BDD
	 */
	/**
	 * enter the Text on the element.
	 * 
	 * @param driver
	 * @param elementValue
	 * @param inputText
	 * @throws AutomationException
	 */

	public void setText(WebDriver driver, String elementValue, String inputText) throws AutomationException {

		try {

			WebElement elem = uah.getElementValue(driver, elementValue);
			elem.sendKeys(inputText);
		} catch (Exception e) {
			throw new AutomationException("Unable to locate the element:" + elementValue);
		}
	}

	/**
	 * enter the Text on the Field after the clearing the same.
	 * 
	 * @param driver
	 * @param elementValue
	 * @param inputValue
	 * @throws AutomationException
	 */
	public void clearAndSetText(WebDriver driver, String elementValue, String inputValue) throws Exception {

		try {
			WebElement elem = uah.getElementValue(driver, elementValue);
			elem.clear();
			elem.sendKeys(inputValue);
		} catch (Exception e) {
			throw new AutomationException("Unable to locate the element:" + elementValue);
		}
	}
}
