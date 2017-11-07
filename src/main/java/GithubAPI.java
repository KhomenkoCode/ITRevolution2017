package main.java;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ArrayList;

import com.google.gson.Gson;

public abstract class GithubAPI {
	private static final String clientID = "737d83576351a46442c7";
	private static final String clientSecret = "436307c777c140fcda1b53e12847d2b34bd58360";

	static IssuesLabel[] getAllLabels(String project, String accessToken) {
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
				url = new URL("https://api.github.com/repos/" + project + "/labels?page=" + page + "&" + accessToken);

				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));

				if ((line = rd.readLine()) != null) {
					sb.append(line.substring(1, line.length() - 1));
				}

				header = conn.getHeaderField("Link");
				if (header == null)
					break;
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

	public static Issue[] getPageOfIssues(String project, String label, String page, String state, String accessToken) {

		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;

		try {
			// issues?labels=Type:%20bug&state=all&per_page=100
			String urlString = "https://api.github.com/repos/" + project + "/issues?labels="
					+ java.net.URLEncoder.encode(label, "UTF-8") + "&state=" + state + "&per_page=50&page=" + page + "&"
					+ accessToken;

			url = new URL(urlString);

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));

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

	public static boolean hasNextPage(String project, String label, String page, String state, String accessToken) {
		String urlString = null;
		try {
			urlString = "https://api.github.com/repos/" + project + "/issues?labels="
					+ java.net.URLEncoder.encode(label, "UTF-8") + "&state=" + state + "&per_page=50&page=" + page + "&"
					+ accessToken;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		urlString = urlString.replaceAll(" ", "%20");

		return findWordInLinkHeader(urlString, "rel=\"next\"");
	}

	public static boolean hasPrevPage(String project, String label, String page, String state, String accessToken) {
		String urlString = null;
		try {
			urlString = "https://api.github.com/repos/" + project + "/issues?labels="
					+ java.net.URLEncoder.encode(label, "UTF-8") + "&state=" + state + "&per_page=50&page=" + page + "&"
					+ accessToken;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		urlString = urlString.replaceAll(" ", "%20");

		return findWordInLinkHeader(urlString, "rel=\"prev\"");
	}

	public static Map<String, ArrayList<String>> parseLabelsNames(IssuesLabel[] labels) {
		Map<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
		String labelType;
		for (int i = 0; i < labels.length; i++) {

			if (labels[i] != null) {
				labelType = labels[i].getName();
				ArrayList<String> Subtypes = new ArrayList<>();
				int lastIndexOfColon = labelType.lastIndexOf(":");

				if (lastIndexOfColon != -1) {
					for (int j = 0; j < labels.length; j++)
						if (labels[j] != null)
							if (labels[j].getName().lastIndexOf(labelType.substring(0, lastIndexOfColon)) != -1)
								Subtypes.add(labels[j].getName()
										.substring(lastIndexOfColon + 1, labels[j].name.length()).trim());
					result.put(labelType.substring(0, lastIndexOfColon), Subtypes);
				} else
					result.put(labelType, null);

			}
		}

		return result;
	}

	public static Issue getSingleIssueByNum(String project, String number, String accessToken) {
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		try {
			String urlString = "https://api.github.com/repos/" + project + "/issues/" + number + "&" + accessToken;
			urlString = urlString.replaceAll(" ", "%20");
			url = new URL(urlString);

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));

			if ((line = rd.readLine()) != null) {
				sb.append(line);
			}

			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return gson.fromJson(sb.toString(), Issue.class);
	}

	public static int getNumOfIssuesInLabel(String project, String label, String state, String accessToken) {
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;

		int issuesPerPage = 1;
		int numOfIssues = 0;
		try {
			String header;
			int page = 1;
			String urlString = "https://api.github.com/repos/" + project + "/issues?labels="
					+ java.net.URLEncoder.encode(label, "UTF-8") + "&state=" + state + "&per_page=" + issuesPerPage
					+ "&page=" + page + "&" + accessToken;
			// urlString = urlString.replaceAll(" ", "%20");
			url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			header = conn.getHeaderField("Link");
			if (header != null) {
				if (header.contains("rel=\"last\"")) {
					String lastPageUrl = getLastLink(conn);
					int index = lastPageUrl.lastIndexOf("page=") + 5;
					int index2 = lastPageUrl.lastIndexOf("&access_token");
					page = Integer.parseInt(lastPageUrl.substring(index, index2));
				}
				numOfIssues += issuesPerPage * (page);
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
				if (rd.readLine() != null)
					numOfIssues = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return numOfIssues;
	}

	static boolean isLabelExist(String repo, String label, String accessToken) {
		Gson gson = new Gson();
		String jsonString = null;
		try {
			jsonString = getJsonStringAllLabels(repo, accessToken);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		if ((jsonString == null) || (Objects.equals(jsonString, ""))) {
			return false;
		}

		IssuesLabel[] labels = gson.fromJson(jsonString, IssuesLabel[].class);
		for (IssuesLabel el : labels) {
			String lab = el.getName();
			if (Objects.equals(lab, label)) {
				return true;
			}
		}
		return false;
	}

	static boolean isProjectExist(String repo, String accessToken) {
		StringBuilder sb = new StringBuilder();
		URL url;
		HttpURLConnection conn;
		String jsonString = "";
		String urlString = "https://api.github.com/repos/" + repo + "?" + accessToken;
		String name = repo.substring(repo.lastIndexOf("/") + 1);
		try {
			url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			getFromConn(sb, conn);
		} catch (IOException e) {
			// This also handles 404 error
			// e.printStackTrace();
			return false;
		}
		if (sb.toString().equals("")) {
			return false;

		}
		sb.insert(0, "{");
		sb.append("}");
		Gson gson = new Gson();
		RepoResponse r = gson.fromJson(sb.toString(), RepoResponse.class);
		return Objects.equals(r.name, name);
	}

	@Deprecated
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	static String getJsonStringAllLabels(String project, String accessToken) throws FileNotFoundException {
		StringBuilder sb = new StringBuilder();
		URL url;
		HttpURLConnection conn;
		try {
			String header;
			int page = 1;
			sb.append("[ ");
			do {
				String urlString = "https://api.github.com/repos/" + project + "/labels?page=" + page + "&"
						+ accessToken;
				url = new URL(urlString);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				getFromConn(sb, conn);
				header = conn.getHeaderField("Link");
				if (header != null) {
					if (header.lastIndexOf("rel=\"next\"") != -1)
						sb.append(",");
					++page;
				}
			} while (header != null && header.lastIndexOf("rel=\"next\"") != -1);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		sb.append(" ]");

		return sb.toString();
	}

	static String requestAPI(String project, String label,String accessToken) throws IOException, FileNotFoundException {
		StringBuilder sb = new StringBuilder();
		URL url;
		HttpURLConnection conn;
		String jsonString = "";
		final String PER_PAGE = "100";
		final String STATE = "closed";
		String urlString = "https://api.github.com/repos/" + project + "/issues?labels="
				+ java.net.URLEncoder.encode(label, "UTF-8") + "&state=" + STATE + "&per_page=" + PER_PAGE
				+"&" + accessToken;
		// urlString = urlString.replaceAll(" ", "%20");
		url = new URL(urlString);
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		getFromConn(sb, conn);
		if (getLastLink(conn) != null && !sb.toString().equals("")) {
			String link = getLastLink(conn);
			if (link != null)
				url = new URL(link);
			else
				return jsonString;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			sb.substring(0, sb.length() - 2);
			sb.append(",");
			StringBuilder lastSb = new StringBuilder();
			getFromConn(lastSb, conn);
			if (!lastSb.toString().equals("")) {
				lastSb.substring(0, lastSb.length());
			}
			sb.append(lastSb);
		}
		sb.insert(0, "[");
		sb.append("]");
		jsonString = sb.toString();
		return jsonString;
	}

	static String requestAPIwithoutlabel(String repo,String accessToken) throws IOException, FileNotFoundException {
		URL url;
		HttpURLConnection conn;
		StringBuilder sb = new StringBuilder("");
		String jsonString = "";

		final String PER_PAGE = "100";
		final String STATE = "closed";

		url = new URL("https://api.github.com/repos/" + repo + "/issues?state=" + STATE + "&per_page=" + PER_PAGE
                +"&" + accessToken);
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		String header = conn.getHeaderField("Link");
		getFromConn(sb, conn);

		if (getLastLink(conn) != null && !sb.toString().equals("")) {
			String link;
			if ((link = getLastLink(conn)) != null) {
				url = new URL(link);
			} else
				return jsonString;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			sb.substring(0, sb.length() - 2);
			sb.append(",");
			StringBuilder lastSb = new StringBuilder();
			getFromConn(lastSb, conn);
			if (!lastSb.toString().equals("")) {
				lastSb.substring(0, lastSb.length());
			}
			sb.append(lastSb);
		}
		sb.insert(0, "[");
		sb.append("]");
		jsonString = sb.toString();
		return jsonString;
	}

	static private void getFromConn(StringBuilder sb, HttpURLConnection conn)
			throws IOException, FileNotFoundException {

		try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"))) {
			String line;
			if ((line = rd.readLine()) != null) {
				sb.append(line.substring(1, line.length() - 1));
			}
		} catch (Exception e) {
			if (e instanceof FileNotFoundException)
				throw e;
			e.printStackTrace();
		}

	}

	static private String getNextLink(HttpURLConnection connection) {
		String link = connection.getHeaderField("Link"); // <http://foobar&gt;;
															// rel="next",
															// <http://blah/&gt;;
															// rel=last /
		if (link != null) {
			String[] links = link.split(",");
			for (String l : links) { // <http://foobar&gt;; rel="next" /
				if (l.contains("rel=\"next\"")) {
					String[] tmp1 = l.split("<", 2);
					if (tmp1.length == 2) {
						return tmp1[1].split(">", 2)[0]; // http://foobar /
					}
				}
			}
		}
		return null;
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

	/**
	 * Getting issue from API by using given number
	 * 
	 * @param repo
	 * @param issueNumber
	 * @return
	 * @throws IOException
	 */
	static String getIssuebyNumberRequest(String repo, String issueNumber, String accessToken) throws IOException {
		URL url;
		HttpURLConnection conn;
		StringBuilder sb = new StringBuilder("");
		String jsonString = "";
		url = new URL("https://api.github.com/repos/" + repo + "/issues/" + issueNumber + "&" + accessToken);
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		// String header = conn.getHeaderField("Link");
		getFromConn(sb, conn);
		if (getLastLink(conn) != null && !sb.toString().equals("")) {
			String link;
			if ((link = getLastLink(conn)) != null)
				url = new URL(link);
			else
				return jsonString;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			sb.substring(0, sb.length() - 2);
			sb.append(",");
			StringBuilder lastSb = new StringBuilder();
			getFromConn(lastSb, conn);
			if (!lastSb.toString().equals("")) {
				lastSb.substring(0, lastSb.length());
			}
			sb.append(lastSb);
		}
		sb.insert(0, "{");
		sb.append("}");
		jsonString = sb.toString();
		return jsonString;
	}

	static String changeSpecialSymbolsinStringToTheirCodes(String stringToChange) {
		stringToChange = stringToChange.replaceAll("<", "&#60;");
		stringToChange = stringToChange.replaceAll(">", "&#62;");
		stringToChange = stringToChange.replaceAll("\"", "&#34;");
		return stringToChange;
	}

	static Issue changeSpecialSymbolsinIssue(Issue issueToChange) {
		issueToChange.setTitle(changeSpecialSymbolsinStringToTheirCodes(issueToChange.getTitle()));
		issueToChange.setBody(changeSpecialSymbolsinStringToTheirCodes(issueToChange.getBody()));
		return issueToChange;
	}

	static Issue[] changeSpecialSymbolsinArrayOfIssues(Issue[] arrayOfIssueToChange) {
		for (int i = 0; i < arrayOfIssueToChange.length; i++)
			arrayOfIssueToChange[i] = changeSpecialSymbolsinIssue(arrayOfIssueToChange[i]);
		return arrayOfIssueToChange;
	}

	static String getAccessToken(String tmpCode) {
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line = "";

		try {
			url = new URL("https://github.com/login/oauth/access_token?client_id=" + clientID + "&client_secret="
					+ clientSecret + "&code=" + tmpCode + "&accept=json");

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));

			line = rd.readLine();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}
}
