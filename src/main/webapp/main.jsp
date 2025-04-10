﻿<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.User" %>
<%
    User user = (User)session.getAttribute("currentUser");
    String username = user.getUsername();
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
        <li><a href="logout.jsp">Logout</a></li>
    </ul>
</nav>
    <section class="BackImg">
        <div class="InnerParra">
            <h1 class="MainTitle">Welcome <%=username%> to IoTBay</h1>
            <div class="BigButton"><a href="#"><button>WhateverButton</button></a></div>
        </div>
    </section>
<main >
    <p class="FillerText">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dolore et molestias non praesentium sit. Alias dolor, eaque in incidunt ipsa ipsum labore magni, nemo numquam quaerat quas rem suscipit temporibus! Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dolore et molestias non praesentium sit. Alias dolor, eaque in incidunt ipsa ipsum labore magni, nemo numquam quaerat quas rem suscipit temporibus! Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dolore et molestias non praesentium sit. Alias dolor, eaque in incidunt ipsa ipsum labore magni, nemo numquam quaerat quas rem suscipit temporibus!</p>
    <a href="logout.jsp"><button>Logout</button></a>
</main>
</body>
</html>