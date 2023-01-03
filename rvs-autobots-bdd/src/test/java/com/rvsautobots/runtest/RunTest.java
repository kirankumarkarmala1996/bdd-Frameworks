package com.rvsautobots.runtest;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.rvsautobots.base.BrowserManager;
import com.rvsautobots.exceptions.AutomationException;
import com.rvsautobots.reporting.AutomationReport;

import cucumber.api.CucumberOptions;

@CucumberOptions(strict = true, monochrome = true, features = "./src/test/resources/Features", glue = "com.rvsautobots.stepdefinitions", plugin = {
		"com.cucumber.listener.ExtentCucumberFormatter:" }, tags = { "@test" })
public class RunTest extends BrowserManager {

	AutomationReport rmObj = new AutomationReport();

	@BeforeSuite
	public void testFun() throws UnknownHostException, AutomationException {
		rmObj.setUp();
		launchBrowser();
		System.out.println("Inside the BeforeSuite");
	}

	@AfterClass
	public void afterClassMethod() throws AddressException, IOException, MessagingException {
		rmObj.writeReport();
	}

	@AfterSuite
	public void closeApp()
			throws AddressException, MessagingException, IOException, AutomationException, InterruptedException {
		rmObj.tearDown();
	}

}
