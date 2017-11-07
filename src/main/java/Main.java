package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {



    public static void main(String[] args) {
        HashMap<String,String> map;
        System.out.println("Hello World!");
        String repo =  "ddci/vkfilebot";

        HashMap<String,ArrayList<String>> res = Calculations.findRelevantPRandIssue(repo,String.valueOf(11442),"myaccesstoken" );
        
        System.out.println(GithubAPI.isProjectExist(repo,"myaccesstoken"));
        System.out.println(GithubAPI.isLabelExist(repo,"Resolution: Need More Information","myaccesstoken"));

        map = Calculations.calculateFI(repo,"myaccesstoken");

        for (Map.Entry entry : map.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }


        map = Calculations.calculateDF(repo,"myaccesstoken");

        for (Map.Entry entry : map.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }

        System.out.println("Hello World!");
    }
}
