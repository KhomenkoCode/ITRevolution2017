package main.java;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

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
		if (request.getParameter("project") == null) {
			response.sendRedirect("index");
			return;
		} else {
			String project = request.getParameter("project");
			IssuesLabel[] labels = GithubAPI.getAllLabels(project);
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

					if (labelForIssuesPage == "")
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

					if (labelForIssuesPage == "")
						response.sendRedirect("index");
					else
						response.sendRedirect("issues?project=" + project + "&label=" + labelForIssuesPage);
					return;
				}

				request.setAttribute("choosedType", choosedType);
				request.setAttribute("subtypeLabels", labelsByTypeMap.get(choosedType));
			} else
				request.setAttribute("typeLabels", labelsByTypeMap);

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
