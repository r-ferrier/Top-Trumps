import java.util.ArrayList;

public class GamePlay {

    private int roundCounter;
    private int chosenCategory;
    private ArrayList<Card> cardsInPlay;
//    private ArrayList<Player> players;
//    private GameData gameData;
    private Deck deck;
    private String[] categories;

    public static void main(String[] args){

        GamePlay newGamePlay = new GamePlay();
        newGamePlay.gamePlay();

    }

    private void gamePlay(){
        createDeck();
        setCategories();
    }

    private void createDeck(){
        deck = new Deck();
    }

    private void setCategories(){
        categories = deck.getCategories();
    }

//    private ArrayList<Player> dealHands(){
//        return players;
//    }

    private void chooseFirstPlayer(){

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
