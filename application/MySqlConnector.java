package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnector {
	private static final String DB_NAME = "podcastdb";
	private static final String DB_IP= "localhost:88";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "dckfdtio2005"; //bld hadchi hada 3la 7sabk
	
	public static Connection getDBConnection() {
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://"+DB_IP+"/"+DB_NAME, DB_USER,DB_PASSWORD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return connection;
	}
}
