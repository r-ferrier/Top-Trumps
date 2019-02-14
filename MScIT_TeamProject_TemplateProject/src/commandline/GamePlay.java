package commandline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GamePlay {

	private int roundCounter = 1;
	private int drawCounter;
	private int chosenCategory;
	private ArrayList<Card> communalPile = new ArrayList<>();
	public ArrayList<Player> players;
	private Player winner;
	private Deck deck;
	private int humanIndex;
	private int currentPlayer = 0; // set to first player (0) initially, will be updated to the winner index
	private int activePlayers;
	private boolean humanKnockedOut = false;
	private int gameWinner;
	private boolean startGame;
	private Database database;

	/**
	 * Constructor for GamePlay class. It creates a deck of cards from the .txt file in
	 * in the commandline.Deck class and stores the categories for the cards. It also creates
	 * the AI players and uses gameBegins() method to start gameplay.
	 */

	public GamePlay() {

		createDeck();
		setAIPlayers();
		database = new Database();// Setting up all the elements needed for the game
		gameBegins(); // Prints output and prompts user entry of name

		chooseFirstPlayer(); // Shuffles player ArrayList so the order of players is random
		dealCardsToPlayers();

		while (!isGameOver()) {// Play rounds while game is not over

			announceRoundNumber();
			playRound();
			roundCounter++;
		}
		gameWinner = decideWinner(); // Once game is over decide winner
		database.uploadGameStats(drawCounter, gameWinner, roundCounter);

	}

	public static void main(String[] args) {
		new GamePlay();
	}

	/**
	 * Creates the ArrayList for players and then populate it with AI players
	 */
	public void setAIPlayers() {

		players = new ArrayList<>();

		players.add(new Player("Clive", false, 1));
		players.add(new Player("Brenda", false, 2));
		players.add(new Player("Philip", false, 3));
		players.add(new Player("Janet", false, 4));

	}
	/**
	 * Creates Deck of cards from file.
	 */
	public void createDeck() {
		deck = new Deck();
	}
	/**
	 * Prints the number of current round using roundCounter.
	 */
	public void announceRoundNumber() {
		System.out.println("Round " + roundCounter + ": Players have drawn their cards.");
	}

	/**
	 * Once the user has entered a name
	 * they are added to an ArrayList of players. Players ArrayList is shuffled and cards
	 * dealt into player's 'hand'. User offered the option to begin game or view stats. 
	 */
	private void gameBegins() {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Hello human player. \n" + "Please enter your name: ");

		players.add(new Player(scanner.nextLine(), true, 0));

		System.out.println(players.get(4).getName() + "! What a lovely name.\n" + "My name is "
				+ players.get(0).getName() + ". \n" + "Please meet my friends: " + players.get(1).getName() + ", "
				+ players.get(2).getName() + " and " + players.get(3).getName() + ".\n");

		startGame = false;
		// Loops start game question until user selects that they want to start
		// a new game.
		while (startGame == false) {
			System.out.println("Would you like to start a new game or see previous game stats? "
					+ "Enter: 1 for a new game or 0 for previous game stats.");
			try {
			int gameStats = scanner.nextInt();
			if (gameStats == 0) {
				database.pullGameStats();
				database.printGameStats();
			} else {
				startGame = true;
				System.out.println("Ok, is everybody ready? Then let's play.");
			}
			}catch (InputMismatchException e) {
				System.out.println("Oops that was not a number. \n");
			}
		}

	}
	/**
	 * NEED TO REVIEW!
	 */
    private ArrayList<commandline.Player> dealHands(){
        return players;
    }

	/**
	 * Shuffles the ArrayList of players and sets the index of human player.
	 */
	public void chooseFirstPlayer() {

		Collections.shuffle(players);
		setHumanPlayerIndex();
	}

	/**
	 * Takes the shuffled ArrayList of card objects and distributes them to players in set order.
	 */
	private void dealCardsToPlayers() {

		int i = 0;
		for (Card card : deck.getDeck()) {
			players.get(i).dealCard(card);
			i++;

			if (i > 4) {
				i = 0;
			}
		}
		TestLog.logAllocatedHands(players);
	}

	private void playRound() {

		setHumanPlayerIndex(); // At beginning of each round, set where the human is in the ArrayList.
		announceCurrentPlayer(); // Announce which player will be choosing the category (ie. player in position 0 for first
		// round). After that the most recent winner will choose category. 

		showHumanTopCard(); // Print the human player's card.
		chooseCategory(); // Ask the human to pick a category OR ask the computer to select the highest
		// category from the card

		if(!humanKnockedOut) {
			playCard(); // Ask the human to press enter to advance the round as long as they are still in the game.
		}
		addCardsToCommunalPile(declareRoundWinOrDraw());
		/*
		 * This runs two methods. The declareRoundWinOrDraw() method returns a boolean, true for win, false for a draw.
		 * This boolean is passed to the addCardsToCommunalPile() method which removes every player's
		 * top card. If the player won the round, the top cards go to the end of that player's
		 * 'hand' ArrayList, along with any cards currently in the communal pile ArrayList. If there was no
		 * winner, cards go into the same communal pile .
		 */

		removeKnockedOutPlayers(); 


	}
	/**
	 * Any players with no cards left at the end of the game are removed from the
	 * players ArrayList. Current player index is changed to reflect new position. Check 
	 * if the human is still in the game.
	 */
	private void removeKnockedOutPlayers() {
		for (int i = 0; i < players.size(); i++) {

			if (players.get(i).amIKnockedOut()) {

				if (players.get(i).checkHuman()) {
					humanKnockedOut = true;
				}

				System.out.println(players.get(i).getName() + " was knocked out.");
				players.remove(i);

				i--; // If array is shortened during loop, remove 1 from the counter so we look at every position

				if (currentPlayer > i) {
					currentPlayer--; // If current player's position was after i or if it was i and current player
					// is being knocked out after a series of draws, current player position is
					// moved one position down in the ArrayList to reflect either their change in position or the turn moving to
					// the next player.
				}

			}
		}

	}
	/**
	 * Scans through ArrayList checking the ArrayList index for human player.
	 */
	private void setHumanPlayerIndex() {
		int humanIndex = 0;

		for (Player p : players) {
			if (p.checkHuman()) {
				break;
			} else {
				humanIndex++;
			}
		}
		this.humanIndex = humanIndex;

	}

	/**
	 * Call this method each time a player is knocked out. Method checks the number of
	 * active players and sets gameOver to true when there is only one active player remaining.
	 */
	private boolean isGameOver() {

		activePlayers = players.size();

		if (activePlayers == 1) {
			System.out.println("Game Over!");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * When game is over, loop through players and return the player who has not been knocked out
	 * @return winner as player number.
	 */
	private int decideWinner() {
		int winner = -1;

		for (Player player : players) {
			if (player.amIKnockedOut() == false) {
				winner = player.getNumber();
				System.out.println("The winner is " + player.getName());

				TestLog.logWinner(player);
			}
		}
		return winner;
	}

	private void announceCurrentPlayer() {

		System.out.println("It's " + players.get(currentPlayer).getName() + "'s turn.");
	}
	/**
	 * Get current player's top card and name. If the current player is human game prompts them to choose a category from top card.
	 * Takes keyboard input and checks for a valid integer.
	 * If current player is AI it calls findBestCategory() method on topCard.
	 */
	private void chooseCategory() {
		Card topCard = players.get(currentPlayer).getTopCard();
		String name = players.get(currentPlayer).getName();


		if (players.get(currentPlayer).checkHuman() == true) {

			System.out.println("Please select your category, the categories are:" + "\n" + topCard.chooseACategory());

			boolean categorySelected = false;

			while(!categorySelected) {

				try {
					Scanner categorySelection = new Scanner(System.in);
					chosenCategory = categorySelection.nextInt();

					while (chosenCategory > 5 || chosenCategory < 1) {
						System.out.println("I'm sorry, that is not a valid category! Please choose again, your number must be between" +
								" 1 and 5.");
						chosenCategory = categorySelection.nextInt();
					}
					categorySelected=true;

				} catch (InputMismatchException i) {
					System.out.println("I'm sorry, that is not a valid category! Please choose again, your number must be between" +
							" 1 and 5.");
				}
			}

		} else {
			chosenCategory = topCard.findBestCategory();
		}

		System.out.println(name + " has chosen category " + chosenCategory + ", "
				+ topCard.getCategories()[chosenCategory - 1] + ".");
		
		TestLog.logSelectedCategory(chosenCategory, topCard.getCategories()[chosenCategory - 1], players);		
	}

	/**
	 * Gives human player the option to play their card or quit the game.
	 */
	private void playCard() {

		System.out.println("Press enter to play your card, or type q to quit");
		Scanner scan = new Scanner(System.in);

		String readString = scan.nextLine();

		if (readString == null) {
			return;
		} else if (readString.isEmpty()) {
			return;
		}else if(readString.charAt(0)=='q') {
				System.exit(0);
				return;
			}

			return;

	}

	/**
	 * If human player is still in the game, print their top card.
	 */
	private void showHumanTopCard(){

		if (!humanKnockedOut) {

			Card topCard = players.get(humanIndex).getTopCard();
			System.out.println();
			System.out.println("You have drawn " + players.get(humanIndex).getTopCard().getDescription() + ".");
			System.out.println(topCard.toString());

		} else {
			System.out.println("\n" + "You have no cards left to play and have been knocked out of the game" + "\n");
		}
		TestLog.logCardsInPlay(players);
	}

	/**
	 * Compares the value of the chosen category on each player's top card and finds the highest positive value. It checks if the
	 * round is draw by looking for two matching positive integers of the highest value. If the round is a draw, the winner is set
	 * to null and the method returns false. If it is not a draw, the currentPlayer index is updated to reflect the position of the 
	 * round winner and the method returns true.
	 */
	private boolean declareRoundWinOrDraw() {

		winner = players.get(currentPlayer);
		int playerIndex = 0;

		Integer valueOfChosenCategory;
		int currentHighestCategoryValue = 0;
		int winnerIndex = 0;
		ArrayList<Integer> topCardValues = new ArrayList<>();

		for (Player p : players) {

			valueOfChosenCategory = p.getTopCard().getAnyCategory(chosenCategory);
			topCardValues.add(valueOfChosenCategory);

			if (valueOfChosenCategory > currentHighestCategoryValue) {
				currentHighestCategoryValue = valueOfChosenCategory;

				winner = p;
				winnerIndex = playerIndex;
			}


			playerIndex++;
		}

		Collections.sort(topCardValues);
		if (topCardValues.get(topCardValues.size()-1) == topCardValues.get(topCardValues.size()-2)) {
			System.out.println("IT WAS A DRAW\n\n");
			winner = null;
			drawCounter++;
			return false;
		} else {

			// I have tried this but I don't think it works quite right and I couldn't figure out what else to use.
			//database.setRoundWins(p.getNumber());
			database.setRoundWins(winner.getNumber());

			currentPlayer = winnerIndex;

			System.out.println(winner.getName() + " won this round with the card " + winner.getTopCard().getDescription()
					+ " which had a " + winner.getTopCard().getCategories()[chosenCategory - 1] + " value of "
					+ currentHighestCategoryValue + ".\n\n");
			return true;
		}
	}

	/**
	 * If the round was won, loop through players and if player is not the winner, get their top card, remove it, and add it 
	 * to the winners 'hand'. Add any cards from the communal pile to the winner's hand.
	 * If the round was a draw, get the top card from each player and add to the communal pile.
	 * 
	 * @param win boolean true/win false/draw
	 */
	private void addCardsToCommunalPile(boolean win) {

		if (win) {

			for (int i = 0; i < players.size(); i++) {
				players.get(currentPlayer).dealCard(players.get(i).getTopCard());
				players.get(i).removeTopCardFromHand();
			}

			for (Card c : communalPile) {
				players.get(currentPlayer).dealCard(c);
			}
			communalPile.clear();

		} else {

			for (Player p : players) {
				communalPile.add(p.getTopCard());
				p.removeTopCardFromHand();
			}
		}
		TestLog.logCommunalPile(communalPile);
		TestLog.logHandsAfterRound(players);	
	}

}