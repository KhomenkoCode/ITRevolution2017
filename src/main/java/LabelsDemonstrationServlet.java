package main.java;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
		if (request.getParameter("project") == null)
			response.sendRedirect("index");
		else {
			String project = request.getParameter("project");
			IssuesLabel[] labels = GithubAPI.getAllLabels(project);
			request.setAttribute("aaa", labels[0].name);
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

}
