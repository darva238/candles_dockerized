<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: dasha
  Date: 05.05.2025
  Time: 21:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Главная</title>
</head>
<body>
<div class="container">
    <h1>Добро пожаловать, ${sessionScope.login}!</h1>

    <%
        List<String> actions = (List<String>) request.getAttribute("actions");
        if (actions != null) {
    %>
    <p>Вы можете:</p>
    <ul>
        <%
            for (String action : actions) {
        %>
        <li><%= action %>
        </li>
        <%}%>
    </ul>
    <%}%>

    <form action="<%=request.getContextPath() + "/candles"%>" method="get">
        <button type="submit">К моим свечам</button>
    </form>

    <form action="<%= request.getContextPath() + "/logout" %>" method="get">
        <button type="submit" class="logout">Выйти</button>
    </form>
</div>
</body>
</html>

