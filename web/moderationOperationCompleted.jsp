<%--
  Created by IntelliJ IDEA.
  User: Roee
  Date: 06-05-15
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String moderator = (String)request.getAttribute("moderator"); %>
<% String op = (String)request.getAttribute("op"); %>


<html>
<head>
  <title></title>
</head>
<body>
<h1>
  <%
    if(op.equals("appoint")){
  %>
  You Appointed <%=moderator%> As Moderator!
  <%
  }

  else{
  %>
  You Dismissed <%=moderator%> As Moderator!
  <%
    }
  %>
</h1>
</body>
</html>
