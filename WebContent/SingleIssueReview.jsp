<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
	<title>Insert title here</title>
	<meta charset="UTF-8">
</head>
<body>

	<img src="${issue.getUser().getAvatar_url()}" height="50pt">
	${issue.getUser().getLogin()}; ${issue.getState()}
	${issue.getCreated_at()}
	<br> ${issue.getTitle()}
	<br>
	<br> ${issue.getBody()}
	<br>
	<br>

	<c:forEach var="label" items="${issue.getLabels()}">
		${label.getName()}
	</c:forEach>
	<br>

	<table>
		<tr>
			<td><textarea readonly="true" rows="10" cols="80" id="log"></textarea>
			</td>
		</tr>
		<tr>
			<td><input type="text" size="15" id="username" placeholder="To" /> <input
				type="text" size="51" id="msg" placeholder="Message" />
				<button type="button" onclick="send();">Send</button></td>
		</tr>
	</table>
	<script src="static/js/script.js"></script>

    
<%--BEGIN RLEVANT PR AND ISSUES BLOCK--%>
<%-- BEGIN PR BLOCK--%>
	<c:if test="${not empty pull_requests_array}">
        <c:forEach var="pr_el" items="${pull_requests_array}">
            <a href="issue?project=${project}&num=${pr_el}">#${pr_el}</a><br>
        </c:forEach>
    </c:if>
<%--END PR BLOCK--%>
    <c:if test="${not empty issues_array}">
        <c:forEach var="issues_el" items="${issues_array}">
            <a href="issue?project=${project}&num=${issues_el}">#${issues_el}</a><br>
        </c:forEach>
    </c:if>
<%--END RLEVANT PR AND ISSUES BLOCK--%>


</body>
</html>