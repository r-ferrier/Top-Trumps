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
		database = new Database();// setting up all the elements needed for the game
		gameBegins(); // prints some output and prompts user entry of name

		chooseFirstPlayer(); // shuffles player array so the order of players is random and fair
		dealCardsToPlayers();

		while (!isGameOver()) {// play rounds while game is not over

			announceRoundNumber();
			playRound();
			roundCounter++;
		}
		gameWinner = decideWinner(); // once game is over, decide winner
		database.uploadGameStats(drawCounter, gameWinner, roundCounter);

	}

	public static void main(String[] args) {
		new GamePlay();
	}

	/**
	 * creates the array for our players and adds in all of the AI players
	 */
	public void setAIPlayers() {

		players = new ArrayList<>();

		players.add(new Player("Clive", false, 1));
		players.add(new Player("Brenda", false, 2));
		players.add(new Player("Philip", false, 3));
		players.add(new Player("Janet", false, 4));

	}

	public void createDeck() {
		deck = new Deck();
	}

	public void announceRoundNumber() {
		System.out.println("Round " + roundCounter + ": Players have drawn their cards.");
	}

	/**
	 * Once the user has entered a name
	 * they are added to an array of players. Players are shuffled and have cards
	 * dealt to them, and then they can begin to play rounds.
	 * prints out prompts and collects the users name. Creates a new human player
	 * and adds to the player arraylist.
	 */
	private void gameBegins() {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Hello human player. \n" + "Please enter your name: ");

		players.add(new Player(scanner.nextLine(), true, 0));

		System.out.println(players.get(4).getName() + "! What a lovely name.\n" + "My name is "
				+ players.get(0).getName() + ". \n" + "Please meet my friends: " + players.get(1).getName() + ", "
				+ players.get(2).getName() + " and " + players.get(3).getName() + ".\n");

		startGame = false;
		// loops the start game question until the user selects that they want to start
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

    private ArrayList<commandline.Player> dealHands(){
        return players;
    }

	/**
	 * shuffles the order of the players.
	 */
	public void chooseFirstPlayer() {

		Collections.shuffle(players);
		setHumanPlayerIndex();
	}

	/**
	 * takes the shuffled pack of cards and hands them out one at a time to the
	 * players in the order they have been set for this game in the array.
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

		setHumanPlayerIndex(); // at beginning of each round, check where the human is in the player array
		announceCurrentPlayer(); // announce which player will be playing round (player in position 0 for first
		// round,
		// after that always the most recent winner. draws are ignored.

		showHumanTopCard(); // print the human player's card into the terminal
		chooseCategory(); // ask the human to pick a category OR ask the computer to select the highest
		// category from the card

		if(!humanKnockedOut) {
			playCard(); // ask the human to press enter to advance the round as long as they are still in the game
		}
		addCardsToCommunalPile(declareRoundWinOrDraw());
		/*
		 * run two methods. First is to declare whether or not the round had a winner or
		 * was a draw. If the method finds any two scores that match it will immediately
		 * return false, set the winner as null and exit the method without making any
		 * further changes. If the method returns as true, it will change the
		 * currentPlayer index to reflect the position of the round winner. The
		 * true/false result is passed to the second method which removes every player's
		 * top card. If the player won the round, the top cards go onto that player's
		 * pile, along with any cards currently in the communal pile. If there was no
		 * winner, cards go onto a communal pile.
		 *
		 */

		removeKnockedOutPlayers(); // any players with no cards left at the end of the game are removed from the
		// players
		// array, current player index is changed to reflect new position and we check
		// if the human is still in the game.


	}

	private void removeKnockedOutPlayers() {
		for (int i = 0; i < players.size(); i++) {

			if (players.get(i).amIKnockedOut()) {

				if (players.get(i).checkHuman()) {
					humanKnockedOut = true;
				}

				System.out.println(players.get(i).getName() + " was knocked out.");
				players.remove(i);

				i--; // if array is shortened during loop, remove 1 from the counter so we look at
				// every position

				if (currentPlayer > i) {
					currentPlayer--; // if current player's position was after i or if it WAS i and current player
					// is being knocked out after a series of draws, current player position is
					// moved one position down
					// in the array to reflect either their change in position or the turn moving to
					// the next person at
					// the table.
				}

			}
		}

	}

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
	 * call this method each time a player is knocked out. checks the number of
	 * active players and set gameOver to true when there is only one active player
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
	 * when game is over, loop through players and return the player who is not
	 * knocked out
	 *
	 * @return winner
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
	 * says if the round has been won or drew, sets the player for the next round
	 * (currentplayer) and sets the winner to the winner of this round (winner)
	 *
	 * @return true if win, false if draw
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

	private void addCardsToCommunalPile(boolean win) {
		// for each player, if player is not the winner, get their top card, remove it,
		// and add it to the winner.Hand

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