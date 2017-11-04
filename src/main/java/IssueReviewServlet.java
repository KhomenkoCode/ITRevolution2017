package main.java;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/issue")
public class IssueReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public IssueReviewServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("project") == null || request.getParameter("num") == null){
			response.sendRedirect("index");
			return;
		}
		
		String project = request.getParameter("project");
		String num = request.getParameter("num");
		
		Issue currentIssue = GithubAPI.getSingleIssueByNum(project,num);
		
		request.setAttribute("project", project);
		request.setAttribute("issue", currentIssue);
		response.setContentType("text/html");
		RequestDispatcher dispatcher = (RequestDispatcher) request.getRequestDispatcher("/SingleIssueReview.jsp");
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
