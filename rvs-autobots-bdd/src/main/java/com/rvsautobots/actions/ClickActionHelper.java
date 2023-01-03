package com.rvsautobots.actions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.rvsautobots.exceptions.AutomationException;

public class ClickActionHelper {
	UtilityActionHelper uah = new UtilityActionHelper();

	/**
	 * @Author AutobotsBDD Cucumber Team
	 * @Description :BDD
	 */

	/**
	 * click on the element when it is ready.
	 * 
	 * @param driver
	 * @param elementValue
	 * @throws AutomationException
	 */
	public void clickWhenReady(WebDriver driver, String elementValue) throws AutomationException {
		WebElement element;
		try {
			element = uah.getElementValue(driver, elementValue);
			element.click();
		} catch (Exception e) {
			throw new AutomationException("Unable to locate or click the element:" + elementValue);
		}
	}

	/**
	 * Click on the element by expect location. This method resolves the issue when
	 * the element is found on the page by it is not clickable. The methods finds
	 * the element and String elementValue and get the exact position of the element
	 * and click on it
	 * 
	 * @param driver
	 * @param elementValue
	 * @throws AutomationException
	 */
	public void clickByExactElementLocation(WebDriver driver, String elementValue) throws AutomationException {
		try {
			WebElement elem = uah.getElementValue(driver, elementValue);

			int width = elem.getSize().getWidth();

			Actions act = new Actions(driver);
			act.moveToElement(elem).moveByOffset((width / 2) - 2, 0).click().perform();
		} catch (NoSuchElementException e) {

		}
	}

	/**
	 * Simulates the move of the mouse on top of the hidden element and once the
	 * element appears it clicks on element
	 * 
	 * @param driver
	 * @param elementValue
	 * @param elementValueTarget
	 * @throws AutomationException
	 * 
	 */

	public void moveMouseOverAndClick(WebDriver driver, String elementValue, String elementValueTarget)
			throws AutomationException {
		uah.moveMouseOver(driver, elementValue);
		clickWhenReady(driver, elementValueTarget);
	}

	/**
	 * Method to click on a web element using JS Executor
	 * 
	 * @param driver
	 * @param elementValue
	 * @return
	 */

	public void clickUsingJSExecutor(WebDriver driver, String elementValue) throws AutomationException {
		try {
			WebElement ele = uah.getElementValue(driver, elementValue);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", ele);
		} catch (Exception e) {
			throw new AutomationException("Unable locate or click the element:" + elementValue);
		}
	}

}
