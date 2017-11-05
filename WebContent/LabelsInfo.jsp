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
	<c:choose>
    	<c:when test="${empty choosedType}">
			<c:forEach var="type" items="${typeLabels}">
   				<a href="labels?project=${project}&type=${type.key}">${type.key}</a><br>
			</c:forEach>
			<br><br>
			<c:forEach var="numOfIssues" items="${NumOfTypesIssuesList}">
   				${numOfIssues}<br>
			</c:forEach>
		</c:when>
    	<c:otherwise>
    		<c:forEach var="subtype" items="${subtypeLabels}">
    			<a href="labels?project=${project}&type=${choosedType}&subtype=${subtype}">${subtype}</a><br>
			</c:forEach>
			<c:forEach var="numOfIssues" items="${NumOfSubtypesIssuesList}">
   				${numOfIssues}<br>
			</c:forEach>
    	</c:otherwise>
	</c:choose>
</body>
</html>