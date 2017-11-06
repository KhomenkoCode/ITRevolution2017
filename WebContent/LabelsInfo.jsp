<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%--
  Created by IntelliJ IDEA.
  User: Daniel Nikulin/Nikita Khomenko
  Date: 2017-11-06
  Time: 02:04
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Title</title>
	<link rel="stylesheet" href="<c:url value="/static/styles/stars.css"/>" type="text/css">
	<link rel="stylesheet" href="<c:url value="/static/styles/formval.css"/>" type="text/css">
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


<%-- BEGIN FI and DF info an Contributors--%>
    <div>
        Our ratio based on <c:out value="${mapFIResults['amount']}"/> issues which we gladly scanned. <br>
        FI Ratio: <c:out value="${mapFIResults['ratio']}"/><br>
        Usually it takes <c:out value="${mapFIResults['average']}"/> days yo integrate feature to this repository.<br>
        We omit repos that were closed in less than 15 minutes.<br>
        Fastest issue (enhancement,feature) was closed in <c:out value="${mapFIResults['min']}"/> <br>
        Longest issue (enhancement,feature) was closed in <c:out value="${mapFIResults['max']}"/> <br>

    </div>

    <div>
        Our ratio based on <c:out value="${mapDFResults['amount']}"/> issues which we gladly scanned. <br>
        DF Ratio: <c:out value="${mapDFResults['ratio']}"/><br>
        Usually it takes <c:out value="${mapDFResults['average']}"/> days yo integrate feature to this repository.<br>
        We omit repos that were closed in less than 25 minutes.<br>
        Fastest issue (bug,defect) was closed in <c:out value="${mapDFResults['min']}"/> <br>
        Longest issue (bug,defect) was closed in <c:out value="${mapDFResults['max']}"/> <br>

    </div>


<%--END DF and FI info an Contributors--%>

<%--BEGIN Review--%>
	<%--@elvariable id="average_rating_on_reviews" type=""--%>
	<b>Average Rating ${average_rating_on_reviews}</b>
	<%--@elvariable id="reviews" type="main.java.reviews.Reviews"--%>
	<c:forEach var="review" items="${reviews}">
		<br><b>Name:</b> ${review.getNameField()}
		<br><b>Review:</b> ${review.getTextField()}
		<div class="stars">
			<c:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 100) %></c:set>
			<input class="star star-5" id="star-5${rand}" type="radio"
					<c:choose>
						<c:when test="${review.getGivenRating()==5}">
							checked
						</c:when>
						<c:otherwise>
							disabled
						</c:otherwise>
					</c:choose>/>
			<label class="star star-5" for="star-5${rand}"></label>
			<input class="star star-4" id="star-4${rand}" type="radio"
					<c:choose>
						<c:when test="${review.getGivenRating()==4}">
							checked
						</c:when>
						<c:otherwise>
							disabled
						</c:otherwise>
					</c:choose>/>
			<label class="star star-4" for="star-4${rand}"></label>
			<input class="star star-3" id="star-3${rand}" type="radio"
					<c:choose>
						<c:when test="${review.getGivenRating()==3}">
							checked
						</c:when>
						<c:otherwise>
							disabled
						</c:otherwise>
					</c:choose>/>
			<label class="star star-3" for="star-3${rand}"></label>
			<input class="star star-2" id="star-2${rand}" type="radio"
					<c:choose>
						<c:when test="${review.getGivenRating()==2}">
							checked
						</c:when>
						<c:otherwise>
							disabled
						</c:otherwise>
					</c:choose>/>
			<label class="star star-2" for="star-2${rand}"></label>
			<input class="star star-1" id="star-1${rand}" type="radio"
					<c:choose>
						<c:when test="${review.getGivenRating()==1}">
							checked
						</c:when>
						<c:otherwise>
							disabled
						</c:otherwise>
					</c:choose>/>
			<label class="star star-1" for="star-1${rand}"></label>
		</div>
	</c:forEach>
	<fieldset>
		<legend>Review</legend>
		<form method="post">
			<label for="name">Name</label>
			<input name="name" id="name" required type="text">
			<br><br>
			<label for="review_text">Post</label>
			<textarea name = "review_text" id="review_text" rows="5" cols="60" required></textarea>
			<div class="stars">
				<input class="star star-5" id="star-5" type="radio" required name="star" value="5"/>
				<label class="star star-5" for="star-5"></label>
				<input class="star star-4" id="star-4" type="radio" required name="star" value="4"/>
				<label class="star star-4" for="star-4"></label>
				<input class="star star-3" id="star-3" type="radio" required name="star" value="3"/>
				<label class="star star-3" for="star-3"></label>
				<input class="star star-2" id="star-2" type="radio" required name="star" value="2"/>
				<label class="star star-2" for="star-2"></label>
				<input class="star star-1" id="star-1" type="radio" required name="star" value="1"/>
				<label class="star star-1" for="star-1"></label>
			</div>
			<input type="submit" value="Submit" />

		</form>
	</fieldset>
<%--END Review--%>


</body>
</html>