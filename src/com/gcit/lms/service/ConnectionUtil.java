package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost/library";
	private static String user = "root";
	private static String pass = "Edc12@SQL";

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, pass);
		conn.setAutoCommit(Boolean.FALSE);
		return conn;
	}
}
