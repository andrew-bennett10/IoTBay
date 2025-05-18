<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.model.Customer" %>
<%@ page import="classes.model.Staff" %>
<%
    String fName = null;
    String lName = null;
    String name = null;
    if("customer".equals(session.getAttribute("userType"))){
        Customer customer = (Customer)session.getAttribute("loggedInUser");
        fName = customer.getFName();
        lName = customer.getLName();
        name = fName + " " + lName;
    } else if ("staff".equals(session.getAttribute("userType"))) {
        Staff staff = (Staff) session.getAttribute("loggedInUser");
        fName = staff.getFName();
        lName = staff.getLName();
        name = fName + " " + lName;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Main</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body class="main-page">
<nav class="navigation">
    <ul>
        <li class="specialNav">IoTBay Digital Services</li>
        <li><a href="#">Home</a></li>
        <li><a href="#">About</a></li>
        <li><a href="#">Browse</a></li>
        <li><a href="#">Profile</a></li>
        <%
            //Only staff and only "role == admin" can access the "User Management" interface
            if("staff".equalsIgnoreCase((String) session.getAttribute("userType"))
                    && "Admin".equalsIgnoreCase((String)session.getAttribute("role"))){
        %>
        <li><a href="createAndViewUsers.jsp">User Management</a></li>
        <%
            }
        %>
        <li><a href="logout.jsp">Logout</a></li>
    </ul>
</nav>
    <section class="BackImg">
        <div class="InnerParra">
            <h1 class="MainTitle">Welcome <%=name%> to IoTBay</h1>
            <div class="BigButton"><a href="#"><button>WhateverButton</button></a></div>
        </div>
    </section>
<main>
    <p class="FillerText">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dolore et molestias non praesentium sit. Alias dolor, eaque in incidunt ipsa ipsum labore magni, nemo numquam quaerat quas rem suscipit temporibus! Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dolore et molestias non praesentium sit. Alias dolor, eaque in incidunt ipsa ipsum labore magni, nemo numquam quaerat quas rem suscipit temporibus! Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dolore et molestias non praesentium sit. Alias dolor, eaque in incidunt ipsa ipsum labore magni, nemo numquam quaerat quas rem suscipit temporibus!</p>
    <a href="logout.jsp"><button>Logout</button></a>
</main>
</body>
</html>