package commandline;

import java.util.ArrayList;
import java.util.Collections;
import static java.lang.Integer.parseInt;

public class Card {
	private int category1;
	private int category2;
	private int category3;
	private int category4;
	private int category5;
	private String description;
	private String[] categories = new String[5];

	public Card(String CategoryInformation, String categoryDescriptions) {

		setCategories(CategoryInformation);
		setCategoryDescriptions(categoryDescriptions);

	}

	private void setCategories(String categoryInformation) {
		String[] cardDetails = categoryInformation.split(" ");
		description = cardDetails[0];
		category1 = parseInt(cardDetails[1]);
		category2 = parseInt(cardDetails[2]);
		category3 = parseInt(cardDetails[3]);
		category4 = parseInt(cardDetails[4]);
		category5 = parseInt(cardDetails[5]);
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

}