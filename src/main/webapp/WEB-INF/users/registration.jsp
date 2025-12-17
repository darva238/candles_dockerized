<%--
  Created by IntelliJ IDEA.
  User: dasha
  Date: 05.05.2025
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Регистрация</title>
</head>
<body>
<div class="container">
    <h2>Регистрация</h2>
    <form method="post" action="<%= request.getContextPath() + "/registration" %>">
        <div class="form-group">
            <label>Логин</label><br/>
            <input type="text" name="login" autocomplete="off" minlength="4" required><br/>
        </div>
        <div class="form-group">
            <label>Пароль</label><br/>
            <input type="password" name="password" autocomplete="off" id="passwordField" minlength="6" required>
            <span class="toggle-password" onclick="togglePassword()">
      </span>
        </div>
        <br/><br/>
        <button type="submit" class="register">Зарегистрироваться</button>
    </form>

    <%
        String error = (String) request.getAttribute("error");
        if (error != null && !error.isEmpty()) {
    %>
    <p class="error"><%=error%>
    </p><%
    }%>
</div>
</body>
</html>
