package main.java;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
		String code = (String) request.getParameter("code");
		if (code != null) {

			String acessToken = GithubAPI.getAccessToken(code);
			if(acessToken.contains("error"))
			{
				response.sendRedirect("index");
				return;
			}
			Cookie myCookie = new Cookie("github_access_token", acessToken);
			response.addCookie(myCookie);
			request.setAttribute("code", acessToken);
		}

		response.setContentType("text/html");
		RequestDispatcher dispatcher = (RequestDispatcher) request.getRequestDispatcher("/EntryForm.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String projectUrl = (String) request.getParameter("project");
		int lastIndexOfGithubCom = projectUrl.lastIndexOf("github.com/");
		if (lastIndexOfGithubCom == -1) {
            Cookie[] cookies = request.getCookies();
            String accessToken = null;
            for(int i=0;i<cookies.length;i++)
                if(cookies[i].getName().equals("github_access_token"))
                    accessToken = cookies[i].getValue();
			request.setAttribute("WrongUrlMessage", "Wrong URL! URL must contain \"github.com/\"");
            //request.setAttribute("example", "github.com/facebook/react");
            request.setAttribute("code", accessToken);
            response.setContentType("text/html");
            RequestDispatcher dispatcher = (RequestDispatcher) request.getRequestDispatcher("/EntryForm.jsp");
            dispatcher.forward(request, response);
		} else {
			String projectCuttedUrl = projectUrl.substring(lastIndexOfGithubCom + 11, projectUrl.length());
			if (projectCuttedUrl.trim().charAt(projectCuttedUrl.length() - 1) == '/')
				projectCuttedUrl = projectCuttedUrl.trim().substring(0, projectCuttedUrl.length() - 1);

			response.sendRedirect("labels?project=" + projectCuttedUrl);
		}
	}
}