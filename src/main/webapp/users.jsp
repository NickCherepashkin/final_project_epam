<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8" />
    <title>Sherlock - on-line library</title>
    <script src="https://use.fontawesome.com/releases/v5.15.4/js/all.js" crossorigin="anonymous"></script>
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
    <link href="https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet" type="text/css" />
    <link href="css/index-styles.css" rel="stylesheet" />
</head>

<c:if test="${usersList == null}">
    <jsp:forward page="/users-list">
        <jsp:param name="command" value="get_users_list" />
    </jsp:forward>
</c:if>

<body id="page-top">
<jsp:include page="menu.jsp" />
<input type="hidden" id="status" value="${requestScope.get("status")}">
<section class="page-section-books novelty" id="novelty">
    <div class="container">
        <h2 class="page-section-heading text-center text-uppercase text-secondary mb-0">Пользователи</h2>
        <div class="divider-custom">
            <div class="divider-custom-line"></div>
            <div class="divider-custom-icon">
                <i class="fas fa-star"></i>
            </div>
            <div class="divider-custom-line"></div>
        </div>

        <div class="divider-custom">
            <table width="100%">
                <tr>
                    <td align="center" width="50%">
                        <div class="page-section-heading d-lg-inline-flex mb-3" >
                            <input type="text" name=text" class="search form-control-lg " placeholder="Искать по фамилии">
                            <input type="submit" name="submit" class="btn btn-primary" value="Search">
                        </div>
                    </td>
                    <td align="center" width="50%">
                        <div class="page-section-heading d-lg-inline-flex mb-0" >
                            <input type="button" data-bs-toggle="modal" data-bs-target="#addUserInfo" class="search form-control-lg " value="Добавить нового пользователя">
                        </div>
                    </td>
                </tr>
            </table>
        </div>

        <c:forEach var="user" items="${usersList}">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editUserLabel">${user.userInfo.name}</h5>
                    </div>
                    <div class="modal-body">
                        <form method="post" action="users-list">
                            <div class="mb-3">
                                <table>
                                    <tr>
                                        <td  class="descript-table"><h6>Адрес: </h6></td>
                                        <td  class="descript-table">${user.userInfo.address}</td>
                                    </tr>
                                    <tr>
                                        <td  class="descript-table"><h6>Контактный телефон: </h6></td>
                                        <td  class="descript-table">${user.userInfo.contact}</td>
                                    </tr>
                                    <tr>
                                        <td  class="descript-table"><h6>E-mail: </h6></td>
                                        <td  class="descript-table">${user.userInfo.email}</td>
                                    </tr>
                                    <tr>
                                        <td  class="descript-table"><h6>Роль: </h6></td>
                                        <td  class="descript-table">${user.user.role}</td>
                                    </tr>
                                </table>
                            </div>
                            <div class=d-inline-block></div>
                            <div class="modal-footer">
                                <button class="btn btn-primary" type="button" data-bs-toggle="modal" data-bs-target="#editUserInfo${user.user.id}">Редактировать</button>
                                <button type="submit" name="command" value="delete_user" class="btn btn-primary">Удалить</button>
                                <input type="hidden" id="idUser"  name="idUser" value="${user.user.id}">
                                <input type="hidden" id="idRole" name="idRole" value="${user.user.idRole}">
                                <input type="hidden" id="name" name="name" value="${user.userInfo.name}">
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="editUserInfo${user.user.id}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="editUserInfoLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id=" ">Редактирование данных</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
                        </div>
                        <div class="modal-body">
                            <form  action="users-list" method="post">
                                 <div class="mb-3">
                                    <label class="col-form-label">Фамилия, имя:</label>
                                    <input type="hidden" id="id_user" name="id" class="form-control" value="${user.user.id}">
                                     <input type="hidden" id="id_role" name="id_role" class="form-control" value="${user.user.idRole}">
                                    <label for="name_user"></label><input type="text" id="name_user" class="form-control" name="name" value="${user.userInfo.name}">
                                </div>
                                <div class="mb-3">
                                    <label class="col-form-label">Адрес:</label>
                                    <label for="address_user"></label><input type="text" id="address_user" class="form-control" name="address" value="${user.userInfo.address}">
                                </div>
                                <div class="mb-3">
                                    <label class="col-form-label">Контактный телефон:</label>
                                    <label for="contact_user"></label><input type="text" id="contact_user" class="form-control" name="contact" value="${user.userInfo.contact}">
                                </div>
                                <div class="mb-3">
                                    <label class="col-form-label">E-mail:</label>
                                    <label for="email_user"></label><input type="text" id="email_user" class="form-control" name="email" value="${user.userInfo.email}">
                                </div>
                                <div class="modal-footer">
                                    <button type="submit" name="command" value="update_user_info" class="btn btn-primary">Сохранить</button>
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>
            </div>
        </c:forEach>

        <div class="modal fade" id="addUserInfo" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="addUserInfoLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Добавление нового пользователя</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
                    </div>
                    <div class="modal-body">
                        <form  action="users-list" method="post">
                            <div class="mb-3">
                                <label class="col-form-label">Фамилия, имя:</label>
                                <label for="name_user"></label><input type="text" class="form-control" name="name" required>
                            </div>
                            <div class="mb-3">
                                <label class="col-form-label">Адрес:</label>
                                <label for="address_user"></label><input type="text" class="form-control" name="address" required>
                            </div>
                            <div class="mb-3">
                                <label class="col-form-label">Контактный телефон:</label>
                                <label for="contact_user"></label><input type="text" class="form-control" name="contact" required>
                            </div>
                            <div class="mb-3">
                                <label class="col-form-label">E-mail:</label>
                                <label for="email_user"></label><input type="email" class="form-control" name="email" required>
                            </div>
                            <div class="mb-3">
                                <label class="col-form-label">Роль:</label>
                                <select class="form-control" name="id_role">
                                    <c:forEach var="role" items="${rolesList}">
                                        <option value="${role.id}">${role.title}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="col-form-label">Пароль:</label>
                                <label></label><input type="password" class="form-control" name="password" required>
                            </div>
                            <div class="mb-3">
                                <label class="col-form-label">Повторите пароль:</label>
                                <label></label><input type="password" class="form-control" name="re_pass" required>
                            </div>

                            <div class="modal-footer">
                                <button type="submit" name="command" value="registration" class="btn btn-primary">Добавить</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link rel="stylesheet" href="css/sweet-alert.css">
<script type="text/javascript">
    var status = document.getElementById("status").value;
    if (status == "failed") {
        swal("Внимание!", "${message}", "warning");
    } else if (status == "success"){
        swal("Внимание!", "${message}", "success");
    }
    // "Информация о пользователе изменена"
</script>
</body>
</html>