<%@ page import="content.SubForum" %>
<%@ page import="content.Thread" %>
<%--
  Created by IntelliJ IDEA.
  User: thinkPAD
  Date: 5/6/2015
  Time: 4:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% SubForum sub = (SubForum)request.getAttribute("subforum"); %>

<html>
<head>
    <title></title>
</head>
<body>
<h2>Sub-Forum: <%= sub.getName() %> (<%=sub.viewThreads().size()%>)</h2><br><br>
<% for (Thread t : sub.viewThreads()) { %>
  <a href="\thread?id=<%=t.id%>"><%=t.getOpeningMessage().getTitle()%></a><br>
<%}%>
</body>
</html>
