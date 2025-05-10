<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Landing</title>
  <link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body class="other-pages">
  <div class="container">
    <h2>IoTBay</h2>
    <a href="login.jsp"><button>Login</button></a>
    <a href="register.jsp"><button>Register</button></a>
    <a href="main.jsp"><button>Continue as Guest</button></a>

    <%
      String errorMessage = (String) request.getAttribute("errorMessage");
      if (errorMessage != null) {
    %>
    <div class="error"><%= errorMessage %></div>
    <%
      }
    %>
  </div>
</body>
</html>