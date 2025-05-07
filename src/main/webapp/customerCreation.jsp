<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer Creation</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body class="other-pages">
<div class="container">
  <h2>Create New Customer</h2>
  <%
    if (request.getAttribute("errorMessage") != null) {
  %>
  <div class="error-message">
    <%= request.getAttribute("errorMessage") %>
  </div>
  <%
    }
  %>
  <form action="CreateUserServlet" method="post">
    <input type="hidden" name="userType" value="customer">
    <div>
      <label for="email">Email:</label>
      <input type="email" id="email" name="email" required>
    </div>
    <div>
      <label for="password">Password:</label>
      <input type="password" id="password" name="password" required>
    </div>
    <div>
      <label for="fName">First Name:</label>
      <input type="text" id="fName" name="fName" required>
    </div>
    <div>
      <label for="lName">Last Name:</label>
      <input type="text" id="lName" name="lName" required>
    </div>
    <div>
      <label for="age" >Age:</label>
      <input type="text" id="age" name="age" min="1" required>
    </div>
    <div>
      <label for="phoneNumber">Phone Number:</label>
      <input type="text" id="phoneNumber" name="phoneNumber" required>
    </div>
    <div>
      <label for="address">Address:</label>
      <input type="text" id="address" name="address" required>
    </div>
    <div>
      <input type="submit" value="Create Customer" class="btn">
      <a href="createUser.jsp" class="btn cancel">Back</a>
    </div>
  </form>
</div>
</body>
</html>
