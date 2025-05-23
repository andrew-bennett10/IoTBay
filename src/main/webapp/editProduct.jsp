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

            if (product != null) {
    %>
                <h2>Edit Product</h2>
                <form action="EditProductServlet" method="post">
                    <input type="hidden" name="id" value="<%=product.getId()%>">
                    <label>Name
                        <input type="text" name="name" value="<%=product.getName()%>" required>
                    </label>
                    <label>Description
                        <input type="text" name="description" value="<%=product.getDescription()%>" required>
                    </label>
                    <label>Stock
                        <input type="number" name="stock" value="<%=product.getStock()%>" required min="1" max="10000">
                    </label>
                    <label>Price
                        <input type="number" name="price" step="0.05" value="<%=product.getPrice()%>" required min="1" max="1000">
                    </label>
                    <label>Supplier
                        <input type="text" name="supplier" value="<%=product.getSupplier()%>" required>
                    </label>
                    <input type="submit" name="update" value="Update">
                    <button type="submit" formaction="products.jsp" formnovalidate>Back to Products</button>
                </form>
    <%
            }
        }
    %>
</div>
</body>
</html>