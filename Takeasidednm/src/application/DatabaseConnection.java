package application;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
	
	private Connection connection;
	public void setConnection() {
		String serverName = "LAPTOP-LGCQ6NPH";
		int portNumber = 1433;
		String databaseName = "TakeASide";
		String username = "sa";
		String password = "takeaside";
		try { 
			String connectionUrl = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + databaseName + ";encrypt=true;trustServerCertificate=true";
			connection = DriverManager.getConnection(connectionUrl, username, password);
			System.out.println("Connected to SQL Server");
			} catch (SQLException e) {
				e.printStackTrace();
				}
		}
	public Connection getConnection() {
			return connection;
	}
	public void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				
			}
		}catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}