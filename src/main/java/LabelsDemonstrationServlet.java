package main.java;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.GithubAPI;
import main.java.reviews.Reviews;

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

        if (cookies!=null) {
            for(int i=0;i<cookies.length;i++)
                if(cookies[i].getName().equals("github_access_token"))
                    accessToken = cookies[i].getValue();
        }

        if (project == null || accessToken == null) {
			response.sendRedirect("index");
		} else {
            IssuesLabel[] labels = GithubAPI.getAllLabels(project,accessToken);
			Map<String, ArrayList<String>> labelsByTypeMap = GithubAPI.parseLabelsNames(labels);
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
                
                
                ArrayList<String> resultSet = new  ArrayList<String>();
                ArrayList<Integer> ListOfIssuesNumberInSubtype = getListOfIssuesNumberInSubtype(project, labels, labelsByTypeMap, choosedType, accessToken);
                ArrayList<String> subtypes = labelsByTypeMap.get(choosedType);
                
                Iterator<String> it = subtypes.iterator();
				Iterator<Integer> it2 = ListOfIssuesNumberInSubtype.iterator();
				while (it.hasNext()) {
				   
				    resultSet.add(it.next());
				    resultSet.add(it2.next().toString());
				}
                
				request.setAttribute("NumOfSubtypesIssuesList", resultSet);
				request.setAttribute("choosedType", choosedType);
				//request.setAttribute("subtypeLabels", );
			} else {
				// Show Types
				ArrayList<String> resultSet = new  ArrayList<String>();
				ArrayList<Integer> numOfIssuesInTypes =	getListOfIssuesNumberInType(project, labels, labelsByTypeMap,accessToken);
				
				Iterator<Entry<String, ArrayList<String>>> it = labelsByTypeMap.entrySet().iterator();
				Iterator<Integer> it2 = numOfIssuesInTypes.iterator();
				while (it.hasNext()) {
				    Entry<String, ArrayList<String>> pair = it.next();
				   
				    resultSet.add(pair.getKey());
				    resultSet.add(it2.next().toString());
				}
				request.setAttribute("NumOfTypesIssuesList", resultSet);
				//request.setAttribute("typeLabels", labelsByTypeMap);
			}
            //END LABEL LOGIC

            //BEGIN FI,DF,Contributors LOGIC

            //FI
            request.setAttribute("mapFIResults",Calculations.calculateFI(project,accessToken));
			//DF
			request.setAttribute("mapDFResults",Calculations.calculateDF(project,accessToken));
			//END FI,DF Contributors LOGIC

            //BEGIN REVIEW LOGIC
            //Evaluate Average
            Reviews.evaluateAverageRating(project);
            request.setAttribute("reviews", Reviews.projects.get(project));
            request.setAttribute("average_rating_on_reviews", Reviews.getAverageRating(project));
            //END REVIEW LOGIC

            response.setContentType("text/html; charset=UTF-8");

			response.setContentType("text/html");
			RequestDispatcher dispatcher = (RequestDispatcher) request.getRequestDispatcher("/LabelsInfo.jsp");
			dispatcher.forward(request, response);
		}//END OF ELSE

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        //BEGIN REVIEW LOGIC
        String project = request.getParameter("project");
        String textField = request.getParameter("review_text");
        String name = request.getParameter("name");
        int givenRating = Integer.parseInt(request.getParameter("star"));
        if((!Objects.equals(textField, "")) && (!Objects.equals(name, "")) && (givenRating <=5 && givenRating>=1)){
            if (Reviews.projects.containsKey(project)) {
                Reviews.projects.get(project).add(new Reviews.Review(name,textField,givenRating));
            }else {
                Reviews.projects.computeIfAbsent(project, k -> new ArrayList<>())
                        .add(new Reviews.Review(name,textField,givenRating));
            }
        }
        //END REVIEW LOGIC
		doGet(request, response);
	}

	private ArrayList<Integer> getListOfIssuesNumberInType(String project, IssuesLabel[] labels,
			Map<String, ArrayList<String>> labelsByTypeMap, String accessToken) {

		ArrayList<Integer> resultList = new ArrayList<Integer>();
		for (Map.Entry<String, ArrayList<String>> entry : labelsByTypeMap.entrySet()) {
			int tmpNumber = 0;
			for (int i = 0; i < labels.length; i++)
				if (labels[i].getName().contains(entry.getKey()))
					tmpNumber += GithubAPI.getNumOfIssuesInLabel(project, labels[i].getName(), "all", accessToken);
			resultList.add(tmpNumber);
		}

		return resultList;
	}

	private ArrayList<Integer> getListOfIssuesNumberInSubtype(String project, IssuesLabel[] labels,
			Map<String, ArrayList<String>> labelsByTypeMap, String choosedType, String accessToken) {

		ArrayList<Integer> resultList = new ArrayList<Integer>();
		ArrayList<String> subtypeArrayList = labelsByTypeMap.get(choosedType);
		for (String entry : subtypeArrayList) {
			int tmpNumber = 0;
			for (int i = 0; i < labels.length; i++)
				if (labels[i].getName().contains(entry) && labels[i].getName().contains(choosedType))
					resultList.add(GithubAPI.getNumOfIssuesInLabel(project, labels[i].getName(), "all", accessToken));
		}

		return resultList;
	}

}
