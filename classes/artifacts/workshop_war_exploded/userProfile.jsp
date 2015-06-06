<%@ page import="content.Forum" %>
<%@ page import="users.User" %>
<%@ page import="users.FriendRequest" %>
<%@ page import="java.util.Set" %>
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
<b><i>State: <%= user.getStateName() %></i></b> <br>


<%--Friend Requests--%>
<span style="text-decoration: underline; font-weight: bold">Send Friend Requests:</span> <br><br>
<UL>
  <%
      Set<User> friends = user.getFriends();
  for (User member : forum.getMembers()) {
    if(!user.getUsername().equals(member.getUsername()) && !friends.contains(member)){
%>

  <LI>
        <a href="\friendRequest.jsp?receiverId=<%=member.getId()%>&receiver=<%=member.getUsername()%>">
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
      <a href="\replyToFriendRequest?friendReqId=<%=fr.id%>&answer=1">
          <b>Accept</b><br>
      </a>
      <a href="\replyToFriendRequest?friendReqId=<%=fr.id%>&answer=0">
          <b>Deny</b><br>
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
        <a href="\reportMember.jsp?reporteeId=<%=member.getId()%>&reportee=<%=member.getUsername()%>">
            <b><%= member.getUsername() %></b>
        </a>
    </LI>
    <%
            }
        }
    %>
</UL>

<%--Ban Members--%>
    <% if(user.isMod()){ %>
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
        }
    %>
</UL>



</body>
</html>
