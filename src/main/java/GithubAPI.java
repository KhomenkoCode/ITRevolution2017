package main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.google.gson.Gson;

public abstract class GithubAPI {

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
					sb.append(line.substring(1, line.length() - 1));
				}

				header = conn.getHeaderField("Link");
				if (header.lastIndexOf("rel=\"next\"") != -1)
					sb.append(",");
				++page;
			} while (header.lastIndexOf("rel=\"next\"") != -1);
			rd.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		sb.append(" ]");

		IssuesLabel[] labels = gson.fromJson(sb.toString(), IssuesLabel[].class);
		return labels;
	}

	public static Issue[] getPageOfIssues(String project, String label, String page, String state) {

		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;

		try {
			// issues?labels=Type:%20bug&state=all&per_page=100
			String urlString = "https://api.github.com/repos/" + project + "/issues?labels=" + label + "&state=" + state
					+ "&per_page=50&page=" + page;
			urlString = urlString.replaceAll(" ", "%20");
			url = new URL(urlString);

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			if ((line = rd.readLine()) != null) {
				sb.append(line);
			}

			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return gson.fromJson(sb.toString(), Issue[].class);
	}

	private static boolean findWordInLinkHeader(String urlString, String word) {
		StringBuilder sb = new StringBuilder();
		URL url;
		HttpURLConnection conn;
		String header = "";
		try {
			url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			header = conn.getHeaderField("Link");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (header != null)
			if (header.lastIndexOf(word) != -1)
				return true;
		return false;

	}

	public static boolean hasNextPage(String project, String label, String page, String state) {
		String urlString = "https://api.github.com/repos/" + project + "/issues?labels=" + label + "&state=" + state
				+ "&per_page=50&page=" + page;
		urlString = urlString.replaceAll(" ", "%20");

		return findWordInLinkHeader(urlString, "rel=\"next\"");
	}

	public static boolean hasPrevPage(String project, String label, String page, String state) {
		String urlString = "https://api.github.com/repos/" + project + "/issues?labels=" + label + "&state=" + state
				+ "&per_page=50&page=" + page;
		urlString = urlString.replaceAll(" ", "%20");

		return findWordInLinkHeader(urlString, "rel=\"prev\"");
	}

	public static Map<String, Vector<String>> parseLabelsNames(IssuesLabel[] labels) {
		Map<String, Vector<String>> result = new HashMap<String, Vector<String>>();
		String labelType;
		for (int i = 0; i < labels.length; i++) {

			if (labels[i] != null) {
				labelType = labels[i].getName();
				Vector<String> Subtypes = new Vector<>();
				int lastindexOf—olon = labelType.lastIndexOf(":");

				if (lastindexOf—olon != -1) {
					for (int j = 0; j < labels.length; j++)
						if (labels[j] != null)
							if (labels[j].getName().lastIndexOf(labelType.substring(0, lastindexOf—olon)) != -1)
								Subtypes.add(labels[j].getName()
										.substring(lastindexOf—olon + 1, labels[j].name.length()).trim());
					result.put(labelType.substring(0, lastindexOf—olon), Subtypes);
				} else
					result.put(labelType, null);

			}
		}

		return result;
	}

	public static Issue getSingleIssueByNum(String project, String number) {
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		try {
			String urlString = "https://api.github.com/repos/" + project + "/issues/" + number;
			urlString = urlString.replaceAll(" ", "%20");
			url = new URL(urlString);

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			if ((line = rd.readLine()) != null) {
				sb.append(line);
			}

			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return gson.fromJson(sb.toString(), Issue.class);
	}

	public static int getNumOfIssuesInLabel(String project, String label, String state) {
		Gson gson = new Gson();
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;

		int issuesPerPage = 30;
		int numOfIssues = 0;
		try {
			String header;
			int page = 1;
			String urlString = "https://api.github.com/repos/" + project + "/issues?labels=" + label + "&state=" + state
					+ "&per_page=" + issuesPerPage + "&page=" + page;
			urlString = urlString.replaceAll(" ", "%20");
			url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			header = conn.getHeaderField("Link");
			if (header != null) {

				if (header.contains("rel=\"last\"")) {
					String lastPageUrl = getLastLink(conn);
					int index = lastPageUrl.lastIndexOf("page=") + 5;
					System.out.println(lastPageUrl);
					page = Integer.parseInt(lastPageUrl.substring(index, lastPageUrl.length()));
				}
				numOfIssues += issuesPerPage*(page-1);

			}
			urlString = "https://api.github.com/repos/" + project + "/issues?labels=" + label + "&state=" + state
					+ "&per_page=" + issuesPerPage + "&page=" + page;
			urlString = urlString.replaceAll(" ", "%20");
			url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			IssuesLabel[] labels = gson.fromJson(rd.readLine(), IssuesLabel[].class);
			rd.close();
			numOfIssues += labels.length;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return numOfIssues;
	}

	static private String getLastLink(HttpURLConnection connection) {
		String link = connection.getHeaderField("Link"); // <http://foobar&gt;;
															// rel="next",
															// <http://blah/&gt;;
															// rel=last /
		if (link != null) {
			String[] links = link.split(",");
			for (String l : links) { // <http://foobar&gt;; rel="next" /
				if (l.contains("rel=\"last\"")) {
					String[] tmp1 = l.split("<", 2);
					if (tmp1.length == 2) {
						return tmp1[1].split(">", 2)[0]; // http://foobar /
					}
				}
			}
		}
		return null;
	}
}
