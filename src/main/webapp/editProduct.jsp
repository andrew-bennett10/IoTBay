


<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
%>
<div class="error"><%= errorMessage %></div>
<%
    }
%>


