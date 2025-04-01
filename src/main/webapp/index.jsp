<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  session.invalidate();
%>

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
  </div>
</body>
</html>