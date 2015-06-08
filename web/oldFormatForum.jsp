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
    <% if(!user.isGuest()){%>
    <%=HtmlUtils.getAjaxScript()%>
    <%}%>
    <link href="css/list.css" rel="stylesheet">
    <link href="css/buttons.css" rel="stylesheet">
</head>
<body>
<h1> Hi <%= user.getUsername()%>!</h1> <br>
<%if(!user.isGuest()){%>
<%=HtmlUtils.getNotificationsLink(user)%>
<%--Some notifications--%>
<div class="container">
    <a href="\profile" class="button"><span>✓</span>Profile</a>
</div>
<%}%>

<div class="container">
    <% if(user.isGuest() || !user.isLoggedIn()){%>
    <a href="\register.jsp?forumId=<%=forum.id%>" class="button"><span>✓</span>Register</a>
    <a href="\login.jsp?forumId=<%=forum.id%>" class="button"><span>✓</span>Login</a>
    <%} else{ %>
    <a href="\logout?forumId=<%=forum.id%>" class="button"><span>✓</span>Logout</a>
    <%} %>
</div>
<%--<a href="\register.jsp?forumId=<%=forum.id%>">register</a>--%>
<%--<a href="\login.jsp?forumId=<%=forum.id%>">login</a>--%>
<h2>Forum: <%= forum.getName() %> (<%=forum.getSubForums().size()%>)</h2><br>
<br><br>
<div>
    <h2>SubForums:</h2>
    <ul>
        <% for (SubForum sub : forum.getSubForums()) { %>
        <li>
            <a href="\subForum?subForumId=<%=sub.id%>"><%=sub.getName()%></a><br>
        </li>
        <%}%>
    </ul>
</div>

<%if(user.isAdmin()){%>
<a href="\newSubForum.jsp?forumId=<%=forum.id%>">Add Sub Forum</a><br>
<a href="\deleteSubForumRequest?forumId=<%=forum.id%>">Delete Sub Forums</a><br>
<%}%>
<a href="\cleanup?forumId=<%=forum.id%>">CleanUp</a><br>
</body>
</html>
