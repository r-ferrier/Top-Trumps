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
//    private int bestCategory;
    private String description;

    public Card(String CategoryInformation){
        setCategories(CategoryInformation);
    }

    private void setCategories(String categoryInformation){
        String[] cardDetails = categoryInformation.split(" ");
        description = cardDetails[0];
        category1 = parseInt(cardDetails[1]);
        category2 = parseInt(cardDetails[2]);
        category3 = parseInt(cardDetails[3]);
        category4 = parseInt(cardDetails[4]);
        category5 = parseInt(cardDetails[5]);
    }

    protected int findBestCategory(){
        /**
         * When it is a computer player's turn, this method will be called which finds the highest value
         * across the categories and returns the index it is assigned to.
         *
         * Sarah's note to team: I did have bestCategory as a class attribute but realised it didn't really
         * need to be one as it is only accessed here. This also cannot be a private method as it makes most sense
         * just to call the method from the GamePlay class when AI.
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
    public String getDescription(){
        return getDescription();
    }
    public int getCategory1(){
        return category1;
    }

    public int getCategory2() {
        return category2;
    }

    public int getCategory3() {
        return category3;
    }

    public int getCategory4() {
        return category4;
    }

    public int getCategory5() {
        return category5;
    }
}
