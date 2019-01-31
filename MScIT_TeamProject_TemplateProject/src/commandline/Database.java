package commandline;

import.java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;


public class Database {

	private static Connection c;
	private static Statement stmt;
	private int roundWinner;
	private static int gameNumber;
	//private Player player;
	private static int totalNumberGames;
	private static int numComputerWon;
	private static int numHumanWon;
	private static double averageDraws;
	private static int largestNumberRound;
	
	public Database (Player player) {
		this.player = player;
	}

	public static void connectToDatabase() {

		/*
		 * This will connect our program to the database so our data can be updated and
		 * recieved.
		 */

		c = null;
		stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Could not find JDBC Driver");
			e.printStackTrace();
			return;
		}
		
		try {
			c = DriverManager.getConnection("jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/", "m_18_2028263c", "2028263c");
			System.out.println("Opened database sucessfully");
		} catch (SQLException e) {
			System.out.println("Connection Failed - database.");
			e.printStackTrace();
			return;
		}
		
		if (c != null) {
			createNewTable();
		} else {
			System.out.println("Failed to establish connection.");
		}

	}

	private static void createNewTable() {

		/*
		 * This creates a new table for the persistant game data if one does not already
		 * exist I am currently working under the assumption that we don't want a new
		 * table each time we call the program to run - but this can easily be changed.
		 */
		
		try {
			System.out.println("Controlling your database...");
			Statement statement = c.createStatement();
			String sqlStringGameStats = "CREATE TABLE IF NOT EXISTS testing_game_stats ( "
					//testing_game_stats is the testing database, once we know it all works it will be updated to game_stats
					+ " game_number integer PRIMARY KEY NOT NULL, " 
					+ " game_winner integer NOT NULL, "
					+ " number_of_rounds integer NOT NULL, " 
					+ " number_of_draws integer, " 
					+ " computer_player_1_rounds_won integer, "
					+ " computer_player_2_rounds_won integer, "
					+ " computer_player_3_rounds_won integer, "
					+ " computer_player_4_rounds_won integer, "
					+ " human_player_rounds_won integer"
					+ ");";
			statement.executeUpdate(sqlStringGameStats);
		} catch (SQLException e) {
			System.out.println("Connection Failed - create new table");
			e.printStackTrace();
			return;

		}
	}

	public static void uploadGameStats(int gameDraws, int gameWinner, int gameRounds) {

		/*
		 * This will take the results from the game just played and upload them to the
		 * database. roundsPerPlayer as an array with the index relating to the player -
		 * linked to the number of rounds they won
		 */
		
		connectToDatabase();
		setGameNumber();
		int status = 0;
		try {
			String SQLInsert = "INSERT INTO testing_game_stats(game_number, game_winner, number_of_rounds, number_of_draws) VALUES ("
				+ gameNumber + ", " + gameWinner + ", " + gameRounds + ", " + gameDraws + "); ";

			status = stmt.executeUpdate(SQLInsert);
			
			stmt.close();
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// check the insertion
		if (status == 1) {
			System.out.println("Persistant Game Statistics are inserted");
		} else {
			System.out.println("No insertion completed");
		}
		
	}
	
//	private void uploadPlayerStats() {
//		
//		/*
//		 * This will upload the number of rounds each player won to the game stats for that game. 
//		 */
//		connectToDatabase();
//		try {
//			//testing_game_stats to game_stats
//			String SQLInsert = "INSERT INTO testing_game_stats ("
//				+ " computer_player_1_rounds_won, "
//				+ " computer_player_2_rounds_won, "
//				+ " computer_player_3_rounds_won, "
//				+ " computer_player_4_rounds_won, "
//				+ " human_player_rounds_won) "
//				+ "VALUES (" 
//				+ player.get(0).roundsWon() 
//				+ player.get(1).roundsWon()
//				+ player.get(2).roundsWon()
//				+ player.get(3).roundsWon()
//				+ player.get(4).roundsWon() //This is the human player. 
//				+ ") ";
//		
//			// invoke executeUpdate to insert
//			int status = stmt.executeUpdate(SQLInsert);
//
//			// check the insertion
//			if (status == 1) {
//				System.out.println("Persistant Game Statistics are inserted");
//			} else {
//				System.out.println("No insertion completed");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		stmt.close();
//		c.close();
//		
//	}

	private static void pullGameStats() {

		/*
		 * This will print the information about the previous games, while the game is
		 * not in progress.
		 */
		
		connectToDatabase();
		//testing_game_stats to game_stats
		try {
			stmt = c.createStatement();
			
			ResultSet gameNumber = stmt.executeQuery("SELECT COUNT(*) game_number FROM testing_game_stats");
			while (gameNumber.next()) {
			totalNumberGames = gameNumber.getInt(1);
			}
			
			ResultSet compWon = stmt.executeQuery("SELECT COUNT(*) game_winner FROM testing_game_stats WHERE game_winner <> 4"); // 4 is the human player
			while (compWon.next()) {
				numComputerWon = compWon.getInt(1);
			}
			
			ResultSet humanWon = stmt.executeQuery("SELECT COUNT(*) game_winner FROM testing_game_stats WHERE game_winner = 4"); // 4 is the human player
			while (humanWon.next()) {
				numHumanWon = humanWon.getInt(1);
			}
			
			ResultSet avgDraws = stmt.executeQuery("SELECT AVG(number_of_draws) FROM testing_game_stats");
			if (avgDraws.next()) {
				averageDraws = avgDraws.getDouble(1);
				averageDraws = Math.Round(averageDraws); //Rounds the average number of draws, so the number is more readable. 
			}
				
			ResultSet largestRound = stmt.executeQuery("SELECT MAX(number_of_rounds) FROM testing_game_stats");
			while (largestRound.next()) {
				largestNumberRound = largestRound.getInt(1);
			}

			gameNumber.close();
			compWon.close();
			humanWon.close();
			avgDraws.close();
			largestRound.close();
			stmt.close();
			c.close();
			printGameStats();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	private static void printGameStats() {
		
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

	public void setRoundWinner(int roundWinnerInput) {

		roundWinner = roundWinnerInput;

	}

	public int getRoundWinner() {
		return roundWinner;
	}

	public static int setGameNumber() {
		
		/*
		 * Connects to the database and gets the game number of the last game and adds 1 to give the most recent game number. 
		 */
		
		connectToDatabase();
		try {
			stmt = c.createStatement();
			// testing_game_stats to game_stats
			ResultSet lastGameNumber = stmt.executeQuery("SELECT game_number COUNT(*) FROM testing_game_stats");
			if (lastGameNumber.next()) {
				gameNumber = lastGameNumber.getInt("game_number");
				
			}
			lastGameNumber.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		gameNumber ++;
		return gameNumber;
	}

}