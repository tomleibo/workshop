<%--
  Created by IntelliJ IDEA.
  User: Roee
  Date: 06-05-15
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String forum = request.getParameter("forum"); %>
<% String userId = request.getParameter("userId"); %>
<% String friend = request.getParameter("friend"); %>
<% String friendId = request.getParameter("friendId"); %>


<form id="frm" action="${pageContext.request.contextPath}/removeFriendServlet" method="get">
  <p>
    Your friend removal request to <%=friend%>
  </p><br>
  <label>
    <input type="hidden" name="forum" value=<%=forum%> />
  </label>
  <label>
    <input type="hidden" name="userId" value=<%=userId%> />
  </label>
  <label>
    <input type="hidden" name="friend" value=<%=friend%> />
  </label>
  <label>
    <input type="hidden" name="friendId" value=<%=friendId%> />
  </label>
  <label>
  <textarea name="content" rows="5" cols="50" form="frm">
  <%="Hi "+friend+",\n"%>
  </textarea>  </label><br>
  <input type="submit" value="Send">
</form>