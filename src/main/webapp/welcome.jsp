<%@ page import="classes.model.dao.DAO" %>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String name = null;
    String fName;
    String lName;

    if("customer".equals(session.getAttribute("userType"))){
        fName = (String) session.getAttribute("fName");
        lName = (String) session.getAttribute("lName");
        name = fName + " " + lName;
    } else if ("staff".equals(session.getAttribute("userType"))) {
        fName = (String) session.getAttribute("fName");
        lName = (String) session.getAttribute("lName");
        name = fName + " " + lName;
    }

    String errorMessage;

    DAO db = (DAO) session.getAttribute("db");
    if(db == null){
        errorMessage = "Failed in connecting database";
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("register.jsp").forward(request,response);
        return;
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
    <p>Welcome <%=name%>!</p>
    <a href="main.jsp"><button>Main Page</button></a>
    <a href="logout.jsp"><button>Logout</button></a>
</div>
</body>
</html>