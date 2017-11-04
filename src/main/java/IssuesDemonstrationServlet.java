package main.java;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/issues")
public class IssuesDemonstrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public IssuesDemonstrationServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String choosedLabel = request.getParameter("label");
		String project = request.getParameter("project");
		
		Issue[] pageOfIssues = GithubAPI.getPageOfIssues(project, choosedLabel, 1, "all");
		
		request.setAttribute("project", project);
		request.setAttribute("label", choosedLabel);
		request.setAttribute("issues", pageOfIssues);
		response.setContentType("text/html");
		RequestDispatcher dispatcher = (RequestDispatcher) request.getRequestDispatcher("/IssuesInfo.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
