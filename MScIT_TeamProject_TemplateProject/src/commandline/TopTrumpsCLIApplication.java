package commandline;



/**
 * Top Trumps command line application
 */

public class TopTrumpsCLIApplication {

	/**
	 * This main method is called by TopTrumps.java when the user specifies that
	 * they want to run in command line mode. The contents of args[0] is whether we
	 * should write game logs to a file.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		boolean writeTestLog = false; // Should we write game logs to file?

		if (args[0].equalsIgnoreCase("true"))
<<<<<<< HEAD
			writeTestLog = true; // Command line selection

		TestLog testLog = new TestLog(writeTestLog); // Create logger. If writeTestLog is false, no logs will be written
=======

			writeGameLogsToFile = true; // Command line selection
>>>>>>> 68834f473a51cce404169a4e9f133d3592c9e722


		// State
		boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application

		// Loop until the user wants to exit the game
		while (!userWantsToQuit) {

			GamePlay newGame = new GamePlay();

			userWantsToQuit = true; // use this when the user wants to exit the game

		}

	}

}
