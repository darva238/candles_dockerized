<%--
  Created by IntelliJ IDEA.
  User: dasha
  Date: 05.05.2025
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Выход</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/signup" method="Post">
    <label>
        <input type="text" name="login" value="enter login">
    </label>
    <label>
        <input type="password" name="password" value="enter password">
    </label>
</form>
</body>
</html>
