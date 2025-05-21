<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.model.Product" %>
<%@ page import="classes.model.dao.DAO" %>
<%

    DAO db = (DAO) session.getAttribute("db");


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
        <li><a href="createOrder.jsp">Create Order</a></li>
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
    <%
        String index = request.getParameter("index");
        if (index != null) {
            Product product = (Product) session.getAttribute("chosen_product" + index);
            if (product != null ) {
                System.out.println(product.getPrice());
    %>

    <h2>Product</h2>
    <p>Product: <%= product.getName() %></p>
    <p>Description: <%= product.getDescription() %></p>
    <p>Stock: <%= product.getStock() %></p>
    <p>Price: <%= product.getPrice() %></p>
    <p>Supplier: <%= product.getSupplier() %></p>
    <a href="products.jsp">
        <button type="submit">Back to Products</button>
    </a>
    <%
            }
        }
    %>
</div>
</body>
</html>