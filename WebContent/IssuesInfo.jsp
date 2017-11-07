<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Issues</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="static/css/bootstrap.min.css" rel="stylesheet">
<link href="static/css/starter-template.css" rel="stylesheet">
</head>
<body>
	<div
		class="navbar navbar-toggleable-md navbar-inverse bg-inverse fixed-top"
		style="height: 80%;">
		<a class="navbar-brand" href="labels?project=${project}">${project}</a>
		<div class="collapse navbar-collapse" id="navbarsExampleDefault">


			<a href="https://github.com/login/oauth/authorize?client_id=737d83576351a46442c7"
				style="text-decoration: none; color: white; line-height: 3.7em;">
				[Change project]</a>
				
				<a href="labels?project=${project}" style="text-decoration: none; color: white; line-height: 3.7em;">[Project labels]</a>
				
		</div>
	</div>

	<div class="container">
		<c:if test="${hasPrev == true}">
			
			<button onclick="location.href = 'issues?project=${project}&label=${label}&page=${page-1}'" class="btn btn-primary" type="button" style="margin-bottom:5pt;">
				&larr; Prev
			</button>
		</c:if>
		<c:if test="${hasNext == true}">
			<button onclick="location.href = 'issues?project=${project}&label=${label}&page=${page+1}'" class="btn btn-primary" type="button" style="margin-bottom:5pt;">
				 Next &rarr;
			</button>
		</c:if>
		<br>
		
		
		<c:forEach var="issue" items="${issues}">

			<div class="alert alert-info" role="alert" style="margin-bottom:10pt;">
			<a href="issue?project=${project}&num=${issue.getNumber()}">${issue.getTitle()}</a>
			<br> Labels: 
		<c:forEach var="label" items="${issue.getLabels()}">
			<a href="issues?project=facebook/react&label=${label.getName()}"><span class="label label-info">${label.getName()}</span></a>
		</c:forEach>
			</div>

		</c:forEach>
		
		
		<c:if test="${hasPrev == true}">
			
			<button onclick="location.href = 'issues?project=${project}&label=${label}&page=${page-1}'" class="btn btn-primary" type="button" style="margin-bottom:5pt;">
				&larr; Prev
			</button>
		</c:if>
		<c:if test="${hasNext == true}">
			<button onclick="location.href = 'issues?project=${project}&label=${label}&page=${page+1}'" class="btn btn-primary" type="button" style="margin-bottom:5pt;">
				 Next &rarr;
			</button>
		</c:if>
		
	</div>
</body>
</html>