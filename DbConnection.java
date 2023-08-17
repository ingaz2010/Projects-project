package projects.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import projects.exception.DbException;

public class DbConnection {
	private static String HOST = "localhost";
	private static String PASSWORD = "Qaz12345";
	private static int PORT = 3306;
	private static String SCHEMA = "projects";
	private static String USER = "projects";

	public DbConnection() {
		// TODO Auto-generated constructor stub
		
	}
	public static Connection getConnection() {
		String uri = String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s&useSSL=false", HOST, PORT, SCHEMA, USER, PASSWORD);
		Connection connect;
		try {
			connect = DriverManager.getConnection(uri);
			System.out.println("Connection to schema " + SCHEMA + " is successful");
			return connect;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to get conncection at " + uri);
			throw new DbException("Unable to get connection at \" + uri");
		}
		
	}

	
}
