<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error</title>
</head>

<body>
<jsp:include page="menu.jsp" />
<br>
<br>
<h1>Oh, something went wrong, try again later.</h1>
${param.errorMsg}
<br>
<br>
<jsp:include page="footer.jsp" />
</body>

</html>