<%@ page import="content.Thread" %>
<%@ page import="content.Message" %>
<%--
  Created by IntelliJ IDEA.
  User: thinkPAD
  Date: 5/6/2015
  Time: 4:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% content.Thread t = (content.Thread)request.getAttribute("thread"); %>

<html>
<head>
    <title><%= t.getOpeningMessage().getTitle() %></title>
</head>
<body>
<h1></h1>
<h2>Thread: <%= t.getOpeningMessage().getTitle() %> </h2><br><br>
<%= printMessageAndComments(t.getOpeningMessage(),0)%>
</body>
</html>


<%!
  public static String printMessageAndComments(content.Message msg, int depth) {
    StringBuilder sb= new StringBuilder();
    if (msg == null) {
      return "";
    }
    sb.append(generateHtmlSpaces(depth));
    sb.append("<b>"+msg.getTitle()+"</b>&nbsp;(");
    sb.append("<a href=\"reply.jsp?op=reply&id="+msg.id+"&title="+msg.getTitle()+"\">reply</a>");

    sb.append("&nbsp;|&nbsp;<a href=\"\\reply.jsp?body="+msg.getBody()+"&title="+msg.getTitle()+"&op=edit&id="+ msg.id +"\">edit</a>");

    sb.append("&nbsp;|&nbsp;<a href=delete?id="+ msg.id +">delete</a>)");
    sb.append("<br>"+generateHtmlSpaces(depth)+msg.getBody()+"<br><br>");
    for (Message comment : msg.getComments()) {
      sb.append(printMessageAndComments(comment,depth+1));
    }
    return sb.toString();
  }
%>

<%!
  private static String generateHtmlSpaces(int depth) {
    StringBuilder sb = new StringBuilder();
    for (int i=0; i<depth; i++) {
      sb.append("&nbsp;");
      sb.append("&nbsp;\n");
    }
    return sb.toString();
  }
%>
