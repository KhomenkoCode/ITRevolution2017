package main.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class GithubAPI {
	public static IssuesLabel[] getAllLabels(String project) {
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		try {
			String header;
			int page = 1;
			sb.append("[ ");
			do {
				url = new URL("https://api.github.com/repos/" + project + "/labels?page=" + page);
				
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				
				if ((line = rd.readLine()) != null) {
					sb.append(line.substring(1,line.length()-1));
				}
				sb.append(",");
				header = conn.getHeaderField("Link");
				++page;
			} while (header.lastIndexOf("rel=\"next\"") != -1);
			rd.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sb.append(" ]");
		System.out.println(sb.toString());
	
		IssuesLabel[] labels = gson.fromJson(sb.toString(), IssuesLabel[].class);
		return labels;
	}
}
