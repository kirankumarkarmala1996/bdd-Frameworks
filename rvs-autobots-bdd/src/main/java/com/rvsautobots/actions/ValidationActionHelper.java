package com.rvsautobots.actions;

import java.io.File;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.rvsautobots.exceptions.AutomationException;

public class ValidationActionHelper {
	/**
	 * @Author AutobotsBDD Cucumber Team
	 * @Description :BDD
	 */
	UtilityActionHelper uah = new UtilityActionHelper();

	/**
	 * Method to check if an element is present or not.
	 * 
	 * @param driver
	 * @param locator
	 * @return true - if element is displayed or False - if element is not displayed
	 * @throws AutomationException
	 */
	public boolean isElementPresent(WebDriver driver, String elementValue) throws AutomationException {
		boolean rtn = false;
		try {
			WebElement elem = uah.getElementValue(driver, elementValue);
			if (elem != null) {
				rtn = true;
			}
		} catch (Exception e) {
			rtn = false;
		}
		return rtn;

	}

	/**
	 * This method is to check whether element found or not using isDisplayed with
	 * element name method
	 * 
	 * @param driver
	 * @param locator
	 * @return true - if element is displayed or False - if element is not displayed
	 * @throws AutomationException
	 */

	public boolean isElementVisible(WebDriver driver, String elementValue) throws AutomationException {
		try {
			return uah.getElementValue(driver, elementValue).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * This method returns true or false based on whether the web element is not
	 * visible but present
	 * 
	 * @param driver
	 * @param locator
	 * @return true - if element is displayed or False - if element is not displayed
	 * @throws AutomationException
	 */

	public boolean isElementInvisible(WebDriver driver, String elementValue) throws AutomationException {
		try {
			return uah.getElementValue(driver, elementValue).isDisplayed();
		} catch (NoSuchElementException e) {
			return true;
		}
	}

	/**
	 * This method returns a boolean value based on whether the web element in
	 * enabled
	 * 
	 * @param driver
	 * @param locator
	 * @return true - if element is displayed or False - if element is not displayed
	 * @throws AutomationException
	 */

	public boolean isElementEnabled(WebDriver driver, String elementValue) throws AutomationException {

		try {
			if (uah.getElementValue(driver, elementValue).isEnabled())
				return true;
			else
				return false;
		} catch (Exception e) {
			throw new AutomationException("Element not Enabled");
		}
	}

	/**
	 * Method to check whether the element is exist or not @param driver @param
	 * elementValue @throws
	 */

	public boolean checkElementNotExist(WebDriver driver, String elementValue) throws AutomationException {

		try {
			new UtilityActionHelper().getElementValue(driver, elementValue);
			return false;
		} catch (NoSuchElementException e) {
			return true;
		}

	}

	/**
	 * This method verifies whether a check box is selected
	 * 
	 * @param driver
	 * @param locator
	 * @throws AutomationException
	 */
	public boolean isCheckboxSelected(WebDriver driver, String elementValue) throws AutomationException {
		try {
			return uah.getElementValue(driver, elementValue).isSelected();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * This method verifies whether a values are corrected or not
	 * 
	 * @param actual
	 * @param expected
	 * @throws AutomationException
	 */

	public void verifyValues(String actual, String expected) throws AutomationException {

		try {
			Assert.assertEquals(actual, expected, actual + "and" + expected + "values are not equal!!");
		} catch (Exception e) {
			throw new AutomationException("Assert failed");
		}
	}

	/**
	 * This method verifies whether condition is true
	 * 
	 * @throws AutomationException
	 */
	public void verifyConditionTrue(boolean condition) throws AutomationException {

		try {
			Assert.assertTrue(condition, condition + " Condition is failed");
		} catch (Exception e) {
			throw new AutomationException(condition + "Assert failed");
		}

	}
	/**
	 * This method verifies whether condition is false
	 * 
	 * @throws AutomationException
	 */
	public void verifyConditionFalse(boolean condition) throws AutomationException {

		try {
			Assert.assertFalse(condition, condition + " Condition is true");
		} catch (Exception e) {
			throw new AutomationException("Assert failed");
		}

	}

	/**
	 * Check If any alert is present
	 * 
	 * @param driver
	 * @return
	 */

	public boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}

	/**
	 * determine if Ajax exists or not on WebPage.
	 * 
	 * @param driver
	 * 
	 * @return
	 */
	public boolean isAjaxNotActive(WebDriver driver) {
		try {
			return (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active == 0", " ");

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Method for checking the file is download into the file path .
	 * 
	 * @param dirpath
	 * @param ext
	 * 
	 * @return
	 */

	public boolean isFileDownloaded(String dirPath, String ext) {
		try {
			boolean flag = false;
			System.out.println(dirPath);
			File dir = new File(dirPath);
			File[] files = dir.listFiles();
			if (files == null || files.length == 0) {
				System.out.println("No files");
				flag = false;
			}

			for (int i = 1; i < files.length; i++) {
				if (files[i].getName().contains(ext)) {
					System.out.println("pdf file found");
					flag = true;
				}
			}
			return flag;
		} catch (Exception e) {
			return false;
		}
	}
}