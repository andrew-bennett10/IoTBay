<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String name = (String) session.getAttribute("name");
%>
<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body class="other-pages">
<div class="container">
    <h2>Welcome to IoTBay!</h2>
    <p>Welcome <%=name%>!</p>
    <a href="main.jsp"><button>Main Page</button></a>
    <a href="logout.jsp"><button>Logout</button></a>
</div>
</body>
</html>