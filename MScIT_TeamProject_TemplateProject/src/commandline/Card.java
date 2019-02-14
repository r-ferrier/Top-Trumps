package commandline;

import java.util.ArrayList;
import java.util.Collections;
import static java.lang.Integer.parseInt;

public class Card {

	private String description;
	private String[] categories = new String[5];
	private int categoryValues[];
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
	 * int[] categoryValues is initialised with category ints. 
	 * @param categoryInformation
	 */
	private void setCategories(String categoryInformation) {

	    String[] categoryValues = categoryInformation.split(" ");

		description = categoryValues[0];
		category1 = parseInt(categoryValues[1]);
		category2 = parseInt(categoryValues[2]);
		category3 = parseInt(categoryValues[3]);
		category4 = parseInt(categoryValues[4]);
		category5 = parseInt(categoryValues[5]);

		this.categoryValues= new int[]{category1,category2,category3,category4,category5};
	}
		/**
		 * When it is an AI player's turn findBestCategory() method is called to locate.
		 * the highest positive integer from an array of category values.
		 * It returns the position in the list by adding one to the array index. 
		 * @return bestCategory
		 */

	public int findBestCategory() {
		
		ArrayList<Integer> categoryList = new ArrayList<>();
		categoryList.add(category1);
		categoryList.add(category2);
		categoryList.add(category3);
		categoryList.add(category4);
		categoryList.add(category5);
		int max = Collections.max(categoryList);
		int bestCategory = categoryList.indexOf(max) + 1;
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
	public int getAnyCategory(int categoryToReturn) {

		if (categoryToReturn == 1) {
			return category1;
		} else if (categoryToReturn == 2) {
			return category2;
		} else if (categoryToReturn == 3) {
			return category3;
		} else if (categoryToReturn == 4) {
			return category4;
		} else {
			return category5;
		}
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

	public int[] getCategoryValues(){
		return categoryValues;
	}

	public int getCardNumber(){
		return cardNumber;
	}

}