package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import application.Service.User;

public class signInWindowController implements Initializable{

    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private Hyperlink returnLink;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField ageTextField;
    @FXML
    private Button submitButton;
    
    
    

    @FXML
    void Select(ActionEvent event) {
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<String> gendersList = FXCollections.observableArrayList("male","female");
    	genderComboBox.setItems(gendersList);
	}
	
	@FXML
	public void handleGoBackLinkClick(ActionEvent event) {
	    try {
	        // Load the FXML file for the first window
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Sample.fxml"));
	        Parent root = loader.load();

	        // Create a new stage/window
	        Stage newStage = new Stage();
	        newStage.setScene(new Scene(root));
	        newStage.setTitle("First Window");

	        // Show the first window
	        newStage.show();

	        // Close the current window
	        Stage currentStage = (Stage) returnLink.getScene().getWindow();
	        currentStage.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	@FXML
    public void handleSubmitClick(ActionEvent event) {
   	    User user = new User();
   	    
	   	String email = emailTextField.getText();
	   	String password = passwordTextField.getText();
	   	String name = nameTextField.getText();
	   	String surname = surnameTextField.getText();
	   	int age = Integer.parseInt(ageTextField.getText());
	   	String gender = genderComboBox.getValue();
	   	int genderBit = (gender.equals("male")) ? 1 : 0;
	   	
	   	user.setEmail(email);
        user.setName(name);
        user.setLastName(surname);
        user.setGender(genderBit);
        user.setPassword(password);
        user.setAge(age);
	   	 
	   	DatabaseConnection dbConnection = new DatabaseConnection();
	   	dbConnection.setConnection();
	   	Connection con = dbConnection.getConnection();
	   	
	   	if(user.isExistingUser(con, email)==true) {
	   	// Show an error message
		    Alert alert = new Alert(AlertType.ERROR);
		    alert.setTitle("SignIn Failed");
		    alert.setHeaderText(null);
		    alert.setContentText("This e-mail already exists.");
		    alert.showAndWait();
		    return;
	   	}else {
	   		user.insertIntoDatabase(con);
	   		Alert alert = new Alert(AlertType.INFORMATION);
		    alert.setTitle("SignIn Successfull");
		    alert.setHeaderText(null);
		    alert.setContentText("You have created your account.");
		    alert.showAndWait();
	   	}
   	}


}