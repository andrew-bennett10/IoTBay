<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.model.dao.DAO" %>
<%

    DAO db = (DAO) session.getAttribute("db");


    Integer size = (Integer) session.getAttribute("size");
    String name = (String) session.getAttribute("name");

    if (name == null){
        name = "Guest";
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Product</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<nav class="navigation">
    <ul>
        <li class="specialNav">IoTBay Digital Services</li>
        <li><a href="main.jsp">Home</a></li>
        <li><a href="#">About</a></li>
        <li><a href="products.jsp">Products</a></li>
        <%
            if (!name.equals("Guest")) {

        %>
        <li><a href="profile.jsp">Profile</a></li>
        <li><a href="logout.jsp">Logout</a></li>
        <li><a href="PaymentHistoryServlet">View Payment History</a></li>
        <li><a href="payment.jsp">Make Payment</a></li>
        <%
        } else {
        %>
        <li><a href="index.jsp">Sign in</a></li>
        <%
            }
        %>
    </ul>
</nav>
<body class="main-page">
<div class="container">
    <h2>Create Product</h2>
    <form action="CreateProductServlet" method="post">
        <input type="hidden" name="id" value="<%=size%>">
        <label>Name
            <input type="text" name="name" required>
        </label>
        <label>Description
            <input type="text" name="description" required>
        </label>
        <label>Stock
            <input type="number" name="stock" required min="1" max="10000">
        </label>
        <label>Price
            <input type="number" name="price" step="0.05" required min="1" max="1000">
        </label>
        <label>Supplier
            <input type="text" name="supplier" required>
        </label>
        <input type="submit" name="update" value="Create">
        <button type="submit" formaction="products.jsp" formnovalidate>Back to Products</button>
    </form>
</div>
</body>
</html>