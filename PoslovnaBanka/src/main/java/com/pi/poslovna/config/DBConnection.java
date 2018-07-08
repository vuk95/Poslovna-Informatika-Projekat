package com.pi.poslovna.config;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Klasa koja je zaduzena za uspostavljanje konekcije sa bazom podataka.
 *
 */
public class DBConnection {

	private static DBConnection instance;
	private  Connection connection;


	public static DBConnection getInstance() {
		
		if (instance == null) {
			instance = new DBConnection();
			instance.openConnection();
		}
		return instance;
	}
	
	
	private void openConnection() {	
			try {
					Class.forName("com.mysql.jdbc.Driver");
		    		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?useSSL=true","root","isatim32");    		
				} catch(Exception e) {
					e.printStackTrace();
				}
	}
	
	public  Connection getConnection() {
		return connection;
	}
	
}
