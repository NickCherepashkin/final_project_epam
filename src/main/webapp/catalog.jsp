<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

    <c:if test="${bookList == null}">
        <jsp:forward page="/catalog">
            <jsp:param name="command" value="get_book_list" />
        </jsp:forward>
    </c:if>

    <body id="page-top">
        <jsp:include page="menu.jsp" />

        <input type="hidden" id="status" value="${requestScope.get("status")}">
        <section class="page-section-books novelty" id="novelty">
            <div class="container">
                <!-- Novelty Section Heading-->
                <h2 class="page-section-heading text-center text-uppercase text-secondary mb-0">Книги</h2>
                <!-- Icon Divider-->
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
                                <form action="catalog" method="post">
                                    <input type="hidden" value="sorted_books" name="command" >
                                    <div class="page-section-heading d-lg-inline-flex mb-0" >
                                        <label class="form-control" >Cортировать по:</label>
                                        <button type="submit" class="search form-control" name="sort_param" value="title">названию</button>
                                        <button type="submit" class="search form-control" name="sort_param" value="author" >автору</button>
                                        <button type="submit" class="search form-control" name="sort_param" value="genre" >жанру</button>
                                    </div>
                                </form>

                            </td>
                            <td align="center" width="50%">
                                <form method="get">
                                    <div class="page-section-heading d-lg-inline-flex mb-3" >
                                        <input type="text" name="find_param" class="search form-control-lg " placeholder="По автору, названию..." required>
                                        <button type="submit" name="command" class="btn btn-primary" value="find_books">Поиск</button>
                                    </div>
                                </form>
                            </td>
                        </tr>
                    </table>
                </div>


                <div class="divider-custom">
                    <div class="divider-custom-line"></div>
                    <div class="divider-custom-icon">
                        <i class="fas fa-star"></i>
                    </div>
                    <div class="divider-custom-line"></div>
                </div>

                <!-- Novelty Grid Items-->
                <div class="row justify-content-center">
                    <c:forEach var="book" items="${bookList}">
                        <div class="col-md-6 col-lg-4 mb-5">
                            <div>
                                <div class="novelty-item mx-auto" data-bs-toggle="modal" data-bs-target="#noveltyModal${book.id}">
                                    <div class="novelty-item-caption d-flex align-items-center justify-content-center h-100 w-100">
                                        <div class="novelty-item-caption-content text-center text-white">
                                            <i>Подробнее</i>
                                        </div>
                                    </div>
                                    <img class="img-fluid" src="assets/img/books/${book.id}.png" alt="..." />
                                </div>
                                <c:if test="${user != null}">
                                    <div class="novelty-item mx-auto">
                                        <form method="post" action="add-order">
                                            <input type="hidden" name="command" value="add_order">
                                            <input type="hidden" name="id_user" value="${user.id}">
                                            <input type="hidden" name="id_book" value="${book.id}">
                                            <button type="submit" class="btn btn-primary btn-read" data-bs-dismiss="modal" >Читать</button>
                                        </form>
                                    </div>
                                </c:if>

                            </div>
                        </div>

                        <div class="novelty-modal modal fade" id="noveltyModal${book.id}"
                             tabindex="-1" aria-labelledby="noveltyModal${book.id}" aria-hidden="true">
                            <div class="modal-dialog modal-xl">
                                <div class="modal-content">
                                    <div class="modal-header border-0">
                                        <button class="btn-close" type="button" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body pb-5">
                                        <div class="container">
                                            <div class="row justify-content-center">
                                                <div class="col-lg-8">
                                                    <!-- novelty Modal - Title-->
                                                    <h2 class="novelty-modal-title text-center text-secondary text-uppercase mb-0">${book.title}</h2>
                                                    <!-- Icon Divider-->

                                                    <div class="divider-custom">
                                                        <div class="divider-custom-line"></div>
                                                        <div class="divider-custom-icon">
                                                            <i class="fas fa-star"></i>
                                                        </div>
                                                        <div class="divider-custom-line"></div>
                                                    </div>
                                                    <!-- novelty Modal - Image-->
                                                    <div>

                                                        <table>
                                                            <style>
                                                                h5 {
                                                                    font-style: oblique;
                                                                }
                                                            </style>
                                                            <tr>
                                                                <td rowspan="5" ><img class="img-fluid rounded" src="assets/img/books/${book.id}.png" alt="..." /></td>
                                                                <td class="descript-table"><h5>Автор(ы): ${book.author}</h5></td>
                                                            </tr>
                                                            <tr>
                                                                <td class="descript-table"><h5>Язык: ${book.language}</h5></td>
                                                            </tr>
                                                            <tr>
                                                                <td class="descript-table"><h5>Жанр(ы): ${book.genreTitle}</h5></td>
                                                            </tr>
                                                            <tr>
                                                                <td class="descript-table"><h5>Год: ${book.year}</h5></td>
                                                            </tr>
                                                            <tr>
                                                                <td class="descript-table"><h5> Количество страниц: ${book.pages}</h5></td>
                                                            </tr>
                                                        </table>
                                                    </div>

                                                    <!-- novelty Modal - Text-->
                                                    <p class="mb-4">${book.description}</p>
<%--                                                    <button class="btn btn-primary" href="#!"--%>
<%--                                                            data-bs-dismiss="modal">--%>
<%--                                                        <i class="fas fa-times fa-fw"></i> Закрыть описание--%>
<%--                                                    </button>--%>
                                                    <button class="btn-primary text-center" type="button" data-bs-dismiss="modal" >Закрыть описание</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">
                    <li class="page-item disabled">
                        <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
                    </li>
                    <li class="page-item"><a class="page-link" href="#">1</a></li>
                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                    <li class="page-item">
                        <a class="page-link" href="#">Next</a>
                    </li>
                </ul>
            </nav>

        </section>


        <!-- Footer-->
        <jsp:include page="footer.jsp" />

        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <%--<script src="../../js/scripts.js"></script>--%>
        <script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <link rel="stylesheet" href="css/sweet-alert.css">
        <script type="text/javascript">
            var status = document.getElementById("status").value;
            if (status == "success") {
                swal("Ура!", "Книга добавлена в Ваши заказы.", "success");
            } else if(${status == "failed"}){
                swal("Внимание!", "${message}", "error");
            }
        </script>

    </body>
</html>