<%@ page import="content.ForumSystem" %>
<%@ page import="content.Forum" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Shai Rippel
  Date: 05/05/2015
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<Forum> forums = (List<Forum>) request.getAttribute("forums"); %>
<html>
<head>
  <title></title>
</head>
<body>
<h2>FORUM CREATION !!!</h2>
<br><br>

Forum name:<br>
<%--Add a box to write a name --%>
policy:<br>
<%--add options to choose a policy--%>
Name of admin:<br>
<%--add a box to to write a name--%>
<%--add a button to accept--%>
</body>
</html>

