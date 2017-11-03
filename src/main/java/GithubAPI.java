package main.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import com.google.gson.Gson;

public class GithubAPI {
	public static IssuesLabel[] getAllLabels(String project) {
		Gson gson = new Gson();

		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String jsonString = "";
		try {
			url = new URL("https://api.github.com/repos/" + project + "/labels");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				jsonString += line;
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		IssuesLabel[] labels = gson.fromJson(jsonString, IssuesLabel[].class);
		return labels;
	}
}
