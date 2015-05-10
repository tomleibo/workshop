<%--
  Created by IntelliJ IDEA.
  User: Roee
  Date: 06-05-15
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String reportee = (String)request.getAttribute("reportee"); %>

<html>
<head>
  <title></title>
</head>
<body>
<h1>
  Report Sent To <%=reportee%>!
</h1>
</body>
</html>
