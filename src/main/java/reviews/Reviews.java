package main.java.reviews;

import java.util.ArrayList;
import java.util.HashMap;

public class Reviews {

    static public double getAverageRating(String project) {
        return averageRating.getOrDefault(project, 0.0);
    }
    static HashMap<String, ArrayList<Review>> getProjects() {
        return projects;
    }

    private static HashMap<String,Double> averageRating = new HashMap<>();

    public static HashMap<String,ArrayList<Review>> projects = new HashMap<>();
   // public static ArrayList<Review> reviews = new ArrayList<>();



    public static void evaluateAverageRating(String project){

        if (!project.isEmpty()) {

            if (projects.containsKey(project)) {
                double sum = 0.0;
                for (Review rev:projects.get(project)) {
                    sum += rev.givenRating;
                }
                if (projects.get(project)!=null){
                    averageRating.put(project, (sum / projects.get(project).size()));
                }
            }

        }
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
