package main.java;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
		Cookie[] cookies = request.getCookies();
		String accessToken = null;
		for(int i=0;i<cookies.length;i++)
			if(cookies[i].getName().equals("github_access_token"))
				accessToken = cookies[i].getValue();
		
		if (request.getParameter("project") == null || request.getParameter("num") == null || accessToken == null){
			response.sendRedirect("index");
			return;
		}
		
		String project = request.getParameter("project");
		String num = request.getParameter("num");
		
		Issue currentIssue = GithubAPI.getSingleIssueByNum(project,num,accessToken);
		
		request.setAttribute("project", project);
		request.setAttribute("issue", GithubAPI.changeSpecialSymbolsinIssue(currentIssue));
		response.setContentType("text/html");
		RequestDispatcher dispatcher = (RequestDispatcher) request.getRequestDispatcher("/SingleIssueReview.jsp");
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
