package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnection {
	
	public static Connection getConnection() throws SQLException {
		String url ="jdbc:sqlserver://DESKTOP-BJVCGJB\\SQLEXPRESS;;databaseName=Northwind;integratedSecurity=true;TrustServerCertificate=True;Trusted_Connection=true";
		String username="";
		String pwd="";
		return DriverManager.getConnection(url);
		}

}
