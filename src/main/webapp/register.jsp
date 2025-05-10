<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css?v=1.0">
</head>
<body class="other-pages">
<div class="container">
    <h2>Register</h2>
    <form action="RegisterServlet" method="post">
        <label>
            <input type="text" name="fName" placeholder="First Name" required>
        </label>
        <label>
            <input type="text" name="lName" placeholder="Last Name" required>
        </label>
        <label>
            <input type="email" name="email" placeholder="Email" required>
        </label>
        <label>
            <input type="number" name="age" placeholder="Age" required min="1" max="100">
        </label>
        <label>
            <input type="text" name="address" placeholder="Address" required>
        </label>
        <label>
            <input type="password" name="password" placeholder="Password" required>
        </label>
        <label>I agree to the TOS
            <input type="checkbox" name="tos">
        </label>
        <input type="submit" name="register" value="Register">
        <input type="reset" name="reset" value="Reset">
    </form>
    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
    <div class="error"><%= errorMessage %></div>
    <%
        }
    %>
    <a href="index.jsp"><button>Return</button></a>
</div>
</body>
</html>