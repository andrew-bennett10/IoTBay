<%@ page import="classes.User" %>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String firstname = request.getParameter("firstName");
    String lastname = request.getParameter("lastName");
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String confirmPassword = request.getParameter("confirmPassword");

    String username = firstname+" "+lastname;

    if (password.equals(confirmPassword)) {
        User user = new User(username, email, password);
        session.setAttribute("currentUser", user);
    }
    else {
        response.sendRedirect("register.jsp");
    }
%>
<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body class="other-pages">
<div class="container">
    <h2>Welcome to IoTBay!</h2>
    <p>Welcome <%=username%>!</p>
    <a href="main.jsp"><button>Main Page</button></a>
    <a href="logout.jsp"><button>Logout</button></a>
</div>
</body>
</html>