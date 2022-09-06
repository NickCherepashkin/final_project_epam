<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language" var="lang"/>

<fmt:message bundle="${lang}" key="footer.address" var="address_txt"/>
<fmt:message bundle="${lang}" key="footer.street" var="street_txt"/>
<fmt:message bundle="${lang}" key="footer.city" var="city_txt"/>
<fmt:message bundle="${lang}" key="footer.work_time" var="work_time_txt"/>
<fmt:message bundle="${lang}" key="footer.weekdays" var="weekdays_txt"/>
<fmt:message bundle="${lang}" key="footer.stday" var="stday_txt"/>
<fmt:message bundle="${lang}" key="footer.snday" var="snday_txt"/>
<fmt:message bundle="${lang}" key="footer.contacts" var="contacts_txt"/>

<footer class="footer text-center">
    <div class="container">
        <div class="row">
            <div class="col-lg-4 mb-5 mb-lg-0">
                <h4 class="text-uppercase mb-4">${address_txt}</h4>
                <p class="lead mb-0">
                    123456, ${city_txt} <br /> ${street_txt}, 37
                </p>
            </div>
            <div class="col-lg-4 mb-5 mb-lg-0">
                <h4 class="text-uppercase mb-4">${work_time_txt}</h4>
                <p class="lead mb-0">
                    ${weekdays_txt} 9:00-18:00 <br/>
                    ${stday_txt} 9:00-15:00 <br/>
                    ${snday_txt}
                </p>
            </div>
            <div class="col-lg-4">
                <h4 class="text-uppercase mb-4">${contacts_txt}</h4>
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