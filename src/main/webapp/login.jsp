<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language" var="lang"/>

<fmt:message bundle="${lang}" key="language.title" var="site_title" />
<fmt:message bundle="${lang}" key="language.button.menu" var="menu" />
<fmt:message bundle="${lang}" key="language.link.menu-home" var="text_home" />

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Sign Up Form</title>
		<link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css">
		<link rel="stylesheet" href="css/style.css">
	</head>

	<body>
		<input type="hidden" id="status" value="${sessionScope.get("status")}">
		<div class="main">
			<!-- Sing in  Form -->
			<section class="sign-in">
				<div class="container">
					<div class="signin-content">
						<div class="signin-image">
							<figure>
								<img src="images/signin-image.jpg" alt="sing up image">
							</figure>
							<a href="registration.jsp" class="signup-image-link">Создать аккаунт</a>
						</div>

						<div class="signin-form">
							<h2 class="form-title">Войти</h2>
							<form method="post" action="login" class="register-form" id="login-form">
								<input type="hidden" name="command" value="authorization" />
								<div class="form-group">
									<label for="username"><i class="zmdi zmdi-account material-icons-name"></i></label>
									<input type="text" name="username" id="username" placeholder="Your Name" required = "required"/>
								</div>
								<div class="form-group">
									<label for="password"><i class="zmdi zmdi-lock"></i></label>
									<input type="password" name="password" id="password" placeholder="Password" required = "required"/>
								</div>
								<div class="form-group form-button">
									<input type="submit" name="signin" id="signin" class="form-submit" value="Вход" />
									<input type="button" onclick="history.back();" id="back" class="form-submit" value="Назад" />
								</div>
							</form>
						</div>
					</div>
				</div>
			</section>
		</div>

		<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
		<link rel="stylesheet" href="css/sweet-alert.css">
		<script type="text/javascript">
			var status = document.getElementById("status").value;
			if (status == "failed") {
				swal("Ошибка!", "Имя пользователя или пароль введены неверно.", "error");
			}
		</script>
	</body>
</html>