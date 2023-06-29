package application;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import application.Service.Book;
import application.Service.Game;
import application.Service.Movie;
import application.Service.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class webmasterHomepageController implements Initializable{
	
	
	@FXML
	private TextField addMovieNameTextField;
	@FXML
	private TextField addMovieYearTextField;
	@FXML
	private TextField addMovieGenreTextField;
	@FXML
	private TextField addMovieDirectorTextField;
	@FXML
	private TextField addMovieProducerTextField;
	@FXML
	private TextField addMovieActorTextField;
	@FXML
	private TextField addMovieScenaristTextField;
	@FXML
	private Button addMovieSubmitButton;
	
	@FXML
	private TextField addBookNameTextField;
	@FXML
	private TextField addBookYearTextField;
	@FXML
	private TextField addBookGenreTextField;
	@FXML
	private TextField addBookAutorTextField;
	@FXML
	private TextField addBookPageNumberTextField;
	@FXML
	private TextField addBookPublisherTextField;
	@FXML
	private Button addBookSubmitButton;
	
	@FXML
	private TextField addGameNameTextField;
	@FXML
	private TextField addGameYearTextField;
	@FXML
	private TextField addGameGenreTextField;
	@FXML
	private TextField addGameDeveloperTextField;
	@FXML
	private TextField addGameDurationTextField;
	@FXML
	private TextField addGameTypeTextField;
	@FXML
	private Button addGameSubmitButton;
	
	@FXML
	private TextField deleteUserEmailTextField;
	@FXML
	private Button deleteUserDeleteButton;

	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	
	public void addBook(ActionEvent event) {
		Book book = new Book();
   	 
	   	String name = addBookNameTextField.getText();
	   	int year = Integer.parseInt(addBookYearTextField.getText());
	   	String genre = addBookGenreTextField.getText();
	   	String autor = addBookAutorTextField.getText();
	   	int page_number = Integer.parseInt(addBookPageNumberTextField.getText());
	   	String publisher = addBookPublisherTextField.getText();
	   	
	   	book.setName(name);
	   	book.setYear(year);
	   	book.setGenre(genre);
	   	book.setAutor(autor);
	   	book.setPageNumber(page_number);
	   	book.setPubliher(publisher);
	   	 
	   	 DatabaseConnection dbConnection = new DatabaseConnection();
	   	 dbConnection.setConnection();
	   	 Connection con = dbConnection.getConnection();
	   	 
	   	if(book.isExistingBook(con, name, year, autor, page_number, publisher, genre)==true) {
		   	// Show an error message
			    Alert alert = new Alert(AlertType.ERROR);
			    alert.setTitle("Failure");
			    alert.setHeaderText(null);
			    alert.setContentText("This book already exists.");
			    alert.showAndWait();
			    return;
		}else {
		   		book.insertIntoDatabase(con);
		   		Alert alert = new Alert(AlertType.INFORMATION);
			    alert.setTitle("Successfull");
			    alert.setHeaderText(null);
			    alert.setContentText("You have added successfully.");
			    alert.showAndWait();
		}
	}
	
	public void addGame(ActionEvent event) {
		Game game = new Game();
   	 
	   	String name = addGameNameTextField.getText();
	   	int year = Integer.parseInt(addGameYearTextField.getText());
	   	String genre = addBookGenreTextField.getText();
	   	String developer = addGameDeveloperTextField.getText();
	   	int duration = Integer.parseInt(addGameDurationTextField.getText());
	   	String type = addGameTypeTextField.getText();
	   	
	   	game.setName(name);
	   	game.setYear(year);
	   	game.setGenre(genre);
	   	game.setDeveloper(developer);
	   	game.setDuration(duration);
	   	game.setType(type);
	   	 
	   	 DatabaseConnection dbConnection = new DatabaseConnection();
	   	 dbConnection.setConnection();
	   	 Connection con = dbConnection.getConnection();
	   	 
	   	if(game.isExistingGame(con,name, year, duration, developer, genre, type)==true) {
		   	// Show an error message
			    Alert alert = new Alert(AlertType.ERROR);
			    alert.setTitle("Failure");
			    alert.setHeaderText(null);
			    alert.setContentText("This game already exists.");
			    alert.showAndWait();
			    return;
		}else {
		   		game.insertIntoDatabase(con);
		   		Alert alert = new Alert(AlertType.INFORMATION);
			    alert.setTitle("Successfull");
			    alert.setHeaderText(null);
			    alert.setContentText("You have added successfully.");
			    alert.showAndWait();
		}
	}
	
	public void addMovie(ActionEvent event) {
		Movie movie = new Movie();
   	 
	   	String name = addMovieNameTextField.getText();
	   	int year = Integer.parseInt(addMovieYearTextField.getText());
	   	String genre = addMovieGenreTextField.getText();
	   	String director = addMovieDirectorTextField.getText();
	   	String producer = addMovieProducerTextField.getText();
	   	String actor = addMovieActorTextField.getText();
	   	String scenarist = addMovieScenaristTextField.getText();
	   	
	   	movie.setName(name);
	   	movie.setYear(year);
	   	movie.setGenre(genre);
	   	movie.setDirector(director);
	   	movie.setProducer(producer);
	   	movie.setActor(actor);
	   	movie.setScenarist(scenarist);
	   	 
	   	 DatabaseConnection dbConnection = new DatabaseConnection();
	   	 dbConnection.setConnection();
	   	 Connection con = dbConnection.getConnection();
	   	 
	   	if(movie.isExistingMovie(con, name, year, director, scenarist, producer, genre, actor)==true) {
		   	// Show an error message
			    Alert alert = new Alert(AlertType.ERROR);
			    alert.setTitle("Failure");
			    alert.setHeaderText(null);
			    alert.setContentText("This movie already exists.");
			    alert.showAndWait();
			    return;
		}else {
		   		movie.insertIntoDatabase(con);
		   		Alert alert = new Alert(AlertType.INFORMATION);
			    alert.setTitle("Successfull");
			    alert.setHeaderText(null);
			    alert.setContentText("You have added successfully.");
			    alert.showAndWait();
		}
	}
	
	public void HandleDelete(ActionEvent event) {
		String email = deleteUserEmailTextField.getText();
		
		DatabaseConnection dbConnection = new DatabaseConnection();
	   	dbConnection.setConnection();
	   	Connection con = dbConnection.getConnection();
	   	
	   	User user = new User();
	   	boolean l = user.deleteFromDatabase(con, email);
	   	
	   	if(l) {
	   		Alert alert = new Alert(AlertType.INFORMATION);
		    alert.setTitle("Successfull");
		    alert.setHeaderText(null);
		    alert.setContentText("User deleted.");
		    alert.showAndWait();
	   	}else {
	   		Alert alert = new Alert(AlertType.ERROR);
		    alert.setTitle("Failure");
		    alert.setHeaderText(null);
		    alert.setContentText("User not found or deletion failed.");
		    alert.showAndWait();
	   	}
	}
}
