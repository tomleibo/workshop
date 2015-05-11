<%@ page import="content.Forum" %>
<%@ page import="users.User" %>
<%@ page import="content.SubForum" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% User user = (User)request.getAttribute("user"); %>
<% Forum forum = (Forum)request.getAttribute("forum"); %>
<% SubForum subForum = (SubForum) request.getAttribute("subForumId"); %>

<html>
<head>
  <title></title>

</head>
<body>

<span style="text-decoration: underline; font-weight: bold">Dismiss Moderator</span> <br><br>
<UL>
  <%
  for (User member : subForum.getModerators()) {
    if(!user.getUsername().equals(member.getUsername())){
%>

  <LI>
    <a href=<%="\\dismissModerator?forumId="+forum.id+
            "&subForumId="+subForum.id+"&moderatorId="+member.getId()%>>
      <b><%= member.getUsername() %></b>
    </a>
  </LI>
<%
    }
  }
%>

</body>
</html>