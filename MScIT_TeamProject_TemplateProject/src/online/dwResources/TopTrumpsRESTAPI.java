package online.dwResources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import commandline.*;
import online.configuration.TopTrumpsJSONConfiguration;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
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

    private Database database;

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

    }

    @GET
    @Path("/get-players/{number}")
    public String players(@PathParam("number") int numOfPlayers) throws JsonProcessingException {

        ArrayList<Card> deck = new Deck().getDeck();
        Collections.shuffle(deck);

        ArrayList<Player> players = new ArrayList<>();
        String playerNames[] = new String[]{"Clive", "Janet", "Brenda", "Philip"};

        players.add(new Player("You", true, 0));

        for (int i = 0; i < numOfPlayers-1; i++) {
            players.add(new Player(playerNames[i], false, i));
        }

        Collections.shuffle(players);

        int i = 0;
        for (Card card : deck) {
            players.get(i).dealCard(card);
            i++;
            if (i > numOfPlayers-1) {
                i = 0;
            }
        }

        return oWriter.writeValueAsString(players);
    }

    @GET
    @Path("/pull-game-stats")
    public String pullGameStats() throws JsonProcessingException {

        database = new Database();
        database.pullGameStats();

        int[] statsArray = new int[5];

        statsArray[0] = database.getTotalNumberGames();
        statsArray[1] = database.getNumComputerWon();
        statsArray[2] = database.getNumHumanWon();
        statsArray[3] = (int) database.getAverageDraws();
        statsArray[4] = database.getLargestNumberRound();

        return oWriter.writeValueAsString(statsArray);
    }


    @GET
    @Path("/writeDatabase/{databaseArray}/{playersArray}")
    public String databaseWriter(@PathParam("databaseArray") String databaseData, @PathParam("playersArray") String playersData) {

        database = new Database();

        String[] databaseArray = databaseData.split(",");
        int draw = Integer.parseInt(databaseArray[0]);
        int gameWinner = Integer.parseInt(databaseArray[1]);
        int roundCounter = Integer.parseInt(databaseArray[2]);

        database.uploadGameStats(draw, gameWinner, roundCounter);

        String[] playersArray = playersData.split(",");

        int[] playersArrayAsInts = new int[playersArray.length];

        for(int i = 0; i <playersArray.length; i++){
            playersArrayAsInts[i] = Integer.parseInt(playersArray[i]);
        }

        database.setRoundWinsfromOnlineVersion(playersArrayAsInts);

        return "draws: " + draw + " winner: " + gameWinner + " number of rounds: " + roundCounter + "players array as ints" + Arrays.toString(playersArrayAsInts);
    }

}