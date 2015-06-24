<%@ page import="content.Forum" %>
<%@ page import="users.User" %>
<%@ page import="content.SubForum" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.ArrayList" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Forum forum = (Forum)request.getAttribute("forum"); %>
<% SubForum subForum = (SubForum)request.getAttribute("subForum"); %>
<% String action = (String) request.getAttribute("action"); %>
<% String returnPage = (String) request.getAttribute("returnPage"); %>

<!DOCTYPE html>
<html lang="en">
<head>

  <!-- start: Meta -->
  <meta charset="utf-8">
  <title>Great Minds</title>
  <meta name="description" content="Bootstrap Metro Dashboard">
  <meta name="author" content="Dennis Ji">
  <meta name="keyword" content="Metro, Metro UI, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
  <!-- end: Meta -->

  <!-- start: Mobile Specific -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- end: Mobile Specific -->

  <!-- start: CSS -->
  <link id="bootstrap-style" href="css/bootstrap.min.css" rel="stylesheet">
  <link href="css/bootstrap-responsive.min.css" rel="stylesheet">
  <link id="base-style" href="css/style.css" rel="stylesheet">
  <link id="base-style-responsive" href="css/style-responsive.css" rel="stylesheet">
  <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext' rel='stylesheet' type='text/css'>
  <!-- end: CSS -->


  <!-- The HTML5 shim, for IE6-8 sutipport of HTML5 elements -->
  <!--[if lt IE 9]>
  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
  <link id="ie-style" href="css/ie.css" rel="stylesheet">
  <![endif]-->

  <!--[if IE 9]>
  <link id="ie9style" href="css/ie9.css" rel="stylesheet">
  <![endif]-->

  <!-- start: Favicon -->
  <link rel="shortcut icon" href="img/favicon.ico">
  <!-- end: Favicon -->

  <style type="text/css">
    body { background: url(img/bg-login.jpg) !important; }
  </style>



</head>

<body>
<div class="container-fluid-full">
  <div class="row-fluid">

    <div class="row-fluid">
      <div class="login-box ">
        <div>
          <br><br>
          <h1><center><%=getTitle(action)%></center></h1>
          <br>
          <h4><center><%=getName(action, forum, subForum)%></center></h4>
          <br><br>



          <!--                    <form class="form-horizontal center" action="index.html" method="post">-->



          <i class="glyphicons-icon crown pull-left"></i>
          <center>
            <form action="<%=getActionPage(action)%>">
              <fieldset>
                <div class="span10 collapse-group row">
                  <center>
                    <p>
                      <select name="userId"id="appointDD" data-rel="chosen" >
                        <%for (User user : getListOfUsers(action, forum, subForum)){%>
                        <option value="<%=user.getId()%>"><%=user.getUsername()%></option>
                        <%}%>
                      </select>

                    </p>
                    <br><br><br><br><br><br><br><br><br><br>
                    <div class="">
                      <input type="hidden" name="forumId" value="<%=forum.id%>">
                      <%=getHiddenInputs(action,forum,subForum)%>
                      <input type="submit" href="#" value="<%=getSubmitButtonValue(action)%>" class="btn btn-primary pull-right">
        </div>
        <br><br><br><br>
        </center>
      </div>

      </fieldset>
      </form>
      </center>

      <!--

                          </form>
      -->

    </div><!--/span-->
  </div><!--/row-->
</div>

</div><!--/.fluid-container-->

</div><!--/fluid-row-->
<div class="common-modal modal fade" id="common-Modal1" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-content">
    <ul class="list-inline item-details">
      <li><a href="http://themifycloud.com">Admin templates</a></li>
      <li><a href="http://themescloud.org">Bootstrap themes</a></li>
    </ul>
  </div>
</div>
<!-- start: JavaScript-->

<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/jquery-migrate-1.0.0.min.js"></script>

<script src="js/jquery-ui-1.10.0.custom.min.js"></script>

<script src="js/jquery.ui.touch-punch.js"></script>

<script src="js/modernizr.js"></script>

<script src="js/bootstrap.min.js"></script>

<script src="js/jquery.cookie.js"></script>

<script src='js/fullcalendar.min.js'></script>

<script src='js/jquery.dataTables.min.js'></script>

<script src="js/excanvas.js"></script>
<script src="js/jquery.flot.js"></script>
<script src="js/jquery.flot.pie.js"></script>
<script src="js/jquery.flot.stack.js"></script>
<script src="js/jquery.flot.resize.min.js"></script>

<script src="js/jquery.chosen.min.js"></script>

<script src="js/jquery.uniform.min.js"></script>

<script src="js/jquery.cleditor.min.js"></script>

<script src="js/jquery.noty.js"></script>

<script src="js/jquery.elfinder.min.js"></script>

<script src="js/jquery.raty.min.js"></script>

<script src="js/jquery.iphone.toggle.js"></script>

<script src="js/jquery.uploadify-3.1.min.js"></script>

<script src="js/jquery.gritter.min.js"></script>

<script src="js/jquery.imagesloaded.js"></script>

<script src="js/jquery.masonry.min.js"></script>

<script src="js/jquery.knob.modified.js"></script>

<script src="js/jquery.sparkline.min.js"></script>

<script src="js/counter.js"></script>

<script src="js/retina.js"></script>

<script src="js/custom.js"></script>
<!-- end: JavaScript-->

</body>
</html>


<%!
  public static String getTitle(String action){
    String title="";

    if(action.equals("changeAdmin")) {
      title = "Choose user to appoint";
    }
    else if(action.equals("appointModerator")){
      title = "Choose user to appoint";
    }
    else if(action.equals("dismissModerator")){
      title = "Choose moderator";
    }

    return title;
  }

  public static String getName(String action, Forum forum, SubForum subForum){
    String name = "";

    if(action.equals("changeAdmin")) {
      name = forum.getName();

    }
    else if(action.equals("appointModerator")){
      name = subForum.getName();
    }
    else if(action.equals("dismissModerator")){
      name = subForum.getName();
    }

    return name;
  }

  public static String getActionPage(String action){
    String name = "";

    if(action.equals("changeAdmin")) {
      name = "/changeAdmin";
    }
    else if(action.equals("appointModerator")){
      name = "/appointModerator";
    }
    else if(action.equals("dismissModerator")){
      name = "/dismissModerator";
    }

    return name;
  }



  public static List<User> getListOfUsers(String action, Forum forum, SubForum subForum){
    List<User> users = new ArrayList<User>();

    if(action.equals("changeAdmin")) {
      users = forum.getNonGuestMembers();
    }
    else if(action.equals("appointModerator")){
      users = forum.getNonGuestMembers();
    }
    else if(action.equals("dismissModerator")){
      users = subForum.getModerators();
    }

    return users;
  }

  public static String getSubmitButtonValue(String action){
    String name = "";

    if(action.equals("changeAdmin")) {
      name = "Change";
    }
    else if(action.equals("appointModerator")){
      name = "Appoint";
    }
    else if(action.equals("dismissModerator")){
      name = "Dismiss";
    }

    return name;
  }

  public static String getHiddenInputs(String action, Forum forum, SubForum subForum){
    String inputs = "";

    if(action.equals("changeAdmin")) {
      inputs = "";
    }
    else if(action.equals("appointModerator")){
      inputs = "<input type=\"hidden\" name=\"subForumId\" value=\""+subForum.id+"\">";
    }
    else if(action.equals("dismissModerator")){
      inputs = "<input type=\"hidden\" name=\"subForumId\" value=\""+subForum.id+"\">";
    }

    return inputs;
  }

%>