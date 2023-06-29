package application.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.DatabaseConnection;

public class Game {
	
	private String game_id;
	private String g_name;
	private int release_year;
	private int duration;
	private String developer;
	private float vote_rate;
	private String genre;
	private String ex_type;
	private int liked;
	private int total_vote;
	
	public Game() {
		
	}
	
	public Game(String game_id, String g_name, int release_year, int duration, String developer, String genre, String ex_type) {
		this.game_id = game_id;
		this.g_name = g_name;
		this.release_year = release_year;
		this.duration = duration;
		this.developer = developer;
		this.genre = genre;
		this.ex_type = ex_type;
	}
	
	public String getGameId() {
		return game_id;
	}
	
	public String getName() {
		return g_name;
	}
	
	public int getYear() {
		return release_year;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public String getDeveloper() {
		return developer;
	}
	
	public float getVoteRate() {
		return vote_rate;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public String getType() {
		return ex_type;
	}
	
	public int getLiked() {
		return liked;
	}
	
	public int getTotalVote() {
		return total_vote;
	}
	
	public void setName(String a) {
		g_name = a;
	}
	
	public void setYear(int a) {
		release_year = a;
	}
	
	
	public void setDuration(int a) {
		duration = a;
	}
	
	public void setDeveloper(String a) {
		developer = a;
	}
	
	public void setVoteRate(float a) {
		vote_rate = a;
	}
	
	public void setGenre(String a) {
		genre = a;
	}
	
	public void setType(String a) {
		ex_type = a;
	}
	
	public void setLiked(int a) {
		liked= a;
	}
	
	public void setTotalVote(int a) {
		total_vote= a;
	}
	
	@Override
	public boolean equals(Object anObject){
		if (anObject instanceof Game){
			Game y = (Game) anObject;
			if (getGameId().equals(y.getGameId())){
				return true;
			}
			return false;
	    }
	    return false;
	}
	
	
	public void insertIntoDatabase(Connection connection) {
	    try {
	        String query = "INSERT INTO Games (g_name, release_year, duration, developer, genre, ex_type, liked, total_vote) VALUES (?, ?, ?, ?, ?, ?, 0 ,0)";
	        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

	        statement.setString(1, this.getName());
	        statement.setInt(2, this.getYear());
	        statement.setInt(3, this.getDuration());
	        statement.setString(4, this.getDeveloper());
	        statement.setString(5, this.getGenre());
	        statement.setString(6, this.getType());

	        statement.executeUpdate();

	        // Get the auto-generated book ID from the statement (if applicable)
	        ResultSet generatedKeys = statement.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            int game_id = generatedKeys.getInt(1);
	            // Do something with the generated book ID if needed
	        }

	        statement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void updateLikeRate(Connection connection, String name) {
	    try {
	        String updateQuery = "UPDATE Games SET liked = liked + 1, total_vote = total_vote + 1 WHERE g_name = ?";
	        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
	        updateStatement.setString(1, name);

	        int rowsUpdated = updateStatement.executeUpdate();

	        if (rowsUpdated > 0) {
	            String selectQuery = "SELECT liked, total_vote FROM Games WHERE g_name = ?";
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

	                String updateVoteRateQuery = "UPDATE Games SET vote_rate = ? WHERE g_name = ?";
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
	            System.out.println("Game not found or update failed.");
	        }

	        updateStatement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void updateDislikeRate(Connection connection, String name) {
	    try {
	        String updateQuery = "UPDATE Games SET total_vote = total_vote + 1 WHERE g_name = ?";
	        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
	        updateStatement.setString(1, name);

	        int rowsUpdated = updateStatement.executeUpdate();

	        if (rowsUpdated > 0) {
	            String selectQuery = "SELECT liked, total_vote FROM Games WHERE g_name = ?";
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

	                String updateVoteRateQuery = "UPDATE Games SET vote_rate = ? WHERE g_name = ?";
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
	            System.out.println("Game not found or update failed.");
	        }

	        updateStatement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	    public List<String> getTopRatedGames(Connection connection, int year, String genre, String developer, String exType, int duration) {
	        List<String> gameList = new ArrayList<>();

	        try {
	            StringBuilder queryBuilder = new StringBuilder();
	            queryBuilder.append("SELECT g_name FROM Games WHERE 1=1");

	            if (year > 0) {
	                queryBuilder.append(" AND release_year = ?");
	            }
	            if (genre != null && !genre.isEmpty()) {
	                queryBuilder.append(" AND genre = ?");
	            }
	            if (developer != null && !developer.isEmpty()) {
	                queryBuilder.append(" AND developer = ?");
	            }
	            if (exType != null && !exType.isEmpty()) {
	                queryBuilder.append(" AND ex_type = ?");
	            }
	            if (duration > 0) {
	                queryBuilder.append(" AND duration = ?");
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
	            if (developer != null && !developer.isEmpty()) {
	                statement.setString(parameterIndex++, developer);
	            }
	            if (exType != null && !exType.isEmpty()) {
	                statement.setString(parameterIndex++, exType);
	            }
	            if (duration > 0) {
	                statement.setInt(parameterIndex, duration);
	            }

	            ResultSet resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                String gameName = resultSet.getString("g_name");
	                gameList.add(gameName);
	            }

	            resultSet.close();
	            statement.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return gameList;
	    }
	
	public String getGameInfo(Connection connection, String gameName) {
        try {
            String query = "SELECT * FROM Games WHERE g_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, gameName);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int gameId = resultSet.getInt("game_id");
                int releaseYear = resultSet.getInt("release_year");
                String developer = resultSet.getString("developer");
                int duration = resultSet.getInt("duration");
                float voteRate = resultSet.getFloat("vote_rate");
                String genre = resultSet.getString("genre");
                String exType = resultSet.getString("ex_type");

                return "Game ID: " + gameId + "\n" +
                        "Release Year: " + releaseYear + "\n" +
                        "Developer: " + developer + "\n" +
                        "Duration: " + duration + "\n" +
                        "Vote Rate: " + voteRate + "\n" +
                        "Genre: " + genre + "\n" +
                        "Ex Type: " + exType;
            } else {
                return "Game not found.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error occurred while retrieving game information.";
        }
    }
	
	public boolean isExistingGame(Connection connection,String g_name,int release_year,int duration,String developer,String genre,String ex_type) {
	    try {
	        String query = "SELECT COUNT(*) FROM Games WHERE g_name = ? AND release_year = ? AND duration = ? AND developer = ? AND genre = ? AND ex_type = ?";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, g_name);
	        statement.setInt(2, release_year);
	        statement.setInt(3, duration);
	        statement.setString(4, developer);
	        statement.setString(5, genre);
	        statement.setString(6, ex_type);

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
	        String query = "INSERT INTO VoteGame (game_id, email) VALUES (?, ?)";
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
	        String query = "SELECT COUNT(*) FROM VoteGame WHERE email = ? AND game_id = ?";
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
	
	public List<String> TopRatedGames() {
	    List<String> gameList = new ArrayList<>();
	    
	    DatabaseConnection dbConnection = new DatabaseConnection();
	   	dbConnection.setConnection();
	   	Connection con = dbConnection.getConnection();

	    try {
	        String query = "SELECT g_name FROM Games ORDER BY vote_rate DESC";
	        PreparedStatement statement = con.prepareStatement(query);
	        ResultSet resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	            String gameName = resultSet.getString("g_name");
	            gameList.add(gameName);
	        }

	        resultSet.close();
	        statement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return gameList;
	}

}
