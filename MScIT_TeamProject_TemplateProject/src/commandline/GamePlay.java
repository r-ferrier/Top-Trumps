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
    private int humanIndex;
    private int currentPlayer = 0; //set to first player (0) initially, will be updated to the winner index
    private boolean gameOver = false;
    private int activePlayers;

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


        setHumanPlayerIndex();

        playRound();

       // activePlayers = players.size(); // set number of players in game 
        
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
    public static void main(String[] args){
        new GamePlay();
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
        checkCurrentPlayer();
        checkTopCard();
        chooseCategory();

    }
    private void setHumanPlayerIndex() {
        int humanIndex = 0;

        for (Player p: players) {
            if (p.checkHuman()){
                break;
            }else {
                humanIndex++;
            }
        }
        this.humanIndex = humanIndex;
        System.out.println("HUMAN" + humanIndex + players.get(humanIndex).getName());
    }

    private void setCurrentPlayer(){
        /** this sets currentPlayer to be the winner's player number. :)
        */
//        currentPlayer = winner;
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
    private void checkCurrentPlayer(){
        System.out.println("It is " + players.get(currentPlayer).getName() + "'s turn.");
    }

    private void chooseCategory(){
        Card topCard = players.get(currentPlayer).getTopCard();
        String name = players.get(currentPlayer).getName();

        Scanner categorySelection = new Scanner(System.in);

        if (players.get(currentPlayer).checkHuman() == true){
            System.out.println("Please select your category:");
            chosenCategory = categorySelection.nextInt();
        }else {
            chosenCategory = topCard.findBestCategory();
        }
        System.out.println(name + " has chosen category " + chosenCategory + ".");
    }

    private void checkTopCard(){
        Card topCard = players.get(humanIndex).getTopCard();
        System.out.println("Your top card is:\n" + topCard.toString());
    }


    private void addCardsToCardsInPlay(){

    }

    private void declareRounWinOrDraw(){

    }
}
