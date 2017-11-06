<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<br>
	<br>
	<p>Well, hello there!</p>

	<c:choose>
		<c:when test="${empty code}">

			<p>
				We're going to now talk to the GitHub API. Ready? <a
					href="https://github.com/login/oauth/authorize?client_id=737d83576351a46442c7">Click
					here</a> to begin!
			</p>

		</c:when>
		<c:otherwise>
		${code}
		${WrongUrlMessage}<br>
			<form method="post">
				Введите ссылку на репозиторий Github:<br> <input type="text"
					name="project" placeholder="github.com/"><br> <br>
				<input type="submit" value="Submit">
			</form>
		</c:otherwise>
	</c:choose>
</body>
</html>