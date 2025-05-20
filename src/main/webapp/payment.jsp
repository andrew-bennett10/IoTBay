<%--
  Created by IntelliJ IDEA.
  User: Fuzzy
  Date: 17/05/2025
  Time: 5:36 pm
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.model.Customer" %>
<%@ page import="classes.model.dao.DAO" %>
<%@ page import="classes.model.PaymentDetail" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%
  // Check if user is logged in as a customer
  String customerName = (String) session.getAttribute("name");
  String userType = (String) session.getAttribute("userType");
  Customer loggedInCustomer = null;
  String customerEmail = null;

  // Variables for autofilling payment details
  String autoFillCardName = "";
  String autoFillCardNumber = "";
  String autoFillExpiryDate = "";
  String autoFillCvc = "";

  if ("customer".equals(userType)) {
    Object userObj = session.getAttribute("loggedInUser");
    if (userObj instanceof Customer) {
      loggedInCustomer = (Customer) userObj;
      customerEmail = loggedInCustomer.getEmail();
    }
  }

// Check if the user is logged in and has a valid customer object
  if (loggedInCustomer == null || customerEmail == null || customerEmail.trim().isEmpty() || customerName == null || customerName.trim().isEmpty()) {
    response.sendRedirect("index.jsp");
    return;
  }

  // Attempt to fetch latest payment details for autofill
  if (customerEmail != null && !customerEmail.trim().isEmpty()) {
    try {
      DAO dao = new DAO(); // Initialize DAO
      List<PaymentDetail> paymentHistory = dao.PaymentDetails().getPaymentsByEmail(customerEmail);
      if (paymentHistory != null && !paymentHistory.isEmpty()) {
        PaymentDetail latestPayment = paymentHistory.get(0); // getPaymentsByEmail sorts by date desc
        if (latestPayment != null) {
          autoFillCardName = latestPayment.getCardName() != null ? latestPayment.getCardName() : "";
          autoFillCardNumber = latestPayment.getCardNumber() != null ? latestPayment.getCardNumber() : "";
          autoFillExpiryDate = latestPayment.getExpiryDate() != null ? latestPayment.getExpiryDate() : "";
          autoFillCvc = latestPayment.getCvc() != null ? latestPayment.getCvc() : "";
        }
      }
    } catch (SQLException e) {
      System.err.println("Error fetching payment details for autofill: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Unexpected error during payment page setup: " + e.getMessage());
    }
  }


  // Retrieve cart total from session, if available - this may have to be changed depending on cart implementation
  Double cartTotal = (Double) session.getAttribute("cartTotal");
  if (cartTotal == null) {
    cartTotal = 0.0;
  }
  String orderId = (String) session.getAttribute("orderId"); // This assumes orderId is set before reaching payment - currently if it is null, it will be randomly generated
  if (orderId == null) {
    orderId = "N/A";
  }
%>




<!DOCTYPE html>
<html>
<head>
  <title>Payment Page</title>
  <link rel="stylesheet" type="text/css" href="styles/style.css">
  <script>
    function validateForm() {
      var cardNumber = document.forms["paymentForm"]["cardNumber"].value;
      var cardName = document.forms["paymentForm"]["cardName"].value;
      var expiryDate = document.forms["paymentForm"]["expiryDate"].value;
      var cvc = document.forms["paymentForm"]["cvc"].value;

      // Basic validation for empty fields and format
      if (cardNumber === "" || cardName === "" || expiryDate === "" || cvc === "") {
        alert("All fields must be filled out");
        return false;
      }

      if (!/^\d{13,19}$/.test(cardNumber)) {
        alert("Invalid card number format. Must be 13-19 digits.");
        return false;
      }

      if (!/^(0[1-9]|1[0-2])\/\d{2}$/.test(expiryDate)) {
        alert("Invalid expiry date format. Must be MM/YY.");
        return false;
      }

      if (!/^\d{3,4}$/.test(cvc)) {
        alert("Invalid CVC format. Must be 3 or 4 digits.");
        return false;
      }
      return true;
    }
  </script>
</head>
<body class="main-page">
<nav class="navigation">
  <ul>
    <li class="specialNav">IoTBay Digital Services</li>
    <li><a href="main.jsp">Home</a></li>
    <li><a href="#">About</a></li>
    <li><a href="products.jsp">Products</a></li>
    <li><a href="profile.jsp">Profile</a></li>
    <li><a href="createOrder.jsp">Create Order</a></li>
    <li><a href="logout.jsp">Logout</a></li>
  </ul>
</nav>

<main>

  <div class="payment-container">
    <%
      String productName = (String) session.getAttribute("productName");
      Integer quantity = (Integer) session.getAttribute("quantity");
      Double unitPrice = (Double) session.getAttribute("unitPrice");
      Double totalPrice = (Double) session.getAttribute("totalPrice");

      if (productName == null) productName = "Unknown";
      if (quantity == null) quantity = 0;
      if (unitPrice == null) unitPrice = 0.0;
      if (totalPrice == null) totalPrice = 0.0;
    %>

    <h2>Payment Details</h2>
    <p>Order ID: <%= orderId %></p>

    <p><strong>Product:</strong> <%= productName %></p>
    <p><strong>Quantity:</strong> <%= quantity %></p>
    <p><strong>Total Price:</strong> $<%= String.format("%.2f", totalPrice) %></p>


    <form name="paymentForm" action="ProcessPaymentServlet" method="POST" onsubmit="return validateForm()">
      <div>
        <label for="cardName">Name on Card:</label>
        <input type="text" id="cardName" name="cardName" value="<%= autoFillCardName %>" required>
      </div>
      <div>
        <label for="cardNumber">Card Number:</label>
        <input type="text" id="cardNumber" name="cardNumber" value="<%= autoFillCardNumber %>" pattern="\d{13,19}" title="Card number must be 13-19 digits" required>
      </div>
      <div>
        <label for="expiryDate">Expiry Date (MM/YY):</label>
        <input type="text" id="expiryDate" name="expiryDate" value="<%= autoFillExpiryDate %>" placeholder="MM/YY" pattern="(0[1-9]|1[0-2])\/\d{2}" title="MM/YY format" required>
      </div>
      <div>
        <label for="cvc">CVC:</label>
        <input type="text" id="cvc" name="cvc" value="<%= autoFillCvc %>" pattern="\d{3,4}" title="CVC must be 3 or 4 digits" required>
      </div>

      <input type="hidden" name="amount" value="<%= cartTotal %>">

      <div>
        <input type="submit" value="Pay Now" class="button">
      </div>
    </form>
    <%
      String paymentError = (String) session.getAttribute("paymentError");
      if (paymentError != null) {
    %>
    <p style="color:red;"><%= paymentError %></p>
    <%
        session.removeAttribute("paymentError");
      }
      String paymentSuccess = (String) session.getAttribute("paymentSuccess");
      if (paymentSuccess != null) {
    %>
    <p style="color:green;"><%= paymentSuccess %></p>
    <%
        session.removeAttribute("paymentSuccess");
      }
    %>
  </div>
</main>
</body>
</html>