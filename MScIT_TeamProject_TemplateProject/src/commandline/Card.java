package commandline;

import java.util.ArrayList;
import java.util.Collections;
import static java.lang.Integer.parseInt;

public class Card {

	private String description;
	private String[] categories = new String[5];
	private ArrayList<Integer> categoryValues = new ArrayList<Integer>();
	private int cardNumber;

	private int category1;
	private int category2;
	private int category3;
	private int category4;
	private int category5;

	/**
	 * Constructor for card object. Makes cardNumber local.
	 * @param CategoryInformation 
	 * @param categoryDescriptions
	 * @param cardNumber
	 */
	public Card(String CategoryInformation, String categoryDescriptions, int cardNumber) {

		setCategories(CategoryInformation);
		setCategoryDescriptions(categoryDescriptions);
		this.cardNumber = cardNumber;

	}
	/**
	 * Takes in a line of the deck as a String and creates an array of elements.  
	 * First index of the array becomes the card description.
	 * Subsequent elements of the array are parsed as integers to set each category value.
	 * ArrayList categoryValues is initialised with category ints. 
	 * @param categoryInformation
	 */
	protected void setCategories(String categoryInformation) {

	    String[] categoryInfo = categoryInformation.split(" ");

		description = categoryInfo[0];
		category1 = parseInt(categoryInfo[1]);
		category2 = parseInt(categoryInfo[2]);
		category3 = parseInt(categoryInfo[3]);
		category4 = parseInt(categoryInfo[4]);
		category5 = parseInt(categoryInfo[5]);

		//Adds values to ArrayList
		categoryValues.add(category1);
		categoryValues.add(category2);
		categoryValues.add(category3);
		categoryValues.add(category4);
		categoryValues.add(category5);
	}

	/**
	 * When it is an AI player's turn findBestCategory() method is called to locate.
	 * the highest positive integer from an array of category values.
	 * It returns the position in the list by adding one to the array index.
	 * @return bestCategory
	 */
	protected int findBestCategory(){
		int max = Collections.max(categoryValues);
		int bestCategory = categoryValues.indexOf(max) + 1; 
		return bestCategory;
	}

	/**
	 * toString() method to display card.
	 */
	public String toString() {

		String cardDetails = categories[0] + ": " + category1 + " \n" + categories[1] + ": " + category2 + " \n"
				+ categories[2] + ": " + category3 + " \n" + categories[3] + ": " + category4 + " \n" + categories[4]
				+ ": " + category5 + " \n";

		return cardDetails;
	}
	/**
	 * chooseACategory() method. Method is used to present human player category numbers and corresponding values
	 * when it is their turn.
	 * @return cardCategories 
	 */

	public String chooseACategory() {

		String cardCategories = "1. " + category1 + "\n2. " + category2 + "\n3. " + category3 + "\n4. " + category4
				+ "\n5. " + category5 + "\n";
		return cardCategories;
	}
	/**
	 * 
	 * @param categoryToReturn Takes in an integer for each category.
	 * @return Returns an integer that corresponds to the value in the chosen category.
	 */
//	public int getAnyCategory(int categoryToReturn) {
//
//		if (categoryToReturn == 1) {
//			return category1;
//		} else if (categoryToReturn == 2) {
//			return category2;
//		} else if (categoryToReturn == 3) {
//			return category3;
//		} else if (categoryToReturn == 4) {
//			return category4;
//		} else {
//			return category5;
//		}
//	}
// Possible new method to replace one above..........
	public int getAnyCategory(int categoryToReturn) {
		return categoryValues.get(categoryToReturn - 1);
		}

	/**
	 * @return Returns String description ie. name of each sandwich.
	 */
	public String getDescription() {
	return description;
	}

	/**
	 * setCategoryDescriptions() sets the five categories for the deck and stores them as an array of Strings in
	 * the correct order. The method creates two arrays to remove the first entry, which
	 * just reads 'description'.
	 * @param categoryDescriptions first line of the imported file
	 */
	public void setCategoryDescriptions(String categoryDescriptions) {

		String[] categoriesIncludingDescription;
		categoriesIncludingDescription = categoryDescriptions.split(" ");

		for (int i = 0; i < 5; i++) {
			categories[i] = categoriesIncludingDescription[i + 1];
		}
	}

	public String[] getCategories() {
		return categories;
	}

	public ArrayList<Integer> getCategoryValues(){
		return categoryValues;
	}

	public int getCardNumber(){
		return cardNumber;
	}

}