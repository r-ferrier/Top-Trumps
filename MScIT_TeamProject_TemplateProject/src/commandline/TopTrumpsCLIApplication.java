package commandline;



/**
 * Top Trumps command line application
 */

public class TopTrumpsCLIApplication {

	/**
	 * This main method is called by TopTrumps.java when the user specifies that
	 * they want to run the game in commandline mode. The contents of args[0] is whether we
	 * should write game logs to a file.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		boolean writeTestLog = false; // Should we write game logs to file?

		if (args[0].equalsIgnoreCase("true")) {
			writeTestLog = true; // Command line selection
		}

		TestLog testLog = new TestLog(writeTestLog); // Create logger. If writeTestLog is false, no logs will be written


		// State
		boolean userWantsToQuit = false; // Flag to check whether the user wants to quit the application

		// Loop until the user wants to exit the game
		while (!userWantsToQuit) {

			GamePlay newGame = new GamePlay();

			userWantsToQuit = true; // Use this when the user wants to exit the game

		}

	}

}