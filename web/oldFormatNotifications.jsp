<%@ page import="users.User" %>
<%@ page import="users.Notification" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% User user = (User)request.getAttribute("user"); %>

<html>
<head>
  <title></title>

</head>
<body>
<b><i>Hi <%= user.getUsername() %>! </i></b> <br>

<%--Notifications--%>
<span style="text-decoration: underline; font-weight: bold">Your Notifications:</span> <br><br>
<UL>
  <%
  for (Notification notification : user.getPendingNotifications()) {
%>

  <LI>
    <%=notification.getTitle()+":"+notification.getMessage()%>
  </LI>
<%
  }
%>

<% user.getPendingNotifications().clear();%>

</UL>

</body>
</html>
