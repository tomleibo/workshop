<%--
  Created by IntelliJ IDEA.
  User: Roee
  Date: 06-05-15
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="frm" action="/postNewThread" method="get">
  <p>
    Post New Thread
  </p><br>
  <label>
    Title:<br>
    <input type="text" name="title"/>
  </label><br>
  <label>
        <textarea name="content" rows="5" cols="50" form="frm"></textarea>
  </label><br>
  <input type="submit" value="Post">
</form>