package commandline;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Player {

	public String name;
	private int number;
	public ArrayList<Card> hand = new ArrayList<>(); // This ArrayList contains the player's card objects

	public boolean knockedOut = false; // When the 'hand' ArrayList contains no cards the player will be knocked out
	public boolean human; //These are public so that the api will get them back correctly

	/**
 	 * Constructor for Player. 
 	 * Each Player has a String name, integer and ArrayList called 'hand' containing commandline.Card objects.
 	 * In addition booleans for human, knocked out status and a counter for rounds won are associated with 
 	 * each Player.
	 * @param name  name of each Player
	 * @param human if true, the Player is human, if false, the Player is an AI.
	 */

	public Player(String name, boolean human, int number) {
		this.name = name;
		this.human = human;
		this.number = number;
	}

	/**
	 * Method to set player number - set after construction so that they can be
	 * randomly assigned an order and number.
	 * @param number order the game will be played in
	 */
	public void setPlayerNumber(int number) {
		this.number = number;
	}

	/**
	 * Method to add new card to 'hand' ArrayList.
	 * @param card commandline.Card object
	 */
	public void dealCard(Card card) {
		hand.add(card);
	}

	/**
	 * Method to return the top card from a player's 'hand'. getTopCard() method can be used to show the
	 * player the card they're looking at at the start of each round. 
	 * Top card is in position 0. Bottom card is in position size-1. 
	 * @return commandline.Card object
	 */
	@JsonIgnore
	public Card getTopCard() {
		return hand.get(0);
	}

	
	 /**
	  * Boolean method to return if a player is still in the game.
	  * @return boolean true when 'hand' ArrayList is empty.
	  */
	public boolean amIKnockedOut() {
		return knockedOut;
	}

	/**
	 * Sets knockedOut to true. 
	 */
	public void knockOutPlayer() {
		knockedOut = true;
	}

	/**
	 * Returns player's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get player's number
	 * @return player number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Boolean method to return if a player is human
	 * @return boolean true when player is human
	 */
	public boolean checkHuman() {
		return human;
	}

	/**
	 * getHand() method returns the 'hand' ArrayList
	 * @return 'hand' ArrayList of Card objects.
	 */
	public ArrayList<Card> getHand() {
		return this.hand;
	}
	
	/**
	 * The removeTopCardFromHand() method makes use of the trimToSize() method 
	 * to resize ArrayList once card is removed from index 0 of 'hand' ArrayList.
	 * When no commandline.Card objects remain in 'hand' ArrayList boolean knockedOut
	 * is set to true.
	 */
	@JsonIgnore
	public void removeTopCardFromHand() {

		hand.remove(0);
		hand.trimToSize();

		if (hand == null || hand.isEmpty()) {
			knockedOut = true;
		}

	}
}