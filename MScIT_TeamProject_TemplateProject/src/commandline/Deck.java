package commandline;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class Deck {

    private ArrayList<Card> deck = new ArrayList<>();
    private  String fileName = "Sandwich.txt";

    /**
     * Constructor for deck needs to open and read the file. If it can do this successfully, it will pass each line of the file
     * to a new card object, storing the information and creating a list of categories with positive integer values. 
     * Once all the objects have been made they are
     * stored as an arraylist and shuffled.
     */
    public Deck(){
        readFile();
    }

    /**
     * The readFile () method reads in file line by line. It uses the first line to generate category titles. 
     * It then creates a commandline.Card object 
     * from every subsequent line. It stores these objects in a 'deck' Arraylist.
     */
    public void readFile(){


        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String categoryDescriptions = (reader.readLine());
            String thisLine;
            int cardNumber = 0;
            while ((thisLine = reader.readLine()) != null) {
                Card nextCard = new Card(thisLine, categoryDescriptions, cardNumber);
                addCard(nextCard);
                cardNumber++;
            }
            TestLog.logDeck(deck);
            shuffleCards();
            TestLog.logShuffle(deck);

        } catch (IOException fileNotFound){
            fileNotFound.printStackTrace();
        }
    }

    /**
     * The addCard() method adds a new card object to the deck Arraylist
     * @param nextCard commandline.Card object is created as file is read in.
     */
    private void addCard(Card nextCard){
        deck.add(nextCard);
    }

    /** 
     * shuffleCards() shuffles the Arraylist of cards, making use of the Collections shuffle method.
     */
    private void shuffleCards(){
        Collections.shuffle(deck);
    }

    /** 
     * Getter for deck Arraylist.
     */
    public ArrayList<Card> getDeck(){
        return deck;
    }


}