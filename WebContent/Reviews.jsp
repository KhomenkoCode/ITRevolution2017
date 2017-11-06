<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%--
  Created by IntelliJ IDEA.
  User: danil
  Date: 2017-11-06
  Time: 00:04
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<c:url value="/static/styles/stars.css"/>" type="text/css">
    <link rel="stylesheet" href="<c:url value="/static/styles/formval.css"/>" type="text/css">

</head>
<body>
<%--${WrongUrlMessage}<br>--%>

<%--@elvariable id="reviews" type="main.java.reviews.Reviews"--%>
    <c:forEach var="review" items="${reviews}">
        <br><b>Name:</b> ${review.getNameField()}
        <br><b>Review:</b> ${review.getTextField()}
        <div class="stars">
            <c:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 100) %></c:set>
            <input class="star star-5" id="star-5${rand}" type="radio" checked/>
            <label class="star star-5" for="star-5${rand}"></label>
            <input class="star star-4" id="star-4${rand}" type="radio" disabled/>
            <label class="star star-4" for="star-4${rand}"></label>
            <input class="star star-3" id="star-3${rand}" type="radio" disabled/>
            <label class="star star-3" for="star-3${rand}"></label>
            <input class="star star-2" id="star-2${rand}" type="radio" disabled/>
            <label class="star star-2" for="star-2${rand}"></label>
            <input class="star star-1" id="star-1${rand}" type="radio" disabled/>
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
            <textarea name = "review_text" id="review_text" rows="10" cols="40" required></textarea>
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

</body>
</html>
