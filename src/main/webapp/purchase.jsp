<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Complete Your Purchase</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
  <style>
    /* Additional styles specific to this page if needed, or can be added to style.css */
    .payment-form-container {
      max-width: 500px;
      margin: 20px auto;
      padding: 20px;
      border: 1px solid #ddd;
      border-radius: 8px;
      background-color: #f9f9f9;
    }
    .payment-form-container h1 {
      text-align: center;
      color: #333;
    }
    .payment-form-container h2 {
      text-align: center;
      color: #555;
      margin-bottom: 20px;
    }
    .error-message {
      color: red;
      margin-bottom: 15px;
      text-align: center;
    }
    .home-button-container {
      padding: 10px 20px; /* Add some padding around the button */
      text-align: left; /* Align button to the left, or center/right as needed */
    }
    .home-button {
      padding: 8px 15px;
      background-color: #007bff;
      color: white;
      text-decoration: none;
      border: none;
      border-radius: 5px;
      cursor: pointer;
    }
    .home-button:hover {
      background-color: #0056b3;
    }
  </style>
</head>
<body>

<div class="home-button-container">
  <button onclick="window.location.href='${pageContext.request.contextPath}/'" class="home-button">Home</button>
</div>

<main>
  <div class="payment-form-container">
    <h1>Payment Details</h1>

    <%-- Display Cart Total - Assuming 'cartTotal' is set as a request attribute --%>
    <%
      Object cartTotalObj = request.getAttribute("cartTotal");
      String cartTotalDisplay = "N/A"; // Default if not set
      if (cartTotalObj != null) {
        try {
          double total = Double.parseDouble(cartTotalObj.toString());
          cartTotalDisplay = String.format("$%.2f", total);
        } catch (NumberFormatException e) {
          // Handle case where cartTotal is not a valid number
          cartTotalDisplay = "Error";
        }
      }
    %>
    <h2>Total Amount: <%= cartTotalDisplay %></h2>

    <%-- Error Message Display --%>
    <%
      String errorMessage = (String) request.getAttribute("errorMessage");
      if (errorMessage != null && !errorMessage.isEmpty()) {
    %>
    <p class="error-message"><%= errorMessage %></p>
    <%
      }
    %>

    <form action="${pageContext.request.contextPath}/ProcessPaymentServlet" method="POST">
      <div>
        <label for="cardHolderName">Card Holder Name:</label>
        <input type="text" id="cardHolderName" name="cardHolderName" required>
      </div>
      <div>
        <label for="cardNumber">Card Number:</label>
        <input type="text" id="cardNumber" name="cardNumber" required pattern="[0-9]{13,19}" title="Enter a valid card number (13-19 digits)">
      </div>
      <div>
        <label for="expiryDate">Expiry Date (MM/YY):</label>
        <input type="text" id="expiryDate" name="expiryDate" placeholder="MM/YY" required pattern="(0[1-9]|1[0-2])\/([0-9]{2})" title="Enter date in MM/YY format">
      </div>
      <div>
        <label for="cvv">CVV:</label>
        <input type="text" id="cvv" name="cvv" required pattern="[0-9]{3,4}" title="Enter a 3 or 4 digit CVV">
      </div>
      <div>
        <input type="submit" value="Complete Purchase">
      </div>
    </form>
  </div>
</main>

</body>
</html>