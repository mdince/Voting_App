package application.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WebMaster {
	private String Id;
	private String first_name;
	private String last_name;
	private int gender;
	private String tNo;
	
	public WebMaster(String Id) {
		this.Id = Id;
	}
	
	public WebMaster() {
		
	}
	
	public String getId() {
		return Id;
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
	
	public String getTNo() {
		return tNo;
	}
	
	public void setId(String a) {
		Id = a;
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
	
	public void setTNo(String a) {
		tNo = a;
	}
	
	@Override
	public boolean equals(Object anObject){
		if (anObject instanceof WebMaster){
			WebMaster y = (WebMaster) anObject;
			if (getId().equals(y.getId())){
				return true;
			}
			return false;
	    }
	    return false;
	}
	
	public void insertIntoDatabase(Connection connection) {
	    try {
	        String query = "INSERT INTO Webmaster (id, first_name, last_name, gender, tNo) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

	        statement.setString(1, this.getId());
	        statement.setString(2, this.getName());
	        statement.setString(3, this.getLastName());
	        statement.setInt(4, this.getGender());
	        statement.setString(5, this.getTNo());

	        statement.executeUpdate();
	        statement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public boolean isExistingWebmaster(Connection connection, String id) {
	    try {
	        String query = "SELECT COUNT(*) FROM Webmaster WHERE id = ?";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, id);

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
	
	public boolean loginWebmaster(Connection connection, String id, String tNo) {
        try {
            String query = "SELECT COUNT(*) FROM Webmaster WHERE id = ? AND tNo = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            statement.setString(2, tNo);

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
