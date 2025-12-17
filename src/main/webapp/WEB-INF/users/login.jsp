<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
<head>
    <title>Вход</title>
</head>
<body>
<h2>Вход</h2>
<form method="post" action="<%= request.getContextPath() + "/login" %>">
    <p>
        <label>Логин</label><br/>
        <input type="text" name="login">
    </p>
    <p>
        <label>Пароль</label><br/>
        <input type="password" name="password">
    </p>
    <button type="submit">Войти</button>
</form>

<%
    String error = (String) request.getAttribute("error");
    if (error != null && !error.isEmpty()) {
%>
<p style="color: red;"><%= error %>
</p>
<%
    }
%>

<form action="<%= request.getContextPath() + "/registration" %>" method="get">
    <button type="submit">Зарегистрироваться</button>
</form>
</body>
</html>
