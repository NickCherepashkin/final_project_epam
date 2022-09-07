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

  <c:if test="${ordersList == null}">
    <jsp:forward page="/orders-list">
      <jsp:param name="command" value="get_orders" />
    </jsp:forward>
  </c:if>

  <body id="page-top">
    <jsp:include page="menu.jsp" />
    <input type="hidden" id="status" value="${requestScope.get("status")}">
    <section class="page-section-books novelty" id="novelty">
      <div class="container">
        <h2 class="page-section-heading text-center text-uppercase text-secondary mb-0">Заказы</h2>

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
                <form action="orders-list" method="post">
                  <input type="hidden" value="get_orders" name="command" >
                  <div class="page-section-heading d-lg-inline-flex mb-0" >
                    <label class="form-control" ><h6>Cортировать по:</h6></label>
                    <button type="submit" class="search form-control" name="sort_param" value="id">номеру</button>
                    <c:if test="${user.idRole == 1}" >
                      <button type="submit" class="search form-control" name="sort_param" value="fio" >фамилии</button>
                    </c:if>
                    <button type="submit" class="search form-control" name="sort_param" value="status" >статусу</button>
                  </div>
                </form>

              </td>
<%--              <td align="center" width="50%">--%>
<%--                <form method="get">--%>
<%--                  <div class="page-section-heading d-lg-inline-flex mb-3" >--%>
<%--                    <input type="text" name="find_param" class="search form-control-lg " placeholder="По автору, названию..." required>--%>
<%--                    <button type="submit" name="command" class="btn btn-primary" value="find_books">Поиск</button>--%>
<%--                  </div>--%>
<%--                </form>--%>
<%--              </td>--%>
            </tr>
          </table>
        </div>

        <c:forEach var="order" items="${ordersList}">
          <div class="modal-dialog modal-lg">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="editAuthorLabel">Заказ №${order.idOrder}</h5>
                <c:if test="${user.idRole == 1}" >
                  <h5 class="modal-title" id="editAuthorLabel">${order.fio}</h5>
                </c:if>
              </div>
              <div class="modal-body">
                <form method="post" action="orders-list">
                  <input type="hidden"  name="command" value="edit_order_status">
                  <input type="hidden" class="form-control" name="id_order" value="${order.idOrder}">
                  <div class="mb-3">
                    <table>
                      <tr>
                        <td rowspan="2" ><img width="45px" height="65px" src="assets/img/books/${order.idBook}.png" alt=""></td>
                        <td  class="descript-table"><h5>Название: </h5></td>
                        <td  class="descript-table">${order.titleBook}</td>
                      </tr>
                      <tr>
                        <td  class="descript-table"><h5>Автор(ы): </h5></td>
                        <td  class="descript-table">${order.author}</td>
                      </tr>
                    </table>
                  </div>
                  <div class=d-inline-block></div>
                  <div class="mb-3">
                    <table width="100%">
                      <tr>
                        <td align="center"><label> Статус заказа: </label></td>
                        <td><input type="text" class="form-control" name="order_status" value="${order.statusOrder}" readonly="readonly"></td>
                        <td align="right">
                          <c:if test="${user.idRole == 1}" >
                            <c:if test="${order.idStatus == 1}" >
                              <button type="submit" name="id_status" value="2" class="btn btn-primary">Выдать</button>
                              <button type="submit" name="id_status" value="4" class="btn btn-primary">Отменить</button>
                            </c:if>
                            <c:if test="${order.idStatus == 2}" >
                              <button type="submit" name="id_status" value="3" class="btn btn-primary">Возврат</button>
                            </c:if>
                          </c:if>
                          <c:if test="${user.idRole == 2}" >
                            <c:if test="${order.idStatus == 1}" >
                              <button type="submit" name="id_status" value="4" class="btn btn-primary">Отменить</button>
                            </c:if>
                          </c:if>
                        </td>
                      </tr>
                    </table>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </c:forEach>
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
      }
    </script>
  </body>
</html>


