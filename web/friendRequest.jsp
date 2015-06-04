<%--
  Created by IntelliJ IDEA.
  User: Roee
  Date: 06-05-15
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String receiver = request.getParameter("receiver"); %>
<% String receiverId = request.getParameter("receiverId"); %>


<form id="frm" action="${pageContext.request.contextPath}/sendFriendRequest" method="get">
  <p>
    Your friend request to <%=receiver%>
  </p><br>
    <input type="hidden" name="receiverId" value=<%=receiverId%> />
  <label>
  <textarea name="content" rows="5" cols="50" form="frm">
  <%="Hi "+receiver+",\n"%>
  </textarea>  </label><br>
  <input type="submit" value="Send">
</form>