<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body class="other-pages">
<div class="container">
    <h2>Login</h2>
    <form action="LoginServlet" method="post">
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <input type="submit" name="login" value="Login">
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