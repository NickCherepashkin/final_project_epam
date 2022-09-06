<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="utf-8" />
    <title>Sherlock - on-line library</title>
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
    <link href="https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet" type="text/css" />
    <link href="css/index-styles.css" rel="stylesheet" />
</head>
<body>
    <jsp:include page="menu.jsp" />
    <c:if test="${genresList == null}" >
        <jsp:forward page="/genres-list">
            <jsp:param name="command" value="get_genres_list"/>
        </jsp:forward>
    </c:if>
    <input type="hidden" id="status" value="${requestScope.get("status")}">
    <section class="page-section-books novelty" id="novelty">
        <div class="container">
            <form>
                <div id="genre" class="genre-header">
                    <h2 class="page-section-heading text-center text-uppercase text-secondary mb-0">Жанры</h2>
                    <input class="genre-input" type="text" name="genre" id="myInput" placeholder="Название жанра">
                    <button type="submit" formmethod="post" formaction="genres-list" name="command" value="add_genre" class="addGenreBtn">Добавить</button>
                </div>

                <div class="row justify-content-center">
                    <c:forEach var="genre" items="${genresList}" varStatus="counter">

                        <div class="genre-div">
                            <i class="genre-input"> ${counter.count}. ${genre.title} </i>
                            <button class="genreBtn" type="button" data-bs-toggle="modal" data-bs-target="#editGenre${genre.id}"> Редактировать </button>
                            <button type="submit" formmethod="post" class="genreBtn"><a href="genres-list?command=delete_genre&id=${genre.id}&title=${genre.title}">Удалить</a> </button>
                        </div>


                        <div class="modal fade" id="editGenre${genre.id}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="editGenreLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id=" ">Редактирование данных</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form>
                                            <div class="mb-3">
                                                <label class="col-form-label">Название жанра:</label>
                                                <input type="hidden" id="id_genre" name="id" class="form-control" value="${genre.id}">
                                                <label for="title_genre"></label><input type="text" id="title_genre" class="form-control" name="title" value="${genre.title}">
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                                                <button type="submit" name="command" value="edit_genre" class="btn btn-primary">Сохранить</button>
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
        const status = document.getElementById("status").value;
        if (status == "failed") {
            swal("Извините!", "${message}", "error");
        } else if (status == "success") {
            swal("Внимание!", "${message}", "success");
        }
    </script>
</body>
</html>
