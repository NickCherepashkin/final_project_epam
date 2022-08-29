<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="utf-8" />
    <title>Sherlock - on-line library</title>
    <script src="https://use.fontawesome.com/releases/v5.15.4/js/all.js" crossorigin="anonymous"></script>
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
    <link href="https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet" type="text/css" />
    <link href="css/index-styles.css" rel="stylesheet" />
</head>
<body>
    <jsp:include page="menu.jsp" />
    <c:if test="${authorsList == null}" >
        <jsp:forward page="/authors-list">
            <jsp:param name="command" value="get_authors_list"/>
        </jsp:forward>
    </c:if>
    <input type="hidden" id="status" value="${requestScope.get("status")}">
    <section class="page-section-books novelty" id="novelty">
        <div class="container">
            <form>
                <div id="author" class="genre-header">
                    <h2 class="page-section-heading text-center text-uppercase text-secondary mb-0">Авторы</h2>
                    <input class="genre-input" type="text" name="author" id="myInput" placeholder="ФИО автора">
                    <button type="submit" formmethod="post" formaction="authors-list" name="command" value="add_author" class="addGenreBtn">Добавить</button>
                </div>

                <div class="row justify-content-center">
                    <c:forEach var="author" items="${authorsList}" varStatus="counter">

                        <div class="genre-div">
                            <i class="genre-input"> ${counter.count}. ${author.name} </i>
                            <button class="genreBtn" type="button" data-bs-toggle="modal" data-bs-target="#editAuthor${author.id}"> Редактировать </button>
                            <button type="submit" formmethod="post" class="genreBtn"><a href="authors-list?command=delete_author&id=${author.id}&name=${author.name}">Удалить</a> </button>
                        </div>

                        <div class="modal fade" id="editAuthor${author.id}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="editAuthorLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="editAuthorLabel">Редактирование данных</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form>
                                            <div class="mb-3">
                                                <label class="col-form-label">ФИО автора:</label>
                                                <input type="hidden" name="id_author" class="form-control" value="${author.id}">
                                                <input type="text" class="form-control" name="name" value="${author.name}">
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                                                <button type="submit" name="command" value="edit_author" class="btn btn-primary">Сохранить</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </form>
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
            swal("Ошибка!", "${message}", "error");
        } else if (status == "success") {
            swal("Ура!", "${message}", "success");
        }
    </script>
</body>
</html>
