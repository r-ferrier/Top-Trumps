package online.dwResources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import commandline.*;
import online.configuration.TopTrumpsJSONConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

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
    public String itsYourTurn() {

        deck = new Deck();
        players = new ArrayList<>();

        players.add(new Player("You", true, 0));
        players.add(new Player("Clive", false, 1));
        players.add(new Player("Brenda", false, 2));
        players.add(new Player("Philip", false, 3));
        players.add(new Player("Janet", false, 4));

        Database database = new Database();

        Collections.shuffle(players);

        String name = players.get(0).getName();
        String itsYourTurn;

        if (name.equals("You")) {
            itsYourTurn = "It's your turn!";
        } else {
            itsYourTurn = "It's " + name + "'s turn.";
        }

        return itsYourTurn;
    }

    @GET
    @Path("/category-values")
    public int[] categoryValues(){

        int[] categoryValues = new int[]{5,10,6,5,4};

        //put java method in here to return category values of current player's top card as an int array

        return categoryValues;
    }

    @GET
    @Path("/all-top-cards")
    public String[] topCards(){

        String[] topCards = new String[5];

        //put java method in here to return string array containing the description name of every top card for this round

        return topCards;
    }

    @GET
    @Path("/winner")
    public int winner(){

        int winner = 0;

        //put java method in here to return the number of the winner of this round

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
