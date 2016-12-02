package fuzzymovies;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
	
	public static Connection getConnection(String url){
		
		Connection connection = null;
		
		try{
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + url);
		}
		catch(Exception e){
			System.out.print(e.getMessage());
		}
		
		return connection;
	}
}
