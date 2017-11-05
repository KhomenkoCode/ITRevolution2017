<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Issues</title>
</head>
<body>

<c:if test="${hasPrev == true}">
<a href="issues?project=${project}&label=${label}&page=${page-1}">Prev
</a>
</c:if>
<c:if test="${hasNext == true}">
<a href="issues?project=${project}&label=${label}&page=${page+1}">
Next
</a>
</c:if>
<br>
	<c:forEach var="issue" items="${issues}">
		 <a href="issue?project=${project}&num=${issue.getNumber()}">${issue.getTitle()}</a> Labels:
		
		
		<c:forEach var="label" items="${issue.getLabels()}">
			${label.getName()}
		</c:forEach> 
		
		 
		 <br>
	</c:forEach>
	
	
</body>
</html>