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
<input type="hidden" id="status" value="${sessionScope.get("status")}">
<div class="main">
  <!-- Sign up form -->
  <section class="signup">
    <div class="container">
      <div class="signup-content">
        <div class="signup-form">
          <h2 class="form-title">Мои данные</h2>
          <form method="post" action="userinfo" class="register-form" id="register-form">
            <input type="hidden" name="command" value="update_user_info" />
            <div class="form-group">
              <label for="name">
                <i class="zmdi zmdi-account material-icons-name"></i>
              </label>
              <input type="hidden" value="${user.id}" name="id"/>
              <input type="text" value="${userInfo.name}" name="name" id="name" placeholder="Фамилия, Имя" required="required" readonly="readonly"/>
            </div>
            <div class="form-group">
              <label for="address">
                <i class="zmdi zmdi-my-location"></i>
              </label>
              <input type="text" value="${userInfo.address}" name="address" id="address" placeholder="Адрес проживания" required="required" readonly="readonly"/>
            </div>
            <div class="form-group">
              <label for="contact">
                <i class="zmdi zmdi-phone"></i>
              </label>
              <input type="text" value="${userInfo.contact}" name="contact" id="contact" placeholder="Телефон" required="required" readonly="readonly"/>
            </div>
            <div class="form-group">
              <label for="email">
                <i class="zmdi zmdi-email"></i>
              </label>
              <input type="email" value="${userInfo.email}" name="email" id="email" placeholder="Email" required="required" readonly="readonly"/>
            </div>
            <div class="form-group">
              <label for="pass">
                <i class="zmdi zmdi-lock"></i>
              </label>
              <input type="hidden" name="password" id="pass" placeholder="Новый пароль" required="required"/>
            </div>
            <div class="form-group">
              <label for="re_pass">
                <i class="zmdi zmdi-lock-outline"></i>
              </label>
              <input type="hidden" name="re_pass" id="re_pass" placeholder="Повторите новый пароль" required="required"/>
            </div>
            <div class="form-group form-button">
              <input type="button" onclick="editDataFunction();" id="editInfo" class="form-submit" value="Редактировать" />
              <input type="button" onclick="history.back();" id="back" class="form-submit" value="Назад" />
              <input type="hidden" onclick="changePasswordValue();" id="changePass" class="form-submit" value="Пароль" />
              <input type="hidden" id="saveInfo" class="form-submit" value="Сохранить" />
              <input type="hidden" onclick="cancelEditFunction();" name="signup" id="cancelInfo" class="form-submit" value="Отменить" />
            </div>
          </form>
        </div>
      </div>
    </div>
  </section>
</div>

<%--	<script src="vendor/jquery/jquery.min.js"></script>--%>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link rel="stylesheet" href="css/sweet-alert.css">
<script src="js/menu.js"></script>

<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link rel="stylesheet" href="css/sweet-alert.css">
<script type="text/javascript">
  var status = document.getElementById("status").value;
  if (status == "success") {
    swal("" , "Данные были успешно сохранены.", "success");
  }
</script>


</body>
</html>