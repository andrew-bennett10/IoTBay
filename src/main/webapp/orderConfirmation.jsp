<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
  <title>Order Confirmation</title>
  <link rel="stylesheet" type="text/css" href="styles/style.css">
  <style>
    .confirmation-box {
      background: #fff;
      padding: 20px;
      width: 350px;
      border-radius: 10px;
      margin: 40px auto;
      text-align: center;
      box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }

    .confirmation-box h2 {
      color: #333;
    }

    .confirmation-box a {
      display: inline-block;
      margin-top: 20px;
      padding: 10px 15px;
      background-color: #007BFF;
      color: white;
      text-decoration: none;
      border-radius: 5px;
    }

    .confirmation-box a:hover {
      background-color: #0056b3;
    }
  </style>
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
    <li><a href="createAndViewUsers.jsp">User Management</a></li>
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
    <h1 class="MainTitle">Thank You, <%=name%>!</h1>
  </div>
</section>

<main>
  <div class="confirmation-box">
    <h2>Order Confirmed</h2>
    <p>You ordered <strong><%= session.getAttribute("quantity") %></strong> x
      <strong><%= session.getAttribute("productName") %></strong></p>
    <p>Unit Price: $<%= session.getAttribute("unitPrice") %></p>
    <p>Total Paid: <strong>$<%= session.getAttribute("totalPrice") %></strong></p>
    <a href="payment.jsp">Pay now!</a>
  </div>
</main>
</body>
</html>
