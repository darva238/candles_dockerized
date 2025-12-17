<%--
  Created by IntelliJ IDEA.
  User: dasha
  Date: 05.05.2025
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Candle" %>

<html>
<head>
    <title>Свечи</title>
</head>
<body>
<header>
    <h1>Свечи</h1>
</header>

<div class="container">

    <form class="search" action="<%= request.getContextPath() + "/candles" %>" method="get">
        <input type="text" name="search" placeholder="Поиск по названию"
               value="<%=request.getParameter("search") != null ? request.getParameter("search") : ""%>">
        <button type="submit" name="action" value="search">Поиск</button>
        <button type="reset" onclick="window.location.href = '<%=request.getContextPath() + "/candles"%>';">Сбросить
            поиск
        </button>
    </form>

    <table>
        <thead>
        <tr>
            <th>Название</th>
            <th>Стоимость</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Candle> candles = (List<Candle>) request.getAttribute("candles");
            if (candles != null) {
                for (Candle candle : candles) {
        %>
        <tr>
            <td><%= candle.getCandle() %>
            </td>
            <td><%= candle.getCost() %>
            </td>
            <td>

                <form action="<%=request.getContextPath() + "/candles"%>" method="post" style="display: inline;">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="id" value="<%= candle.getId() %>">
                    <div class="form-group">
                        Название<br/>
                        <input name="candle" value="<%= candle.getCandle() %>">
                    </div>
                    <div class="form-group">
                        Стоимость<br/>
                        <input type="number" step="0.01" name="cost" value="<%= candle.getCost() %>" required>
                    </div>
                    <button type="submit" class="edit-button">Сохранить</button>
                </form>


                <form action="<%=request.getContextPath() + "/candles"%>" method="post" style="display: inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="<%= candle.getId() %>">
                    <button type="submit" class="delete-button"
                            onclick="return confirm('Удалить свечу?')">Удалить
                    </button>
                </form>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>


    <form action="<%= request.getContextPath() + "/candles" %>" method="post">
        <input type="hidden" name="action" value="add">
        <div class="form-group">
            <br/><br/>Название<br/>
            <input name="candle" required>
        </div>
        <div class="form-group">
            Стоимость<br/>
            <input type="number" step="1" name="cost" min="1" max="1000000000000" required>
        </div>
        <button type="submit" class="add-button">Добавить свечу</button>
    </form>

    <form action="<%= request.getContextPath() + "/logout" %>" method="get">
        <button type="submit" class="logout-button">Выйти</button>
    </form>

</div>
</body>
</html>
