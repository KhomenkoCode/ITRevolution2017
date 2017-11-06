<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
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
	<script src="js/script.js"></script>



</body>
</html>