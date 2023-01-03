package com.rvsautobots.stepdefinitions;

import org.testng.Assert;

import cucumber.api.java.en.Given;

public class StepDefinitions {

	// This step def is using to navigate to a URL which is mentioned
	@Given("^the user navigates to \"([^\"]*)\" application$")
	public void the_user_navigates_to(String strURL) throws Throwable {

		System.out.println("URl is: " + strURL);

	}

	@Given("^the user navigates to \"([^\"]*)\" of the application$")
	public void the_user_navigates_to_of_the_application(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Assert.assertTrue(false);
	}

}
