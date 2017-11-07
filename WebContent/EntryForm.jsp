<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link href="static/css/bootstrap.min.css" rel="stylesheet">
    <link href="static/css/cover.css" rel="stylesheet">
    <link rel="shortcut icon" type="image/png" href="static/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Github Issue Browser (GiB)</title>
</head>
<body>
${WrongUrlMessage}
<c:if test="${not empty example}" ><br>
	<a href="/labels?project=facebook/react" >Example github.com/facebook/react</a>
</c:if>
			<c:choose>
				<c:when test="${empty code}">


					<div class="site-wrapper">

						<div class="site-wrapper-inner">

							<div class="cover-container">



								<div class="inner cover">
								
									<h1 class="cover-heading">Github Issue Browser</h1>
									<p class="lead">Hey, we need to authenticate you through Github.</p>
									<%--<p class="lead">We really need it,for research purpose obviously.</p>--%>
									<p class="lead">
										<a
											href="https://github.com/login/oauth/authorize?client_id=737d83576351a46442c7"
											class="btn btn-lg btn-secondary">Connect to Github</a>
									</p>
									
								</div>



							</div>

						</div>

					</div>

				</c:when>
				<c:otherwise>

					<div class="site-wrapper">

						<div class="site-wrapper-inner">

							<div class="cover-container">


								<div class="inner cover">
									<h1 class="cover-heading">So let's begin!</h1>


									<p class="lead">Enter an link in the field below to continue.</p>

									<div class="col-lg-6">
										<div class="input-group">
											<form method="post">
												<span class="input-group-btn"> <input type="text"
													name="project" placeholder="github.com/"
													class="form-control" style="width: 180%;"> <input
													type="submit" class="btn btn-default" value="Submit">
												</span>
											</form>
										</div>

									</div>
								</div>



							</div>

						</div>

					</div>

					<!-- <br>
			<form method="post">
				Введите ссылку на репозиторий Github:<br> <input type="text"
					name="project" placeholder="github.com/"><br> <br>
				<input type="submit" value="Submit">
			</form> -->
				</c:otherwise>
			</c:choose>
		
</body>
</html>