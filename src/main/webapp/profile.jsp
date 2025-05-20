<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.model.Customer" %>
<%@ page import="classes.model.User" %>
<%@ page import="classes.model.Staff" %>
<%@ page import="classes.model.AccessLog" %>
<%@ page import="classes.model.dao.DAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.sql.SQLException" %>
<%
    // Search term stuff for logs
    String input = request.getParameter("searchText");
    if (input != null) {
        session.setAttribute("logSearch", input);
    }
    String searchTerm = (String) session.getAttribute("logSearch");
    if (searchTerm == null){
        searchTerm = "";
    }

    // Imports and whatnot
    String userType = (String) session.getAttribute("userType");
    DAO db = (DAO) session.getAttribute("db");

    String fname = null;
    String lname = null;
    Integer age = null;
    String address = null;
    String password = null;
    String email = null;
    String role = null;

    ArrayList<AccessLog> accessLogList = null;
    if ("customer".equals(userType)) {
        Customer user = (Customer) session.getAttribute("loggedInUser");
        fname = user.getFName();
        lname = user.getLName();
        age = user.getAge();
        address = user.getAddress();
        email = user.getEmail();
        password = user.getPassword();
        try {
            accessLogList = db.AccessLog().getItems(user.getId(), "customer", searchTerm);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } else if ("staff".equals(userType)) {
        Staff user = (Staff) session.getAttribute("loggedInUser");
        fname = user.getFName();
        lname = user.getLName();
        role = user.getRole();
        password = user.getPassword();
        email = user.getEmail();
        try {
            accessLogList = db.AccessLog().getItems(user.getId(), "staff", searchTerm);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    String name = (String) session.getAttribute("name");
%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css?v=1.1">
</head>
<nav class="navigation">
    <ul>
        <li class="specialNav">IoTBay Digital Services</li>
        <li><a href="main.jsp">Home</a></li>
        <li><a href="#">About</a></li>
        <li><a href="products.jsp">Products</a></li>
        <%
            if (!name.equals("Guest")) {
        %>
        <li><a href="profile.jsp">Profile</a></li>
        <li><a href="createOrder.jsp">Create Order</a></li>
        <li><a href="logout.jsp">Logout</a></li>
        <%
        } else {
        %>
        <li><a href="index.jsp">Sign in</a></li>
        <%
            }
        %>
    </ul>
</nav>
<body class="main-page">
<div class="container">
    <h2>Profile</h2>
    <form action="EditAccountServlet" method="post">
        <label>First Name
            <input type="text" name="fName" placeholder="First Name" value="<%=fname%>" required>
        </label>
        <label>Last Name
            <input type="text" name="lName" placeholder="Last Name" value="<%=lname%>" required>
        </label>
        <label>Email
            <input type="email" name="email" placeholder="Email" value="<%=email%>" required>
        </label>
        <label>Password
            <input type="password" name="password" placeholder="Password" value="<%=password%>" required>
        </label>
        <%
            if (userType.contains("customer")) {
        %>
        <label>Age
            <input type="number" name="age" placeholder="Age" required min="1" max="100" value="<%=age%>">
        </label>
        <label>Address
            <input type="text" name="address" placeholder="Address" required value="<%=address%>">
        </label>
        <% } else { %>
        <label>Role
            <input type="text" name="role" placeholder="Role" required value="<%=role%>">
        </label>
        <% } %>
        <input type="submit" name="update" value="Update">

    </form>
    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
    <div class="error"><%=errorMessage%></div>
    <%
        }
    %>
    <a href="main.jsp"><button>Cancel</button></a>

    <form action="DeleteAccountServlet" method="post">
        <button type="submit">Delete my Account</button>
    </form>

    <h2>Access Logs</h2>
    <form action="profile.jsp" method="post">
        <label for="searchText">Search Logs by Login:</label>
        <input type="text" name="searchText" id="searchText">
        <button type="submit">Search</button>
    </form>
    <p>Criteria: <%= (searchTerm.isEmpty()) ? "None specified" : searchTerm %></p>
    <%
        for (AccessLog accessLog : accessLogList) {
            Integer logId = accessLog.getUserId();
            Date loginTime = accessLog.getLogin();
            Date logoutTime = accessLog.getLogout();
            String logoutText = "Active Session";
            if (logoutTime != null){
                logoutText = String.valueOf(logoutTime);
            }
    %>
    <p>Session ID: <%=logId%><br>Login: <%=loginTime%><br>Logout: <%=logoutText%></p>
    <%
        }
    %>
    <p>(Max of 10 records shown)</p>
</div>
</body>
</html>