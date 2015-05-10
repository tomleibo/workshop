<%--
  Created by IntelliJ IDEA.
  User: thinkPAD
  Date: 5/6/2015
  Time: 6:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String idString = (String)request.getParameter("id"); %>
<% String op = (String)request.getParameter("op"); %>
<% String title = (String)request.getParameter("title"); %>
<% String body = (String)request.getParameter("body"); %>
<%
  int id=-1;
  try {
    id=Integer.parseInt(idString);
  }
  catch (NumberFormatException e) {
    System.out.println(e);
  }
%>
<html>
<head>
    <title>
    <%= title(op) %> post <%=id%>
    </title>
</head>
<body>
    <h2><%=title(op)%> post <%=id%></h2><br>
    <form action="\<%=title(op)%>" method="get" id="editform">
      <input name="id" type="hidden" value="<%=id%>">
      <input name="title" type="text" value="<%=title==null?"":title%>"><br>
      <textarea name="body" form="editform">
        <%=body==null?"":body%>
      </textarea>
      <br><input type="submit">
    </form>
</body>
</html>

<%!
  private String title(String op) {
    System.out.println(op);
    StringBuilder sb = new StringBuilder();
    if (op.equals("edit")) {
      sb.append("edit");
    }
    else if (op.equals("reply")) {
      sb.append("reply");
    }
    return sb.toString();
  }
%>