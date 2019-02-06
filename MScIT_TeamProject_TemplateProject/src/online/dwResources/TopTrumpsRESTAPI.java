package online.dwResources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import commandline.Card;
import commandline.Database;
import commandline.Deck;
import commandline.Player;
import online.configuration.TopTrumpsJSONConfiguration;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;

@Path("/toptrumps") // Resources specified here should be hosted at http://localhost:7777/toptrumps
@Produces(MediaType.APPLICATION_JSON) // This resource returns JSON content
@Consumes(MediaType.APPLICATION_JSON) // This resource can take JSON content as input
/**
 * This is a Dropwizard Resource that specifies what to provide when a user
 * requests a particular URL. In this case, the URLs are associated to the
 * different REST API methods that you will need to expose the game commands
 * to the Web page.
 *
 * Below are provided some sample methods that illustrate how to create
 * REST API methods in Dropwizard. You will need to replace these with
 * methods that allow a TopTrumps game to be controled from a Web page.
 */
public class TopTrumpsRESTAPI {

    public Deck deck;
    public ArrayList<Player> players;

    /**
     * A Jackson Object writer. It allows us to turn Java objects
     * into JSON strings easily.
     */
    ObjectWriter oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();

    /**
     * Contructor method for the REST API. This is called first. It provides
     * a TopTrumpsJSONConfiguration from which you can get the location of
     * the deck file and the number of AI players.
     *
     * @param conf
     */
    public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) {

        conf.setDeckFile("Sandwich.txt");
        conf.setNumAIPlayers(4);
        // ----------------------------------------------------
        // Add relevant initalization here
        // ----------------------------------------------------
    }

    // ----------------------------------------------------
    // Add relevant API methods here
    // ----------------------------------------------------



    @GET
    @Path("/start-game")
    public String itsYourTurn(int numOfPlayers) {

        deck = new Deck();
        players = new ArrayList<>();

        for(int i=0;i<numOfPlayers;i++){
            players.add(new Player(i));
        }

        Database database = new Database();

        Collections.shuffle(players);

        String name = "Player " + (players.get(0).getNumber() + 1);
        String itsYourTurn;

        if (players.get(0).getNumber() == 0) {
            itsYourTurn = "It's your turn!";
        } else {
            itsYourTurn = "It's " + name + "'s turn.";
        }

        return itsYourTurn;
    }

    @GET
    @Path("/category-values")
    public int[] categoryValues(){
        //return int array containing values for each category on the "top card"

        int[] categoryValues = new int[5];

        Card topCard = players.get(0).getTopCard();
        categoryValues[0] = topCard.getAnyCategory(1);
        categoryValues[1] = topCard.getAnyCategory(2);
        categoryValues[2] = topCard.getAnyCategory(3);
        categoryValues[3] = topCard.getAnyCategory(4);
        categoryValues[4] = topCard.getAnyCategory(5);

        return categoryValues;
    }

    @GET
    @Path("/all-top-cards")
    public String[] topCards(){
        //return string array containing the description name of every top card for this round
        String[] topCards = new String[5];

        for (int i = 0; i<5; i++) {
            topCards[i] = players.get(i).getTopCard().getDescription();
        }

        return topCards;
    }

    @GET
    @Path ("/get-winner")
    public String[] getWinner(){
        String[] winnerInfo = new String[]{"winner","1"};


        return winnerInfo;
    }

    @GET
    @Path ("/players-turn")
    public String[] playerTurn() {
        String[] playerInfo = new String[]{"playerNumber", "1", "276", "3", "4", "5", "1"};
        return playerInfo;
    }



    @GET
    @Path("/winner")
    public String winner(){
        //this method currently is not returning an int like requested but just returns a string stating what
        //happened. Very open to changing this but thought i'd just put something in for now.

        String winner="";
        //i think this could be done by calling GamePlay.declareRoundWinOrDraw() as it would be a lot of the
        //same code, but that may not work if the other variables aren't being set from GamePlay? obv would
        //also have to make it a protected method so we could access it.

        int category = 0;
        int categoryValue;
        int currentHighestCategoryValue = 0;
        int index = 0;
        String winnerName;

        for (Player p : players) {

            categoryValue = p.getTopCard().getAnyCategory(category);

            if (categoryValue > currentHighestCategoryValue) {
                currentHighestCategoryValue = categoryValue;

                winnerName = p.getName();
                winner = winnerName + " has won this round!";


            } else if (categoryValue == currentHighestCategoryValue) {

                winner = "It's a draw!!!";
            }
        }


        //put java method in here to return the number of the winner

        return winner;
    }


//
//    @GET
//    @Path("/helloJSONList")
//    /**
//     * Here is an example of a simple REST get request that returns a String.
//     * We also illustrate here how we can convert Java objects to JSON strings.
//     * @return - List of words as JSON
//     * @throws IOException
//     */
//    public String helloJSONList() throws IOException {
//
//        List<String> listOfWords = new ArrayList<String>();
//        listOfWords.add("Hello");
//        listOfWords.add("World!");
//
//        // We can turn arbatory Java objects directly into JSON strings using
//        // Jackson seralization, assuming that the Java objects are not too complex.
//        String listAsJSONString = oWriter.writeValueAsString(listOfWords);
//
//        return listAsJSONString;
//    }
//
//    @GET
//    @Path("/helloWord")
//    /**
//     * Here is an example of how to read parameters provided in an HTML Get request.
//     * @param Word - A word
//     * @return - A String
//     * @throws IOException
//     */
//    public String helloWord(@QueryParam("Word") String Word) throws IOException {
//        return "Hello " + Word;
//    }

}