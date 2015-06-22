<%@ page import="content.Forum" %>
<%@ page import="content.SubForum" %>
<%@ page import="utils.HtmlUtils" %>
<%@ page import="users.User" %>
<%@ page import="java.util.Set" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Set<String> openSessions = (Set<String>)request.getAttribute("sessions"); %>
<% User user = (User) request.getAttribute("user"); %>

<html>
<head>
    <title>Open Sessions</title>
    <link href="css/list.css" rel="stylesheet">
    <link href="css/buttons.css" rel="stylesheet">
</head>
<body>
<h1> Open Sessions!</h1> <br>


<% for (String ses : openSessions) { %>
    <a href="/openSession?sid=<%=ses%>"><%=ses%></a><br>
<% } %>
</body>
</html>
