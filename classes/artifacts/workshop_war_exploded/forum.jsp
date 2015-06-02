<%@ page import="content.Forum" %>
<%@ page import="content.SubForum" %>
<%@ page import="utils.HtmlUtils" %>
<%@ page import="users.User" %>
<%--
  Created by IntelliJ IDEA.
  User: thinkPAD
  Date: 5/6/2015
  Time: 4:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Forum forum = (Forum)request.getAttribute("forum"); %>
<% User user = (User) request.getAttribute("user"); %>


<html>
<head>
    <title></title>
</head>
<body>
<%=HtmlUtils.getNotificationsLink(user)%>
<h2>Forum: <%= forum.getName() %> (<%=forum.getSubForums().size()%>)</h2><br>
<a href="\register.jsp?forumId=<%=forum.id%>">register</a> &nbsp;&nbsp;
<a href="\login.jsp?forumId=<%=forum.id%>">login</a>
<br><br>
<% for (SubForum sub : forum.getSubForums()) { %>
  <a href="\subForum?subForumId=<%=sub.id%>"><%=sub.getName()%></a><br>
<%}%>

<a href="\deleteSubForum.jsp?forumId=<%=forum.id%>">Delete Sub Forums</a><br>
</body>
</html>
