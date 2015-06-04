<%@ page import="content.Forum" %>
<%@ page import="users.User" %>
<%@ page import="content.SubForum" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Forum forum = (Forum)request.getAttribute("forum"); %>

<html>
<head>
  <title></title>

</head>
<body>

<span style="text-decoration: underline; font-weight: bold">Delete Sub Forum</span> <br><br>
<UL>
  <%
  for (SubForum sf : forum.getSubForums()) {
%>

  <LI>
    <a href="\deleteSubForum?forumId=<%=forum.id%>&subForumId=<%=sf.id%>">
      <b><%= sf.getName() %></b>
    </a>
  </LI>
<%
  }
%>

</body>
</html>
