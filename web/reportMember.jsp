<%--
  Created by IntelliJ IDEA.
  User: Roee
  Date: 06-05-15
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String reportee = request.getParameter("reportee"); %>
<% String reporteeId = request.getParameter("reporteeId"); %>


<form id="frm" action="${pageContext.request.contextPath}/reportMember" method="get">
  <p>
    Your report to <%=reportee%><br>
  </p><br>
    <input type="hidden" name="reporteeId" value=<%=reporteeId%> />
  <label>
    Title:<br>
    <input type="text" name="title" maxlength="25" required/><br>
  </label>
  <label>
    Content:<br>
  <textarea name="content" rows="5" cols="50" form="frm">
  <%="Hi "+reportee+",\n"%>
  </textarea>  </label><br>
  <input type="submit" value="Send">
</form>