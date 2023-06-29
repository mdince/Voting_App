package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;
import application.Service.Movie;
import application.Service.Game;
import application.Service.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class HomepageController implements Initializable{
	
	@FXML
	private  Label emailLabel;
	@FXML
	private ListView<Object> homepageMoviesListView;	
	@FXML
	private ListView<Object> homepageBooksListView;	
	@FXML
	private ListView<Object> homepageGamesListView;	
	
	
	@FXML
	private TextField moviepageSearchBarTextField;	
	@FXML
	private TextField moviepageYearTextField;
	@FXML
	private TextField moviepageGenreTextField;
	@FXML
	private TextField moviepageDirectorTextField;
	@FXML
	private TextField moviepageScenaristTextField;
	@FXML
	private TextField moviepageProducerTextField;
	@FXML
	private ListView<Object> moviepageMoviesListView;
	@FXML
	private Button moviepageFilterButton;
	@FXML
	private Button moviepageSearchButton;
	@FXML
	private Button moviepageLikeButton;
	@FXML
	private Button moviepageDislikeButton;
	@FXML
	private TextArea moviepageDescriptionTextArea;
	
	
	@FXML
	private TextField bookpageSearchBarTextField;	
	@FXML
	private TextField bookpageYearTextField;
	@FXML
	private TextField bookpageGenreTextField;
	@FXML
	private TextField bookpageAutorTextField;
	@FXML
	private TextField bookpagePageNumberTextField;
	@FXML
	private ListView<Object> bookpageBooksListView;
	@FXML
	private Button bookpageFilterButton;
	@FXML
	private Button bookpageSearchButton;
	@FXML
	private Button bookpageLikeButton;
	@FXML
	private Button bookpageDislikeButton;
	@FXML
	private TextArea bookpageDescriptionTextArea;

	
	
	@FXML
	private TextField gamepageSearchBarTextField;	
	@FXML
	private TextField gamepageYearTextField;
	@FXML
	private TextField gamepageGenreTextField;
	@FXML
	private TextField gamepageDeveloperTextField;
	@FXML
	private TextField gamepageTypeTextField;
	@FXML
	private TextField gamepageDurationTextField;
	@FXML
	private ListView<Object> gamepageGamesListView;
	@FXML
	private Button gamepageFilterButton;
	@FXML
	private Button gamepageSearchButton;
	@FXML
	private Button gamepageLikeButton;
	@FXML
	private Button gamepageDislikeButton;
	@FXML
	private TextArea gamepageDescriptionTextArea;
	
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	
	public  void setEmailLabel(String s) {
		emailLabel.setText(s);
	}
	
	public void setTopMovies(Object[] arr) {
		homepageMoviesListView.getItems().clear();
		homepageMoviesListView.getItems().addAll(arr);
	}
	
	public void setTopBooks(Object[] arr) {
		homepageBooksListView.getItems().clear();
		homepageBooksListView.getItems().addAll(arr);
	}
	
	public void setTopGames(Object[] arr) {
		homepageGamesListView.getItems().clear();
		homepageGamesListView.getItems().addAll(arr);
	}
	
	public void handleMovieSearch(ActionEvent event) {
		String name = moviepageSearchBarTextField.getText();
		Movie movie = new Movie();
		
		DatabaseConnection dbConnection = new DatabaseConnection();
	   	dbConnection.setConnection();
	   	Connection con = dbConnection.getConnection();
	   	
	   	String info = movie.getMovieInfo(con, name);
	   	moviepageDescriptionTextArea.setText(info);
	}
	
	public void handleBookSearch(ActionEvent event) {
		String name = bookpageSearchBarTextField.getText();
		Book book = new Book();
		
		DatabaseConnection dbConnection = new DatabaseConnection();
	   	dbConnection.setConnection();
	   	Connection con = dbConnection.getConnection();
	   	
	   	String info = book.getBookInfo(con, name);
	   	bookpageDescriptionTextArea.setText(info);
	}
	
	public void handleGameSearch(ActionEvent event) {
		String name = gamepageSearchBarTextField.getText();
		Game game = new Game();
		
		DatabaseConnection dbConnection = new DatabaseConnection();
	   	dbConnection.setConnection();
	   	Connection con = dbConnection.getConnection();
	   	
	   	String info = game.getGameInfo(con, name);
	   	gamepageDescriptionTextArea.setText(info);
	}
	
	public void handleMovieLike(ActionEvent event) {
		String name = moviepageSearchBarTextField.getText();
		Movie movie = new Movie();
		
		DatabaseConnection dbConnection = new DatabaseConnection();
	   	dbConnection.setConnection();
	   	Connection con = dbConnection.getConnection();
	   	
	   	String query = "SELECT movie_id FROM Movies WHERE m_name = ?";
	   	try {
	   	    PreparedStatement statement = con.prepareStatement(query);
	   	    statement.setString(1, name);

	   	    ResultSet resultSet = statement.executeQuery();
	   	    if (resultSet.next()) {
	   	        int movieId = resultSet.getInt("movie_id");
	   	        if(movie.isExistingVote(con, emailLabel.getText(), movieId)) {
	   	        	Alert alert = new Alert(AlertType.ERROR);
	    		    alert.setTitle("Vote Failed");
	    		    alert.setHeaderText(null);
	    		    alert.setContentText("You have already voted.");
	    		    alert.showAndWait();
	   	        }else {
	   	        	movie.addToVoted(con, emailLabel.getText(), movieId);
	   	        	movie.updateLikeRate(con, name);
	   	        	Alert alert = new Alert(AlertType.INFORMATION);
				    alert.setTitle("Successfull");
				    alert.setHeaderText(null);
				    alert.setContentText("You have voted successfully.");
				    alert.showAndWait();
	   	        }
	   	        // Process the movieId value as needed
	   	    } else {
	   	        // Handle the case when no rows are returned
	   	    }
	   	    resultSet.close();
	   	    statement.close();
	   	} catch (SQLException e) {
	   	    // Handle any SQL exceptions that occur
	   	    e.printStackTrace();
	   	}
	}
	
	public void handleMovieDisike(ActionEvent event) {
		String name = moviepageSearchBarTextField.getText();
		Movie movie = new Movie();
		
		DatabaseConnection dbConnection = new DatabaseConnection();
	   	dbConnection.setConnection();
	   	Connection con = dbConnection.getConnection();
	   	
	   	String query = "SELECT movie_id FROM Movies WHERE m_name = ?";
	   	try {
	   	    PreparedStatement statement = con.prepareStatement(query);
	   	    statement.setString(1, name);

	   	    ResultSet resultSet = statement.executeQuery();
	   	    if (resultSet.next()) {
	   	        int movieId = resultSet.getInt("movie_id");
	   	        if(movie.isExistingVote(con, emailLabel.getText(), movieId)) {
	   	        	Alert alert = new Alert(AlertType.ERROR);
	    		    alert.setTitle("Vote Failed");
	    		    alert.setHeaderText(null);
	    		    alert.setContentText("You have already voted.");
	    		    alert.showAndWait();
	   	        }else {
	   	        	movie.addToVoted(con, emailLabel.getText(), movieId);
	   	        	movie.updateDislikeRate(con, name);
	   	        	Alert alert = new Alert(AlertType.INFORMATION);
				    alert.setTitle("Successfull");
				    alert.setHeaderText(null);
				    alert.setContentText("You have voted successfully.");
				    alert.showAndWait();
	   	        }
	   	        // Process the movieId value as needed
	   	    } else {
	   	        // Handle the case when no rows are returned
	   	    }
	   	    resultSet.close();
	   	    statement.close();
	   	} catch (SQLException e) {
	   	    // Handle any SQL exceptions that occur
	   	    e.printStackTrace();
	   	}
	}
	
	public void handleBookLike(ActionEvent event) {
		String name = bookpageSearchBarTextField.getText();
		Book book = new Book();
		
		DatabaseConnection dbConnection = new DatabaseConnection();
	   	dbConnection.setConnection();
	   	Connection con = dbConnection.getConnection();
	   	
	   	String query = "SELECT book_id FROM Books WHERE b_name = ?";
	   	try {
	   	    PreparedStatement statement = con.prepareStatement(query);
	   	    statement.setString(1, name);

	   	    ResultSet resultSet = statement.executeQuery();
	   	    if (resultSet.next()) {
	   	        int bookId = resultSet.getInt("book_id");
	   	        if(book.isExistingVote(con, emailLabel.getText(), bookId)) {
	   	        	Alert alert = new Alert(AlertType.ERROR);
	    		    alert.setTitle("Vote Failed");
	    		    alert.setHeaderText(null);
	    		    alert.setContentText("You have already voted.");
	    		    alert.showAndWait();
	   	        }else {
	   	        	book.addToVoted(con, emailLabel.getText(), bookId);
	   	        	book.updateLikeRate(con, name);
	   	        	Alert alert = new Alert(AlertType.INFORMATION);
				    alert.setTitle("Successfull");
				    alert.setHeaderText(null);
				    alert.setContentText("You have voted successfully.");
				    alert.showAndWait();
	   	        }
	   	        // Process the movieId value as needed
	   	    } else {
	   	        // Handle the case when no rows are returned
	   	    }
	   	    resultSet.close();
	   	    statement.close();
	   	} catch (SQLException e) {
	   	    // Handle any SQL exceptions that occur
	   	    e.printStackTrace();
	   	}
	}
	
	public void handleBookDisike(ActionEvent event) {
		String name = bookpageSearchBarTextField.getText();
		Book book = new Book();
		
		DatabaseConnection dbConnection = new DatabaseConnection();
	   	dbConnection.setConnection();
	   	Connection con = dbConnection.getConnection();
	   	
	   	String query = "SELECT book_id FROM Books WHERE b_name = ?";
	   	try {
	   	    PreparedStatement statement = con.prepareStatement(query);
	   	    statement.setString(1, name);

	   	    ResultSet resultSet = statement.executeQuery();
	   	    if (resultSet.next()) {
	   	        int bookId = resultSet.getInt("book_id");
	   	        if(book.isExistingVote(con, emailLabel.getText(), bookId)) {
	   	        	Alert alert = new Alert(AlertType.ERROR);
	    		    alert.setTitle("Vote Failed");
	    		    alert.setHeaderText(null);
	    		    alert.setContentText("You have already voted.");
	    		    alert.showAndWait();
	   	        }else {
	   	        	book.addToVoted(con, emailLabel.getText(), bookId);
	   	        	book.updateDislikeRate(con, name);
	   	        	Alert alert = new Alert(AlertType.INFORMATION);
				    alert.setTitle("Successfull");
				    alert.setHeaderText(null);
				    alert.setContentText("You have voted successfully.");
				    alert.showAndWait();
	   	        }
	   	        // Process the movieId value as needed
	   	    } else {
	   	        // Handle the case when no rows are returned
	   	    }
	   	    resultSet.close();
	   	    statement.close();
	   	} catch (SQLException e) {
	   	    // Handle any SQL exceptions that occur
	   	    e.printStackTrace();
	   	}
	}
	
	public void handleGameLike(ActionEvent event) {
		String name = gamepageSearchBarTextField.getText();
		Game game = new Game();
		
		DatabaseConnection dbConnection = new DatabaseConnection();
	   	dbConnection.setConnection();
	   	Connection con = dbConnection.getConnection();
	   	
	   	String query = "SELECT game_id FROM Games WHERE g_name = ?";
	   	try {
	   	    PreparedStatement statement = con.prepareStatement(query);
	   	    statement.setString(1, name);

	   	    ResultSet resultSet = statement.executeQuery();
	   	    if (resultSet.next()) {
	   	        int gameId = resultSet.getInt("game_id");
	   	        if(game.isExistingVote(con, emailLabel.getText(), gameId)) {
	   	        	Alert alert = new Alert(AlertType.ERROR);
	    		    alert.setTitle("Vote Failed");
	    		    alert.setHeaderText(null);
	    		    alert.setContentText("You have already voted.");
	    		    alert.showAndWait();
	   	        }else {
	   	        	game.addToVoted(con, emailLabel.getText(), gameId);
	   	        	game.updateLikeRate(con, name);
	   	        	Alert alert = new Alert(AlertType.INFORMATION);
				    alert.setTitle("Successfull");
				    alert.setHeaderText(null);
				    alert.setContentText("You have voted successfully.");
				    alert.showAndWait();
	   	        }
	   	        // Process the movieId value as needed
	   	    } else {
	   	        // Handle the case when no rows are returned
	   	    }
	   	    resultSet.close();
	   	    statement.close();
	   	} catch (SQLException e) {
	   	    // Handle any SQL exceptions that occur
	   	    e.printStackTrace();
	   	}
	}
	
	public void handleGameDisike(ActionEvent event) {
		String name = gamepageSearchBarTextField.getText();
		Game game = new Game();
		
		DatabaseConnection dbConnection = new DatabaseConnection();
	   	dbConnection.setConnection();
	   	Connection con = dbConnection.getConnection();
	   	
	   	String query = "SELECT game_id FROM Games WHERE g_name = ?";
	   	try {
	   	    PreparedStatement statement = con.prepareStatement(query);
	   	    statement.setString(1, name);

	   	    ResultSet resultSet = statement.executeQuery();
	   	    if (resultSet.next()) {
	   	        int gameId = resultSet.getInt("game_id");
	   	        if(game.isExistingVote(con, emailLabel.getText(), gameId)) {
	   	        	Alert alert = new Alert(AlertType.ERROR);
	    		    alert.setTitle("Vote Failed");
	    		    alert.setHeaderText(null);
	    		    alert.setContentText("You have already voted.");
	    		    alert.showAndWait();
	   	        }else {
	   	        	game.addToVoted(con, emailLabel.getText(), gameId);
	   	        	game.updateDislikeRate(con, name);
	   	        	Alert alert = new Alert(AlertType.INFORMATION);
				    alert.setTitle("Successfull");
				    alert.setHeaderText(null);
				    alert.setContentText("You have voted successfully.");
				    alert.showAndWait();
	   	        }
	   	        // Process the movieId value as needed
	   	    } else {
	   	        // Handle the case when no rows are returned
	   	    }
	   	    resultSet.close();
	   	    statement.close();
	   	} catch (SQLException e) {
	   	    // Handle any SQL exceptions that occur
	   	    e.printStackTrace();
	   	}
	}
	
	public void handleMovieFilter(ActionEvent event) {
		int year = 0; // Default value in case the text field is empty or not a valid integer
		if (!moviepageYearTextField.getText().isEmpty()) {
		    try {
		        year = Integer.parseInt(moviepageYearTextField.getText());
		    } catch (NumberFormatException e) {
		        // Handle the case when the text is not a valid integer
		        // Display an error message or take appropriate action
		    }
		}
		String genre = moviepageGenreTextField.getText();
		String director = moviepageDirectorTextField.getText();
		String scenarist = moviepageScenaristTextField.getText();
		String producer = moviepageProducerTextField.getText();
		
		Movie movie = new Movie();
		DatabaseConnection dbConnection = new DatabaseConnection();
	   	dbConnection.setConnection();
	   	Connection con = dbConnection.getConnection();
	   	
	   	moviepageMoviesListView.getItems().clear();
	   	
	    List<String> movieList = new ArrayList<>();
	   	movieList = movie.getTopRatedMovies(con, year, genre, director, scenarist, producer);
	   	
	   	moviepageMoviesListView.getItems().addAll(movieList.toArray());
	}
	
	public void handleGameFilter(ActionEvent event) {
		int year = 0; // Default value in case the text field is empty or not a valid integer
		if (!gamepageTypeTextField.getText().isEmpty()) {
		    try {
		        year = Integer.parseInt(gamepageTypeTextField.getText());
		    } catch (NumberFormatException e) {
		        // Handle the case when the text is not a valid integer
		        // Display an error message or take appropriate action
		    }
		}
		String genre = gamepageGenreTextField.getText();
		String developer = gamepageDeveloperTextField.getText();
		String type = gamepageTypeTextField.getText();
		int duration = 0; // Default value in case the text field is empty or not a valid integer
		if (!gamepageDurationTextField.getText().isEmpty()) {
		    try {
		        duration = Integer.parseInt(gamepageDurationTextField.getText());
		    } catch (NumberFormatException e) {
		        // Handle the case when the text is not a valid integer
		        // Display an error message or take appropriate action
		    }
		}
		
		Game game = new Game();
		DatabaseConnection dbConnection = new DatabaseConnection();
	   	dbConnection.setConnection();
	   	Connection con = dbConnection.getConnection();
	   	
	   	gamepageGamesListView.getItems().clear();
	   	
	    List<String> gameList = new ArrayList<>();
	   	gameList = game.getTopRatedGames(con, year, genre, developer, type, duration);
	   	
	   	gamepageGamesListView.getItems().addAll(gameList.toArray());
	}
	
	public void handleBookFilter(ActionEvent event) {
		int year = 0; // Default value in case the text field is empty or not a valid integer
		if (!bookpageYearTextField.getText().isEmpty()) {
		    try {
		        year = Integer.parseInt(bookpageYearTextField.getText());
		    } catch (NumberFormatException e) {
		        // Handle the case when the text is not a valid integer
		        // Display an error message or take appropriate action
		    }
		}
		String genre = bookpageGenreTextField.getText();
		String autor = bookpageAutorTextField.getText();
		int pageNumber = 0; // Default value in case the text field is empty or not a valid integer
		if (!bookpagePageNumberTextField.getText().isEmpty()) {
		    try {
		        pageNumber = Integer.parseInt(bookpagePageNumberTextField.getText());
		    } catch (NumberFormatException e) {
		        // Handle the case when the text is not a valid integer
		        // Display an error message or take appropriate action
		    }
		}
		
		Book book = new Book();
		DatabaseConnection dbConnection = new DatabaseConnection();
	   	dbConnection.setConnection();
	   	Connection con = dbConnection.getConnection();
	   	
	   	bookpageBooksListView.getItems().clear();
	   	
	    List<String> bookList = new ArrayList<>();
	   	bookList = book.getTopRatedBooks(con, year, genre, autor, pageNumber);
	   	
	   	bookpageBooksListView.getItems().addAll(bookList.toArray());
	}
	
}
