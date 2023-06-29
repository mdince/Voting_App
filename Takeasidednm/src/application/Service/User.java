package application.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	
	private String email;
	private String first_name;
	private String last_name;
	private int gender;
	private String password;
	private int age;
	
	public User() {
		
	}
	
	public User(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getName() {
		return first_name;
	}
	
	public String getLastName() {
		return last_name;
	}
	
	public int getGender() {
		return gender;
	}
	
	public String getPassword() {
		return password;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setEmail(String a) {
		email = a;
	}
	
	public void setName(String a) {
		first_name = a;
	}
	
	public void setLastName(String a) {
		last_name = a;
	}
	
	public void setGender(int a) {
		gender = a;
	}
	
	public void setPassword(String a) {
		password = a;
	}
	
	public void setAge(int a) {
		age = a;
	}
	
	@Override
	public boolean equals(Object anObject){
		if (anObject instanceof User){
			User y = (User) anObject;
			if (getEmail().equals(y.getEmail())){
				return true;
			}
			return false;
	    }
	    return false;
	}
	
	public void insertIntoDatabase(Connection connection) {
	    try {
	        
	        String query = "INSERT INTO Users (email, first_name, last_name, gender, u_password, age) VALUES (?, ?, ?, ?, ?, ?)";
	        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

	        statement.setString(1, this.getEmail());
	        statement.setString(2, this.getName());
	        statement.setString(3, this.getLastName());
	        statement.setInt(4, this.getGender());
	        statement.setString(5, this.getPassword());
	        statement.setInt(6, this.getAge());

	        statement.executeUpdate();
	        statement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public boolean isExistingUser(Connection connection, String email) {
	    try {
	        String query = "SELECT COUNT(*) FROM Users WHERE email = ?";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, email);

	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            int count = resultSet.getInt(1);
	            return count > 0; // Returns true if the count is greater than 0
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false; // Return false in case of an exception or no matching user found
	}
	
	public boolean deleteFromDatabase(Connection connection, String email) {
	    try {
	        String query = "DELETE FROM Users WHERE email = ?";
	        PreparedStatement statement = connection.prepareStatement(query);

	        statement.setString(1, email);

	        int rowsDeleted = statement.executeUpdate();

	        statement.close();

	        if (rowsDeleted > 0) {
	            System.out.println("User deleted successfully.");
	            return true;
	        } else {
	            System.out.println("User not found or deletion failed.");
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	 public boolean loginUser(Connection connection, String email, String password) {
	        try {
	            String query = "SELECT COUNT(*) FROM Users WHERE email = ? AND u_password = ?";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, email);
	            statement.setString(2, password);

	            ResultSet resultSet = statement.executeQuery();
	            if (resultSet.next()) {
	                int count = resultSet.getInt(1);
	                return count > 0; // Returns true if the count is greater than 0
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        return false; // Return false in case of an exception or no matching user found
	    }


}
