<%@ page import="content.Forum" %>
<%@ page import="content.SubForum" %>
<%--
  Created by IntelliJ IDEA.
  User: Shai Rippel
  Date: 05/05/2015
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Forum forum = (Forum)request.getAttribute("forum"); %>
<html>
<head>
    <title></title>
</head>
<body>
bla bla bla bla
<%
  for (SubForum sub : forum.getSubForums()) {
%>
    <h1> name: <%= sub.getName() %></h1> <br><br>
<%
  }
%>
</body>
</html>
