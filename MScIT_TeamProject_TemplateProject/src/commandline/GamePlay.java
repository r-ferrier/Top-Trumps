package commandline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GamePlay {

    private int roundCounter = 1;
    private int chosenCategory;
    private ArrayList<Card> cardsInPlay = new ArrayList<>();
    private ArrayList<Player> players;
    private Player winner;
    //    private GameData gameData;
    private Deck deck;
    private int humanIndex;
    private int currentPlayer = 0; //set to first player (0) initially, will be updated to the winner index
    private int activePlayers;
    private boolean humanKnockedOut = false;

    /**
     * constructor for this class. It creates a deck of cards from the file chosen in the commandline.Deck class,
     * stores the categories for the cards, creates the AI players and begins the game. Once the user has entered their
     * name they're added to an array of players, players are shuffled and have cards dealt to them, and then they can
     * begin to play rounds.
     */
    public GamePlay() {

        createDeck();
        setAIPlayers(); //setting up all the elements needed for the game
        gameBegins(); //prints some output and prompts user entry of name

        chooseFirstPlayer(); //shuffles player array so the order of players is random and fair
        dealCardsToPlayers();

         // set number of players in game

        /**
         * play rounds while game is not over
         */
         while(!isGameOver()){

             announceRoundNumber();
             playRound();
             roundCounter++;
         }
         decideWinner(); // once game is over, decide winner

    }

    public static void main(String[] args) {
        new GamePlay();
    }

    /**
     * creates the array for our players and adds in all of the AI players
     */
    private void setAIPlayers() {

        players = new ArrayList<>();

        players.add(new Player("Clive", false, 1));
        players.add(new Player("Brenda", false, 2));
        players.add(new Player("Philip", false, 3));
        players.add(new Player("Janet", false, 4));

    }

    private void createDeck() {
        deck = new Deck();
    }

    private void announceRoundNumber(){
        System.out.println("Round "+roundCounter+": Players have drawn their cards.");
    }


    /**
     * prints out prompts and collects the users name. Creates a new human player and adds to the player arraylist.
     */
    private void gameBegins() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello human player. \n" +
                "Please enter your name: ");

        players.add(new Player(scanner.nextLine(), true, 0));

        System.out.println(players.get(4).getName() + "! What a lovely name.\n" +
                "My name is " + players.get(0).getName() + ". \n" +
                "Please meet my friends: " + players.get(1).getName() +
                ", " + players.get(2).getName() + " and " + players.get(3).getName() + ".\n");

        System.out.println("Ok, is everybody ready? Then let's play.");
    }

//    private ArrayList<commandline.Player> dealHands(){
//        return players;
//    }

    /**
     * shuffles the order of the players.
     */
    private void chooseFirstPlayer() {

        Collections.shuffle(players);
        setHumanPlayerIndex();
    }

    /**
     * takes the shuffled pack of cards and hands them out one at a time to the players in the order they have been
     * set for this game in the array.
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

    }

    private void playRound() {

        setHumanPlayerIndex();
        announceCurrentPlayer();

        showHumanTopCard();
        chooseCategory();

        addCardsToCardsInPlay();

        for(int i = 0; i< players.size(); i++){

            if(players.get(i).amIKnockedOut()){

                if(players.get(i).checkHuman()){
                    humanKnockedOut = true;
                }

                System.out.println(players.get(i).getName()+" was knocked out.");
                players.remove(i);

                i--;

                if(currentPlayer>i){
                    currentPlayer--;
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
     * call this method each time a player is knocked out. checks the number of active players and set gameOver to true when there is
     * only one active player
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
     * when game is over, loop through players and return the player who is not knocked out
     *
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

    private void announceCurrentPlayer() {

        System.out.println("It's " + players.get(currentPlayer).getName() + "'s turn.");
    }

    private void chooseCategory() {
        Card topCard = players.get(currentPlayer).getTopCard();
        String name = players.get(currentPlayer).getName();

        Scanner categorySelection = new Scanner(System.in);

        if (players.get(currentPlayer).checkHuman() == true) {

            System.out.println("Please select your category, the categories are:" +
                    "\n"+topCard.chooseACategory());

            chosenCategory = categorySelection.nextInt();

        } else {
            chosenCategory = topCard.findBestCategory();
        }

        System.out.println(name + " has chosen category " + chosenCategory + ", "+topCard.getCategories()[chosenCategory-1]+".");
    }

    private void showHumanTopCard() {


        if(!humanKnockedOut) {

            Card topCard = players.get(humanIndex).getTopCard();
            System.out.println();
            System.out.println("You have drawn "+players.get(humanIndex).getTopCard().getDescription()+".");
            System.out.println(topCard.toString());

        } else {
            System.out.println("\n"+"You have no cards left to play and have been knocked out of the game"+"\n");
        }
    }


    /**
     * says if the round has been won or drew, sets the player for the next round (currentplayer) and sets the winner
     * to the winner of this round (winner)
     *
     * @return true if win, false if draw
     */
    private boolean declareRoundWinOrDraw() {

        winner = players.get(currentPlayer);
        int playerIndex = 0;

        int valueOfChosenCategory;
        int currentHighestCategoryValue = 0;
        int winnerIndex = 0;

        for (Player p : players) {

            valueOfChosenCategory = p.getTopCard().getAnyCategory(chosenCategory);

            if (valueOfChosenCategory > currentHighestCategoryValue) {
                currentHighestCategoryValue = valueOfChosenCategory;

                winner = p;
                winnerIndex = playerIndex;

            } else if (valueOfChosenCategory == currentHighestCategoryValue) {

                System.out.println("IT WAS A DRAW");

                winner = null;
                return false;
            }

            playerIndex++;
        }

        currentPlayer = winnerIndex;

        System.out.println(winner.getName() + " won this round with a value of " + currentHighestCategoryValue+".\n\n");
        return true;
    }


    private void addCardsToCardsInPlay() {
        //for each player, if player is not the winner, get their top card, remove it, and add it to the winner.Hand

        if (declareRoundWinOrDraw()) {


            for (int i = 0; i < players.size(); i++) {

                players.get(currentPlayer).dealCard(players.get(i).getTopCard());
                players.get(i).removeTopCardFromHand();

            }

            for(Card c: cardsInPlay){
                players.get(currentPlayer).dealCard(c);
            }
            cardsInPlay.clear();


        } else {

            for(Player p: players){
                cardsInPlay.add(p.getTopCard());
                p.removeTopCardFromHand();
            }
        }


    }
}