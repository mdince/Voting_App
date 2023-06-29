package application;
import javafx.scene.control.Alert;
import java.sql.Connection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import application.Service.Book;
import application.Service.Game;
import application.Service.Movie;
import application.Service.User;

public class SampleController {

    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private Hyperlink signInLink;
    @FXML
    private Button loginButton;
    @FXML
    private Hyperlink webmasterLink;
    
    User user = new User();
    
    @FXML
    public void handleSignInLinkClick(ActionEvent event) {
        try {
            // Load the FXML file for the new window
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/signInWindow.fxml"));
            Parent root = loader.load();

            // Create a new stage/window
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle("Sign in!");

            // Show the new window
            newStage.show();

            // Close the current window
            Stage currentStage = (Stage) signInLink.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        
     @FXML
     public void handleWebmasterLinkClick(ActionEvent event) {
    	try {
            // Load the FXML file for the new window
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/webMaster.fxml"));
            Parent root = loader.load();

            // Create a new stage/window
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle("Webmaster");

            // Show the new window
            newStage.show();

            // Close the current window
            Stage currentStage = (Stage) webmasterLink.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
     }
     
     @FXML
     public void handleLoginLinkClick(ActionEvent event) {
    	 
    	 String email = emailField.getText();
    	 String password = passwordField.getText();
    	 
    	 DatabaseConnection dbConnection = new DatabaseConnection();
    	 dbConnection.setConnection();
    	 Connection con = dbConnection.getConnection();
    	 
    	 boolean l = user.loginUser(con, email, password); 
    	 
    	 if (l==false) {
    		    // Show an error message
    		    Alert alert = new Alert(AlertType.ERROR);
    		    alert.setTitle("Login Failed");
    		    alert.setHeaderText(null);
    		    alert.setContentText("Invalid email or password. Please try again.");
    		    alert.showAndWait();
    	}else {
    		try {
	             // Load the FXML file for the new window
	         	 FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Homepage.fxml"));
	             Parent root = loader.load();
	             
	             HomepageController homepageController = loader.getController();
	             homepageController.setEmailLabel(email);
	             
	             Movie movie = new Movie();
	             Book book = new Book();
	             Game game = new Game();
	             homepageController.setTopBooks(book.TopRatedBooks().toArray());
	             homepageController.setTopGames(game.TopRatedGames().toArray());
	             homepageController.setTopMovies(movie.TopRatedMovies().toArray());
	
	             // Create a new stage/window
	             Stage newStage = new Stage();
	             newStage.setScene(new Scene(root));
	             newStage.setTitle("Homepage");
	             
	             
	
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
    public void initialize() {
        Platform.runLater(() -> loginButton.requestFocus()); // Set focus to the submit button using Platform.runLater()
    }

    // Add any additional methods or event handlers as needed

}
