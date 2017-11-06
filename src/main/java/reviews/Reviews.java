package main.java.reviews;

import java.util.ArrayList;

public class Reviews {

    public static ArrayList<Review> reviews = new ArrayList<>();


    public static class Review{

        public Review(String nameField, String textField, int givenRating) {
            this.nameField = nameField;
            this.textField = textField;
            this.givenRating = givenRating;
        }

        String nameField;
        String textField;
        int givenRating;

        public int getGivenRating() {
            return givenRating;
        }

        public void setGivenRating(int givenRating) {
            this.givenRating = givenRating;
        }

        public String getNameField() {
            return nameField;
        }

        public void setNameField(String nameField) {
            this.nameField = nameField;
        }

        public String getTextField() {
            return textField;
        }

        public void setTextField(String textField) {
            this.textField = textField;
        }
    }


}
