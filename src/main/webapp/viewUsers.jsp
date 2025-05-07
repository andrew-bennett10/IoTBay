<%@ page import="classes.model.Customer" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.model.Staff" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>View Users</title>
  <link rel="stylesheet" href="styles/style.css">
</head>
<body class="main-page">
<h2>View Users</h2>
<form action="ViewUsersServlet" method="get">
  <input type="text" name="fName" placeholder="First Name"/>
  <input type="text" name="lName" placeholder="Last Name"/>
  <input type="text" name="phoneNumber" placeholder="Phone Number"/>
  <input type="submit" value="Search"/>
  <input type="hidden" name="viewAll" value="true" />
</form>
<form action="ViewUsersServlet" method="get">
  <input type="submit" value="View All Users" />
</form>
<form action="main.jsp" method="get">
  <input type="submit" value="Back to main page"/>
</form>

<h2>Customer List</h2>
<%
  List<Customer> customers = (List<Customer>) request.getAttribute("customers");
  if (customers != null && !customers.isEmpty()) {
%>
<table border="2px">
  <tr>
    <th>Email</th>
    <th>Password</th>
    <th>First Name</th>
    <th>Last Name</th>
    <th>Age</th>
    <th>Address</th>
    <th>Registered</th>
    <th>Phone Number</th>
    <th>Is Active</th>
  </tr>
  <%
    for (Customer customer : customers) {
  %>
  <tr>
    <td><%= customer.getEmail() %></td>
    <td><%= customer.getPassword() %></td>
    <td><%= customer.getFName() %></td>
    <td><%= customer.getLName() %></td>
    <td><%= customer.getAge() %></td>
    <td><%= customer.getAddress() %></td>
    <td><%= customer.getRegistered() %></td>
    <td><%= customer.getPhoneNumber() %></td>
    <td><%= customer.getActive() %></td>
    <td>
      <a href="editUser.jsp?userType=customer&userId=<%= customer.getId() %>">Update</a>
    </td>
    <td>
      <a href="DeleteUserServlet?userType=customer&userId=<%= customer.getId() %>" onclick="return confirm('Are you sure you want to delete this user?')">Delete</a>
    </td>
  </tr>
  <%
    }
  %>
</table>
<%
  } else {
%>
<p>No customers found matching your search criteria.</p>
<%
  }
%>
<h2>Staff List</h2>
<%
  List<Staff> staffMembers = (List<Staff>) request.getAttribute("staffMembers");
  if (staffMembers != null && !staffMembers.isEmpty()) {
%>
<table border="2px">
  <tr>
    <th>Email</th>
    <th>Password</th>
    <th>First Name</th>
    <th>Last Name</th>
    <th>Role</th>
    <th>Phone Number</th>
    <th>Is Active</th>
  </tr>
  <%
    for (Staff staff : staffMembers) {
  %>
  <tr>
    <td><%= staff.getEmail() %></td>
    <td><%= staff.getPassword() %></td>
    <td><%= staff.getFName() %></td>
    <td><%= staff.getLName() %></td>
    <td><%= staff.getRole() %></td>
    <td><%= staff.getPhoneNumber() %></td>
    <td><%= staff.getActive() %></td>
    <td>
      <a href="editUser.jsp?userType=staff&userId=<%= staff.getId() %>">Update</a>
    </td>
    <td>
      <a href="DeleteUserServlet?userType=staff&userId=<%= staff.getId() %>" onclick="return confirm('Are you sure you want to delete this user?')">Delete</a>
    </td>
  </tr>
  <%
    }
  %>
</table>
<%
} else {
%>
<p>No staff members found matching your search criteria.</p>
<%
  }
%>
</body>
</html>
