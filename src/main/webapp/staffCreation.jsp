<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create New Staff</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>
<body class="other-pages">
<div class="container">
    <h2>Create New Staff</h2>
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
        <input type="hidden" name="userType" value="staff">
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
            <label for="role">Role:</label>
            <input type="text" id="role" name="role" required>
        </div>
        <div>
            <label for="phoneNumber">Phone Number:</label>
            <input type="text" id="phoneNumber" name="phoneNumber" required>
        </div>
        <div>
            <input type="submit" value="Create Staff" class="btn">
            <a href="createUser.jsp" class="btn cancel">Back</a>
        </div>
    </form>
</div>
</body>
</body>
</html>
