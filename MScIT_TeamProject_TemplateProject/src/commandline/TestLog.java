package commandline;

import java.util.ArrayList;
import java.util.logging.*;
import java.io.IOException;

public class TestLog {

    private static final Logger LOGGER = Logger.getGlobal();
    private static String splitter = "\r\n--------------------------------------------------------------------------\r\n";

    /**
     * Use boolean to enable logger. If writeLog is false LogManager resets and no
     * log file will be created. If writeLog is true, set up logger to enable file
     * writing.
     * 
     */
    public TestLog(boolean writeLog) {
        LogManager.getLogManager().reset();

        if (writeLog) {
            setUpLogger();
        }

    }

    /**
     * Set up logger formatting and file handler. Use LogManager to prevent print out
     * in console.
     */
    public void setUpLogger() {
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

    /**
     * Log the contents of the complete deck once it has been read in and constructed.
     * 
     */
    public static void logDeck(ArrayList<Card> deck) {
        String allCardsInDeck = "";
        for (Card c : deck) {
            allCardsInDeck += c.getDescription() + "\r\n" + c.toString() + " \r\n";
        }
        LOGGER.log(Level.INFO, "Deck read-in from file and constructed\r\n\r\n" + allCardsInDeck + splitter);

    }

    /**
     * Log the contents of the complete deck after it has been shuffled.
     * 
     */
    public static void logShuffle(ArrayList<Card> deck) {
        String shuffledDeck = "";
        for (Card c : deck) {
            shuffledDeck += c.getDescription() + "\r\n";
        }
        LOGGER.log(Level.INFO, "Shuffled deck\r\n\r\n" + shuffledDeck + splitter);

    }

    /**
     * Check if player is human or computer and convert to a string
     * 
     */
    public static String humanOrComputer(boolean checkHuman) {
        String status = "";
        if (checkHuman) {
            status = "Human";
        } else {
            status = "Computer";
        }
        return status;
    }

    /**
     * Log the contents of the user's deck and the computer's deck(s) once they have
     * been allocated. Indicate which the user's deck is and which the
     * computer's deck(s) is.
     * 
     */
    public static void logAllocatedHands(ArrayList<Player> players) {
        String allocatedHands = "";
        for (Player p : players) {
            String playerHand = "";
            String name = p.getName() + " (" + humanOrComputer(p.checkHuman()) + ")";
            ArrayList<Card> hand = p.getHand();
            for (Card c : hand) {
                playerHand += " | " + c.getDescription();

            }
            allocatedHands += name + ":\r\n" + playerHand + "\r\n";
        }
        LOGGER.log(Level.INFO, "Hands allocated to players\r\n\r\n" + allocatedHands + splitter);

    }

    /**
     * Log the contents of the communal pile when cards are added or removed from it.
     * 
     */
    public static void logCommunalPile(ArrayList<Card> communalPile) {
        String contents = "";
        for (Card c : communalPile) {
            contents += c.getDescription() + "\r\n";
        }
        LOGGER.log(Level.INFO, "Contents of Communal Pile:\r\n" + contents + splitter);

    }

    /**
     * Log the contents of the current cards in play (the cards from the top of the
     * user's deck and the computer's deck(s)).
     * 
     */
    public static void logCardsInPlay(ArrayList<Player> players) {
        String cardsInPlay = "";
        for (Player p : players) {
            String name = p.getName();
            String topCard = p.getTopCard().getDescription();
            cardsInPlay += name + "'s card: " + topCard + "\r\n";
        }
        LOGGER.log(Level.INFO, "Cards in play\r\n\r\n" + cardsInPlay + splitter);
    }

    /**
     * Log the category selected and corresponding values when a user or computer
     * selects a category.
     * 
     */
    public static void logSelectedCategory(int selectedCategory, String categoryDescription,
            ArrayList<Player> players) {
        String categoryValues = "";
        for (Player p : players) {
            String name = p.getName();
            Card card = p.getTopCard();
            String description = card.getDescription();
            int value = card.getAnyCategory(selectedCategory);
            categoryValues += name + " has " + description + ": " + value + "\r\n";
        }
        LOGGER.log(Level.INFO, "Category selected and corresponding values\r\n\r\n" + "Selected category is "
                + selectedCategory + ", " + categoryDescription + ".\r\n\r\n" + categoryValues + splitter);

    }

    /**
     * Log the contents of each deck after a round.
     * 
     */
    public static void logHandsAfterRound(ArrayList<Player> players) {
        String handsAfterRound = "";
        for (Player p : players) {
            String playerHand = "";
            String name = p.getName();
            ArrayList<Card> hand = p.getHand();
            for (Card c : hand) {
                playerHand += " | " + c.getDescription();

            }
            handsAfterRound += name + "'s Hand:\r\n" + playerHand + "\r\n";
        }

        LOGGER.log(Level.INFO, "Players hands after a round\n\n" + handsAfterRound + splitter);
    }

    /**
     * Log the winner of the game.
     * 
     */
    public static void logWinner(Player winner) {
        LOGGER.log(Level.INFO, "Game winner: " + winner.getName() + splitter);

    }
}
