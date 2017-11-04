package main.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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
					sb.append(line.substring(1, line.length() - 1));
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

		IssuesLabel[] labels = gson.fromJson(sb.toString(), IssuesLabel[].class);
		return labels;
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
								Subtypes.add(
										labels[j].getName().substring(lastindexOf—olon + 1, labels[j].name.length()).trim());
					result.put(labelType.substring(0, lastindexOf—olon), Subtypes);
				} else
					result.put(labelType, null);

			}
		}

		return result;
	}
}
