package com.rvsautobots.actions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.rvsautobots.datahandler.PropertyDataHandler;
import com.rvsautobots.exceptions.AutomationException;

/**
 * Browser and other utility actions can be implemented here.
 */

public class WebActionHelper {

	/**
	 * @Author AutobotsBDD Cucumber Team
	 * @Description :BDD
	 */
	UtilityActionHelper uah = new UtilityActionHelper();

	/**
	 * Method to launch the application URL.
	 * 
	 * @param driver
	 * @throws AutomationException
	 */
	public void launchAppURL(WebDriver driver) throws AutomationException {
		try {
			driver.get(PropertyDataHandler.fetchPropertyValue("config", "url"));
		} catch (Exception e) {
			throw new AutomationException("Failed while loading the page");
		}
	}

	/**
	 * Method to launch any url in the same tab.
	 * 
	 * @param url
	 * @param driver
	 * @throws AutomationException
	 */
	public void launchAnyURL(WebDriver driver, String url) throws AutomationException {
		try {
			driver.navigate().to(url);
			uah.waitTillPageToLoad(driver);
			uah.waitTillAngularComponentToLoad(driver);
		} catch (Exception e) {
			throw new AutomationException("No URL detected");
		}
	}

	/**
	 * Method to Check the url.
	 * 
	 * @param regex
	 * @param driver
	 */
	public boolean checkUrl(WebDriver driver, String regex) {
		boolean rtn = false;
		try {

			String url = driver.getCurrentUrl();
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(url);
			if (m.find()) {
				rtn = true;
			}
		} catch (Exception e) {
		}
		return rtn;
	}

	/**
	 * Method is used to get the current loaded URL in web
	 * applications @param @param driver @throws
	 */
	public String getCurrentPageURL(WebDriver driver) throws AutomationException {
		try {
			return driver.getCurrentUrl();
		} catch (Exception e) {
			throw new AutomationException("No URL detected");
		}
	}

	/**
	 * Method is used for return the web element with locators
	 * 
	 * @param locator
	 * @param driver
	 * @param expressionPath
	 */
	public WebElement returnWebElement(WebDriver driver, String locator, String expressionPath)
			throws AutomationException {
		WebElement element;
		if (locator.equals("xpath")) {
			element = driver.findElement(By.xpath(expressionPath));
		} else if (locator.equals("name")) {
			element = driver.findElement(By.name(expressionPath));
		} else if (locator.equals("id")) {
			element = driver.findElement(By.id(expressionPath));
		} else if (locator.equals("tagname")) {
			element = driver.findElement(By.tagName(expressionPath));
		} else if (locator.equals("linkText")) {
			element = driver.findElement(By.linkText(expressionPath));
		} else if (locator.equals("partialLinkText")) {
			element = driver.findElement(By.partialLinkText(expressionPath));
		} else if (locator.equals("className")) {
			element = driver.findElement(By.className(expressionPath));
		} else if (locator.equals("cssSelector")) {
			element = driver.findElement(By.cssSelector(expressionPath));
		} else {
			throw new AutomationException("Element " + "'" + "ELEMENT_NOT_FOUND");
		}

		return element;
	}

	/**
	 * Method is used to navigate to previous page
	 * 
	 * @param driver
	 * @throws AutomationException
	 */

	public void navigateBack(WebDriver driver) throws AutomationException {
		try {
			driver.navigate().back();

		} catch (Exception e) {
			throw new AutomationException("Page is not loaded");
		}
	}
}
