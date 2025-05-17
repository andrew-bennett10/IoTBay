<%--
  Created by IntelliJ IDEA.
  User: Fuzzy
  Date: 17/05/2025
  Time: 5:36 pm
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.model.Customer" %>
<%
  // Check if user is logged in as a customer
  String customerName = (String) session.getAttribute("name");
  String userType = (String) session.getAttribute("userType");
  Customer loggedInCustomer = null;
  String customerEmail = null;

  if ("customer".equals(userType)) {
    Object userObj = session.getAttribute("loggedInUser");
    if (userObj instanceof Customer) {
      loggedInCustomer = (Customer) userObj;
      customerEmail = loggedInCustomer.getEmail(); // Assuming getEmail() returns a non-null/non-empty string for a valid customer
    }
  }

  // If not a logged-in customer, or essential details (name, email) are missing, redirect.
  if (loggedInCustomer == null || customerEmail == null || customerEmail.trim().isEmpty() || customerName == null || customerName.trim().isEmpty()) {
    response.sendRedirect("index.jsp"); // Redirect to login page or main page if not properly logged in
    return;
  }

  // Retrieve cart total from session, if available
  Double cartTotal = (Double) session.getAttribute("cartTotal");
  if (cartTotal == null) {
    cartTotal = 0.0; // Default to 0 if not found
  }
  String orderId = (String) session.getAttribute("orderId"); // Assuming orderId is set before reaching payment
  if (orderId == null) {
    orderId = "N/A"; // Or handle appropriately
  }
%>


<!DOCTYPE html>
<html>
<head>
  <title>Payment Page</title>
  <link rel="stylesheet" type="text/css" href="styles/style.css"> <%-- Assuming you have a style.css --%>
  <script>
    function validateForm() {
      var cardNumber = document.forms["paymentForm"]["cardNumber"].value;
      var cardName = document.forms["paymentForm"]["cardName"].value;
      var expiryDate = document.forms["paymentForm"]["expiryDate"].value;
      var cvc = document.forms["paymentForm"]["cvc"].value;

      if (cardNumber == "" || cardName == "" || expiryDate == "" || cvc == "") {
        alert("All fields must be filled out");
        return false;
      }

      // Basic card number validation (length)
      if (!/^\d{13,19}$/.test(cardNumber)) {
        alert("Invalid card number format. Must be 13-19 digits.");
        return false;
      }

      // Basic expiry date validation (MM/YY)
      if (!/^(0[1-9]|1[0-2])\/\d{2}$/.test(expiryDate)) {
        alert("Invalid expiry date format. Must be MM/YY.");
        return false;
      }

      // Basic CVC validation (3 or 4 digits)
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
    <li><a href="logout.jsp">Logout</a></li>
  </ul>
</nav>

<main>
  <div class="payment-container">
    <h2>Payment Details</h2>
    <p>Order ID: <%= orderId %></p>
    <p>Total Amount: $<%= String.format("%.2f", cartTotal) %></p>
    <form name="paymentForm" action="ProcessPaymentServlet" method="POST" onsubmit="return validateForm()">
      <div>
        <label for="cardName">Name on Card:</label>
        <input type="text" id="cardName" name="cardName" required>
      </div>
      <div>
        <label for="cardNumber">Card Number:</label>
        <input type="text" id="cardNumber" name="cardNumber" pattern="\d{13,19}" title="Card number must be 13-19 digits" required>
      </div>
      <div>
        <label for="expiryDate">Expiry Date (MM/YY):</label>
        <input type="text" id="expiryDate" name="expiryDate" placeholder="MM/YY" pattern="(0[1-9]|1[0-2])\/\d{2}" title="MM/YY format" required>
      </div>
      <div>
        <label for="cvc">CVC:</label>
        <input type="text" id="cvc" name="cvc" pattern="\d{3,4}" title="CVC must be 3 or 4 digits" required>
      </div>
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
        session.removeAttribute("paymentError"); // Clear the error after displaying
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