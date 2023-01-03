package com.rvsautobots.actions;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.rvsautobots.datahandler.PropertyDataHandler;
import com.rvsautobots.exceptions.AutomationException;

public class UtilityActionHelper {

	/**
	 * @Author AutobotsBDD Cucumber Team
	 * @Description :BDD
	 */
	int timeout = Integer.parseInt(PropertyDataHandler.fetchPropertyValue("config", "explicitTimeOut"));
	ValidationActionHelper validationhelper = new ValidationActionHelper();

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * 
	 * @param driver
	 * @param elementValue
	 * @param timeout
	 * @return
	 * @throws AutomationException
	 */
	public boolean waitForElementPresent(WebDriver driver, String elementValue) throws AutomationException {

		WebDriverWait wait = new WebDriverWait(driver, timeout);
		try {
			WebElement element = wait.until(ExpectedConditions.visibilityOf(getElementValue(driver, elementValue)));
			if (element != null)
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;

	}

	/**
	 * An expectation for checking that an element, known to be present on the DOM
	 * of a page, is visible. Visibility means that the element is not only
	 * displayed but also has a height and width that is greater than 0.
	 * 
	 * @param driver
	 * @param elementValue
	 * @param timeout
	 * @return
	 * @throws AutomationException
	 */
	public WebElement waitForElementVisible(WebDriver driver, String elementValue) throws AutomationException {

		WebElement element = null;

		try {
			element = getElementValue(driver, elementValue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new AutomationException("Element value is:" + element + " so not accessible at this moment");
		}
		return element;
	}

	/**
	 * wait for an element to be invisible
	 * 
	 * @param driver
	 * @param elementValue
	 * @param timeout
	 * @return
	 * @throws AutomationException
	 */
	public boolean waitForElementInvisible(WebDriver driver, String elementValue) throws AutomationException {
		boolean rtn = false;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		rtn = wait.until(ExpectedConditions.invisibilityOf(getElementValue(driver, elementValue)));
		return rtn;
	}

	/**
	 * waitForTextVisible
	 * 
	 * @param driver
	 * @param elementValue
	 * @param text
	 * @return
	 * @throws AutomationException
	 */
	public boolean waitForTextVisible(WebDriver driver, String elementValue, String text) throws AutomationException {
		boolean rtn = false;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		rtn = wait.until(ExpectedConditions.textToBePresentInElement(getElementValue(driver, elementValue), text));
		return rtn;
	}

	/**
	 * wait for text to appear on the current page, do not need a target element
	 * 
	 * @param driver
	 * @param text
	 * @return
	 */
	public boolean waitForTextPresent(WebDriver driver, String text) {
		try {
			text = '"' + text + '"';
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(.," + text + ")]")));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * An expectation for checking that an element is ready for click
	 * 
	 * @param driver
	 * @param text
	 * @param elementValue
	 */
	public void selectWhenReady(WebDriver driver, String elementValue, String text) throws AutomationException {
		WebElement element = null;
		try {
			element = getElementValue(driver, elementValue);

			new Select(element).selectByVisibleText(text);
		} catch (Exception e) {
			throw new AutomationException("Element value is:" + element + " so not accessible at this moment");
		}
	}

	/**
	 * An expectation for checking that an element is ready for click using the
	 * value of that element
	 * 
	 * @param driver
	 * @param value
	 * @param elementValue
	 */
	public void selectByValueWhenReady(WebDriver driver, String elementValue, String value) throws AutomationException {
		WebElement element = null;
		try {
			element = getElementValue(driver, elementValue);

			new Select(element).selectByValue(value);
		} catch (Exception e) {
			throw new AutomationException("Element value is:" + element + " so not accessible at this moment");
		}
	}

	/**
	 * wait for Ajax call to finish.
	 * 
	 * @param driver
	 * @param timeout
	 */
	public void waitForAllAjaxCalls(WebDriver driver, int timeout) {

		int second = 0;
		while (second < timeout) {
			second++;
			if ((validationhelper.isAjaxNotActive(driver))) {
				break;
			}
			driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
		}

		if (second >= timeout) {
		}
	}

	/**
	 * Mouse over a web element using element locator
	 * 
	 * @param driver
	 * @param elementValue
	 */

	public void moveMouseOver(WebDriver driver, String elementValue) {
		try {
			// wait for Ajax to complete
			waitForAllAjaxCalls(driver, 30);

			WebElement element = getElementValue(driver, elementValue);
			(new Actions(driver)).moveToElement(element).build().perform();
			// wait for Ajax to complete
			waitForAllAjaxCalls(driver, 10);
		} catch (Exception e) {
		}
	}

	/**
	 * Method that performs click and hold at the location of the source element,
	 * moves by a given offset, then releases the mouse
	 * 
	 * @param driver
	 * @param source
	 * @param xOffset
	 * @param yOffset
	 */

	public void dragAndDropByOffsite(WebDriver driver, String source, int xOffset, int yOffset)
			throws AutomationException {
		try {
			WebElement element = getElementValue(driver, source);

			(new Actions(driver)).clickAndHold(element).moveByOffset(xOffset, yOffset).release().build().perform();

			waitForAllAjaxCalls(driver, 20);
		} catch (Exception e) {
		}

	}

	/**
	 * Method that performs click and hold at the location of the source element (by
	 * string), moves to the location of the target element, then releases the mouse
	 * 
	 * @param driver
	 * @param source
	 * @param target
	 */

	public void dragAndDrop(WebDriver driver, String source, String target) throws AutomationException {
		WebElement element = getElementValue(driver, source);
		WebElement targetEle = getElementValue(driver, target);

		(new Actions(driver)).clickAndHold(element).moveToElement(targetEle).release().build().perform();

		waitForAllAjaxCalls(driver, 20);

	}

	/**
	 * Move scroll bar, let page to locate the target element on the screen center
	 * 
	 * @param driver
	 * @param elementValue
	 */
	public void scrollScreenToElement(WebDriver driver, String elementValue) {
		try {
			WebElement element = getElementValue(driver, elementValue);

			Actions actions = new Actions(driver);
			actions.moveToElement(element);
			actions.perform();
			Thread.sleep(500);
		} catch (Exception e) {
		}
	}

	/**
	 * Scroll up and down the browser scroll bar. Does not work for the scroll bars
	 * that cannot be controlled by mouse scroll. In that case, use moveToElement()
	 * 
	 * @param driver
	 * @param yAxis  Positive number for move Up, Negative for move down
	 */
	public void scrollUpAndDown(WebDriver driver, int yAxis) {
		try {
			JavascriptExecutor jsx = (JavascriptExecutor) driver;
			jsx.executeScript("window.scrollBy(0," + yAxis + ")", "");
		} catch (Exception e) {
		}
	}

	/**
	 * Get the element location and scroll to corresponding element. Works good in
	 * popup screen
	 * 
	 * @param driver
	 * @param locator
	 * @throws AutomationException
	 */

	public void scrollToElementLocationInPopupScreen(WebDriver driver, String elementValue) throws AutomationException {
		try {
			WebElement element = getElementValue(driver, elementValue);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {

		}
	}

	/**
	 * Get the element location and scroll to corresponding element.
	 * 
	 * @param driver
	 * @param locator
	 * @throws AutomationException
	 */

	public void scrollToElementLocation(WebDriver driver, String elementValue) throws AutomationException {
		try {
			String elementLocation = getElementValue(driver, elementValue).getLocation().toString();
			((JavascriptExecutor) driver).executeScript("scroll" + elementLocation + "", "");
		} catch (Exception e) {
		}
	}

	/**
	 * Scroll left and right the browser scroll bar. Does not work for the scroll
	 * bars that cannot be controlled by mouse scroll. In that case, use
	 * moveToElement()
	 * 
	 * @param driver
	 * @param xAxis  Positive number for move right, Negative for move left
	 */
	public void scrollLeftAndRight(WebDriver driver, int xAxis) {
		try {
			JavascriptExecutor jsx = (JavascriptExecutor) driver;
			jsx.executeScript("window.scrollBy(" + xAxis + ",0)", "");
		} catch (Exception e) {
		}
	}

	/**
	 * remove readOnly attribute from an element
	 * 
	 * @param driver
	 * @param id
	 */
	public void removeReadOnly(WebDriver driver, String id) {
		try {
			((JavascriptExecutor) driver)
					.executeScript("document.getElementById ('" + id + "').removeAttribute('readonly',0);");
		} catch (Exception e) {
		}
	}

	public void removeAttribute(WebDriver driver, String id, String attribute) {
		try {
			((JavascriptExecutor) driver)
					.executeScript("document.getElementById (\"" + id + "\").removeAttribute(\"" + attribute + "\");");
		} catch (Exception e) {
		}
	}

	/**
	 * Accept or deny alert message
	 * 
	 * @param driver
	 * @param accept
	 * @param timeout
	 */
	public void manageAlert(WebDriver driver, boolean accept) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			alert = driver.switchTo().alert();
			if (accept) {
				alert.accept();
			} else {
				alert.dismiss();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * To support waitTillAngularComponentToLoad method
	 * 
	 * @return
	 */
	private ExpectedCondition<Boolean> angularHasFinishedProcessing() {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				String hasAngularFinishedScript = "var callback = arguments[arguments.length - 1];\n"
						+ "var el = document.querySelector('html');\n" + "if (!window.angular) {\n"
						+ "    callback('false')\n" + "}\n" + "if (angular.getTestability) {\n"
						+ "    angular.getTestability(el).whenStable(function(){callback('true')});\n" + "} else {\n"
						+ "    if (!angular.element(el).injector()) {\n" + "        callback('false')\n" + "    }\n"
						+ "    var browser = angular.element(el).injector().get('$browser');\n"
						+ "    browser.notifyWhenNoOutstandingRequests(function(){callback('true')});\n" + "}";

				JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
				String isProcessingFinished = javascriptExecutor.executeAsyncScript(hasAngularFinishedScript)
						.toString();

				return Boolean.valueOf(isProcessingFinished);
			}
		};
	}

	/**
	 * To support waitTillPageToLoad
	 * 
	 * @return
	 */
	private ExpectedCondition<Boolean> pageLoadFinished() {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}

		};

	}

	/**
	 * This method used to wait for the web page to Load
	 * 
	 * @param driver
	 * @param element
	 *
	 */
	public void waitTillPageToLoad(WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5, 100);
			wait.until(pageLoadFinished());
		} catch (Throwable e) {
		}
	}

	/**
	 * This method used to wait for angular components to Load
	 * 
	 * @param driver
	 * @param element
	 *
	 */

	public void waitTillAngularComponentToLoad(WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5, 100);
			wait.until(angularHasFinishedProcessing());
		} catch (Throwable e) {
		}
	}

	/**
	 * This method used to scroll to a element of web page Applicable
	 * 
	 * @param driver
	 * @param element
	 * 
	 */

	public void scrollToElementView(WebDriver driver, WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
		}
	}

	/**
	 * This method used to scroll to the bottom of the web page
	 * 
	 * @param driver
	 * 
	 */

	public void scrollToBottom(WebDriver driver) {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		} catch (Exception e) {
		}
	}

	/**
	 * This method used to scroll to the top of the web page
	 * 
	 * @param driver
	 * @throws AutomationException
	 */

	public void scrollToTop(WebDriver driver) {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
		} catch (Exception e) {
		}
	}

	/**
	 * To take the Screenshot when it is needed.
	 * 
	 * @param driver
	 * @param screenshotName
	 * @return destination
	 * @throws AutomationException
	 */
	public String getScreenShot(WebDriver driver, String screenshotName) throws IOException, AutomationException {
		try {
			String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			// after execution, you could see a folder "FailedTestsScreenshots" under src
			// folder
			String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
			File finalDestination = new File(destination);
			FileUtils.copyFile(source, finalDestination);
			return destination;
		} catch (Exception e) {
			throw new AutomationException("Screenshot is not added");
		}
	}

	/**
	 * Generic function to handle the web locators details
	 * 
	 * @param driver
	 * @param elementName
	 * @return element
	 * @throws AutomationException
	 */
	public WebElement getElementValue(WebDriver driver, String elementName) throws AutomationException {
		WebElement element = null;
		String elementValue = PropertyDataHandler.fetchPropertyValue("objectRepository", elementName);
		String waittime = PropertyDataHandler.fetchPropertyValue("config", "timeout");
		long time = Long.parseLong(waittime);
		WebDriverWait wait = new WebDriverWait(driver, time);

		try {
			if (elementValue.startsWith("//")) {
				element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementValue)));
			} else if (elementValue.startsWith("#")) {
				element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(elementValue.split("#")[1])));
			} else if (elementValue.startsWith("*")) {
				element = wait.until(ExpectedConditions.presenceOfElementLocated(By.name(elementValue.split("*")[1])));
			} else if (elementValue.startsWith("css")) {
				element = wait.until(
						ExpectedConditions.presenceOfElementLocated(By.cssSelector(elementValue.split(">>")[1])));
			} else if (elementValue.startsWith("className")) {
				element = wait
						.until(ExpectedConditions.presenceOfElementLocated(By.className(elementValue.split(">>")[1])));
			} else if (elementValue.startsWith("linkText")) {
				element = wait
						.until(ExpectedConditions.presenceOfElementLocated(By.linkText(elementValue.split(">>")[1])));
			} else if (elementValue.startsWith("partial")) {
				element = wait.until(
						ExpectedConditions.presenceOfElementLocated(By.partialLinkText(elementValue.split(">>")[1])));
			} else if (elementValue.startsWith("tag")) {
				element = wait
						.until(ExpectedConditions.presenceOfElementLocated(By.tagName(elementValue.split(">>")[1])));
			}
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		return element;
	}

	public Random random;

	/**
	 * Methods General utility actions
	 **/

	public int getRandomNumber() {
		int randomNum = getRandomNumber(1000, 999999);
		return randomNum;
	}

	/**
	 * Methods for generating Random Numbers
	 **/
	public int getRandomNumber(int lowerBound, int upperBound) {
		random = new Random();
		int randomNum = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
		return randomNum;
	}

	/**
	 * Methods for generating Random Strings
	 **/
	public String getRandomString(int limit) {

		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		StringBuilder sb = new StringBuilder(limit);

		for (int i = 0; i < limit; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}

	/**
	 * Method to get current Date in DD-M-MYY_ HH-mm-ss format
	 **/
	public String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
		Date date = new Date();
		String filePathdate = dateFormat.format(date).toString();
		return filePathdate;
	}

}