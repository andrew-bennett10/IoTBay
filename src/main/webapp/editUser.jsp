<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.model.Customer" %>
<%@ page import="classes.model.Staff" %>
<%@ page import="classes.model.dao.DAO" %>
<%@ page import="java.sql.SQLException" %>

<%
    String userType = request.getParameter("userType");
    int userId     = Integer.parseInt(request.getParameter("userId"));


    DAO db = (DAO) session.getAttribute("db");
    if (db == null) {
        throw new ServletException("The database connection has not been initialized yet, please confirm that the StartupListener is effective.");
    }

    Customer customer = null;
    Staff    staff    = null;
    try {
        if ("customer".equalsIgnoreCase(userType)) {
            customer = db.Customers().getById(userId);
        }
        else if ("staff".equalsIgnoreCase(userType)) {
            staff = db.Staff().getById(userId);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
%>

<html>
<head>
    <title>Edit User</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>
<h2>Edit User Details</h2>
<form action="UpdateUserServlet" method="post">
    <input type="hidden" name="userId" value="<%= userId %>" />
    <input type="hidden" name="userType" value="<%= userType %>" />

    <div>
        <label>Email:</label>
        <input type="email" name="email" value="<%= userType.equals("customer") ? customer.getEmail() : staff.getEmail() %>" required />
    </div>

    <div>
        <label>Password:</label>
        <input type="password" name="password" value="<%= userType.equals("customer") ? customer.getPassword() : staff.getPassword() %>" required />
    </div>

    <div>
        <label>First Name:</label>
        <input type="text" name="fName" value="<%= userType.equals("customer") ? customer.getFName() : staff.getFName() %>" required />
    </div>

    <div>
        <label>Last Name:</label>
        <input type="text" name="lName" value="<%= userType.equals("customer") ? customer.getLName() : staff.getLName() %>" required />
    </div>

    <%
        if ("customer".equals(userType)) {
    %>
    <div>
        <label>Age:</label>
        <input type="text" name="age" value="<%= customer.getAge() %>" required />
    </div>

    <div>
        <label>Address:</label>
        <input type="text" name="address" value="<%= customer.getAddress() %>" required />
    </div>

    <div>
        <label>Registered:</label>
        <select name="registered">
            <option value="true" <%= customer.getRegistered() ? "selected" : "" %>>Yes</option>
            <option value="false" <%= !customer.getRegistered() ? "selected" : "" %>>No</option>
        </select>
    </div>

    <div>
        <label>Phone Number:</label>
        <input type="text" name="phoneNumber" value="<%= customer.getPhoneNumber() %>" required />
    </div>

    <div>
        <label>Is Active:</label>
        <select name="isActive">
            <option value="true" <%= customer.getActive() ? "selected" : "" %>>Active</option>
            <option value="false" <%= !customer.getActive() ? "selected" : "" %>>Inactive</option>
        </select>
    </div>

    <%
        } else if ("staff".equals(userType)) {
    %>


    <div>
        <label>Role:</label>
        <input type="text" name="role" value="<%= staff.getRole() %>" required />
    </div>


    <div>
        <label>Phone Number:</label>
        <input type="text" name="phoneNumber" value="<%= staff.getPhoneNumber() %>" required />
    </div>


    <div>
        <label>Is Active:</label>
        <select name="isActive">
            <option value="true" <%= staff.getActive() ? "selected" : "" %>>Active</option>
            <option value="false" <%= !staff.getActive() ? "selected" : "" %>>Inactive</option>
        </select>
    </div>

    <%
        }
    %>
    <div>
        <input type="submit" value="Update" />
    </div>
</form>
</body>
</html>