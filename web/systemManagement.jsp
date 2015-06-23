<%@ page import="content.Forum" %>
<%@ page import="content.SubForum" %>
<%@ page import="utils.HtmlUtils" %>
<%@ page import="users.User" %>
<%--
  Created by IntelliJ IDEA.
  User: thinkPAD
  Date: 5/6/2015
  Time: 4:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% User superAdmin = (User) request.getAttribute("superAdmin"); %>
<% Integer numberOfForums = (Integer) request.getAttribute("numberOfForums"); %>


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
            <a class="brand" href="index.html"><span>Great Minds</span></a>


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
                    <a href="index.html">Home</a>
                    <i class="icon-angle-right"></i>
                </li>
                <li><a href="#">System Management</a></li>
            </ul>


            <!--

                            //enter here
            -->
            <div>
                <div><div><h1>Hello <%=superAdmin.getUsername()%></h1>
                    <a class="quick-button span2 pull-right">
                        <i class="icon-group "></i>
                        <p>Forums</p>
                        <span class="notification blue "><%=numberOfForums.toString()%></span>
                    </a>
                    <a class="quick-button span2 pull-right" href="/newforum.jsp">
                        <i class="icon-group "></i>
                        <p>Add forum</p>
                        <span class="notification blue "><i class = "halflings-icon white plus"></i></span>
                    </a></div>
                    <br>
                    <div><h3>What would you like to do?</h3></div> </div>


                <br><br><br><br>
                <form class="well">
                    <div>
                        <fieldset>




                            <legend>Add member type</legend>
                            <div>

                                <div class="span12 collapse-group row center">
                                    <p><a class="btn btn-mini" href="#">+</a>&nbsp;&nbsp;&nbsp;Add new member type </p>
                                    <p class="collapse"><input type="text" id="memType" style="background-color: lavender" maxlength="25">&nbsp;&nbsp; <a class="btn btn-small btn-success" href="#" style="vertical-align:top">Update</a></p>


                                </div>

                            </div>

                            <br><br><br>

                            <legend>Appoint admin</legend>

                            <div class="span6 collapse-group row center">
                                <p><select id="appModSub" data-rel="chosen">
                                    <option>Sub 1</option>
                                    <option>Sub 2</option>
                                    <option>Sub 3</option>
                                    <option>Sub 4</option>
                                    <option>Sub 5</option>
                                </select> <a href="#" class="btn btn-primary" style="vertical-align:top">Choose</a> <br><br> </p>

                                <p class="collapse center" style = "margin-left: 50px"><select id="appointAdmin" data-rel="chosen">
                                    <option>user 1</option>
                                    <option>user 2</option>
                                    <option>user 3</option>
                                    <option>user 4</option>
                                    <option>user 5</option>
                                </select> <a href="#" class="btn btn-primary" style="vertical-align:top">Appoint</a></p>
                            </div>

                        </fieldset>

                    </div>


                </form>

            </div>

            <!--                //done here-->

        </div><!--/row-->

        <!-- end: Content -->
    </div><!--/#content.span10-->


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

    <script src = "js/dropdown.js"></script>

    <!-- end: JavaScript-->

</body>
</html>
