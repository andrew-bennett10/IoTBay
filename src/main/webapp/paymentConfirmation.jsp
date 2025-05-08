<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Payment Confirmation</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
  <style>
    .confirmation-container {
      max-width: 600px;
      margin: 50px auto;
      padding: 30px;
      border: 1px solid #ddd;
      border-radius: 8px;
      background-color: #f9f9f9;
      text-align: center;
    }
    .confirmation-container h1 {
      color: #4CAF50; /* Green color for success */
      margin-bottom: 20px;
    }
    .confirmation-container p {
      font-size: 1.2em;
      color: #333;
      margin-bottom: 20px;
    }
    .confirmation-container .home-link-button { /* Changed class name for clarity if needed, or reuse .home-button */
      display: inline-block;
      padding: 10px 20px;
      background-color: #007bff;
      color: white;
      text-decoration: none;
      border-radius: 5px;
      font-size: 1em;
      border: none; /* Ensure it looks like a button */
      cursor: pointer; /* Add cursor pointer */
    }
    .confirmation-container .home-link-button:hover {
      background-color: #0056b3;
    }
    .home-button-container { /* Copied from purchase.jsp for consistency */
      padding: 10px 20px;
      text-align: left;
    }
    .home-button { /* Copied from purchase.jsp for consistency */
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
  <div class="confirmation-container">
    <h1>Payment Successful!</h1>
    <p>Thank you for your purchase. Your order has been processed.</p>
    <%
      String orderId = (String) request.getAttribute("orderId");
      if (orderId != null && !orderId.isEmpty()) {
    %>
    <p>Your Order ID is: <strong><%= orderId %></strong></p>
    <%
      }
    %>
    <p>A confirmation email has been sent to your registered email address (if applicable).</p>
    <%-- Replaced the <a> tag with a button styled similarly, or could use the .home-button class directly --%>
    <button onclick="window.location.href='${pageContext.request.contextPath}/'" class="home-link-button">Return to Home Page</button>
  </div>
</main>

</body>
</html>