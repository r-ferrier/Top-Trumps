
package commandline;

import.java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;


public class Database {

	private Connection c;
	private Statement stmt;
	private int roundWinner;
	private int gameNumber;
	private Player player;
	private int totalNumberGames;
	private int numComputerWon;
	private int numHumanWon;
	private int averageDraws;
	private int largestNumberRound;
	
	// TESTING
	
	public Database (Player player) {
		this.player = player;
	}

	public void connectToDatabase() {

		/*
		 * This will connect our program to the database so our data can be updated and
		 * recieved.
		 */

		c = null;
		stmt = null;
		try {
			Class.forName("org.postresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Could not find JDBC Driver");
			e.printStackTrace();
			return;
		}
		
		try {
			c = DriverManager.getConnection("jdbc:postresql://yacata.dcs.gla.ac.uk:5432/", "m_18_2028263c", "2028263c");
			System.out.println("Opened database sucessfully");
		} catch (SQLException e) {
			System.out.println("Connection Failed.");
			e.printStackTrace();
			return;
		}
		
		if (c != null) {
			createNewTable();
		} else {
			System.out.println("Failed to establish connection.");
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
			String sqlStringGameStats = "CREATE TABLE IF NOT EXISTS testing_game_stats ( \n" 
					//testing_game_stats is the testing database, once we know it all works it will be updated to game_stats
					+ " game_number integer PRIMARY KEY, \n" 
					+ " game_winner integer NOT NULL, \n"
					+ " number_of_rounds integer NOT NULL, \n" 
					+ " number_of_draws integer, \n" 
					+ " computer_player_1_rounds_won integer, \n"
					+ " computer_player_2_rounds_won integer, \n"
					+ " computer_player_3_rounds_won integer, \n"
					+ " computer_player_4_rounds_won integer, \n"
					+ " human_player_rounds_won interger NOT NULL, \n"
					+ ");";
			statement.executeUpdate(sqlStringGameStats);
		} catch (SQLException e) {
			System.out.println("Connection Failed");
			e.printStackTrace();
			return;

		}
	}

	public void uploadGameStats(int gameDraws, int gameWinner, int gameRounds) {

		/*
		 * This will take the results from the game just played and upload them to the
		 * database. roundsPerPlayer as an array with the index relating to the player -
		 * linked to the number of rounds they won
		 */
		
		connectToDatabase();
		setGameNumber();

		String SQLInsert = "INSERT INTO testing_game_stats(game_number, game_winner, number_of_rounds, number_of_draws) VALUES ("
				+ gameNumber + ", " + gameWinner + ", " + gameRounds + ", " + gameDraws + ") ";

		// invoke executeUpdate to insert
		int status = stmt.executeUpdate(SQLInsert);

		// check the insertion
		if (status == 1) {
			System.out.println("Persistant Game Statistics are inserted");
		} else {
			System.out.println("No insertion completed");
		}
		stmt.close();
		c.close();
	}
	
	private void uploadPlayerStats() {
		
		/*
		 * This will upload the number of rounds each player won to the game stats for that game. 
		 */
		connectToDatabase();
		try {
			//testing_game_stats to game_stats
			String SQLInsert = "INSERT INTO testing_game_stats ("
				+ " computer_player_1_rounds_won, "
				+ " computer_player_2_rounds_won, "
				+ " computer_player_3_rounds_won, "
				+ " computer_player_4_rounds_won, "
				+ " human_player_rounds_won) "
				+ "VALUES (" 
				+ player.get(0).roundsWon() 
				+ player.get(1).roundsWon()
				+ player.get(2).roundsWon()
				+ player.get(3).roundsWon()
				+ player.get(4).roundsWon() //This is the human player. 
				+ ") ";
		
			// invoke executeUpdate to insert
			int status = stmt.executeUpdate(SQLInsert);

			// check the insertion
			if (status == 1) {
				System.out.println("Persistant Game Statistics are inserted");
			} else {
				System.out.println("No insertion completed");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		stmt.close();
		c.close();
		
	}

	private void pullGameStats() {

		/*
		 * This will print the information about the previous games, while the game is
		 * not in progress.
		 */
		
		connectToDatabase();
		//testing_game_stats to game_stats
		try {
			stmt = c.createStatement();
			ResultSet gameCount = stmt.executeQuery("SELECT game_number COUNT(*) FROM testing_game_stats");
			while (gameCount.next()) {
				totalNumberGames = gameCount.getInt();
			}
			ResultSet compWon = stmt.executeQuery("SELECT game_winner COUNT(*) FROM testing_game_stats WHERE player <> 4"); // 4 is the human player
			while (compWon.next()) {
				numComputerWon = compWon.getInt();
			}
			ResultSet humanWon = stmt.executeQuery("SELECT game_winner COUNT(*) FROM testing_game_stats WHERE player == 4"); // 4 is the human player
			while (humanWon.next()) {
				numHumanWon = humanWon.getInt();
			}
			ResultSet avgDraws = stmt
					.executeQuery("SELECT AVG (number_of_draws) FROM testing_game_stats");
			while (avgDraws.next()) {
				averageDraws = avgDraws.getInt();
			}
			ResultSet largestRound = stmt.executeQuery("SELECT MAX(number_of_rounds) FROM testing_game_stats");
			while (largestRound.next()) {
				largestNumberRound = largestRound.getInt();
			}
			gameCount.close();
			compWon.close();
			humanWon.close();
			avgDraws.close();
			largestRound.close();
			printGameStats();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stmt.close();
		c.close();
	}
	
	private void printGameStats() {
		
		/*
		 * Prints the persistant game data.
		 */
		
		System.out.println("Number of games played overall: " + totalNumberGames);
		System.out.println("How many times the computer has won: " + numComputerWon);
		System.out.println("How many times the human has won: " + numHumanWon);
		System.out.println("The average number of draws: " + averageDraws);
		System.out.println("The largest number of rounds played in a single game: " + largestNumberRound);
	}

	// GETTERS AND SETTERS

	public int setRoundWinner(int roundWinnerInput) {

		roundWinner = roundWinnerInput;

		return roundWinner;
	}

	public void getRoundWinner() {
		return roundWinner;
	}

	public int setGameNumber() {
		
		/*
		 * Connects to the database and gets the game number of the last game and adds 1 to give the most recent game number. 
		 */
		
		connectToDatabase();
		try {
			stmt = c.createStatement();
			// testing_game_stats to game_stats
			ResultSet lastGameNumber = stmt.executeQuery("SELECT game_number COUNT(*) FROM testing_game_stats");
			if (lastGameNumber.next()) {
				gameNumber = lastGameNumber.getInt();
				gameNumber++;
			}
			lastGameNumber.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stmt.close();
		c.close();
		return gameNumber;
	}
	
	public void hello() {
		System.out.println("hello");
	}

}
