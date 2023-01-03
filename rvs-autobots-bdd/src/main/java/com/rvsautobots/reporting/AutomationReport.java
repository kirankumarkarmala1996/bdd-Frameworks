package com.rvsautobots.reporting;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;
import com.rvsautobots.base.BrowserManager;
import com.rvsautobots.exceptions.AutomationException;
import com.rvsautobots.utils.AutomationMail;

/**
 * @Author AutobotsBDD Cucumber Team
 * @Description :BDD
 */
public class AutomationReport extends BrowserManager {

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;

	/**
	 * Methods for setting the Extent Report
	 **/

	public void setUp() throws UnknownHostException {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
		Date date = new Date();
		String filePathdate = dateFormat.format(date).toString();
		extent = new ExtentReports();
		ExtentProperties extentProperties = ExtentProperties.INSTANCE;
		extentProperties.setReportPath("Reports/" + filePathdate + "_AutomationReport.html");
	}

	/**
	 * Method to send mail after complete all test executions
	 * 
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws IOException
	 * @throws AutomationException
	 */
	public void tearDown() throws AddressException, MessagingException, IOException, AutomationException {
		AutomationMail.sendMailReport();
	}

	/**
	 * Method to write details into HTML report
	 * 
	 * @throws IOException
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void writeReport() throws IOException, AddressException, MessagingException {
		Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
		Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
		Reporter.setSystemInfo("Machine", System.getProperty("os.name"));
		Reporter.setSystemInfo("Java Version", System.getProperty("java.version"));
	}

}
