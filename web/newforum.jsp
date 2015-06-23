<%@ page import="content.Forum" %>
<%@ page import="policy.ForumPolicy" %>
<%--
  Created by IntelliJ IDEA.
  User: Shai Rippel
  Date: 05/05/2015
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>

  <!-- start: Meta -->
  <meta charset="utf-8">
  <title>Greate Minds</title>
  <meta name="description" content="Bootstrap Metro Dashboard">
  <meta name="author" content="Dennis Ji">
  <meta name="keyword" content="Metro, Metro UI, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
  <!-- end: Meta -->

  <!-- start: Mobile Specific -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- end: Mobile Specific -->

  <!-- start: CSS -->
  <link id="bootstrap-style" href="css/bootstrap.min.css" rel="stylesheet">
  <link href="bcss/bootstrap-responsive.min.css" rel="stylesheet">
  <link href="css/comment.css" rel="stylesheet">
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




</head>

<body>
<!-- start: Header -->
<div class="navbar">
  <div class="navbar-inner">
    <div class="container-fluid">
      <a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a>
      <a class="brand" href="/home"><span>Great Minds</span></a>

      <!-- start: Header Menu -->
      <!-- end: Header Menu -->

    </div>
  </div>
</div>
<!-- start: Header -->

<div class="container-fluid-full">
  <div class="row-fluid">

    <!-- start: Main Menu -->
    <div id="sidebar-left" class="span2">
      <div class="nav-collapse sidebar-nav">
        <ul class="nav nav-tabs nav-stacked main-menu">
          <li><a href="/home"><i class="glyphicons-icon white group"></i><span class="hidden-tablet"> Forums</span></a></li>


        </ul>
      </div>
    </div>
    <!-- end: Main Menu -->

    <noscript>
      <div class="alert alert-block span10">
        <h4 class="alert-heading">Warning!</h4>
        <p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
      </div>
    </noscript>





    <!-- start: Content -->
    <div id="content" class="span10">


      <ul class="breadcrumb">
        <li>
          <i class="icon-home"></i>
          <a href="/home">Home</a>
          <i class="icon-angle-right"></i>
        </li>
        <li><a href="#">Add new forum</a></li>
      </ul>


      <!--

                      //enter here
      -->
      <div>


        <div class="box span12">
          <div class="box-header" data-original-title>
            <h2><i class="halflings-icon plus"></i><span class="break"></span>Add Forum</h2>


          </div>
          <div class="box-content">


            <div class="control-group">
              <form class="form-horizontal well" action="/addForum" method="get" >
                <label class="control-label" for="forumName"><h3>Forum Name:</h3> </label>
                <div class="controls">
                  <input type="text" name="name" id="forumName" maxlength="25" required>
                </div>

              <br>
                <div>
                  <fieldset>
                    <legend>Forum Policy:</legend>
                    <label class="control-label" for="maxMod">Max number of moderators: </label>
                    <div class="controls">
                      <input type="number" name="maxMods" id="maxMod" min="0" max="10000000000" required>
                    </div>

                    <br>

                    <label class="control-label" for="passReg">Password regex: </label>
                    <div class="controls">
                      <input type="text" name="passRegex" id="passReg"  maxlength="50" required>
                    </div>

                    <br>

                    <label class="control-label" for="selectHash">Hash function: </label>
                    <div class="controls">
                      <select name="hash" id="selectHash" data-rel="chosen">
                        <%for(ForumPolicy.HashFunction hf : ForumPolicy.HashFunction.values()){%>
                            <option><%=hf.name()%></option>
                        <%}%>
                      </select>
                    </div>

                    <br>

                    <label class="control-label" for="sessionTO">Session time out (minutes): </label>
                    <div class="controls">
                      <input type="number" min = "0" name="timeout" id="sessionTO"   max="10000000000" required>
                    </div>

                    <br>

                    <label class="control-label" for="sessionIdle">Session idle time (minutes): </label>
                    <div class="controls">
                      <input type="number" min = "0" name="idle" id="sessionIdle"  max="10000000000" required>
                    </div>

                    <br>




                    <label class="control-label" for="passMaxTime">Password expired time (months): </label>
                    <div class="controls">
                      <input type="number" min="0" max="10000000000" name="passExpire" id="passMaxTime" required>
                    </div>

                    <br>

                    <label class="control-label" for="minPosts">Minimum posts moderator should have: </label>
                    <div class="controls">
                      <input type="number" min="0" max="10000000000" name="minPosts" id="minPosts" required>
                    </div>

                    <br>

                    <label class="control-label" for="minSeniority">Minimum seniority moderator should have: </label>
                    <div class="controls">
                      <input type="number" min="0" max="10000000000" name="minSeniority" id="minSeniority" required>
                    </div>

                    <br>

                    <div class="control-group">
                      <label class="control-label ">Should user have mail authentication?</label>
                      <div class="controls">
                        <label class="radio">
                          <input type="radio" name="authentication" id="mailAuthenticationYes" value="1" checked="">
                          Yes
                        </label>
                        <div style="clear:both"></div>
                        <label class="radio">
                          <input type="radio" name="authentication" id="mailAuthenticationNo" value="0">
                          No
                        </label>
                      </div>
                    </div>

                    <br>


                    <div class="control-group">
                      <label class="control-label ">Should ask user identification question?</label>
                      <div class="controls">
                        <label class="radio">
                          <input type="radio" name="identifyQ" id="yesOptId" value="1" checked="">
                          Yes
                        </label>
                        <div style="clear:both"></div>
                        <label class="radio">
                          <input type="radio" name="identifyQ" id="noOptId" value="0">
                          No
                        </label>
                      </div>
                    </div>

                    <br>


                    <div class="control-group">
                      <label class="control-label ">Should moderator edit posts?</label>
                      <div class="controls">
                        <label class="radio">
                          <input type="radio" name="moderatorEdit" id="yesModEdit" value="1" checked="">
                          Yes
                        </label>
                        <div style="clear:both"></div>
                        <label class="radio">
                          <input type="radio" name="moderatorEdit" id="noModEdit" value="0">
                          No
                        </label>
                      </div>
                    </div>

                    <br>






                </fieldset>




                <div class="form-actions">
                  <input type="submit" class="btn btn-primary" href="comment.html" value="Send">
                  <input type="reset" class="btn" href="/home" value="Cancel">
                </div>

            </div>


            </form>

          </div>
        </div><!--/span-->




      </div>

      <!--                //done here-->

    </div><!--/row-->






    <!-- end: Content -->






  </div><!--/#content.span10-->
</div><!--/fluid-row-->
</div>

<div class="modal hide fade" id="myModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Settings</h3>
  </div>
  <div class="modal-body">
    <p>Here settings can be configured...</p>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">Close</a>
    <a href="#" class="btn btn-primary">Save changes</a>
  </div>
</div>

<div class="common-modal modal fade" id="common-Modal1" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-content">
    <ul class="list-inline item-details">
      <li><a href="http://themifycloud.com">Admin templates</a></li>
      <li><a href="http://themescloud.org">Bootstrap themes</a></li>
    </ul>
  </div>
</div>

<div class="clearfix"></div>

<footer>

</footer>

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
