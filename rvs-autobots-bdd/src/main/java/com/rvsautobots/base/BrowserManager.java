
package com.rvsautobots.base;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.rvsautobots.datahandler.PropertyDataHandler;
import com.rvsautobots.exceptions.AutomationException;

import cucumber.api.testng.AbstractTestNGCucumberTests;

/**
 * @Author AutobotsBDD Cucumber Team
 * @Description :BDD
 */
public class BrowserManager extends AbstractTestNGCucumberTests {
	public WebDriver driver = null;
	public static String strBrowserName = null;

	public void launchBrowser() throws AutomationException {
		driver = getWebdriver();
		driver.get(PropertyDataHandler.fetchPropertyValue("config", "url"));
	}

	/**
	 * @Description : This function is to launch browsers like Chrome and Firefox
	 * @param :
	 *            browserName
	 */
	public WebDriver getWebdriver() {
		try {
			strBrowserName = PropertyDataHandler.fetchPropertyValue("config", "browserName");
			if (strBrowserName == null || strBrowserName.equals(" ")) {
				System.exit(0);
			} else {
				strBrowserName = strBrowserName.toLowerCase();

				switch (strBrowserName) {

				case "chrome":

					System.setProperty("webdriver.chrome.driver",
							"./src/test/resources/Driver_Engines/chromedriver.exe");
					Map<String, Object> prefs = new HashMap<String, Object>();

					// To Turns off multiple download warning
					prefs.put("profile.default_content_settings.popups", 0);
					prefs.put("profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 1);

					// Turns off download prompt
					prefs.put("download.prompt_for_download", false);
					prefs.put("credentials_enable_service", false);

					// To stop Save password propmts
					prefs.put("password_manager_enabled", false);

					ChromeOptions options = new ChromeOptions();
					options.addArguments("chrome.switches", "--disable-extensions");

					// To Disable any browser notifications
					options.addArguments("--disable-notifications");

					// To disable yellow strip info bar which prompts info
					// messages
					options.addArguments("disable-infobars");
					options.setExperimentalOption("prefs", prefs);
					options.addArguments("--test-type");

					driver = new ChromeDriver(options);
					break;

				case "firefox":

					System.setProperty("webdriver.gecko.driver", "./src/test/resources/geckodriver.exe");
					FirefoxProfile fp = new FirefoxProfile();
					fp.setPreference("intl.accept_languages", "no,en-us,en");
					DesiredCapabilities capabilities = DesiredCapabilities.firefox();
					capabilities.setCapability(FirefoxDriver.MARIONETTE, true);
					capabilities.setCapability(FirefoxDriver.PROFILE, fp);
					capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					FirefoxOptions firefoxOptions = new FirefoxOptions(capabilities);
					driver = new FirefoxDriver(firefoxOptions);
					break;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		return driver;
	}
}