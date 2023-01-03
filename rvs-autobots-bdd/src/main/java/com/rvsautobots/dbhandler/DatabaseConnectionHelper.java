package com.rvsautobots.dbhandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.rvsautobots.datahandler.PropertyDataHandler;
import com.rvsautobots.exceptions.AutomationException;

public class DatabaseConnectionHelper {

	/**
	 * @Author AutobotsBDD Cucumber Team
	 * @Description :BDD
	 */
	public Statement stmt = null;
	public Connection con = null;
	public String sqldb_Url = "";

	public DatabaseConnectionHelper() throws AutomationException {

		try {
			sqldb_Url = PropertyDataHandler.fetchPropertyValue("config", "dbUrl");

		} catch (Exception e) {
			throw new AutomationException("Error in Connection string");
		}

	}

	/**
	 * Method to create the statement
	 * 
	 * @param databaseSystem
	 * @return Statement
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws AutomationException
	 */

	public Statement getConnectionStaement() throws ClassNotFoundException, SQLException, AutomationException {

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(sqldb_Url);
			stmt = con.createStatement();

		} catch (Exception e) {
			throw new AutomationException("Exception while DB connection", e);
		}
		return stmt;
	}

}
