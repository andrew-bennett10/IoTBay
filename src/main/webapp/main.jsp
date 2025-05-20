<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.model.Customer" %>
<%@ page import="classes.model.User" %>
<%@ page import="classes.model.Staff" %>
<%
    String userType = (String) session.getAttribute("userType");
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
<%--        <%--%>
<%--            if("staff".equalsIgnoreCase(userType)){//&& "Admin".equalsIgnoreCase((String)session.getAttribute("role"))--%>
<%--        %>--%>
        <li><a href="createAndViewUsers.jsp">User Management</a></li>
<%--        <%--%>
<%--            }--%>
<%--        %>--%>
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
<section class="BackImg">
    <div class="InnerParra">
        <h1 class="MainTitle">Welcome <%=name%> to IoTBay</h1>
    </div>
</section>
<main >
    <p class="FillerText">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dolore et molestias non praesentium sit. Alias dolor, eaque in incidunt ipsa ipsum labore magni, nemo numquam quaerat quas rem suscipit temporibus! Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dolore et molestias non praesentium sit. Alias dolor, eaque in incidunt ipsa ipsum labore magni, nemo numquam quaerat quas rem suscipit temporibus! Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dolore et molestias non praesentium sit. Alias dolor, eaque in incidunt ipsa ipsum labore magni, nemo numquam quaerat quas rem suscipit temporibus!</p>
</main>
</body>
</html>