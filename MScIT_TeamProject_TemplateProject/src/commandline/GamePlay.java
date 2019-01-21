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
    private boolean gameOver = false;
    private int activePlayers;

    public static void main(String[] args){


        GamePlay newGamePlay = new GamePlay();
        newGamePlay.gamePlay();

    }

    /**
     * This will be the constructor for this class. It creates a deck of cards from the file chosen in the commandline.Deck class,
     * stores the categories for the cards, creates the AI players and begins the game. Once the user has entered their
     * name they're added to an array of players, players are shuffled and have cards dealt to them, and then they can
     * begin to play rounds.
     */
    private void gamePlay(){

        createDeck();
        setCategories();
        setAIPlayers(); //setting up all the elements needed for the game

        gameBegins(); //prints some output and prompts user entry of name

        chooseFirstPlayer(); //shuffles player array so the order of players is random and fair

        dealCardsToPlayers();

        activePlayers = players.size(); // set number of players in game 
        
        /**
         * play rounds while game is not over
         */
        // while(!gameOver){
        //     playRound();
        // }
        // decideWinner(); // once game is over, decide winner

        /*
        methods to play round and determine winners in here
         */

    }

    /**
     * creates the array for our players and adds in all of the AI players
     */
    private void setAIPlayers(){

        players = new ArrayList<>();

        players.add(new Player("Clive",false));
        players.add(new Player("Brenda",false));
        players.add(new Player("Philip",false));
        players.add(new Player("Janet",false));

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

        players.add(new Player(scanner.nextLine(),true));

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

        int i = 0;

        for (Player player: players){
            player.setPlayerNumber(i);
            i++;
        }

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

    /**
     * call this method each time a player is knocked out. checks the number of active players and set gameOver to true when there is 
     * only one active player
     */
    private void checkStateOfPlay(){
            if(activePlayers == 1) {
                gameOver = true;
                System.out.println("Game Over!");
		}
	}
    
    /**
     * when game is over, loop through players and return the player who is not knocked out
     * @return winner
     */
    private int decideWinner() {
        int winner = -1;
		for (Player player : players) {
			if (player.amIKnockedOut() == false) {
				winner = player.getNumber();
				System.out.println("The winner is " + player.getName());
			}
			
		}
		return winner;

    }

    private void addCardsToCardsInPlay(){

    }

    private void declareRounWinOrDraw(){

    }
}
