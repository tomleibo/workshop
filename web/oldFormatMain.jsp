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
  <link href="css/list.css" rel="stylesheet">
  <title></title>
</head>
<body>

<h2>Hello Dear guest, Where would you like to go?</h2><br><br>
<br><br><br><br>

<div id="list3">
  <h2>Forums</h2>
  <ul>
    <%
      for (Forum forum : forums) {
    %>
    <li>
      <a href="\forum?forumId=<%=forum.id%>"><%=forum.getName()%></a><br>
    </li>
    <%
      }
    %>
  </ul>
</div>

<form action="\supermain">
  <input type="submit" value="ADMINIZE">
</form>

</body>
</html>

