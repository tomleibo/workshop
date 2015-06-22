<%@ page import="content.Forum" %>
<%@ page import="content.SubForum" %>
<%@ page import="utils.HtmlUtils" %>
<%@ page import="users.User" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Queue" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Queue<String> log = (Queue<String>)request.getAttribute("log"); %>
<% User user = (User) request.getAttribute("user"); %>
<% String sid = (String) request.getAttribute("sid"); %>

<html>
<head>
    <title>Session Log</title>
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

<h2>Showing log for session: <%=sid%></h2>
<br><br>
<% for (String ses : log) { %>
<%=ses%><br>
<% } %>
</body>
</html>
