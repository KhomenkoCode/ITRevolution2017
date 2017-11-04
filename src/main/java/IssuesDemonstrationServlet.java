package main.java;

import java.io.IOException;
import java.util.Vector;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	private Vector<Vector<String>> prepareIssuesToDisplay(Issue[] issues) {
		Vector<Vector<String>> result = new Vector<Vector<String>>();

		for (int i = 0; i < issues.length; i++) {
			if (issues[i] != null) {
				Vector<String> var = new Vector<String>();
				var.addElement(issues[i].getUser().getLogin());
				var.addElement(issues[i].getTitle());
				var.addElement(issues[i].getState());
				var.addElement(issues[i].getCreated_at());
				var.addElement(issues[i].getClosed_at());
				result.add(var);
			}
		}

		return result;
	}
}
