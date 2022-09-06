<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language" var="lang"/>

<fmt:message bundle="${lang}" key="about.about_txt" var="about_txt"/>
<fmt:message bundle="${lang}" key="about.article_header_1" var="article_header_1"/>
<fmt:message bundle="${lang}" key="about.article_text_1" var="article_text_1"/>
<fmt:message bundle="${lang}" key="about.article_header_2" var="article_header_2"/>
<fmt:message bundle="${lang}" key="about.article_text_2" var="article_text_2"/>

<!DOCTYPE html>

<html>
    <head>
        <meta charset="utf-8" />
        <title>Sherlock - on-line library</title>
        <script src="https://use.fontawesome.com/releases/v5.15.4/js/all.js" crossorigin="anonymous"></script>
        <link href="css/index-styles.css" rel="stylesheet" />
    </head>
    <body>
    <jsp:include page="menu.jsp" />
        <section class="page-section bg-primary text-white mb-0" id="about">
            <div class="container">
                <h2 class="page-section-heading text-center text-uppercase text-white">${about_txt}</h2>
                <div class="divider-custom divider-light">
                    <div class="divider-custom-line"></div>
                    <div class="divider-custom-icon">
                        <i class="fas fa-star"></i>
                    </div>
                    <div class="divider-custom-line"></div>
                </div>
                <div class="row">
                    <div class="col-lg-4 ms-auto">
                        <p class="lead"><h5>${article_header_1}</h5><br>"${article_text_1}"</p>
                    </div>
                    <div class="col-lg-4 me-auto">
                        <p class="lead"><h5>${article_header_2}</h5><br>"${article_text_2}"</p>
                    </div>
                </div>
            </div>
        </section>
    <jsp:include page="footer.jsp" />
    </body>
</html>