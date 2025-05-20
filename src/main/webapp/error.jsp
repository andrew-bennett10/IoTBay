<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.PrintWriter" %>

<!DOCTYPE html>
<html>
<head>
    <title>Error - IoTBay</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body class="main-page">
<nav class="navigation">
    <ul>
        <li class="specialNav">IoTBay Digital Services</li>
        <li><a href="main.jsp">Home</a></li>
        <li><a href="#">About</a></li>
        <li><a href="products.jsp">Products</a></li>
        <li><a href="index.jsp">Sign In</a></li>
        <li><a href="profile.jsp">Profile</a></li>
        <li><a href="createOrder.jsp">Create Order</a></li>
        <li><a href="logout.jsp">Logout</a></li>
        <li><a href="PaymentHistoryServlet">View Payment History</a></li>
        <li><a href="payment.jsp">Make Payment</a></li>
    </ul>
</nav>

<section class="BackImg">
    <div class="InnerParra">
        <h1 class="MainTitle">Oops! Something went wrong.</h1>
    </div>
</section>

<main>
    <p class="FillerText">
        Sorry, but an unexpected error occurred while processing your request. Please try again later.
    </p>

    <%
        if (exception != null) {
    %>
    <div style="margin: 20px; padding: 20px; background-color: #f8d7da; color: #721c24; border-radius: 10px;">
        <strong>Error Details:</strong><br>
        <%= exception.getClass().getName() %>: <%= exception.getMessage() %>
    </div>
    <%
        }
    %>
</main>
</body>
</html>
