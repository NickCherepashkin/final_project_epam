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

  <c:if test="${bookList == null}">
    <jsp:forward page="/books-list">
      <jsp:param name="command" value="get_book_list" />
    </jsp:forward>
  </c:if>

  <body id="page-top">
    <jsp:include page="menu.jsp" />
    <input type="hidden" id="status" value="${requestScope.get("status")}">
    <section class="page-section-books novelty" id="novelty">
      <div class="container">
        <h2 class="page-section-heading text-center text-uppercase text-secondary mb-0">Книги</h2>
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
              <td align="center">
                <form action="books-list" method="post">
                  <input type="hidden" value="sorted_books" name="command" >
                  <div class="page-section-heading d-lg-inline-flex mb-0" >
                    <label class="form-control" >Cортировать по:</label>
                    <button type="submit" class="search form-control" name="sort_param" value="title">названию</button>
                    <button type="submit" class="search form-control" name="sort_param" value="author" >автору</button>
                    <button type="submit" class="search form-control" name="sort_param" value="genre" >жанру</button>
                  </div>
                </form>
              </td>
              <td align="center">
                <form method="get" action="books-list">
                  <div class="page-section-heading d-lg-inline-flex mb-3" >
                    <input type="text" name="find_param" class="search form-control-lg " placeholder="По автору, названию..." required>
                    <button type="submit" name="command" class="btn btn-primary" value="find_books">Поиск</button>
                  </div>
                </form>
              </td>
              <td align="center">
                <div class="page-section-heading d-lg-inline-flex mb-0" >
                  <input type="button" data-bs-toggle="modal" data-bs-target="#addBookInfo" class="search form-control-lg " value="Добавить новую книгу">
                </div>
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

        <c:forEach var="book" items="${bookList}">
          <div class="modal-dialog modal-lg">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="editAuthorLabel">${book.title}</h5>
              </div>
              <div class="modal-body">
                <form method="post" action="books-list">
                  <input type="hidden"  name="id_book" value="${book.id}">
                  <div class="mb-3">
                    <table>
                      <tr>
                        <td rowspan="5" ><img width="120px" height="140px" src="assets/img/books/${book.id}.png" alt=""></td>
                        <td  class="descript-table"><h6>Автор(ы): </h6></td>
                        <td  class="descript-table">${book.author}</td>
                      </tr>
                      <tr>
                        <td  class="descript-table"><h6>Жанр: </h6></td>
                        <td  class="descript-table">${book.genreTitle}</td>
                      </tr>
                      <tr>
                        <td  class="descript-table"><h6>Год: </h6></td>
                        <td  class="descript-table">${book.year}</td>
                      </tr>
                      <tr>
                        <td  class="descript-table"><h6>Страниц: </h6></td>
                        <td  class="descript-table">${book.pages}</td>
                      </tr>
                      <tr>
                        <td  class="descript-table"><h6>Язык: </h6></td>
                        <td  class="descript-table">${book.language}</td>
                      </tr>
                    </table>
                  </div>
                  <div class="mb-3">
                    <label for="description-text" class="col-form-label text-center">Описание:</label>
                    <textarea rows="5" class="form-control" id="description-text" readonly="readonly">${book.description}</textarea>
                  </div>
                  <div class=d-inline-block></div>
                  <div class="modal-footer">
                    <button class="btn btn-primary" type="button" data-bs-toggle="modal" data-bs-target="#editBookInfo${book.id}">Редактировать</button>
                    <button type="submit" name="command" value="delete_book" class="btn btn-primary">Удалить</button>
                  </div>

                </form>
              </div>
            </div>
          </div>

          <div class="modal fade" id="editBookInfo${book.id}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="editBookInfoLabel" aria-hidden="true">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id=" ">Редактирование данных</h5>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
                </div>
                <div class="modal-body">
                  <form  action="books-list" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                      <label class="col-form-label">Название книги:</label>
                      <input type="hidden" id="id_book" name="id" class="form-control" value="${book.id}">
                      <label for="title_book"></label><input type="text" id="title_book" class="form-control" name="title" value="${book.title}">
                    </div>
                    <div class="mb-3">
                      <label class="col-form-label">Автор:</label>
                      <label for="title_book"></label>
                      <select class="form-control" name="id_author">
                        <c:forEach var="author" items="${authorsList}">
                          <c:if test="${author.name == book.author}" >
                            <option selected value="${author.id}">${author.name}</option>
                          </c:if>
                          <c:if test="${author.name != book.author}" >
                            <option value="${author.id}">${author.name}</option>
                          </c:if>
                        </c:forEach>
                      </select>
<%--                      <input type="text" id="title_book1" class="form-control" name="title" value="${book.author}">--%>
                    </div>
                    <div class="mb-3">
                      <label class="col-form-label">Жанр:</label>
                      <select class="form-control" name="id_genre">
                        <c:forEach var="genre" items="${genresList}">
                          <c:if test="${genre.title == book.genreTitle}" >
                            <option selected value="${genre.id}">${genre.title}</option>
                          </c:if>
                          <c:if test="${genre.title != book.genreTitle}" >
                            <option value="${genre.id}">${genre.title}</option>
                          </c:if>
                        </c:forEach>
                      </select>
                    </div>
                    <div class="mb-3">
                      <label class="col-form-label">Год:</label>
                      <label for="year"></label><input type="text" id="year" class="form-control" name="year" value="${book.year}">
                    </div>
                    <div class="mb-3">
                      <label class="col-form-label">Количество страниц:</label>
                      <label for="pages"></label><input type="text" id="pages" class="form-control" name="pages" value="${book.pages}">
                    </div>
                    <div class="mb-3">
                      <label class="col-form-label">Язык:</label>
                      <label for="language"></label><input type="text" id="language" class="form-control" name="language" value="${book.language  }">
                    </div>
                    <div class="mb-3">
                      <label class="col-form-label">Обложка:</label>
                      <label></label><input type="file" class="form-control" name="file" required>
                    </div>
                    <div class="mb-3">
                      <label for="description" class="col-form-label text-center">Описание:</label>
                      <textarea rows="5" class="form-control" id="description" name="description">${book.description}</textarea>
                    </div>
                    <div class="modal-footer">
                      <button type="submit" name="command" value="edit_book_info" class="btn btn-primary">Сохранить</button>
                      <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </c:forEach>

        <div class="modal fade" id="addBookInfo" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="addBookInfoLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title">Добавление новой книги</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
              </div>
              <div class="modal-body">
                <form  action="books-list" method="post" enctype="multipart/form-data">
                  <div class="mb-3">
                    <label class="col-form-label">Название книги:</label>
                    <label for="title_new_book"></label><input type="text" id="title_new_book" class="form-control" name="title" required>
                  </div>
                  <div class="mb-3">
                    <label class="col-form-label">Автор:</label>
                    <select class="form-control" name="id_author">
                      <c:forEach var="author" items="${authorsList}">
                        <option value="${author.id}">${author.name}</option>
                      </c:forEach>
                    </select>
                  </div>
                  <div class="mb-3">
                    <label class="col-form-label">Жанр:</label>
                    <select class="form-control" name="id_genre">
                      <c:forEach var="genre" items="${genresList}">
                          <option value="${genre.id}">${genre.title}</option>
                      </c:forEach>
                    </select>
                  </div>
                  <div class="mb-3">
                    <label class="col-form-label">Год:</label>
                    <label for="new_year"></label><input type="text" id="new_year" class="form-control" name="year" required>
                  </div>
                  <div class="mb-3">
                    <label class="col-form-label">Количество страниц:</label>
                    <label for="new_pages"></label><input type="text" id="new_pages" class="form-control" name="pages" required>
                  </div>
                  <div class="mb-3">
                    <label class="col-form-label">Язык:</label>
                    <label for="new_language"></label><input type="text" id="new_language" class="form-control" name="language" required>
                  </div>
                  <div class="mb-3">
                    <label class="col-form-label">Обложка:</label>
                    <label></label><input type="file" class="form-control" name="file" required>
                  </div>
                  <div class="mb-3">
                    <label for="new_description" class="col-form-label text-center">Описание:</label>
                    <textarea rows="5" class="form-control" id="new_description" name="description" required></textarea>
                  </div>
                  <div class="modal-footer">
                    <button type="submit" name="command" value="add_book" class="btn btn-primary">Добавить</button>
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
      if (status == "success") {
        swal("Внимание!", ${message}, "success");
      }
    </script>
  </body>
</html>


