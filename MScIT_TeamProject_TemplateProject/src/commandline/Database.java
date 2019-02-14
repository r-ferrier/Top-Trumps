package commandline;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Database {

	private Connection c;
	private Statement stmt;
	private int gameNumber;
	private int totalNumberGames;
	private int numComputerWon;
	private int numHumanWon;
	private double averageDraws;
	private int largestNumberRound;
	private int[] playerWins = new int[] { 0, 0, 0, 0, 0 };

	public Database() {
	}

	/**
	 * This method will connect our program to the database so our data can be
	 * updated and/or received.
	 */
	private void connectToDatabase() {
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
			c = DriverManager.getConnection("jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/", "m_18_2028263c",
					"2028263c");
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

	/**
	 * This method creates a new table for the persistent game data if one does not
	 * already exist I am currently working under the assumption that we don't want
	 * a new table each time we call the program to run - but this can easily be
	 * changed.
	 */
	private void createNewTable() {
		try {
			System.out.println("Controlling your database...");
			Statement statement = c.createStatement();
			String sqlStringGameStats = "CREATE TABLE IF NOT EXISTS game_stats ( "
					+ " game_number integer PRIMARY KEY NOT NULL, " 
					+ " game_winner integer NOT NULL, "
					+ " number_of_rounds integer NOT NULL, " 
					+ " number_of_draws integer, "
					+ " human_player_rounds_won integer, " 
					+" computer_player_1_rounds_won integer, " 
					+ " computer_player_2_rounds_won integer, "
					+ " computer_player_3_rounds_won integer, " 
					+ " computer_player_4_rounds_won integer"
					+  ");";
			statement.executeUpdate(sqlStringGameStats);
		} catch (SQLException e) {
			System.out.println("Connection Failed - create new table");
			e.printStackTrace();
			return;

		}
	}

	/**
	 * This methodo will take the results from the game just played and upload them
	 * to the database. roundsPerPlayer as an array with the index relating to the
	 * player - linked to the number of rounds they won
	 */
	public void uploadGameStats(int gameDraws, int gameWinner, int gameRounds) {
		connectToDatabase();
		setGameNumber();
		int status = 0;
		try {
			String SQLInsert = "INSERT INTO game_stats(" + "game_number, " + "game_winner, " + "number_of_rounds, "
					+ "number_of_draws, " + "human_player_rounds_won, " + "computer_player_1_rounds_won, "
					+ "computer_player_2_rounds_won, " + "computer_player_3_rounds_won, "
					+ "computer_player_4_rounds_won) " + "VALUES (" + gameNumber + ", " + gameWinner + ", " + gameRounds
					+ ", " + gameDraws + ", " + playerWins[0] + ", " // This is the human player.
					+ playerWins[1] + ", " + playerWins[2] + ", " + playerWins[3] + ", " + playerWins[4] + "); ";

			status = stmt.executeUpdate(SQLInsert);

			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// checks the insertion
		if (status == 1) {
			System.out.println("Persistent Game Statistics are inserted");
		} else {
			System.out.println("No insertion completed");
		}

	}

	/**
	 * This method will print the information about the previous games, while the
	 * game is not in progress.
	 */
	public void pullGameStats() {
		connectToDatabase();
		try {
			stmt = c.createStatement();

			ResultSet gameNumber = stmt.executeQuery("SELECT COUNT(*) game_number FROM game_stats");
			while (gameNumber.next()) {
				totalNumberGames = gameNumber.getInt(1);
			}

			ResultSet compWon = stmt.executeQuery("SELECT COUNT(*) game_winner FROM game_stats WHERE game_winner <> 0");
			// 0 is the human player
			while (compWon.next()) {
				numComputerWon = compWon.getInt(1);
			}

			ResultSet humanWon = stmt.executeQuery("SELECT COUNT(*) game_winner FROM game_stats WHERE game_winner = 0");
			// 0 is the human player
			while (humanWon.next()) {
				numHumanWon = humanWon.getInt(1);
			}

			ResultSet avgDraws = stmt.executeQuery("SELECT AVG(number_of_draws) FROM game_stats");
			if (avgDraws.next()) {
				averageDraws = avgDraws.getDouble(1);
				averageDraws = Math.round(averageDraws); // Rounds the average number of draws
			}

			ResultSet largestRound = stmt.executeQuery("SELECT MAX(number_of_rounds) FROM game_stats");
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
			// printGameStats(); ======== HAVE BEEN PUT INTO GAMEPLAY
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Prints the persistent game data that has been obtained from the database.
	 */
	public void printGameStats() {
		System.out.println("Number of games played overall: " + totalNumberGames);
		System.out.println("How many times the computer has won: " + numComputerWon);
		System.out.println("How many times the human has won: " + numHumanWon);
		System.out.println("The average number of draws: " + averageDraws);
		System.out.println("The largest number of rounds played in a single game: " + largestNumberRound);
	}

	// ---------------- GETTERS AND SETTERS ----------------- \\

	/**
	 * Connects to the database and gets the game number of the last game and adds 1
	 * to give the most recent game number.
	 */
	public int setGameNumber() {
		connectToDatabase();
		try {
			stmt = c.createStatement();
			ResultSet lastGameNumber = stmt.executeQuery("SELECT COUNT(*) game_number  FROM game_stats");
			if (lastGameNumber.next()) {
				gameNumber = lastGameNumber.getInt("game_number");
			}
			lastGameNumber.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		gameNumber++;
		return gameNumber;
	}

	/**
	 * Updates an array of how many rounds each player has won each game.
	 */
	public void setRoundWins(int playerIndex) {
		playerWins[playerIndex]++;
		System.out.println("Player: " + playerIndex + " won a round. They have now won " + playerWins[playerIndex] + " rounds total." );
	}

	public int getRoundWins(int playerIndex) {
		return playerWins[playerIndex];
	}

	public int getGameNumber() {
		return gameNumber;
	}

	public int getTotalNumberGames() {
		return totalNumberGames;
	}

	public int getNumComputerWon() {
		return numComputerWon;
	}

	public int getNumHumanWon() {
		return numHumanWon;
	}

	public double getAvergaeDraws() {
		return averageDraws;
	}

	public int getLargestNumberRound() {
		return largestNumberRound;
	}

}
