package application.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import application.DatabaseConnection;

public class Movie {
	
	private String movie_id;
	private String m_name;
	private int release_year;
	private String director;
	private String scenarist;
	private String producer;
	private float vote_rate;
	private String genre;
	private String actor;
	private int liked;
	private int total_vote;
	
	public Movie() {
		
	}
	
	public Movie(String movie_id, String m_name, int release_year, String director, String scenarist, String producer, String genre, String actor) {
		this.movie_id = movie_id;
		this.m_name = m_name;
		this.release_year = release_year;
		this.director = director;
		this.scenarist = scenarist;
		this.producer = producer;
		this.genre = genre;
		this.actor = actor;
	}
	
	public String getMovieId() {
		return movie_id;
	}
	
	public String getName() {
		return m_name;
	}
	
	public int getYear() {
		return release_year;
	}
	
	public String getDirector() {
		return director;
	}
	
	public String getScenarist() {
		return scenarist;
	}
	
	public String getProducer() {
		return producer;
	}
	
	public float getVoteRate() {
		return vote_rate;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public String getActor() {
		return actor;
	}
	
	public int getLiked() {
		return liked;
	}
	
	public int getTotalVote() {
		return total_vote;
	}
	
	public void setName(String a) {
		m_name = a;
	}
	
	public void setYear(int a) {
		release_year = a;
	}
	
	
	public void setDirector(String a) {
		director = a;
	}
	
	public void setScenarist(String a) {
		scenarist = a;
	}
	
	public void setProducer(String a) {
		producer = a;
	}
	
	public void setVoteRate(float a) {
		vote_rate = a;
	}
	
	public void setGenre(String a) {
		genre = a;
	}
	
	public void setActor(String a) {
		actor = a;
	}
	
	public void setLiked(int a) {
		liked= a;
	}
	
	public void setTotalVote(int a) {
		total_vote= a;
	}
	
	@Override
	public boolean equals(Object anObject){
		if (anObject instanceof Movie){
			Movie y = (Movie) anObject;
			if (getMovieId().equals(y.getMovieId())){
				return true;
			}
			return false;
	    }
	    return false;
	}
	
	public void insertIntoDatabase(Connection connection) {
	    try {
	        String query = "INSERT INTO Movies (m_name, release_year, director, scenarist, producer, genre, actor, liked, total_vote) VALUES (?, ?, ?, ?, ?, ?, ?, 0, 0)";
	        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

	        statement.setString(1, this.getName());
	        statement.setInt(2, this.getYear());
	        statement.setString(3, this.getDirector());
	        statement.setString(4, this.getScenarist());
	        statement.setString(5, this.getProducer());
	        statement.setString(6, this.getGenre());
	        statement.setString(7, this.getActor());

	        statement.executeUpdate();

	        // Get the auto-generated book ID from the statement (if applicable)
	        ResultSet generatedKeys = statement.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            int movie_id = generatedKeys.getInt(1);
	            // Do something with the generated book ID if needed
	        }

	        statement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void updateLikeRate(Connection connection, String name) {
	    try {
	        String updateQuery = "UPDATE Movies SET liked = liked + 1, total_vote = total_vote + 1 WHERE m_name = ?";
	        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
	        updateStatement.setString(1, name);

	        int rowsUpdated = updateStatement.executeUpdate();

	        if (rowsUpdated > 0) {
	            String selectQuery = "SELECT liked, total_vote FROM Movies WHERE m_name = ?";
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

	                String updateVoteRateQuery = "UPDATE Movies SET vote_rate = ? WHERE m_name = ?";
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
	            System.out.println("Movie not found or update failed.");
	        }

	        updateStatement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void updateDislikeRate(Connection connection, String name) {
	    try {
	        String updateQuery = "UPDATE Movie SET total_vote = total_vote + 1 WHERE m_name = ?";
	        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
	        updateStatement.setString(1, name);

	        int rowsUpdated = updateStatement.executeUpdate();

	        if (rowsUpdated > 0) {
	            String selectQuery = "SELECT liked, total_vote FROM Movies WHERE m_name = ?";
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

	                String updateVoteRateQuery = "UPDATE Movies SET vote_rate = ? WHERE m_name = ?";
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
	            System.out.println("Movie not found or update failed.");
	        }

	        updateStatement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	    public List<String> getTopRatedMovies(Connection connection, int year, String genre, String director, String scenarist, String producer) {
	        List<String> movieList = new ArrayList<>();

	        try {
	            StringBuilder queryBuilder = new StringBuilder();
	            queryBuilder.append("SELECT m_name FROM Movies WHERE 1=1");

	            if (year > 0) {
	                queryBuilder.append(" AND release_year = ?");
	            }
	            if (genre != null && !genre.isEmpty()) {
	                queryBuilder.append(" AND genre = ?");
	            }
	            if (director != null && !director.isEmpty()) {
	                queryBuilder.append(" AND director = ?");
	            }
	            if (scenarist != null && !scenarist.isEmpty()) {
	                queryBuilder.append(" AND scenarist = ?");
	            }
	            if (producer != null && !producer.isEmpty()) {
	                queryBuilder.append(" AND producer = ?");
	            }


	            queryBuilder.append(" ORDER BY vote_rate DESC");

	            PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());

	            int parameterIndex = 1;
	            if (year > 0) {
	                statement.setInt(parameterIndex++, year);
	            }
	            if (genre != null && !genre.isEmpty()) {
	                statement.setString(parameterIndex++, genre);
	            }
	            if (director != null && !director.isEmpty()) {
	                statement.setString(parameterIndex++, director);
	            }
	            if (scenarist != null && !scenarist.isEmpty()) {
	                statement.setString(parameterIndex++, scenarist);
	            }
	            if (producer != null && !producer.isEmpty()) {
	                statement.setString(parameterIndex++, producer);
	            }

	            ResultSet resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                String movieName = resultSet.getString("m_name");
	                movieList.add(movieName);
	            }

	            resultSet.close();
	            statement.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return movieList;
	    }
	
	public List<String> TopRatedMovies() {
	    List<String> movieList = new ArrayList<>();
	    
	    DatabaseConnection dbConnection = new DatabaseConnection();
	   	dbConnection.setConnection();
	   	Connection con = dbConnection.getConnection();

	    try {
	        String query = "SELECT m_name FROM Movies ORDER BY vote_rate DESC";
	        PreparedStatement statement = con.prepareStatement(query);
	        ResultSet resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	            String movieName = resultSet.getString("m_name");
	            movieList.add(movieName);
	        }

	        resultSet.close();
	        statement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return movieList;
	}
	
	public String getMovieInfo(Connection connection, String movieName) {
        try {
            String query = "SELECT * FROM Movies WHERE m_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, movieName);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int movieId = resultSet.getInt("movie_id");
                int releaseYear = resultSet.getInt("release_year");
                String director = resultSet.getString("director");
                String scenarist = resultSet.getString("scenarist");
                String producer = resultSet.getString("producer");
                float voteRate = resultSet.getFloat("vote_rate");
                String genre = resultSet.getString("genre");
                String actor = resultSet.getString("actor");

                return "Movie ID: " + movieId + "\n" +
                        "Release Year: " + releaseYear + "\n" +
                        "Director: " + director + "\n" +
                        "Scenarist: " + scenarist + "\n" +
                        "Producer: " + producer + "\n" +
                        "Vote Rate: " + voteRate + "\n" +
                        "Genre: " + genre + "\n" +
                        "Actors: " + actor;
            } else {
                return "Movie not found.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error occurred while retrieving game information.";
        }
    }
	
	public boolean isExistingMovie(Connection connection, String m_name,int release_year,String director,String scenarist,String producer,String genre,String actor ) {
	    try {
	        String query = "SELECT COUNT(*) FROM Movies WHERE m_name = ? AND release_year = ? AND director = ? AND scenarist = ? AND producer = ? AND genre = ? AND actor = ?";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, m_name);
	        statement.setInt(2, release_year);
	        statement.setString(3, director);
	        statement.setString(4, scenarist);
	        statement.setString(5, producer);
	        statement.setString(6, genre);
	        statement.setString(7, actor);

	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            int count = resultSet.getInt(1);
	            return count > 0; // Returns true if the count is greater than 0
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false; // Return false in case of an exception or no matching movie found
	}
	
	public void addToVoted(Connection connection,String email, int id) {
		try {
	        String query = "INSERT INTO VoteMovie (movie_id, email) VALUES (?, ?)";
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
	        String query = "SELECT COUNT(*) FROM VoteMovie WHERE email = ? AND movie_id = ?";
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

}
