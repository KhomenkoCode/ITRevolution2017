package main.java;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/issues")
public class IssuesDemonstrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public IssuesDemonstrationServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Cookie[] cookies = request.getCookies();
		String accessToken = null;
		for(int i=0;i<cookies.length;i++)
			if(cookies[i].getName().equals("github_access_token"))
				accessToken = cookies[i].getValue();
		
		if (request.getParameter("project") == null || request.getParameter("label") == null || accessToken == null){
			response.sendRedirect("index");
			return;
		}
		String choosedLabel = request.getParameter("label");
		String project = request.getParameter("project");
		String page = request.getParameter("page");
		Issue[] pageOfIssues = null;

		

		if (page == null)
			page = "1";

		request.setAttribute("page", page);
		pageOfIssues = GithubAPI.getPageOfIssues(project, choosedLabel, page, "all",accessToken);
		
		request.setAttribute("hasNext", GithubAPI.hasNextPage(project, choosedLabel, page, "all",accessToken));
		request.setAttribute("hasPrev", GithubAPI.hasPrevPage(project, choosedLabel, page, "all",accessToken));
		
		
		
		request.setAttribute("project", project);
		request.setAttribute("label", choosedLabel);
		request.setAttribute("issues", pageOfIssues);
		response.setContentType("text/html");
		
		
		
		RequestDispatcher dispatcher = (RequestDispatcher) request.getRequestDispatcher("/IssuesInfo.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
