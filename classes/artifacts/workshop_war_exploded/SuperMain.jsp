<%@ page import="content.ForumSystem" %>
<%@ page import="content.Forum" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Shai Rippel
  Date: 05/05/2015
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<Forum> forums = (List<Forum>) request.getAttribute("forums"); %>
<html>
<head>
  <title></title>
</head>
<body>
<h2>SUPER ADMIN MODE!!!!!!!!!</h2><br><br>
<%
  for (Forum forum : forums) {
%>
<a href="\forum?id=<%=forum.id%>"><%=forum.getName()%></a> <form action="\forumDeleted.jsp">
  <input type="submit" value="delete">
</form><br>
<%
  }
%>
<br><br><br><br>
<form action="\newforum.jsp">
  <input type="submit" value="Create new forum">
</form>
</body>
</html>

