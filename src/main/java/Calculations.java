package main.java;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class Calculations {

    /** Takes repo and preferred label and time in minutes to omit some issues
     *  and gets first 100  and last (1-100) issues with first label
     *  contains word given word.
     *  Then calculates time passed between opening and closing
     *  and evaluates ratio for given repo.
     *  We assume that if bug/issue was closed within given time in minutes this issue ​
     *  was​​ closed​​ due​​ to​​ invalid​​ requirements​​ for the​​ feature​​ etc.,
     *  therefore we omit it from evaluations.
     * @param repo
     * @param preferredLabelName
     * @param minutes
     *
     * @return map of ratio,max etc.
     */
    private static HashMap<String,String> baseCalculation(String repo, String preferredLabelName, int minutes,String accessToken){
        Gson gson = new Gson();
        String jsonString = null;
        try {
            jsonString = GithubAPI.getJsonStringAllLabels(repo,accessToken);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        if (Objects.equals(jsonString, "")){
            return null;
        }
        ArrayList<IssuesLabel> bugLabels = new ArrayList<>();

        IssuesLabel[] labels = gson.fromJson(jsonString, IssuesLabel[].class);
        for (IssuesLabel el:labels) {
            String lab = el.getName();
            if (lab.toLowerCase().contains(preferredLabelName.toLowerCase())){
                bugLabels.add(el);
            }
        }
        if (bugLabels.size()==0){
            return null;
        }
        try {
            jsonString = GithubAPI.requestAPI(repo,bugLabels.get(0).getName(),accessToken);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (Objects.equals(jsonString, "")){
            return null;
        }
        Issue[] issues = gson.fromJson(jsonString, Issue[].class);
        Issue i = new Issue("dwd","ddw");
        for (Issue el:issues) {
            el.setValidIssue(true);
            el.calculateTime();
            if(el.isValidIssue()){
                if(el.minutesBetween<=minutes) {
                    el.setValidIssue(false);}
            }
        }
        ArrayList<Integer> validIssues =  new ArrayList<>();

        for (Issue el:issues) {
            if(el.isValidIssue()){
                validIssues.add(el.daysBetween);
            }
        }
        //Calculate ratio using our formula
        HashMap<String,String> map = null;
        if (validIssues.size()!=0) {
            int basedOnCountIssues = validIssues.size();
            double sumOfAll = validIssues.stream().reduce(0, (a, b) -> a + b);
            int max = Collections.max(validIssues);
            int min = Collections.min(validIssues);
            double average = sumOfAll/basedOnCountIssues;
            //Ratio
            double percentage = ((average-min) * 100) / (max-min);
            double ratio = 100-percentage;


            map = new HashMap<>();
            map.put("amount", String.valueOf(basedOnCountIssues));
            map.put("max",String.valueOf(max));
            map.put("min",String.valueOf(min));
            map.put("average",String.valueOf((int)average));
            if (sumOfAll==0) map.put("ratio",String.valueOf((100)));
            else map.put("ratio",String.valueOf((int)ratio));
        }
        return map;
    }
    static HashMap<String,String> calculateFI(String repo,String accessToken){
        HashMap<String,String> map = baseCalculation(repo,"feature",15,accessToken);
        if (map==null){
            System.out.println("enchancment");
            map = baseCalculation(repo,"enhancement",15,accessToken);
        }
        if (map==null) {
            map = baseCalculationNoLabel(repo,accessToken);
        System.out.println("base calculation");}
        return map;
    }
    static HashMap<String,String> calculateDF(String repo,String accessToken){
        HashMap<String,String> map = baseCalculation(repo,"bug",25,accessToken);
        if (map==null){
            map = baseCalculation(repo,"defect",15,accessToken);
            System.out.println("bug");
        }
        if (map==null){
            map = baseCalculation(repo,"problem",15,accessToken);
            System.out.println("problem");
        }
        if (map==null){
            map = baseCalculationNoLabel(repo,accessToken);
            System.out.println("base calculation");}
        return map;
    }




    
    private static HashMap<String,String> baseCalculationNoLabel(String repo,String accessToken){
        Gson gson = new Gson();
        String jsonString = "";
        try {
            jsonString = GithubAPI.requestAPIwithoutlabel(repo,accessToken);
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
        if (Objects.equals(jsonString, "")){
            // TODO: 2017-11-04 //wrap
            return null;
        }
        Issue[] issues = gson.fromJson(jsonString, Issue[].class);
        for (Issue el:issues) {
            el.setValidIssue(true);
            el.calculateTime();
        }
        ArrayList<Integer> validIssues =  new ArrayList<>();

        for (Issue el:issues) {
            if(el.isValidIssue()){
                validIssues.add(el.daysBetween);
            }
        }
        //Calculate ratio using our formula

        HashMap<String,String> map = null;
        if (validIssues.size()!=0) {
            int basedOnCountIssues = validIssues.size();
            int sumOfAll = validIssues.stream().reduce(0, (a, b) -> a + b);
            int max = Collections.max(validIssues);
            int min = Collections.min(validIssues);
            double average = sumOfAll/basedOnCountIssues;
            //Ratio
            double percentage = ((average-min) * 100) / (max-min);
            double ratio = 100-percentage;
            map = new HashMap<>();
            map.put("amount", String.valueOf(basedOnCountIssues));
            map.put("max",String.valueOf(max));
            map.put("min",String.valueOf(min));
            map.put("average",String.valueOf((int)average));
            if (sumOfAll==0) map.put("ratio",String.valueOf((100)));
            else map.put("ratio",String.valueOf((int)ratio));
        }
        return map;
    }


    /** Takes repo and issue number as parameters.
     * First func tries to resolve PR from Issue (so if this issue is a pull
     * request we will get link to this PR).
     * Then we try to find all links that were mentioned in body of issue.
     * If there is any URL we will define if it belongs to PR,issues or do not belongs
     * to any of it at all.
     * @param repo
     * @param issueNumber
     * @return found as a result we return HashMap with Key - string (pr or issue)
     * and Value - array of all (related,relevant) mentioned links.
     */
   static HashMap<String,ArrayList<String>> findRelevantPRandIssue(String repo,String issueNumber,String accessToken){
       final Pattern urlPattern = Pattern.compile(
               "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                       + "(([\\w\\-]+\\.){1,}?([\\w\\-~]+\\/?)*"
                       + "[\\p{Alnum}\\-+()\\[\\]\\*$~@!:/{};']*)",// ☦ спаси сохрани от regexp`ов ☦
               Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

        HashMap<String, ArrayList<String>> found = new HashMap<>();
        ArrayList<String> prsArray = new ArrayList<>();
        ArrayList<String> issuesArray = new ArrayList<>();
        Gson gson = new Gson();
        String jsonString = "";
        try {
            jsonString = GithubAPI.getIssuebyNumberRequest(repo,issueNumber,accessToken);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if ((jsonString == null) || (Objects.equals(jsonString, ""))){
            return null;
        }
        Issue issue = gson.fromJson(jsonString, Issue.class);
        if (issue==null) return null;
        if (issue.pullRequest!=null){
            String sub = issue.pullRequest.html_url.substring(issue.pullRequest.html_url.lastIndexOf("/pull/")+6);
            prsArray.add(sub);//here
        }
        if(issue.body!=null && !Objects.equals(issue.body, "")){
            Matcher matcher = urlPattern.matcher(issue.body);
            while (matcher.find()) {
                int matchStart = matcher.start(1);
                int matchEnd = matcher.end();
                String sub = issue.body.substring(matchStart,matchEnd);
                //System.out.println(sub);
                if(sub.contains("/pull/")){
                    if(prsArray.isEmpty()) prsArray.add(sub.substring(sub.lastIndexOf("/pull/")+6));
                    String add = null;
                    for (String s:prsArray) {
                        if(!s.contains(sub.substring(sub.lastIndexOf("/pull/")+6))) {
                            add = sub.substring(sub.lastIndexOf("/pull/")+6);
                        }
                    }
                    if(add != null){
                        prsArray.add(add);
                    }
                }
                if(sub.contains("/issues/")){
                    if(issuesArray.isEmpty()) issuesArray.add(sub.substring(sub.lastIndexOf("/issues/")+8));
                    String add = null;
                    for (String s:issuesArray) {
                        if(!s.contains(sub.substring(sub.lastIndexOf("/issues/")+8))) {
                            add = sub.substring(sub.lastIndexOf("/issues/")+8);
                        }
                    }
                    if(add != null){
                        issuesArray.add(add);
                    }
                }
            }
        }

       if (prsArray.size()!=0) {
           for (int i = 0; i < prsArray.size(); i++) {
               String str = prsArray.get(i);
               if (str.contains("/")) {
                   int firstIndex = str.indexOf("/");
                   if (firstIndex != -1 && firstIndex != 0) {
                       prsArray.set(i,str.substring(0, firstIndex));
                   }
               }
               if (str.contains(")")) {
                   int firstIndex = str.indexOf(")");
                   if (firstIndex != -1 && firstIndex != 0) {
                       prsArray.set(i,str.substring(0, firstIndex));
                   }
               }
           }
           found.put("pull_requests",prsArray);
       }

       if (issuesArray.size()!=0) {
           for (int i = 0; i < issuesArray.size(); i++) {
               String str = issuesArray.get(i);
               if (str.contains("/")) {
                   int firstIndex = str.indexOf("/");
                   if (firstIndex != -1 && firstIndex != 0) {
                       issuesArray.set(i,str.substring(0, firstIndex));
                   }
               }
               if (str.contains(")")) {
                   int firstIndex = str.indexOf(")");
                   if (firstIndex != -1 && firstIndex != 0) {
                       issuesArray.set(i,str.substring(0, firstIndex));
                   }
               }
           }
           found.put("issues",issuesArray);
       }
        if (prsArray.size()!=0) found.put("pull_requests",prsArray);
        if (issuesArray.size()!=0) found.put("issues",issuesArray);
        if(!found.isEmpty()) return found;
        else return null;
    }



}
