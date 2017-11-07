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
<link rel="stylesheet" href="<c:url value="/static/css/stars.css"/>"
	type="text/css">
<link rel="stylesheet" href="<c:url value="/static/css/formval.css"/>"
	type="text/css">
<link href="static/css/bootstrap.min.css" rel="stylesheet">
<link href="static/css/starter-template.css" rel="stylesheet">
</head>
<body>

	<div class="navbar navbar-toggleable-md navbar-inverse bg-inverse fixed-top" style="height:80%;">
		<a class="navbar-brand" href="labels?project=${project}">${project}</a>
		<div class="collapse navbar-collapse" id="navbarsExampleDefault">
	
			<a href="https://github.com/login/oauth/authorize?client_id=737d83576351a46442c7" style="text-decoration: none; color: white; line-height: 3.7em;"> [Change project]</a>
		</div>
	</div>

	<div class="container">



		<table>
		<tr style="vertical-align: top;">
		<td style="width:35%;">
		<h3>Issues type:</h3> <br>
		<c:choose>
			<c:when test="${empty choosedType}">
				<c:forEach var="numOfelem" begin="0"
					end="${NumOfTypesIssuesList.size()-1}" step="2">
				
					<button onclick="location.href = 'labels?project=${project}&type=${NumOfTypesIssuesList.get(numOfelem)}'" class="btn btn-primary" type="button" style="margin-bottom:5pt;">
 						<a style="text-decoration: none; color: white;">${NumOfTypesIssuesList.get(numOfelem)}&nbsp;&nbsp;</a> 
 						<span class="badge">${NumOfTypesIssuesList.get(numOfelem+1)}</span>
					</button>
					
				
				<br>
				</c:forEach>
				
				<c:if test="${NumOfTypesIssuesList.size()==0}"> <h3>Sorry, nothing in there</h3></c:if>
			</c:when>
			<c:otherwise>
				<h4>"${choosedType}" subtypes:</h4>
				
				<c:forEach var="numOfelem" begin="0"
					end="${NumOfSubtypesIssuesList.size()-1}" step="2">
				
					<button onclick="location.href = 'labels?project=${project}&type=${choosedType}&subtype=${NumOfSubtypesIssuesList.get(numOfelem)}'" class="btn btn-primary" type="button" style="margin-bottom:5pt;">
 						<a style="text-decoration: none; color: white;">${NumOfSubtypesIssuesList.get(numOfelem)}&nbsp;&nbsp;</a> 
 						<span class="badge">${NumOfSubtypesIssuesList.get(numOfelem+1)}</span>
					</button>
					
				<br>
				</c:forEach>
				
				<c:if test="${NumOfSubtypesIssuesList.size()==0}"> <h4>Sorry, nothing in there</h4></c:if>
			</c:otherwise>
		</c:choose>
		</td>
		
		<td style="vertical-align: top;padding-right:20pt;">
		<%-- BEGIN FI and DF info an Contributors--%>
		<div >
		<br><br><br>
			<ul class="list-group">
  				<li class="list-group-item" style="background-color:#6cf772;border: 1px solid #6cf772;"><h4>FI Ratio: <c:out value="${mapFIResults['ratio']}" />%</h4></li>
  				<li class="list-group-item" style="background-color:#6cf7ac;border: 1px solid #6cf772;">Our ratio based on
			<c:out value="${mapFIResults['amount']}" />
			issues which we gladly scanned.</li>
  				<li class="list-group-item"  style="background-color:#6cf7ac;border: 1px solid #6cf772;">Usually it takes
			<c:out value="${mapFIResults['average']}" />
			days to integrate feature to this repository.</li>
  				<li class="list-group-item" style="background-color:#6cf7ac;border: 1px solid #6cf772;">We omit repos
			that were closed in less than 15 minutes.</li>
			<li class="list-group-item" style="background-color:#6cf7ac;border: 1px solid #6cf772;">
			 Fastest issue
			(enhancement,feature) was closed in
			<c:out value="${mapFIResults['min']}" /> days
			</li>
  				<li class="list-group-item" style="background-color:#6cf7ac;border: 1px solid #6cf772;">Longest issue (enhancement,feature) was closed in
			<c:out value="${mapFIResults['max']}" /> days</li>
			</ul>
			 <br> 
			<br> <br> 
			<br> 
			<br>

		</div>
		</td>
		
		
		
		
		<td style="vertical-align: top">
		<div>
		<br><br><br>
		<ul class="list-group">
  <li class="list-group-item" style="background-color:#38c8e2;border: 1px solid #38c8e2;"><h4>DF Ratio:
			<c:out value="${mapDFResults['ratio']}" />%</h4></li>
  <li class="list-group-item" style="background-color:#38e2dc;border: 1px solid #38c8e2;">Our ratio based on
			<c:out value="${mapDFResults['amount']}" />
			issues which we gladly scanned.</li>
  <li class="list-group-item" style="background-color:#38e2dc;border: 1px solid #38c8e2;">Usually it takes
			<c:out value="${mapDFResults['average']}" />
			days yo integrate feature to this repository.</li>
  <li class="list-group-item" style="background-color:#38e2dc;border: 1px solid #38c8e2;">We omit repos
			that were closed in less than 25 minutes.  </li>
  <li class="list-group-item" style="background-color:#38e2dc;border: 1px solid #38c8e2;">Fastest issue
			(bug,defect) was closed in
			<c:out value="${mapDFResults['min']}"/> days</li>
  <li class="list-group-item" style="background-color:#38e2dc;border: 1px solid #38c8e2;">Longest issue (bug,defect) was closed in
			<c:out value="${mapDFResults['max']}" /> days</li>
</ul>
			 <br> 
			<br> <br> 
			<br> 
			<br>

		</div>
		</td>
		</tr>
		</table>
		<%--END DF and FI info an Contributors--%>
		
		
		<br><br><br>
		
		
		<%--BEGIN Review--%>
		<%--@elvariable id="average_rating_on_reviews" type=""--%>
		
		<%--@elvariable id="reviews" type="main.java.reviews.Reviews"--%>
	<fieldset>
		<legend>Reviews  <span class="label label-info">Average Rating:  ${average_rating_on_reviews}</span> <br> <br>
			<label for="review_text">Post</label></legend></fieldset>
		<c:forEach var="review" items="${reviews}">
		<div class="alert alert-info" role="alert">
				<table><tr style="vertical-align: middle;">
			<td style="max-width:500pt;word-wrap:break-word;">
			<b>Name:</b> ${review.getNameField()}
		<br>
			<b>Review:</b> ${review.getTextField()} 
			
			</td>
			<td>
			
		<div class="stars">
				<c:set var="rand"><%=java.lang.Math.round(java.lang.Math.random() * 100)%></c:set>
				<input class="star star-5" id="star-5${rand}" type="radio"
					<c:choose>
						<c:when test="${review.getGivenRating()==5}">
							checked
						</c:when>
						<c:otherwise>
							disabled
						</c:otherwise>
					</c:choose> />
				<label class="star star-5" for="star-5${rand}"></label> <input
					class="star star-4" id="star-4${rand}" type="radio"
					<c:choose>
						<c:when test="${review.getGivenRating()==4}">
							checked
						</c:when>
						<c:otherwise>
							disabled
						</c:otherwise>
					</c:choose> />
				<label class="star star-4" for="star-4${rand}"></label> <input
					class="star star-3" id="star-3${rand}" type="radio"
					<c:choose>
						<c:when test="${review.getGivenRating()==3}">
							checked
						</c:when>
						<c:otherwise>
							disabled
						</c:otherwise>
					</c:choose> />
				<label class="star star-3" for="star-3${rand}"></label> <input
					class="star star-2" id="star-2${rand}" type="radio"
					<c:choose>
						<c:when test="${review.getGivenRating()==2}">
							checked
						</c:when>
						<c:otherwise>
							disabled
						</c:otherwise>
					</c:choose> />
				<label class="star star-2" for="star-2${rand}"></label> <input
					class="star star-1" id="star-1${rand}" type="radio"
					<c:choose>
						<c:when test="${review.getGivenRating()==1}">
							checked
						</c:when>
						<c:otherwise>
							disabled
						</c:otherwise>
					</c:choose> />
				<label class="star star-1" for="star-1${rand}"></label>
			</div>
			
			</td>
			</tr></table></div>
		</c:forEach>
		
		
		
		<br><br><br>
		
		
		
		 
		<fieldset>
			<legend>Have a question? Want to leave a feedback?</legend>
			<form method="post" accept-charset="utf-8">
				<label for="name">Name</label> <input  class="form-control" name="name" id="name" required
					type="text"> &nbsp;&nbsp;
                <label for="review_text">You questions,reviews leve here..</label>
                <textarea name="review_text" id="review_text" class="form-control" rows="5" cols="60"
					required></textarea><br> 
				<table>
				<tr style="vertical-align: middle;">
				<td>
				<div class="stars" class="form-group" >
					<input class="star star-5" id="star-5" type="radio" required
						name="star" value="5" /> <label class="star star-5" for="star-5"></label>
					<input class="star star-4" id="star-4" type="radio" required
						name="star" value="4" /> <label class="star star-4" for="star-4"></label>
					<input class="star star-3" id="star-3" type="radio" required
						name="star" value="3" /> <label class="star star-3" for="star-3"></label>
					<input class="star star-2" id="star-2" type="radio" required
						name="star" value="2" /> <label class="star star-2" for="star-2"></label>
					<input class="star star-1" id="star-1" type="radio" required
						name="star" value="1" /> <label class="star star-1" for="star-1"></label>
				</div>
				</td>
				<td>
				 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				 <input class="btn btn-primary" type="submit" value="Post Review" />
				</td>
				</tr>
				</table>
			</form>
		</fieldset>
		<%--END Review--%>

	</div>
</body>
</html>