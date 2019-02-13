package online.dwResources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import commandline.Card;
import commandline.Database;
import commandline.Deck;
import commandline.Player;
import jdk.nashorn.internal.parser.JSONParser;
import online.configuration.TopTrumpsJSONConfiguration;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        // Add relevant initialisation here
        // ----------------------------------------------------
    }

    // ---------------------------------------------------
    // Add relevant API methods here
    // ----------------------------------------------------

    @GET
    @Path("/deck")
    public String getDeck() {
        try {
            return oWriter.writeValueAsString(new Deck().getDeck());
        } catch (IOException e) {
            e.printStackTrace();
            return "soz";
        }
    }

    @GET
    @Path("/get-players/{number}")
    public String players(@PathParam("number")int numOfPlayers) throws JsonProcessingException {

        ArrayList<Player> players;

        players = new ArrayList<>();
        String playerNames[] = new String[]{"Clive","Janet","Brenda","Philip"};

        players.add(new Player("you",true,0));

        for(int i=1;i<numOfPlayers;i++){
            players.add(new Player(playerNames[i],false,i));
        }

        Collections.shuffle(players);

        return oWriter.writeValueAsString(players);
    }


    @GET
    @Path("/category-values/{card}")
    public int[] categoryValues(@PathParam("card")String card){
        //return int array containing values for each category on the "top card"

        String[] cards = card.split(",");

        int[] categoryValues = new int[5];

        categoryValues[0] = 1;
        categoryValues[1] = 2;
        categoryValues[2] = 3;
        categoryValues[3] = 4;
        categoryValues[4] = 5;

        return categoryValues;
    }

//    @GET
//    @Path("/all-top-cards")
//    public String[] topCards(){
//        //return string array containing the description name of every top card for this round
////        String[] topCards = new String[5];
////
////        for (int i = 0; i<5; i++) {
////            topCards[i] = players.get(i).getTopCard().getDescription();
////        }
////
////        return topCards;
//    }

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


//    @GET
//    @Path("/winner")
//    public String winner(){
//        //this method currently is not returning an int like requested but just returns a string stating what
//        //happened. Very open to changing this but thought i'd just put something in for now.
//
//        String winner="";
//        //i think this could be done by calling GamePlay.declareRoundWinOrDraw() as it would be a lot of the
//        //same code, but that may not work if the other variables aren't being set from GamePlay? obv would
//        //also have to make it a protected method so we could access it.
//
//        int category = 0;
//        int categoryValue;
//        int currentHighestCategoryValue = 0;
//        int index = 0;
//        String winnerName;
//
//        for (Player p : players) {
//            categoryValue = p.getTopCard().getAnyCategory(category);
//
//            if (categoryValue > currentHighestCategoryValue) {
//                currentHighestCategoryValue = categoryValue;
//                winnerName = p.getName();
//                winner = winnerName + " has won this round!";
//
//            } else if (categoryValue == currentHighestCategoryValue) {
//                winner = "It's a draw!!!";
//            }
//        }
//        //put java method in here to return the number of the winner
//        return winner;
//    }


}