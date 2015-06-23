<%@ page import="content.Forum" %>
<%--
  Created by IntelliJ IDEA.
  User: thinkPAD
  Date: 5/6/2015
  Time: 6:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String forumIdString = (String)request.getParameter("forumId"); %>
<%
  int id=-1;
  try {
    id=Integer.parseInt(forumIdString);
  }
  catch (NumberFormatException e) {
    System.out.println(e);
  }
%>
<!DOCTYPE html>
<html>
<head>
  <title>
    Registration
  </title>
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


    <!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
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
            <div class="login-box">
                <div class="icons">
                    <a href="/home"><i class="halflings-icon home"></i></a>
                </div>
                <div class = "input-prepend">
                    <h1>Register</h1>
                </div>
                <form class="form-horizontal" action="/register" method="get">
                    <fieldset>
                        <input name="forumId" type="hidden" value="<%=id%>">
                        <div class="input-prepend" title="Username">
                            <span class="add-on"><i class="halflings-icon user"></i></span>
                            <input class="input-large span10" name="username" id="username" type="text" placeholder="type username"  maxlength="25"/>
                        </div>
                        <div class="clearfix"></div>

                        <div class="input-prepend" title="Password">
                            <span class="add-on"><i class="halflings-icon lock"></i></span>
                            <input class="input-large span10" name="pass" id="password" type="password" placeholder="type password"  maxlength="25"/>
                        </div>

                        <div class="input-prepend" title="Email">
                            <span class="add-on"><i class="halflings-icon envelope"></i></span>
                            <input class="email input-large span10" name="email" id="email" type="text" placeholder="type email"  maxlength="30"/>
                        </div>


                        <div class="input-prepend" title="Quest&Ans">

                            <span class="add-on"><i class="halflings-icon question-sign"></i></span>
                            <input class="input-large span10" name="question" id="question" type="text" placeholder="type a question" maxlength="25" style="background-color: cornsilk;"  />

                        </div>

                        <div class="input-prepend" title="Quest&Ans" >
                            <span class="add-on"><i class="halflings-icon pencil"></i></span>
                            <input class="input-large span10" name="answer" id="answer" type="text" placeholder="type possible answer" maxlength="25" style="background-color: cornsilk;"  />
                        </div>

                        <div class="input-prepend" title="Register">
                            <button type="submit" class="btn btn-primary">Register</button>
                        </div>
                        <div class="clearfix"></div>

                </fieldset>
                </form>

            </div><!--/span-->
        </div><!--/row-->


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


<!--
<h2>registration</h2><br>
<form action="\register" method="get" id="registerform">
  <input name="forumId" type="hidden" value="<%=id%>">
  user:&nbsp;<input name="username" type="text" value=""><br>
  password:&nbsp;<input name="pass" type="password" value=""><br>
  <input type="submit">
</form>
</body>
</html>-->
