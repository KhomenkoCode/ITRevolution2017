package main.java.reviews;

import java.util.ArrayList;

public class Reviews {
    public static double getAverageRating() {
        return averageRating;
    }

    public static void setAverageRating(double averageRating) {
        Reviews.averageRating = averageRating;
    }

    static double averageRating;

    public static ArrayList<Review> reviews = new ArrayList<>();


    public static void evaluateAverageRating(){
        double sum = 0.0;
        for (Review rev:reviews) {
            sum += rev.givenRating;
        }
        averageRating = sum/reviews.size();
    }
    public static class Review{
        String nameField;
        String textField;
        int givenRating;

        public Review(String nameField, String textField, int givenRating) {
            this.nameField = nameField;
            this.textField = textField;
            this.givenRating = givenRating;
        }



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
