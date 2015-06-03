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
    <link href="css/list.css" rel="stylesheet">
  <link href="css/buttons.css" rel="stylesheet">
</head>
<body>
<h1> Hi <%= user.getUsername()%>!</h1> <br>
<%=HtmlUtils.getNotificationsLink(user)%>
<div class="container">
  <a href="\register.jsp?forumId=<%=forum.id%>" class="button"><span>✓</span>Register</a>
  <a href="\login.jsp?forumId=<%=forum.id%>" class="button"><span>✓</span>Login</a>
</div>
<%--<a href="\register.jsp?forumId=<%=forum.id%>">register</a>--%>
<%--<a href="\login.jsp?forumId=<%=forum.id%>">login</a>--%>
<h2>Forum: <%= forum.getName() %> (<%=forum.getSubForums().size()%>)</h2><br>
<br><br>
<div>
  <h2>Sub-Forums:</h2>
    <ul>
      <% for (SubForum sub : forum.getSubForums()) { %>
      <li>
        <a href="\subForum?subForumId=<%=sub.id%>"><%=sub.getName()%></a><br>
      </li>
      <%}%>
    </ul>
</div>


<a href="\deleteSubForum.jsp?forumId=<%=forum.id%>">Delete Sub Forums</a><br>
</body>
</html>
