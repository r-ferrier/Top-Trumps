package commandline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GamePlay {

    private int roundCounter;
    private int chosenCategory;
    private ArrayList<Card> cardsInPlay;
    private ArrayList<Player> players;
//    private GameData gameData;
    private Deck deck;
    private String[] categories;

    /**
     * constructor for this class. It creates a deck of cards from the file chosen in the commandline.Deck class,
     * stores the categories for the cards, creates the AI players and begins the game. Once the user has entered their
     * name they're added to an array of players, players are shuffled and have cards dealt to them, and then they can
     * begin to play rounds.
     */
    public GamePlay(){

        createDeck();
        setCategories();
        setAIPlayers(); //setting up all the elements needed for the game

        gameBegins(); //prints some output and prompts user entry of name

        chooseFirstPlayer(); //shuffles player array so the order of players is random and fair

        dealCardsToPlayers();

        /*
        methods to play round and determine winners in here
         */

    }

    /**
     * creates the array for our players and adds in all of the AI players
     */
    private void setAIPlayers(){

        players = new ArrayList<>();

        players.add(new Player("Clive",false,1));
        players.add(new Player("Brenda",false,2));
        players.add(new Player("Philip",false,3));
        players.add(new Player("Janet",false,4));

    }

    private void createDeck(){
        deck = new Deck();
    }

    private void setCategories(){
        categories = deck.getCategories();
    }

    /**
     * prints out prompts and collects the users name. Creates a new human player and adds to the player arraylist.
     */
    private void gameBegins(){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello human player. \n" +
                "Please enter your name: ");

        players.add(new Player(scanner.nextLine(),true,0));

        System.out.println(players.get(4).getName()+"! What a lovely name.\n" +
                "My name is "+players.get(0).getName()+". \n"+
                "Please meet my friends: "+players.get(1).getName()+
                ", "+players.get(2).getName()+" and "+players.get(3).getName()+".\n");

        System.out.println("Ok, is everybody ready? Then let's play.");
    }

//    private ArrayList<commandline.Player> dealHands(){
//        return players;
//    }

    /**
     * shuffles the order of the players then gives them all a player number.
     */
    private void chooseFirstPlayer(){

        Collections.shuffle(players);
        System.out.println(players.get(0).getName()+" goes first!");
    }

    /**
     * takes the shuffled pack of cards and hands them out one at a time to the players in the order they have been
     * set for this game in the array.
     */
    private void dealCardsToPlayers(){

        int i = 0;

        for(Card card: deck.getDeck()){
            players.get(i).dealCard(card);
            i++;

            if(i>4){
                i=0;
            }
        }

    }

    private void playRound(){

    }

    private void decideWinner(){

    }

    private void addCardsToCardsInPlay(){

    }

    private void declareRounWinOrDraw(){

    }
}
