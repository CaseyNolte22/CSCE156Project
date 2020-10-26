package com.bc;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class Connector {
	
	public static final String PARAMETERS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	public static final String USERNAME = "cnolte";
	public static final String PASSWORD = "RMc0GnXh";
	public static final String URL = "jdbc:mysql://cse.unl.edu/" + USERNAME + PARAMETERS;
	
	/**
	 * 
	 * @return Returns established connection to database.
	 */
	public static final Connection establishConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(Connector.URL, Connector.USERNAME, Connector.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return conn;
	}
}
