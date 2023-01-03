package com.rvsautobots.datahandler;

import java.io.InputStream;
import java.util.Properties;

import org.junit.Assert;

public class PropertyDataHandler {

	/**
	 * Method to read values from Property Files
	 * 
	 * @author AutobotsBDD Cucumber Team
	 * @param fileName and VariableName/KeywordName
	 * @returns String
	 **/

	public static String fetchPropertyValue(String fileName, String strVarName) {
		String propertyValue = null;
		try {

			Properties properties = new Properties();
			ClassLoader classLoader = PropertyDataHandler.class.getClassLoader();
			InputStream input = classLoader.getResourceAsStream(fileName + ".properties");
			properties.load(input);
			propertyValue = properties.getProperty(strVarName);

		} catch (

		Exception e) {
			Assert.fail("Property is not defined for : " + strVarName);

		}
		return propertyValue;
	}
}
