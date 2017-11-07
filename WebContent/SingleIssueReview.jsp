<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
	<title>Insert title here</title>
	<meta charset="UTF-8">
	<link href="static/css/bootstrap.min.css" rel="stylesheet">
<link href="static/css/starter-template.css" rel="stylesheet">
</head>
<body>
	
	<div class="navbar navbar-toggleable-md navbar-inverse bg-inverse fixed-top">
		<a class="navbar-brand" href="labels?project=${project}">${project}</a>
		<div class="collapse navbar-collapse" id="navbarsExampleDefault">
	
			<a href="https://github.com/login/oauth/authorize?client_id=737d83576351a46442c7" style="text-decoration: none; color: white; line-height: 3.7em;"> [Change project]</a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="labels?project=${project}" style="text-decoration: none; color: white; line-height: 3.7em;">[Project labels]</a>
				
		</div>
	</div>
	<div class="container">
	<div class="alert alert-info" role="alert">
	<table><tr style="vertical-align: top;"><td>
	<img src="${issue.getUser().getAvatar_url()}" style="margin-right:5pt;margin-bottom:5pt;" height=80pt>
	</td><td>
	User: <b style="font-size:20pt;">${issue.getUser().getLogin()}</b>
	<br>${issue.getCreated_at()}<br>
	
	<span class="label label-warning">${issue.getState()}</span>
	<c:forEach var="label" items="${issue.getLabels()}">
		<span class="label label-primary">${label.getName()}</span>
	</c:forEach>
	
	</td></tr></table>
	
	<h3 style="margin:0pt;">${issue.getTitle()}</h3><br>
	<br> ${issue.getBody()}
	</div>
	
	<br>
		<%--BEGIN RLEVANT PR AND ISSUES BLOCK--%>
<%-- BEGIN PR BLOCK--%>
	<c:if test="${not empty pull_requests_array}">
		Lelevant pool requests or issues:
        <c:forEach var="pr_el" items="${pull_requests_array}">
            <span class="label label-primary"><a href="issue?project=${project}&num=${pr_el}"  style="color:white;">#${pr_el}</a></span>
        </c:forEach>
    </c:if>
<%--END PR BLOCK--%>
    <c:if test="${not empty issues_array}">
        <c:forEach var="issues_el" items="${issues_array}">
            <span class="label label-primary"><a href="issue?project=${project}&num=${issues_el}" style="color:white;">#${issues_el}</a></span>
        </c:forEach>
    </c:if>
<%--END RLEVANT PR AND ISSUES BLOCK--%>

	<br><br>
<fieldset>
		<legend>Issue chat room</legend></fieldset>
	<table>
		<tr>
			<td ><textarea readonly="true" rows="10" cols="80" id="log"></textarea>
			</td>
		</tr>
		<tr>
			<td><input
				type="text" size="70" id="msg" placeholder="Message" />
				<button type="button" onclick="send();">Send</button></td>
		</tr>
	</table>
	<script src="static/js/script.js"></script>



</div>
</body>
</html>