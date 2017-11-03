package main.java;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EntryFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EntryFormServlet() {
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		RequestDispatcher dispatcher = (RequestDispatcher) request.getRequestDispatcher("/EntryForm.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String projectUrl = (String) request.getParameter("project");
		int lastIndexOfGithubCom = projectUrl.lastIndexOf("github.com/");
		System.out.println(lastIndexOfGithubCom);
		if (lastIndexOfGithubCom == -1) {
			request.setAttribute("WrongUrlMessage", "Wrong URL! URL Must contain \"github.com/\"");
			doGet(request, response);
		} else {
			String projectCuttedUrl = projectUrl.substring(lastIndexOfGithubCom + 11, projectUrl.length());
			response.sendRedirect("labels?project=" + projectCuttedUrl);
		}
	}

}
