<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="servlets.lab2.Model" %>
<html>
<head>
    <title>Результаты</title>
</head>
<body>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>X</th>
        <th>Y</th>
        <th>R</th>
        <th>Факт</th>
        <th>Время Запроса</th>
        <th>Время выполнения</th>
    </tr>
    </thead>
    <tbody class="jsTableRes">
    <%
        List<Model> results = (List<Model>) session.getAttribute("results");
        if (results != null) {
            int counter = 1;
            for (Model result : results) {
    %>
    <tr>
        <td><%= counter++ %></td>
        <td><%= result.getX() %></td>
        <td><%= result.getY() %></td>
        <td><%= result.getR() %></td>
        <td><%= result.isHit() ? "Да" : "Нет" %></td>
        <td><%= result.getTime() %></td>
        <td> <%= result.getExecutionTime() %> микросекунд</td>

    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>
</body>
</html>
