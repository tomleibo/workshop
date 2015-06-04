<%@ page import="content.SubForum" %>
<%@ page import="content.Thread" %>
<%@ page import="users.User" %>
<%@ page import="utils.HtmlUtils" %>
<%--
  Created by IntelliJ IDEA.
  User: thinkPAD
  Date: 5/6/2015
  Time: 4:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% SubForum sub = (SubForum)request.getAttribute("subForum"); %>
<% User user = (User) request.getAttribute("user"); %>


<html>
<head>
    <title></title>
</head>
<body>

<%=HtmlUtils.getNotificationsLink(user)%>
<h2>Sub-Forum: <%= sub.getName() %> (<%=sub.viewThreads().size()%>)</h2><br><br>
<% for (Thread t : sub.viewThreads()) { %>
  <a href="\thread?threadId=<%=t.id%>"><%=t.getOpeningMessage().getTitle()%></a><br>
<%}%>

<a href="\appointModerator.jsp?&subForumId=<%=sub.id%>">Appoint Moderator</a><br>
<a href="\dismissModerator.jsp?&subForumId=<%=sub.id%>">Dismiss Moderator</a><br>
</body>
</html>
