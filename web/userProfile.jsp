<%@ page import="content.Forum" %>
<%@ page import="users.User" %>
<%@ page import="users.FriendRequest" %>
<%--
  Created by IntelliJ IDEA.
  User: Shai Rippel
  Date: 05/05/2015
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% User user = (User)request.getAttribute("user"); %>
<% Forum forum = (Forum)request.getAttribute("forum"); %>

<html>
<head>
  <title></title>

</head>
<body>
<h1>Profile Settings</h1>
<b><i>Hi <%= user.getUsername() %>! </i></b> <br>

<%--Friend Requests--%>
<span style="text-decoration: underline; font-weight: bold">Send Friend Requests:</span> <br><br>
<UL>
  <%
  for (User member : forum.getMembers()) {
    if(!user.getUsername().equals(member.getUsername())){
%>

  <LI>
    <a href=<%="\\friendRequest.jsp?forumId="+forum.id+
            "&senderId="+user.getId()+"&receiver="+member.getUsername()+"&receiverId="+member.getId()%>>

      <b><%= member.getUsername() %></b>
    </a>
  </LI>
<%
    }
  }
%>

</UL>

<%--Reply To Friends--%>
<span style="text-decoration: underline; font-weight: bold">Reply To Friend Requests:</span> <br><br>
<UL>
  <%
    for (FriendRequest fr : user.getFriendRequests()) {
  %>

  <LI><p><b><%=fr.getRequestingMember().getUsername()+": "+fr.getMessage()%></b></p>
    <a href=<%="\\replyToFriendRequest?forumId="+forum.id+
            "&userId="+user.getId()+"&friend="+fr.getRequestingMember().getUsername()+"&friendId="+fr.getRequestingMember().getId()+"&answer=1"%>>
      <b>Accept</b><br>
    </a>
    <a href=<%="\\replyToFriendRequest?forumId="+forum.id+
            "&userId="+user.getId()+"&friend="+fr.getRequestingMember().getUsername()+"&friendId="+fr.getRequestingMember().getId()+"&answer=0"%>>
      <b>Deny</b>
    </a>
  </LI>
  <%
    }
  %>

</UL>

<%--Remove Friends--%>
<span style="text-decoration: underline; font-weight: bold">Remove Friends:</span> <br><br>
<UL>
  <%
    for (User friend : user.getFriends()) {
  %>

  <LI>
    <a href=<%="\\removeFriend?forumId="+forum.id+
            "&userId="+user.getId()+"&friend="+friend.getUsername()+"&friendId="+friend.getId()%>>

      <b><%= friend.getUsername() %></b>
    </a>
  </LI>
  <%
    }
  %>

</UL>

<%--Report Members--%>
<span style="text-decoration: underline; font-weight: bold">Report Other Member:</span> <br><br>
<UL>
    <%
        for (User member : forum.getMembers()) {
            if(!user.getUsername().equals(member.getUsername())){
    %>

    <LI>
        <a href=<%="\\reportMember.jsp?forumId="+forum.id+
                "&reporterId="+user.getId()+"&reportee="+member.getUsername()+"&reporteeId="+member.getId()%>>
            <b><%= member.getUsername() %></b>
        </a>
    </LI>
    <%
            }
        }
    %>
</UL>

<%--Ban Members--%>
<span style="text-decoration: underline; font-weight: bold">Ban Member:</span> <br><br>
<UL>
    <%
        for (User member : forum.getMembers()) {
            if(!user.getUsername().equals(member.getUsername())){
                // TODO fix to get moderated
    %>

    <LI>
        <a href=<%="\\banMember?subForumId="+0+
                "&userId="+user.getId()+"&banned="+member.getUsername()+"&bannedId="+member.getId()%>>
            <b><%= member.getUsername() %></b>
        </a>
    </LI>
    <%
            }
        }
    %>
</UL>



</body>
</html>
