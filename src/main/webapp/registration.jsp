<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Регистрация</title>

<!-- Font Icon -->
<link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css">

<!-- Main css -->
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<input type="hidden" id="status" value="${requestScope.get("status")}">
	<div class="main">
		<!-- Sign up form -->
		<section class="signup">
			<div class="container">
				<div class="signup-content">
					<div class="signup-form">
						<h2 class="form-title">Регистрация</h2>
						<form method="post" action="registration" class="register-form" id="register-form">
							<input type="hidden" name="command" value="registration" />
							<div hidden id="error" class="form-group-error">
								<label for="name">
									<img src="images/error.png">
<%--									<i class="zmdi zmdi-alert-octagon material-icons-name"></i>--%>
								</label>
								<textarea class="input-error" type="text" name="errorMessage" id="errorMessage" readonly="readonly">${message}</textarea>
							</div>
							<div class="form-group">
								<label for="name">
									<i class="zmdi zmdi-account material-icons-name"></i>
								</label>
								<input type="text" name="name" id="name" placeholder="Фамилия, Имя" required="required" value="${userInfo.name}"/>
							</div>
							<div class="form-group">
								<label for="address">
									<i class="zmdi zmdi-my-location"></i>
								</label>
								<input type="text" name="address" id="address" placeholder="Адрес проживания" required="required" value="${userInfo.address}"/>
							</div>
							<div class="form-group">
								<label for="contact">
									<i class="zmdi zmdi-phone"></i>
								</label>
								<input type="text" name="contact" id="contact" placeholder="Телефон" required="required" value="${userInfo.contact}"/>
							</div>
							<div class="form-group">
								<label for="email">
									<i class="zmdi zmdi-email"></i>
								</label>
								<input type="email" name="email" id="email" placeholder="Email" required="required" value="${userInfo.email}"/>
							</div>
							<div class="form-group">
								<label for="pass">
									<i class="zmdi zmdi-lock"></i>
								</label>
								<input type="password" name="password" id="pass" placeholder="Пароль" required="required" value="${password}"/>
							</div>
							<div class="form-group">
								<label for="re_pass">
									<i class="zmdi zmdi-lock-outline"></i>
								</label>
								<input type="password" name="re_pass" id="re_pass" placeholder="Повторите пароль" required="required" value="${re_pass}"/>
							</div>
							<div class="form-group form-button">
								<input type="submit" name="signup" id="signup" class="form-submit" value="Зарегистрировать" />
							</div>
						</form>
					</div>
					<div class="signup-image">
						<figure>
							<img src="images/signup-image.jpg" alt="sing up image">
						</figure>
						<a href="login.jsp" class="signup-image-link">Я уже зарегистрирован(а)</a>
						<div class="form-group form-button">
							<a href="index.jsp" class="signup-image-link">На главную</a>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>


	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	<link rel="stylesheet" href="css/sweet-alert.css">
	<script type="text/javascript">
		var status = document.getElementById("status").value;
		if (status == "success") {
			swal("Поздравляем!", "Аккаунт успешно создан.", "success");
		} else if (${message != null}){
			document.getElementById("error").hidden = false;
		}
	</script>

</body>
</html>