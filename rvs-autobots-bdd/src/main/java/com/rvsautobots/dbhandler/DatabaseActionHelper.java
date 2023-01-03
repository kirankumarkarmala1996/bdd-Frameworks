package com.rvsautobots.dbhandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.rvsautobots.exceptions.AutomationException;

public class DatabaseActionHelper extends DatabaseConnectionHelper {
	/**
	 * @Author AutobotsBDD Cucumber Team
	 * @Description :BDD
	 */

	public DatabaseActionHelper() throws AutomationException {
		super();
	}

	ResultSet res;

	/**
	 * Method to operate Database query statements
	 * @param databaseSystem
	 * @param queryStatement
	 * @throws AutomationException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	public ResultSet selectQuery(Statement stmt, String selectquery) throws AutomationException {
		try {
			if (selectquery == null || selectquery.equals("")) {
				throw new AutomationException("");
			}
		} catch (Exception e) {
			e = new Exception("");
			throw new AutomationException("Query passed is null");
		}
		try {
			stmt = new DatabaseConnectionHelper().getConnectionStaement();
			String query = selectquery;
			res = stmt.executeQuery(query);
		} catch (Exception e) {
			throw new AutomationException(e);
		}
		return res;
	}

	

}
