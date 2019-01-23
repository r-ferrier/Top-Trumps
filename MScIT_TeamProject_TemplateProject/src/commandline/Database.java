
package commandline;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	private Connection c;
	private Statement stmt;
	private int roundWinner;

	public void connectToDataBase() {

		/*
		 * This will connect our program to the database so our data can be updated and
		 * recieved.
		 */

		c = null;
		stmt = null;
		try {
			Class.forName("org.postresql.Driver");
			c = DriverManager.getConnection("jdbc:postresql://localhost:1234/testdb", "Kelsey", "KelseyPassword");
			System.out.println("Opened database sucessfully");

		} catch (Exception e) {
			System.out.println("Connection Failed!");
			e.printStackTrace();
			return;

		}
		if (c != null) {
			createNewTable();

		} else {
			System.out.println("Failed to establish connection!");
		}

	}

	private void createNewTable() {

		/*
		 * This creates a new table for the persistant game data if one does not already
		 * exist I am currently working under the assumption that we don't want a new
		 * table each time we call the program to run - but this can easily be changed.
		 */
		try {
			System.out.println("Controlling your database...");
			Statement statement = c.createStatement();
			String sqlStringGameStats = "CREATE TABLE IF NOT EXISTS game_stats ( \n"
					+ " game_number integer PRIMARY KEY, \n" + " game_winner integer NOT NULL, \n"
					+ " number_of_rounds integer NOT NULL, \n" + " number_of_draws integer\n" + ");";
			statement.executeUpdate(sqlStringGameStats);
			String sqlStringPlayerStats = "CREATE TABLE IF NOT EXISTS player_stats ( \n"
					+ " player_number integer PRIMARY KEY, \n" + " rounds_won integer, \n" + ");";
			statement.executeUpdate(sqlStringPlayerStats);
		} catch (SQLException e) {
			e.printStackTrace();
			// try-catch exception//try
		}
	}

	public void uploadStats(int gameNumber, int gameDraws, int gameWinner, int gameRounds, int [] roundsPerPlayer){

	        /*
	        * This will take the results from the game just played and upload them to the database. 
	        * roundsPerPlayer as an array with the index relating to the player - linked to the number of rounds they won 
	        */
	    	String SQLInsert = "INSERT INTO game_stats(game_number, game_winner, number_of_rounds, number_of_draws) VALUES (" 
	    			+ gameNumber + ", " + gameWinner + ", " + gameRounds + ", " + gameDraws + ") ";
	    	
	    	// invoke executeUpdate to insert
	    	int status = statement.executeUpdate(SQLInsert);
	    	
	    	//check the insertion
	    	
	    	if (status == 1) {
	    		System.out.println("Persistant Game Statistics are inserted");
	    	} else {
	    		System.out.println("No insertion completed");

	    }

	private void pullGameStats() {

		/*
		 * This will print the information about the previous games, while the game is
		 * not in progress.
		 */

		int totalNumberGames;
		int numComputerWon;
		int numComputerWon;
		int numHumanWon;
		int averageDraws;
		int largestNumberRound;

		stmt = c.createStatement();
		ResultSet gameCount = stmt.executionQuery("SELECT COUNT(game_number) FROM persistant_game_stats");
		while (gameCount.next()) {
			totalNumberGames = gameCount.getInt();
		}
		ResultSet compWin = stmt.executionQuery("SELECT ADD*(rounds_won) FROM player_stats WHERE player != 1");
		while (compWin.next()) {
			numComputerWon = compWin.getInt();
		}
		ResultSet humanWon = stmt.executionQuery("SELECT ADD*(rounds_won) FROM player_stats WHERE player == 1");
		while (humanWon.next()) {
			numHumanWon = humanWon.getInt();
		}
		ResultSet avgDraws = stmt
				.executionQuery("SELECT ADD*(number_of_draws)/COUNT*(number_of_draws) FROM game_stats");
		while (avgDraws.next()) {
			averageDraws = avgDraws.getInt();
		}
		ResultSet largestRound = stmt.executionQuery("SELECT MAX(number_of_rounds) FROM game_stats");
		while (largestRound.next()) {
			largestNumberRound = largestRound.getInt(columnIndex);
		}
		System.out.println("Number of games played overall: " + totalNumberGames);
		System.out.println("How many times the computer has won: " + numComputerWon);
		System.out.println("How many times the human has won: " + numHumanWon);
		System.out.println("The average number of draws: " + averageDraws);
		System.out.println("The largest number of rounds played in a single game: " + largestNumberRound);

		rs.close();
		stmt.close();
		c.close();
	}
	
	// GETTERS AND SETTERS
	
	public int setRoundWinner(int roundWinnerInput) {
		
		roundWinner = roundWinnerInput;

		return roundWinner;
	}
	
	public void getRoundWinner() {
		return roundWinner;
	}
}
