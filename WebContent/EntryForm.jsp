<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="static/css/bootstrap.min.css" rel="stylesheet">
<link href="static/css/cover.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<c:choose>
		<c:when test="${empty code}">


			<div class="site-wrapper">

				<div class="site-wrapper-inner">

					<div class="cover-container">



						<div class="inner cover">
							<h1 class="cover-heading">Cover your page.</h1>
							<p class="lead">We're going to now talk to the GitHub API.
								Ready?</p>
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
							<h1 class="cover-heading">Cover your page.</h1>
							<p class="lead" style="color: #FC8383">${WrongUrlMessage}</p>

							<p class="lead">Введите ссылку на репозиторий Github:</p>

							<div class="col-lg-6">
								<div class="input-group">
									<form method="post">
										<span class="input-group-btn">
											<input type="text" name="project" placeholder="github.com/" class="form-control" > 
											<input type="submit" class="btn btn-default" value="Submit" >
										</span>
									</form>
								</div>

							</div>
						</div>



					</div>

				</div>

			</div>

			<!-- ${WrongUrlMessage}<br>
			<form method="post">
				Введите ссылку на репозиторий Github:<br> <input type="text"
					name="project" placeholder="github.com/"><br> <br>
				<input type="submit" value="Submit">
			</form> -->
		</c:otherwise>
	</c:choose>
</body>
</html>