<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create User</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body class="other-pages">
<div class="container">
    <h2>Create New User</h2>
    <%
        if (request.getAttribute("errorMessage") != null){
    %>
    <p style="color: red"><%=request.getAttribute("errorMessage")%></p>
    <%
        }
    %>
    <form action="UserTypeSelectServlet" method="post">
        <div>
            <label for="userType">Select User Type:</label>
            <select id="userType" name="userType" required>
                <option value="">Select User Type</option>
                <option value="customer">Customer</option>
                <option value="staff">Staff</option>
            </select>
        </div>
        <div>
            <input type="submit" value="Continue">
            <a href="main.jsp">Cancel</a>
        </div>
    </form>
</div>

</body>
</html>
