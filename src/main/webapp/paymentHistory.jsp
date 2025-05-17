<%--
  Created by IntelliJ IDEA.
  User: Fuzzy
  Date: 17/05/2025
  Time: 6:29 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.model.PaymentDetail" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.LinkedHashMap" %>

<html>
<head>
  <title>Payment History</title>
  <link rel="stylesheet" type="text/css" href="styles/style.css">
  <style>
    table {
      width: 90%;
      margin: 20px auto;
      border-collapse: collapse;
    }
    th, td {
      border: 1px solid #ddd;
      padding: 8px;
      text-align: left;
    }
    th {
      background-color: #f2f2f2;
    }
    .no-payments, .no-stored-details {
      text-align: center;
      margin-top: 20px;
      color: #555;
    }
    .container {
      width: 90%;
      margin: auto;
      padding-top: 20px;
    }
    h1, h2 {
      text-align: center;
    }
    .filter-form {
      margin-bottom: 20px;
      text-align: center;
    }
    .filter-form input[type="text"], .filter-form input[type="date"] {
      padding: 8px;
      margin: 0 10px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    .filter-form input[type="submit"], .delete-button {
      padding: 8px 15px;
      background-color: #007bff;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }
    .filter-form input[type="submit"]:hover, .delete-button:hover {
      background-color: #0056b3;
    }
    .delete-button {
      background-color: #dc3545;
    }
    .delete-button:hover {
      background-color: #c82333;
    }
    .message {
      text-align:center;
      margin-bottom: 15px;
    }
    .message.error {
      color: red;
    }
    .message.success {
      color: green;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>Your Payment Activity</h1>

  <%
    String generalError = (String) session.getAttribute("generalError");
    if (generalError != null) {
  %>
  <p class="message error"><%= generalError %></p>
  <%
      session.removeAttribute("generalError");
    }
    String generalMessage = (String) session.getAttribute("generalMessage");
    if (generalMessage != null) {
  %>
  <p class="message success"><%= generalMessage %></p>
  <%
      session.removeAttribute("generalMessage");
    }
    String filterError = (String) session.getAttribute("filterError");
    if (filterError != null) {
  %>
  <p class="message error"><%= filterError %></p>
  <%
      session.removeAttribute("filterError");
    }
  %>

  <div class="filter-form">
    <form method="GET" action="PaymentHistoryServlet">
      Filter Transactions by Order ID:
      <input type="text" name="orderIdFilter" value="<%= request.getAttribute("orderIdFilter") != null ? request.getAttribute("orderIdFilter") : "" %>">
      Filter Transactions by Date:
      <input type="date" name="dateFilter" value="<%= request.getAttribute("dateFilter") != null ? request.getAttribute("dateFilter") : "" %>">
      <input type="submit" value="Filter Transactions">
    </form>
  </div>

  <%
    List<PaymentDetail> paymentHistory = (List<PaymentDetail>) request.getAttribute("paymentHistory");
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
  %>

  <h2>Payment Transactions</h2>
  <%
    if (paymentHistory != null && !paymentHistory.isEmpty()) {
  %>
  <table>
    <thead>
    <tr>
      <th>Order ID</th>
      <th>Payment Date</th>
      <th>Amount</th>
    </tr>
    </thead>
    <tbody>
    <% for (PaymentDetail payment : paymentHistory) { %>
    <tr>
      <td><%= payment.getOrderId() %></td>
      <td><%= payment.getPaymentDate() != null ? sdf.format(payment.getPaymentDate()) : "N/A" %></td>
      <td>$<%= String.format("%.2f", payment.getAmount()) %></td>
    </tr>
    <% } %>
    </tbody>
  </table>
  <% } else if (request.getAttribute("paymentHistory") != null) { %>
  <p class="no-payments">No payment transactions found matching your criteria.</p>
  <% } %>

  <hr style="margin: 40px auto; width: 80%;">

  <h2>Stored Payment Details</h2>
  <%
    if (paymentHistory != null && !paymentHistory.isEmpty()) {
      Map<String, PaymentDetail> uniquePaymentDetails = new LinkedHashMap<>();
      for (PaymentDetail payment : paymentHistory) {
        String cardKey = (payment.getCardNumber() != null ? payment.getCardNumber() : "N/A_CN") + "|" +
                (payment.getExpiryDate() != null ? payment.getExpiryDate() : "N/A_ED") + "|" +
                (payment.getCardName() != null ? payment.getCardName() : "N/A_CHN") + "|" +
                (payment.getCvc() != null ? payment.getCvc() : "N/A_CVC");
        uniquePaymentDetails.putIfAbsent(cardKey, payment);
      }

      if (!uniquePaymentDetails.isEmpty()) {
  %>
  <table>
    <thead>
    <tr>
      <th>Cardholder Name</th>
      <th>Card Number</th>
      <th>Expiry Date</th>
      <th>CVC</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <% for (PaymentDetail uniqueDetail : uniquePaymentDetails.values()) { %>
    <tr>
      <td><%= uniqueDetail.getCardName() != null ? uniqueDetail.getCardName() : "N/A" %></td>
      <td><%= uniqueDetail.getCardNumber() != null ? uniqueDetail.getCardNumber() : "N/A" %></td>
      <td><%= uniqueDetail.getExpiryDate() != null ? uniqueDetail.getExpiryDate() : "N/A" %></td>
      <td><%= uniqueDetail.getCvc() != null ? uniqueDetail.getCvc() : "N/A" %></td>
      <td>
        <% if (uniqueDetail.getCardNumber() != null && !uniqueDetail.getCardNumber().isEmpty()) { %>
        <form action="DeletePaymentDetailServlet" method="POST" style="display:inline;">
          <input type="hidden" name="cardNumberToDelete" value="<%= uniqueDetail.getCardNumber() %>">
          <input type="submit" value="Delete" class="delete-button" onclick="return confirm('Are you sure you want to delete this payment method?');">
        </form>
        <% } else { %>
        N/A
        <% } %>
      </td>
    </tr>
    <% } %>
    </tbody>
  </table>
  <%
  } else {
  %>
  <p class="no-stored-details">No stored payment details found in the current transaction view.</p>
  <%
    }
  } else if (request.getAttribute("paymentHistory") != null) {
  %>
  <p class="no-stored-details">No payment history available to display stored payment details.</p>
  <%
    }
  %>

  <p style="text-align: center; margin-top: 20px;">
    <a href="main.jsp">Back to Main Page</a>
  </p>
</div>
</body>
</html>>