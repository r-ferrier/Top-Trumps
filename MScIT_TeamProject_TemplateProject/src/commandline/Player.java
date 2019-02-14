package commandline;

import java.util.ArrayList;
/**
 * Constructor for Player. 
 * Each Player has a String name, integer and Arraylist called 'hand' containing commandline.Card objects.
 * In addition booleans for are human, knocked out status and a counter for rounds won are associated with 
 * each Player.
 */
public class Player {

	private String name;
	private int number;
	private ArrayList<Card> hand = new ArrayList<>(); // This ArrayList contains the player's card objects

	private boolean knockedOut = false; // When the 'hand' ArrayList contains no cards the player will be knocked out
	private boolean human;
	private int roundsWon; // A counter for the amount of rounds won by each player.

	/**
	 * Constructor takes a name and wants to know if we're dealing with a human
	 * or an AI player
	 * 
	 * @param name  name of each Player
	 * @param human if true, the Player is human, if false, the Player is an AI.
	 */
	public Player(String name, boolean human, int number) {
		this.name = name;
		this.human = human;
		this.number = number;
	}
	public Player(int number){
		this.number = number;
	}
	/**
	 * method to set player number - set after construction so that they can be
	 * randomly assigned an order and number
	 * 
	 * @param number order the game will be played in
	 */
	public void setPlayerNumber(int number) {
		this.number = number;
	}

	/**
	 * method to add a new card to a hand
	 * 
	 * @param card
	 */
	public void dealCard(Card card) {
		hand.add(card);
	}

	/**
	 * method to return the top card from a player's hand - can be used to show the
	 * player the card they're looking at at the start of each round. Top card is in
	 * position 0. Bottom card is in position size-1.
	 * 
	 * @return
	 */
	public Card getTopCard() {

		return hand.get(0);
	}

	/*
	 * below here are all the getters and setters, more may be required
	 */

	public int getNumberOfCardsInHand() {
		return hand.size();
	}

	public void winRound() {
		roundsWon++;
	}

	//public int roundsWon() {
	//	return roundsWon;
	//}
	/*
	not currently in use
	 */

	public boolean amIKnockedOut() {
		return knockedOut;
	}

	public void knockOutPlayer() {
		knockedOut = true;
	}

	public String getName() {
		return name;
	}

	public int getNumber() {
		return number;
	}

	public boolean checkHuman() {
		return human;
	}

	public ArrayList<Card> getHand() {
		return this.hand;
	}
	/**
	 * The removeTopCardFromHand() method makes use of the trimToSize() method 
	 * to resize Array once card is removed from index 0 of 'hand' ArrayList.
	 * When no commandline.Card objects remain in 'hand' ArrayList boolean knockedOut
	 * is set to true.
	 */
	public void removeTopCardFromHand() {

		hand.remove(0);
		hand.trimToSize();

		if (hand == null || hand.isEmpty()) {
			knockedOut = true;
		}

	}
}