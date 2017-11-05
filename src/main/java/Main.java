package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {



    public static void main(String[] args) {
        HashMap<String,String> map;
        System.out.println("Hello World!");
        String repo =  "facebook/react";


        
        HashMap<String,ArrayList<String>> res = Calculations.findRelevantPRandIssue(repo,String.valueOf(11442));
        
        System.out.println(GithubAPI.isProjectExist(repo));
        System.out.println(GithubAPI.isLabelExist(repo,"bug"));

        map = Calculations.calculateFI(repo);

        for (Map.Entry entry : map.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
        map = Calculations.calculateDF(repo);

        for (Map.Entry entry : map.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }

        System.out.println("Hello World!");
    }
}
