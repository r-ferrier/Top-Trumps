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


	public Card(String CategoryInformation, String categoryDescriptions, int cardNumber) {

		setCategories(CategoryInformation);
		setCategoryDescriptions(categoryDescriptions);
		this.cardNumber = cardNumber;

	}

	private void setCategories(String categoryInformation) {

	    String[] categoryInfo = categoryInformation.split(" ");

		description = categoryInfo[0];
		category1 = parseInt(categoryInfo[1]);
		category2 = parseInt(categoryInfo[2]);
		category3 = parseInt(categoryInfo[3]);
		category4 = parseInt(categoryInfo[4]);
		category5 = parseInt(categoryInfo[5]);

		// add values to ArrayList
		categoryValues.add(category1);
		categoryValues.add(category2);
		categoryValues.add(category3);
		categoryValues.add(category4);
		categoryValues.add(category5);
	}

	protected int findBestCategory() {
		/**
		 * When it is a computer player's turn, this method will be called which finds
		 * the highest value across the categories and returns the index it is assigned
		 * to.
		 *
		 * Sarah's note to team: I did have bestCategory as a class attribute but
		 * realised it didn't really need to be one as it is only accessed here. This
		 * also cannot be a private method as it makes most sense just to call the
		 * method from the GamePlay class when AI.
		 */
		int max = Collections.max(categoryValues);
		int bestCategory = categoryValues.indexOf(max) + 1;
		return bestCategory;
	}

	public String toString() {

		String cardDetails = categories[0] + ": " + category1 + " \n" + categories[1] + ": " + category2 + " \n"
				+ categories[2] + ": " + category3 + " \n" + categories[3] + ": " + category4 + " \n" + categories[4]
				+ ": " + category5 + " \n";

		return cardDetails;
	}

	public String chooseACategory() {

		String cardCategories = "1. " + category1 + "\n2. " + category2 + "\n3. " + category3 + "\n4. " + category4
				+ "\n5. " + category5 + "\n";
		return cardCategories;
	}

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

	public String getDescription() {
		return description;
	}

	/**
	 * sets the 5 categories for the deck and stores them as an array of strings in
	 * the correct order. Has to create a two arrays to remove the first entry which
	 * just reads 'description'
	 * 
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