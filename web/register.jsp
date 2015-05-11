<%@ page import="content.Forum" %>
<%--
  Created by IntelliJ IDEA.
  User: thinkPAD
  Date: 5/6/2015
  Time: 6:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String forumIdString = (String)request.getParameter("forumId"); %>
<%
  int id=-1;
  try {
    id=Integer.parseInt(forumIdString);
  }
  catch (NumberFormatException e) {
    System.out.println(e);
  }
%>

<html>
<head>
  <title>
    registration
  </title>
</head>
<body>
<h2>registration</h2><br>
<form action="\register" method="get" id="registerform">
  <input name="forum" type="hidden" value="<%=id%>">
  user:&nbsp;<input name="user" type="text" value=""><br>
  password:&nbsp;<input name="pass" type="password" value=""><br>
  <input type="submit">
</form>
</body>
</html>
