<%@ page import="content.Forum" %>
<%@ page import="content.SubForum" %>
<%--
  Created by IntelliJ IDEA.
  User: thinkPAD
  Date: 5/6/2015
  Time: 4:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Forum forum = (Forum)request.getAttribute("forum"); %>

<html>
<head>
    <title></title>
</head>
<body>
<h2>Forum: <%= forum.getName() %> (<%=forum.getSubForums().size()%>)</h2><br>
<a href="\register.jsp?id=<%=forum.id%>">register</a> &nbsp;&nbsp;
<a href="\login.jsp?id=<%=forum.id%>">login</a>
<br><br>
<% for (SubForum sub : forum.getSubForums()) { %>
  <a href="\subforum?id=<%=sub.id%>"><%=sub.getName()%></a><br>
<%}%>
</body>
</html>
