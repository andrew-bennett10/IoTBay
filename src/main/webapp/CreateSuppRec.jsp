<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Supplier Record Creation</title>
  <link rel="stylesheet" href="styles/style.css">
</head>
<body class="other-pages">
<div class="container">
  <h2>Create New Supplier Records</h2>
  <form action="CreateSupServlet" method="post">
    <div>
      <label for="ConName">Contact Name:</label>
      <input type="text" id="ConName" name="ConName" required>
    </div>
    <div>
      <label for="company">Company:</label>
      <input type="text" id="company" name="company" required>
    </div>
    <div>
      <label for="Email">Email:</label>
      <input type="email" id="Email" name="Email" required>
    </div>
    <div>
      <label for="SuppStat">Supplier Status:</label>
      <select id="SuppStat" name="SuppStat" required>
        <option value="">Select Supplier Status</option>
        <option value="Active">Active</option>
        <option value="Inactive">Inactive</option>
      </select>
    </div>
    <div>
      <input type="submit" value="Create Supplier Record" class="btn">
      <a href="suppliermanagement.jsp" class="btn cancel">Back</a>
    </div>
  </form>
</div>
</body>
</html>
