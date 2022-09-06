<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language" var="lang"/>

<fmt:message bundle="${lang}" key="language.title" var="site_title" />
<fmt:message bundle="${lang}" key="language.button.menu" var="menu" />
<fmt:message bundle="${lang}" key="language.link.menu-home" var="text_home" />
<fmt:message bundle="${lang}" key="language.link.menu-catalog" var="text_catalog" />
<fmt:message bundle="${lang}" key="language.link.menu-about" var="text_about" />
<fmt:message bundle="${lang}" key="language.link.menu-contact" var="text_contact" />
<fmt:message bundle="${lang}" key="language.link.menu-login" var="text_login" />
<fmt:message bundle="${lang}" key="language.link.menu-authors" var="text_authors" />
<fmt:message bundle="${lang}" key="language.link.menu-book" var="text_book" />
<fmt:message bundle="${lang}" key="language.link.menu-genres" var="text_genres" />
<fmt:message bundle="${lang}" key="language.link.menu-profile" var="text_profile" />
<fmt:message bundle="${lang}" key="language.link.menu-orders" var="text_orders" />
<fmt:message bundle="${lang}" key="language.link.menu-readers" var="text_readers" />
<fmt:message bundle="${lang}" key="language.link.menu-logout" var="text_logout" />
<fmt:message bundle="${lang}" key="language.link.menu-info" var="text_info" />



<script src="js/menu.js"></script>

<nav class="navbar navbar-expand-lg bg-secondary text-uppercase fixed-top" id="mainNav">
  <div class="container">
    <a class="navbar-brand" href="#page-top">${site_title}</a>
  <button
          class="navbar-toggler text-uppercase font-weight-bold bg-primary text-white rounded"
          type="button" data-bs-toggle="collapse"
          data-bs-target="#navbarResponsive" aria-controls="navbarResponsive"
          aria-expanded="false" aria-label="Toggle navigation">
    ${menu} <i class="fas fa-bars"></i>
  </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">

      <ul class="navbar-nav ms-auto">
        <li class="nav-item mx-0 mx-lg-1">
          <a class="nav-link py-3 px-0 px-lg-3 rounded" href="#page-top">${text_home}</a></li>
        <c:if test="${user.idRole != 1}">
          <li class="nav-item mx-0 mx-lg-1">
            <a class="nav-link py-3 px-0 px-lg-3 rounded" href="catalog?command=get_book_list">${text_catalog}</a></li>
        </c:if>
        <li class="nav-item mx-0 mx-lg-1">
          <a class="nav-link py-3 px-0 px-lg-3 rounded" href="aboutUs">${text_about}</a></li>
        <li class="nav-item mx-0 mx-lg-1">
          <a class="nav-link py-3 px-0 px-lg-3 rounded" href="contactUs">${text_contact}</a></li>
        <c:if test="${user == null}">
          <li class="nav-item mx-0 mx-lg-1">
            <a id="signUp" class="nav-link py-3 px-0 px-lg-3 rounded" href="login.jsp">${text_login}</a></li>
        </c:if>
        <c:if test="${user != null}">
          <li class="nav-item mx-0 mx-lg-1">
            <div class="dropdown">
              <button onclick="myFunction()" class="dropbtn" id="btnProfile" >${text_profile}</button>
              <c:if test="${user.idRole == 2}">
                <div id="myDropdown" class="dropdown-content">
                  <a class="nav-link py-3 px-0 px-lg-3 rounded" href="userinfo?command=get_user_info&id=${user.id}">${text_info}</a>
                  <a class="nav-link py-3 px-0 px-lg-3 rounded" href="orders-list?command=get_orders_by_id">${text_orders}</a>
                  <a class="nav-link py-3 px-0 px-lg-3 rounded" href="logout?command=logout">${text_logout}</a>
                </div>
              </c:if>
              <c:if test="${user.idRole == 1}">
                <div id="myDropdown" class="dropdown-content">
                  <a class="nav-link py-3 px-0 px-lg-3 rounded" href="genres-list?command=get_genres_list">${text_genres}</a>
                  <a class="nav-link py-3 px-0 px-lg-3 rounded" href="authors-list?command=get_authors_list">${text_authors}</a>
                  <a class="nav-link py-3 px-0 px-lg-3 rounded" href="books.jsp">${text_book}</a>
                  <a class="nav-link py-3 px-0 px-lg-3 rounded" href="users.jsp">${text_readers}</a>
                  <a class="nav-link py-3 px-0 px-lg-3 rounded" href="orders-list?command=get_orders">${text_orders}</a>
                  <a class="nav-link py-3 px-0 px-lg-3 rounded" href="logout?command=logout">${text_logout}</a>
                </div>
              </c:if>
            </div>
          </li>
        </c:if>
        <c:if test="${sessionScope.language == 'en'}" >
          <li class="nav-item mx-0 mx-lg-1">
              <%--          <a class="nav-link py-3 px-0 px-lg-3 rounded" href="contactUs">RU</a></li>--%>
            <form action="home" method="get">
              <button class="nav-link py-3 px-0 px-lg-3 rounded" type="submit" name="command" value="change_lang">RU</button>
              <input type="hidden" name="language" value="ru">
            </form>

          </li>
        </c:if>

        <c:if test="${sessionScope.language == 'ru' || sessionScope.language == null}" >
          <li class="nav-item mx-0 mx-lg-1">
            <form>
              <button class="nav-link py-3 px-0 px-lg-3 rounded" type="submit" formaction="home" formmethod="post" name="command" value="change_lang">ENG</button>
              <input type="hidden" name="language" value="en">
            </form>
          </li>
        </c:if>
      </ul>

    </div>
  </div>
</nav>

