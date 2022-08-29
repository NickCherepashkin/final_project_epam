<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language" var="lang"/>

<fmt:message bundle="${lang}" key="header.welcome" var="welcome" />
<fmt:message bundle="${lang}" key="header.welcome.new_search" var="new_search" />

<header class="masthead bg-primary text-white text-center">
    <div class="container d-flex align-items-center flex-column">
        <!-- Masthead Avatar Image-->
        <img class="masthead-avatar" src="images/logo.png" alt="Sherlock" width="200px" height="120px">
        <!-- Masthead Heading-->
        <h1 class="masthead-heading text-uppercase mb-0">${welcome}</h1>
        <!-- Icon Divider-->
        <div class="divider-custom divider-light">
            <div class="divider-custom-line"></div>
            <div class="divider-custom-icon">
                <i class="fas fa-star"></i>
            </div>
            <div class="divider-custom-line"></div>
        </div>
        <!-- Masthead Subheading-->
        <p class="masthead-subheading font-weight-light mb-0">${new_search}</p>
    </div>
</header>