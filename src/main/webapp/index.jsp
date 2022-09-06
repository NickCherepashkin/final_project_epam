<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language" var="lang"/>

<fmt:message bundle="${lang}" key="index.novelties" var="novelties"/>
<fmt:message bundle="${lang}" key="index.more_text" var="more_text"/>
<fmt:message bundle="${lang}" key="index.author_text" var="author_text"/>
<fmt:message bundle="${lang}" key="index.language_text" var="language_text"/>
<fmt:message bundle="${lang}" key="index.genre_text" var="genre_text"/>
<fmt:message bundle="${lang}" key="index.year_text" var="year_text"/>
<fmt:message bundle="${lang}" key="index.pages_text" var="pages_text"/>
<fmt:message bundle="${lang}" key="index.btn_close_description_text" var="btn_close_description_text"/>

<!DOCTYPE html>

<html>
    <head>
        <meta charset="utf-8" />
        <title>Sherlock - on-line library</title>
        <script src="https://use.fontawesome.com/releases/v5.15.4/js/all.js" crossorigin="anonymous"></script>
        <link href="css/index-styles.css" rel="stylesheet" />
    </head>

    <c:if test="${noveltyList == null}">
        <jsp:forward page="/home">
            <jsp:param name="command" value="get_novelty_list" />
        </jsp:forward>
    </c:if>

    <body id="page-top">
        <jsp:include page="menu-index.jsp" />
        <jsp:include page="header.jsp" />
        <section class="page-section-novelties novelty" id="novelty">
            <div class="container">
                <h2 class="page-section-heading text-center text-uppercase text-secondary">${novelties}</h2>
                <div class="divider-custom">
                    <div class="divider-custom-line"></div>
                    <div class="divider-custom-icon">
                        <i class="fas fa-star"></i>
                    </div>
                    <div class="divider-custom-line"></div>
                </div>
                <div class="row justify-content-center">
                    <c:forEach var="book" items="${noveltyList}">
                        <div class="col-md-6 col-lg-4 mb-5">
                            <div class="novelty-item mx-auto" data-bs-toggle="modal" data-bs-target="#noveltyModal${book.id}">
                                <div class="novelty-item-caption d-flex align-items-center justify-content-center h-100 w-100">
                                    <div class="novelty-item-caption-content text-center text-white">
                                        <i>${more_text}</i>
                                    </div>
                                </div>
                                <img class="img-fluid" src="assets/img/books/${book.id}.png" alt="..." />
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
                                                    <h2 class="novelty-modal-title text-center text-secondary text-uppercase mb-0">${book.title}</h2>
                                                    <div class="divider-custom">
                                                        <div class="divider-custom-line"></div>
                                                        <div class="divider-custom-icon">
                                                            <i class="fas fa-star"></i>
                                                        </div>
                                                        <div class="divider-custom-line"></div>
                                                    </div>
                                                    <div>
                                                        <table>
                                                            <style>
                                                                h5 {
                                                                    font-style: oblique;
                                                                }
                                                            </style>
                                                            <tr>
                                                                <td rowspan="5" ><img class="img-fluid rounded" src="assets/img/books/${book.id}.png" alt="..." /></td>
                                                                <td class="descript-table"><h5>${author_text} ${book.author}</h5></td>
                                                            </tr>
                                                            <tr><td class="descript-table"><h5> ${language_text} ${book.language}</h5></td></tr>
                                                            <tr><td class="descript-table"><h5> ${genre_text} ${book.genreTitle}</h5></td></tr>
                                                            <tr><td class="descript-table"><h5> ${year_text} ${book.year}</h5></td></tr>
                                                            <tr><td class="descript-table"><h5> ${pages_text} ${book.pages}</h5></td></tr>
                                                        </table>
                                                    </div>
                                                    <p class="mb-5">${book.description}</p>
                                                    <button class="btn-primary text-center" type="button" data-bs-dismiss="modal" > ${btn_close_description_text} </button>
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
        </section>
        <jsp:include page="footer.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
    </body>
</html>
