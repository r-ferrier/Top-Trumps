import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> deck = new ArrayList<>();
    private String[] categories = new String[5];
    private final String fileName = "StarCitizenDeck.txt";

    /**
     * constructor for deck needs to open and read the file. If it can do this successfully, it will pass each line of the file
     * to a new card object to store the information and create a list of categories. Once all the objects have been made they are
     * stored here as an arraylist and then shuffled.
     */
    public Deck(){
        readFile();
    }

    /**
     * reads file line by line. Uses the first line to create categories, and creates a Card object with every line
     * after that, storing these objects in an Arraylist (deck).
     */
    private void readFile(){

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            setCategories(reader.readLine());

            String thisLine;
            while ((thisLine = reader.readLine()) != null) {
                Card nextCard = new Card(thisLine);
                addCard(nextCard);
            }

            shuffleCards();

        } catch (IOException fileNotFound){
            fileNotFound.printStackTrace();
        }
    }

    /**
     * adds a new card object to the deck arraylist
     * @param nextCard Card object created as file is read
     */
    private void addCard(Card nextCard){
        deck.add(nextCard);
    }

    /**
     * shuffles the Arraylist of cards using the Collections method shuffle
     */
    private void shuffleCards(){
        Collections.shuffle(deck);
    }


    /**
     * sets the 5 categories for the deck and stores them as an array of strings in the correct order
     * @param allCategories first line of the imported file
     */
    private void setCategories(String allCategories){

        String[] categoriesIncludingDescription;
        categoriesIncludingDescription = allCategories.split(" ");

        for (int i = 0; i<5; i++){
            categories[i] = categoriesIncludingDescription[i+1];
        }

    }

    public ArrayList<Card> getDeck(){
        return deck;
    }

    public String[] getCategories(){
        return categories;
    }

}
