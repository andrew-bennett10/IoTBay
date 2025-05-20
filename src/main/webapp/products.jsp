<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.model.Product" %>
<%@ page import="classes.model.dao.DAO" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.sql.SQLException" %>
<%

    DAO db = (DAO) session.getAttribute("db");

    LinkedList<Product> productList = null;

    try{
        productList = db.Products().allProducts();
        session.setAttribute("productList", productList);
    } catch (SQLException e) {
        e.printStackTrace();
    }

    String search = request.getParameter("search");


    String userType = (String) session.getAttribute("userType");
    if (userType == null) {
        userType = "customer";
    }
    String name = (String) session.getAttribute("name");
    if (name == null){
        name = "Guest";
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Main</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body class="main-page">
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
<div class="container">
    <h2>Products</h2>
    <form method="get">
    <% if (search != null & search != "") {
    %>
    <p>Current filter: <%=search%></p>
    <%
        }
    %>
    <label>Search
        <input type="text" name="search" placeholder="search here">
    </label>
        <button type="submit">Search</button>
    <% if (search != null & search != "") {
    %>
        <form method="get">
            <button type="submit">Clear search filter</button>
        </form>
    <%
        }
    %>
    </form>
    <% for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            session.setAttribute("product_" + i, product);
            if (search == null) {
    %>
        <p><%=product.getName()%> | $<%=product.getPrice()%> | <%=product.getStock()%></p>
        <a href="product.jsp?index=<%=i%>">
                <button type="submit">View Details</button>
        </a>
    <% if (userType.contains("staff")) {

    %>
        <a href="editProduct.jsp?index=<%=i%>">
            <button type="submit">Edit Details</button>
        </a>
        <form action="DeleteProductServlet" method="post">
            <button type="submit" name="id" value="<%=i + 1%>">Remove Product</button>
        </form>
    <%
        }
    %>
    <%
        }
            else {
                if (product.getName().toLowerCase().contains(search.toLowerCase())) {
    %>
        <p><%=product.getName()%> | $<%=product.getPrice()%> | <%=product.getStock()%></p>
        <a href="product.jsp?index=<%=i%>">
            <button type="submit">View Details</button>
        </a>
    <% if (userType.contains("staff")) {

    %>
        <a href="editProduct.jsp?index=<%=i%>">
            <button type="submit">Edit Details</button>
        </a>
        <form action="DeleteProductServlet" method="post">
            <button type="submit" name="id" value="<%=i + 1%>">Remove Product</button>
        </form>
    <%
        }
    %>
    <%
            }
        }
    }
    Integer size = 1;
        for (int i = 0; i < productList.size(); i++) {
           size += 1;
        }
    session.setAttribute("size", size);
        if (userType.contains("staff")) {
    %>
        <a href="createProduct.jsp">
            <button type="submit">Create Product</button>
        </a>
    <%
        }
    %>
</div>
</body>
</html>