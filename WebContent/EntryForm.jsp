<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
 <br><br>
 		${WrongUrlMessage}<br>
		<form method="post">
		Введите ссылку на репозиторий Github:<br>
		<input type="text" name="project" placeholder="github.com/"><br><br>
		<input type="submit" value="Submit">
		</form>
</body>
</html>