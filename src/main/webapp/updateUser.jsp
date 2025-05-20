<%@ page import="classes.model.Customer" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.model.Staff" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Update User Details</title>
  <link rel="stylesheet" href="styles/style.css">
</head>
<body>
<h2>Update User Details</h2>
<h3>Customer List</h3>
<%
  List<Customer> customers = (List<Customer>) request.getAttribute("customers");
  if(customers != null && !customers.isEmpty()) {
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
  </tr>
  <%
    }
  %>
</table>
<%
} else {
%>
<p>No customers found.</p>
<%
  }
%>

<h3>Staff List</h3>
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
  </tr>
  <%
    }
  %>
</table>
<%
}else {
%>
<p>No staffs found.</p>
<%
  }
%>
</body>
</html>
