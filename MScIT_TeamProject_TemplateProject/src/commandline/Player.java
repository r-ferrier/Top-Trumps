package commandline;

import java.util.ArrayList;

public class Player {

    private final String name;
    private int number;
    private ArrayList<Card> hand = new ArrayList<>();

    private boolean knockedOut = false;
    private boolean human;
    private int roundsWon;

    public Player(String name, boolean human){
        this.name = name;
        this.human = human;
    }

    public void setPlayerNumber(int number){
        this.number = number;
    }

    public void dealCard(Card card){
        hand.add(card);
    }

    public int getNumberOfCardsInHand(){
        return hand.size();
    }

    public void winRound(){
        roundsWon++;
    }

    public boolean amIKnockedOut(){
        return knockedOut;
    }

    public void knockOut(){
        knockedOut = true;
    }

    public String getName(){
        return name;
    }

    public int getNumber(){
        return number;
    }
}
