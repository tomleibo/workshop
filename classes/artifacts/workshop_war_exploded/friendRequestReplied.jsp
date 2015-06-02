<%--
  Created by IntelliJ IDEA.
  User: Roee
  Date: 06-05-15
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String friend = (String)request.getAttribute("friend"); %>
<% String answer = (String)request.getAttribute("answer"); %>


<html>
<head>
  <title></title>
</head>
<body>
<h1>
  <%
    if(answer.equals("1")){
      %>
  Your Friend Request From <%=friend%> Was Accepted!
  <%
    }

    else{
    %>
  Your Friend Request From <%=friend%> Was Declined!
  <%
  }
  %>
</h1>
</body>
</html>
