package commandline;
import java.util.logging.*;
import java.io.IOException;
public class TestLog {
    private static final Logger LOGGER = Logger.getGlobal();

    public TestLog(boolean writeLog){
        if(!writeLog){
            LogManager.getLogManager().reset();
        }
        else{setUpLogger();
        }

    }

    public void setUpLogger(){
        LogManager.getLogManager().reset();
        SimpleFormatter formatter = new SimpleFormatter();
        try {
            FileHandler fh = new FileHandler("toptrumps.log");
            fh.setLevel(Level.ALL);
            fh.setFormatter(formatter);
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "File logger not working");
        }
    }
    //The contents of the complete deck once it has been read in and constructed
    // 
    public static void logDeck(){
        LOGGER.log(Level.INFO, "test" );

    }

    //The contents of the complete deck after it has been shuffled
    public static void logShuffle(){
        LOGGER.log(Level.INFO, "test" );

    }
//    The contents of the user’s deck and the computer’s deck(s) once they have been allocated. Be sure to
//    indicate which the user’s deck is and which the computer’s deck(s) is.
    public static void logAllocatedHands(){
        LOGGER.log(Level.INFO, "test" );

    }

//    The contents of the communal pile when cards are added or removed from it
    public static void logCommunalPile(){
        LOGGER.log(Level.INFO, "test" );

    }

//     The contents of the current cards in play (the cards from the top of the user’s deck and the computer’s
//     deck(s))
    public static void logCardsInPlay() {
        LOGGER.log(Level.INFO, "test" );
        
    }

//     The category selected and corresponding values when a user or computer selects a category
    public static void logSelectedCategoy(){
        LOGGER.log(Level.INFO, "test" );

    }
//     The contents of each deck after a round
    public static void logHandsAfterRound(){
        LOGGER.log(Level.INFO, "test" );

    }

//     The winner of the game 
    public static void logWinner(){
        LOGGER.log(Level.INFO, "getWinner" );

    }
}
