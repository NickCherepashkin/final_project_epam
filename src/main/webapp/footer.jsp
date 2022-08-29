<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language" var="lang"/>

<%--<fmt:message bundle="${lang}" key="language.title" var="site_title" />--%>
<%--<fmt:message bundle="${lang}" key="language.button.menu" var="menu" />--%>
<%--<fmt:message bundle="${lang}" key="language.link.menu-home" var="text_home" />--%>

<footer class="footer text-center">
    <div class="container">
        <div class="row">
            <div class="col-lg-4 mb-5 mb-lg-0">
                <h4 class="text-uppercase mb-4">Адрес</h4>
                <p class="lead mb-0">
                    123456, г. Минск <br /> пр-т Независимости, 37
                </p>
            </div>
            <div class="col-lg-4 mb-5 mb-lg-0">
                <h4 class="text-uppercase mb-4">Время работы</h4>
                <p class="lead mb-0">
                    ПН-ПТ: 9:00-18:00 <br/>
                    СБ: 9:00-15:00 <br/>
                    ВСКР: ВЫХОДНОЙ
                </p>
            </div>
            <div class="col-lg-4">
                <h4 class="text-uppercase mb-4">Контакты</h4>
                <p class="lead mb-0">
                    +375 (29) 356-65-89
                </p>
            </div>
        </div>
    </div>
</footer>
<div class="copyright py-4 text-center text-white">
    <div class="container">
        <small>Copyright &copy; 2022 Veronika Drozdova</small>
    </div>
</div>