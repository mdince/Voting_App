package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import application.Service.User;
import application.Service.WebMaster;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class webMasterController implements Initializable{

	
	@FXML
	private Button loginButton;
	@FXML
    private TextField loginIdTextField;
	@FXML
    private TextField loginTnoTextField;
	@FXML
    private TextField SignInNameTextField;
	@FXML
    private TextField SignInSurnameTextField;
	@FXML
    private TextField SignInIdTextField;
	@FXML
    private TextField SignInTnoTextField;
	@FXML
    private ComboBox<String> genderComboBox;
	@FXML
	private Button submitButton;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<String> gendersList = FXCollections.observableArrayList("male","female");
    	genderComboBox.setItems(gendersList);
		
	}
	
	@FXML
    public void handleLoginLinkClick(ActionEvent event) {
		
		WebMaster wm = new WebMaster();
		
		String id = loginIdTextField.getText();
   	 	String tNo = loginTnoTextField.getText();
   	 	
   	 	DatabaseConnection dbConnection = new DatabaseConnection();
	   	dbConnection.setConnection();
	   	Connection con = dbConnection.getConnection();
   	 	
   	 	boolean l = wm.loginWebmaster(con, id, tNo);
   	 	
   	 	if(l==false) {
   	 	// Show an error message
		    Alert alert = new Alert(AlertType.ERROR);
		    alert.setTitle("Login Failed");
		    alert.setHeaderText(null);
		    alert.setContentText("Invalid id or tNo. Please try again.");
		    alert.showAndWait();
   	 	}else {
   	 		try {
	            // Load the FXML file for the new window
	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/webmasterHomepage.fxml"));
	            Parent root = loader.load();
	
	            // Create a new stage/window
	            Stage newStage = new Stage();
	            newStage.setScene(new Scene(root));
	            newStage.setTitle("Webmaster Homepage");
	
	            // Show the new window
	            newStage.show();
	
	            // Close the current window
	            Stage currentStage = (Stage) loginButton.getScene().getWindow();
	            currentStage.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
   	 	}
        
    }
	
	@FXML
    public void handleSubmitClick(ActionEvent event) {
   	    WebMaster wm = new WebMaster();
   	    
	   	String name = SignInNameTextField.getText();
	   	String surname = SignInSurnameTextField.getText();
	   	String id = SignInIdTextField.getText();
	   	String tNo = SignInTnoTextField.getText();
	   	String gender = genderComboBox.getValue();
	   	int genderBit = (gender.equals("male")) ? 1 : 0;
	   	
	   	
	   	
        wm.setName(name);
        wm.setLastName(surname);
        wm.setGender(genderBit);
        wm.setId(id);
        wm.setTNo(tNo);
	   	 
	   	DatabaseConnection dbConnection = new DatabaseConnection();
	   	dbConnection.setConnection();
	   	Connection con = dbConnection.getConnection();
	   	
	   	if(wm.isExistingWebmaster(con, id)==true) {
	   	// Show an error message
		    Alert alert = new Alert(AlertType.ERROR);
		    alert.setTitle("SignIn Failed");
		    alert.setHeaderText(null);
		    alert.setContentText("This id already exists.");
		    alert.showAndWait();
		    return;
	   	}else {
	   		if(tNo != "123") { //PRIVATE INFORMATION ONLY FOR ADMINS 
		   		Alert alert = new Alert(AlertType.ERROR);
			    alert.setTitle("SignIn Failed");
			    alert.setHeaderText(null);
			    alert.setContentText("You are not authorized.");
			    alert.showAndWait();
			    return;
	   		}
	   		wm.insertIntoDatabase(con);
	   		Alert alert = new Alert(AlertType.INFORMATION);
		    alert.setTitle("SignIn Successfull");
		    alert.setHeaderText(null);
		    alert.setContentText("You have created your account.");
		    alert.showAndWait();
	   	}
   	}
	
}