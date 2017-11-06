package main.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.GithubAPI;

@WebServlet("/labels")
public class LabelsDemonstrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LabelsDemonstrationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String project = request.getParameter("project");
		Cookie[] cookies = request.getCookies();
		String accessToken = null;
		for(int i=0;i<cookies.length;i++)
			if(cookies[i].getName().equals("github_access_token"))
				accessToken = cookies[i].getValue();
				
		if (project == null || accessToken == null) {
			response.sendRedirect("index");
			return;
		} else {
			
			IssuesLabel[] labels = GithubAPI.getAllLabels(project,accessToken);
			Map<String, Vector<String>> labelsByTypeMap = GithubAPI.parseLabelsNames(labels);

			request.setAttribute("project", request.getParameter("project"));

			if (request.getParameter("type") != null) {
				String choosedType = request.getParameter("type");

				if (labelsByTypeMap.get(choosedType) == null) {
					String labelForIssuesPage = "";
					for (int i = 0; i < labels.length; i++) {
						if (labels[i] != null)
							if (labels[i].getName().lastIndexOf(choosedType) != -1)
								labelForIssuesPage = labels[i].getName();
					}

					if (labelForIssuesPage.equals(""))
						response.sendRedirect("index");
					else
						response.sendRedirect("issues?project=" + project + "&label=" + labelForIssuesPage);
					return;
				}

				if (request.getParameter("subtype") != null) {
					String choosedSubtype = request.getParameter("subtype");
					String labelForIssuesPage = "";
					for (int i = 0; i < labels.length; i++) {
						if (labels[i] != null)
							if (labels[i].getName().lastIndexOf(choosedType) != -1
									&& labels[i].getName().lastIndexOf(choosedSubtype) != -1)
								labelForIssuesPage = labels[i].getName();
					}

					if (labelForIssuesPage.equals(""))
						response.sendRedirect("index");
					else
						response.sendRedirect("issues?project=" + project + "&label=" + labelForIssuesPage);
					return;
				}

				// Show Subtypes
				request.setAttribute("NumOfSubtypesIssuesList", getListOfIssuesNumberInSubtype(project, labels, labelsByTypeMap, choosedType, accessToken));
				request.setAttribute("choosedType", choosedType);
				request.setAttribute("subtypeLabels", labelsByTypeMap.get(choosedType));
			} else {
				// Show Types
				
				request.setAttribute("NumOfTypesIssuesList", getListOfIssuesNumberInType(project, labels, labelsByTypeMap,accessToken));
				request.setAttribute("typeLabels", labelsByTypeMap);
			}
			
			response.setContentType("text/html");
			RequestDispatcher dispatcher = (RequestDispatcher) request.getRequestDispatcher("/LabelsInfo.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private ArrayList<Integer> getListOfIssuesNumberInType(String project, IssuesLabel[] labels,
			Map<String, Vector<String>> labelsByTypeMap, String accessToken) {

		ArrayList<Integer> resultList = new ArrayList<Integer>();
		for (Map.Entry<String, Vector<String>> entry : labelsByTypeMap.entrySet()) {
			int tmpNumber = 0;
			for (int i = 0; i < labels.length; i++)
				if (labels[i].getName().contains(entry.getKey()))
					tmpNumber += GithubAPI.getNumOfIssuesInLabel(project, labels[i].getName(), "all", accessToken);
			resultList.add(tmpNumber);
		}

		return resultList;
	}

	private ArrayList<Integer> getListOfIssuesNumberInSubtype(String project, IssuesLabel[] labels,
			Map<String, Vector<String>> labelsByTypeMap, String choosedType, String accessToken) {

		ArrayList<Integer> resultList = new ArrayList<Integer>();
		Vector<String> subtypeVector = labelsByTypeMap.get(choosedType);
		for (String entry : subtypeVector) {
			int tmpNumber = 0;
			for (int i = 0; i < labels.length; i++)
				if (labels[i].getName().contains(entry) && labels[i].getName().contains(choosedType))
					resultList.add(GithubAPI.getNumOfIssuesInLabel(project, labels[i].getName(), "all", accessToken));
		}

		return resultList;
	}

}
