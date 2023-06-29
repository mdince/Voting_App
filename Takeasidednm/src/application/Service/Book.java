package application.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.DatabaseConnection;

public class Book {
	
	private String book_id;
	private String b_name;
	private int release_year;
	private String autor;
	private int page_number;
	private String publisher;
	private float vote_rate;
	private String genre;
	private int liked;
	private int total_vote;
	
	public Book() {
		
	}
	
	public Book(String book_id, String b_name, int release_year ,String autor ,int page_number, String publisher, String genre) {
		this.book_id = book_id;
		this.b_name = b_name;
		this.release_year = release_year;
		this.autor = autor;
		this.page_number = page_number;
		this.publisher = publisher;
		this.genre = genre;
	}
	
	public String getBookId() {
		return book_id;
	}
	
	public String getName() {
		return b_name;
	}
	
	public int getYear() {
		return release_year;
	}
	
	public String getAutor() {
		return autor;
	}
	
	public int getPageNumber() {
		return page_number;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public float getVoteRate() {
		return vote_rate;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public int getLiked() {
		return liked;
	}
	
	public int getTotalVote() {
		return total_vote;
	}

	
	public void setName(String a) {
		b_name = a;
	}
	
	public void setYear(int a) {
		release_year = a;
	}
	
	public void setAutor(String a) {
		autor = a;
	}
	
	public void setPageNumber(int a) {
		page_number = a;
	}
	
	public void setPubliher(String a) {
		publisher = a;
	}
	
	public void setVoteRate(float a) {
		vote_rate = a;
	}
	
	public void setGenre(String a) {
		genre = a;
	}
	
	public void setLiked(int a) {
		liked= a;
	}
	
	public void setTotalVote(int a) {
		total_vote= a;
	}
	
	@Override
	public boolean equals(Object anObject){
		if (anObject instanceof Book){
			Book y = (Book) anObject;
			if (getBookId().equals(y.getBookId())){
				return true;
			}
			return false;
	    }
	    return false;
	}
	
	
	public void insertIntoDatabase(Connection connection) {
	    try {
	        String query = "INSERT INTO Books (b_name, release_year, autor, page_number, publisher, genre  liked, total_vote) VALUES (?, ?, ?, ?, ?, ?,0 ,0)";
	        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

	        statement.setString(1, this.getName());
	        statement.setInt(2, this.getYear());
	        statement.setString(3, this.getAutor());
	        statement.setInt(4, this.getPageNumber());
	        statement.setString(5, this.getPublisher());
	        statement.setString(6, this.getGenre());

	        statement.executeUpdate();

	        // Get the auto-generated book ID from the statement (if applicable)
	        ResultSet generatedKeys = statement.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            int book_id = generatedKeys.getInt(1);
	            // Do something with the generated book ID if needed
	        }

	        statement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void updateLikeRate(Connection connection, String name) {
	    try {
	        String updateQuery = "UPDATE Books SET liked = liked + 1, total_vote = total_vote + 1 WHERE b_name = ?";
	        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
	        updateStatement.setString(1, name);

	        int rowsUpdated = updateStatement.executeUpdate();

	        if (rowsUpdated > 0) {
	            String selectQuery = "SELECT liked, total_vote FROM Books WHERE b_name = ?";
	            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
	            selectStatement.setString(1, name);
	            ResultSet resultSet = selectStatement.executeQuery();

	            if (resultSet.next()) {
	                int likedCount = resultSet.getInt("liked");
	                int totalVotes = resultSet.getInt("total_vote");

	                double voteRate = 0.0;
	                if (totalVotes != 0) {
	                    voteRate = (likedCount * 100.0) / totalVotes;
	                }

	                String updateVoteRateQuery = "UPDATE Books SET vote_rate = ? WHERE b_name = ?";
	                PreparedStatement updateVoteRateStatement = connection.prepareStatement(updateVoteRateQuery);
	                updateVoteRateStatement.setDouble(1, voteRate);
	                updateVoteRateStatement.setString(2, name);

	                int voteRateRowsUpdated = updateVoteRateStatement.executeUpdate();

	                if (voteRateRowsUpdated > 0) {
	                    System.out.println("Vote rate updated successfully.");
	                } else {
	                    System.out.println("Failed to update vote rate.");
	                }

	                updateVoteRateStatement.close();
	            }

	            resultSet.close();
	            selectStatement.close();
	        } else {
	            System.out.println("Book not found or update failed.");
	        }

	        updateStatement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void updateDislikeRate(Connection connection, String name) {
	    try {
	        String updateQuery = "UPDATE Books SET total_vote = total_vote + 1 WHERE b_name = ?";
	        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
	        updateStatement.setString(1, name);

	        int rowsUpdated = updateStatement.executeUpdate();

	        if (rowsUpdated > 0) {
	            String selectQuery = "SELECT liked, total_vote FROM Books WHERE b_name = ?";
	            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
	            selectStatement.setString(1, name);
	            ResultSet resultSet = selectStatement.executeQuery();

	            if (resultSet.next()) {
	                int likedCount = resultSet.getInt("liked");
	                int totalVotes = resultSet.getInt("total_vote");

	                double voteRate = 0.0;
	                if (totalVotes != 0) {
	                    voteRate = (likedCount * 100.0) / totalVotes;
	                }

	                String updateVoteRateQuery = "UPDATE Books SET vote_rate = ? WHERE b_name = ?";
	                PreparedStatement updateVoteRateStatement = connection.prepareStatement(updateVoteRateQuery);
	                updateVoteRateStatement.setDouble(1, voteRate);
	                updateVoteRateStatement.setString(2, name);

	                int voteRateRowsUpdated = updateVoteRateStatement.executeUpdate();

	                if (voteRateRowsUpdated > 0) {
	                    System.out.println("Vote rate updated successfully.");
	                } else {
	                    System.out.println("Failed to update vote rate.");
	                }

	                updateVoteRateStatement.close();
	            }

	            resultSet.close();
	            selectStatement.close();
	        } else {
	            System.out.println("Book not found or update failed.");
	        }

	        updateStatement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	    public List<String> getTopRatedBooks(Connection connection, int year, String genre, String autor, int page_number) {
	        List<String> bookList = new ArrayList<>();

	        try {
	            StringBuilder queryBuilder = new StringBuilder();
	            queryBuilder.append("SELECT b_name FROM Books WHERE 1=1");

	            if (year > 0) {
	                queryBuilder.append(" AND release_year = ?");
	            }
	            if (genre != null && !genre.isEmpty()) {
	                queryBuilder.append(" AND genre = ?");
	            }
	            if (autor != null && !autor.isEmpty()) {
	                queryBuilder.append(" AND autor = ?");
	            }
	            if (page_number > 0) {
	                queryBuilder.append(" AND page_number = ?");
	            }

	            queryBuilder.append(" ORDER BY vote_rate DESC ");

	            PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());

	            int parameterIndex = 1;
	            if (year > 0) {
	                statement.setInt(parameterIndex++, year);
	            }
	            if (genre != null && !genre.isEmpty()) {
	                statement.setString(parameterIndex++, genre);
	            }
	            if (autor != null && !autor.isEmpty()) {
	                statement.setString(parameterIndex++, autor);
	            }
	            if (page_number > 0) {
	                statement.setInt(parameterIndex, page_number);
	            }

	            ResultSet resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                String bookName = resultSet.getString("b_name");
	                bookList.add(bookName);
	            }

	            resultSet.close();
	            statement.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return bookList;
	    }
	
	public String getBookInfo(Connection connection, String bookName) {
        try {
            String query = "SELECT * FROM Books WHERE b_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, bookName);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                int releaseYear = resultSet.getInt("release_year");
                String autor = resultSet.getString("autor");
                int page_number = resultSet.getInt("page_number");
                float voteRate = resultSet.getFloat("vote_rate");
                String genre = resultSet.getString("genre");
                String publisher = resultSet.getString("publisher");

                return "Book ID: " + bookId + "\n" +
                        "Release Year: " + releaseYear + "\n" +
                        "Autor: " + autor + "\n" +
                        "Page Number: " + page_number + "\n" +
                        "Vote Rate: " + voteRate + "\n" +
                        "Genre: " + genre + "\n" +
                        "Publisher: " + publisher;
            } else {
                return "Book not found.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error occurred while retrieving game information.";
        }
    }
	
	public boolean isExistingBook(Connection connection, String b_name,int release_year,String autor,int page_number,String publisher,String genre) {
	    try {
	        String query = "SELECT COUNT(*) FROM Books WHERE b_name = ? AND release_year = ? AND autor = ? AND page_number = ? AND publisher = ? AND genre = ?";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, b_name);
	        statement.setInt(2, release_year);
	        statement.setString(3, autor);
	        statement.setInt(4, page_number);
	        statement.setString(5, publisher);
	        statement.setString(6, genre);

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
	
	public void addToVoted(Connection connection,String email, int id) {
		try {
	        String query = "INSERT INTO VoteBook (book_id, email) VALUES (?, ?)";
	        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

	        statement.setInt(1, id);
	        statement.setString(2, email);
	        statement.executeUpdate();
	        statement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public boolean isExistingVote(Connection connection, String email ,int id) {
	    try {
	        String query = "SELECT COUNT(*) FROM VoteBook WHERE email = ? AND book_id = ?";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, email);
	        statement.setInt(2, id);

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
	
	public List<String> TopRatedBooks() {
	    List<String> bookList = new ArrayList<>();
	    
	    DatabaseConnection dbConnection = new DatabaseConnection();
	   	dbConnection.setConnection();
	   	Connection con = dbConnection.getConnection();

	    try {
	        String query = "SELECT b_name FROM Books ORDER BY vote_rate DESC";
	        PreparedStatement statement = con.prepareStatement(query);
	        ResultSet resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	            String bookName = resultSet.getString("b_name");
	            bookList.add(bookName);
	        }

	        resultSet.close();
	        statement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bookList;
	}


}
