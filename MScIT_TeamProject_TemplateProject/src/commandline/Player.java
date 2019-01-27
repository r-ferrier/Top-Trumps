package commandline;
import java.util.ArrayList;

public class Player {

    private final String name;
    private int number;
    private ArrayList<Card> hand = new ArrayList<>(); //this arrayList contains all of the player's cards

    private boolean knockedOut = false; //when the ArrayList is 0, the player will be knocked out
    private boolean human;
    private int roundsWon;

    /**
     * constructor just takes a name and wants to know if we're dealing with a human or an AI player
     * @param name name of player
     * @param human if true, it's a human, if false, it's an AI player
     */
    public Player(String name, boolean human, int number){
        this.name = name;
        this.human = human;
        this.number = number;
    }

    /**
     * method to set player number - set after construction so that they can be randomly assigned an order and number
     * @param number order the game will be played in
     */
    public void setPlayerNumber(int number){
        this.number = number;
    }

    /**
     * method to add a new card to a hand
     * @param card
     */
    public void dealCard(Card card){
        hand.add(card);
    }


    /**
     * method to return the top card from a player's hand - can be used to show the player the card they're looking
     * at at the start of each round. Top card is in position 0. Bottom card is in position size-1.
     * @return
     */
    public Card getTopCard(){

        return hand.get(0);
    }

    /*
    below here are all the getters and setters, more may be required
     */


    public int getNumberOfCardsInHand(){
        return hand.size();
    }

    public void winRound(){
        roundsWon++;
    }
    
    public int roundsWon() {
    	return roundsWon;
    }

    public boolean amIKnockedOut(){
        return knockedOut;
    }

    public void knockOutPlayer(){
        knockedOut = true;
    }

    
    public String getName(){
        return name;
    }

    public int getNumber(){
        return number;
    }

    public boolean checkHuman() {
        return human;
    }
    
    public ArrayList<Card> getHand(){
    	return this.hand;
    }


    public void removeTopCardFromHand() {

        hand.remove(0);
    	hand.trimToSize();

    	if(hand == null || hand.isEmpty()){
    	    knockedOut = true;
        }


    }
}