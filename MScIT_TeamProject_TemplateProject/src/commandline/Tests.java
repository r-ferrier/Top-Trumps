package commandline;

import static org.junit.Assert.*;

import java.beans.Transient;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Tests {

	static Deck deck;
	private ArrayList<Card> communalPile = new ArrayList<>();
	private ArrayList<Card> hand = new ArrayList<>();
	static Card c;
	static String catDescription;
	static int num;

	@Before
	public void setUp() {
		deck = new Deck();
		catDescription = "Des Size Speed Range Fire Cargo";
		String catInfo = "Name 1 2 5 9 8";
		num = 1;
		c = new Card(catInfo,catDescription, num);
	}
	/**
	 * deckIsNotEmpty() checks the deck ArrayList is populated.
	 */
	@Test
	public void deckIsNotEmpty() {
		assertFalse(deck.getDeck().isEmpty());
	}
	/**
	 * communalPileNotEmpty() checks the communalPile ArrayList can correctly store a Card object.
	 */

	@Test
	public void deckHas40Cards(){
		assertEquals(40, deck.getDeck().size());
	}
	@Test
	public void communalPileNotEmpty() {
		communalPile.add(c);
		assertFalse(communalPile.isEmpty());
	}
	/**
	 * handIsNotEmpty() checks that the hand ArrayList can correctly store a Card object.
	 */
	@Test
	public void handIsNotEmpty() {
		hand.add(c);
		assertEquals(1, hand.size());
	}
	/**
	 * categoryTest() ensures the correct ArrayList index is returned after sorting ArrayList to find
	 * the highest score on the Card object.
	 */
	@Test
	public void findBestCategoryTest() {
		assertEquals(4, c.findBestCategory());
	}
	/**
	 * categorySelectionTest() shows that if all category values are the same integer the game will choose
	 * the first instance of it.
	 */
	@Test
	public void firstHighestCategoryTest() {
		String categoryInformation2 = "Avenger 5 5 5 5 5";
		Card cardSameValues = new Card(categoryInformation2, catDescription, num);
		assertEquals(1, cardSameValues.findBestCategory());
	}
	/**
	 * getAnyCategoryTest() shows that the game retrieves the correct category.
	 */
	@Test
	public void getAnyCategoryTest() {
		assertEquals(1, c.getAnyCategory(1));	

	}

	/**
	 * shuffleTest() shows that the top card of the deck after it has been shuffled is
	 * not the same as the top card of the deck before it was shuffled.
	 */
	@Test
	public void shuffleTest() {
		String originalDeck = deck.getDeck().get(0).getDescription();
		deck.shuffleCards();
		String shuffleDeck = deck.getDeck().get(0).getDescription();
		Assert.assertNotSame(originalDeck, shuffleDeck);
		
	}

}