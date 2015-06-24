<%@ page import="content.Forum" %>
<%@ page import="users.User" %>
<%@ page import="java.util.Date" %>
<%@ page import="utils.HtmlUtils" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Forum forum = (Forum)request.getAttribute("forum"); %>
<% User user = (User) request.getAttribute("user"); %>

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
    <link href="css/comment.css" rel="stylesheet">
    <link id="base-style" href="css/style.css" rel="stylesheet">
    <link id="base-style-responsive" href="css/style-responsive.css" rel="stylesheet">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext' rel='stylesheet' type='text/css'>
    <!-- end: CSS -->
    <% if(!user.isGuest()){%>
    <%=HtmlUtils.getAjaxScript()%>
    <%}%>

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
            <div class="nav-no-collapse header-nav">
                <ul class="nav pull-right">

                    <% if(!user.isGuest()){%>
                    <li class="dropdown hidden-phone">
                        <a class="btn dropdown-toggle" href="/notificationsPage">
                            <i class="icon-bell"></i>
                                        <span id="notificationsButton" class="badge red">
                                        0 </span>
                        </a>
                    </li>
                    <!-- start: Notifications Dropdown -->
                    <!-- end: Notifications Dropdown -->
                    <%--friend requests--%>
                    <li class="dropdown hidden-phone">
                        <a class="btn dropdown-toggle" href="/friendRequests">
                            <i class="icon-user"></i>
                                    <span id="requestsButton" class="badge red">
                                    0 </span>
                        </a>
                    </li>
                    <%}%>

                    <!-- start: User Dropdown -->
                    <li class="dropdown">
                        <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                            <i class="halflings-icon white user"></i> <%=user.getUsername()%>
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="dropdown-menu-title">
                                <span>Account Settings</span>
                            </li>


                            <% if(user.isGuest()){%>
                            <li><a href="/registerPage"><i class="halflings-icon off"></i> Register</a></li>
                            <li><a href="/login.jsp?forumId=<%=forum.id%>"><i class="halflings-icon off"></i> Login</a></li>
                            <%} else{ %>
                            <li><a href="/logout?forumId=<%=forum.id%>"><i class="halflings-icon off"></i> Logout</a></li>
                            <li><a href="/profile"><i class="halflings-icon user"></i> Profile</a></li>
                            <%} %>

                        </ul>
                    </li>
                    <!-- end: User Dropdown -->
                </ul>
            </div>
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




    </div><!--/fluid-row-->
        <!-- start: Content -->
        <div id="content" class="span10">


            <ul class="breadcrumb">
                <li>
                    <i class="icon-home"></i>
                    <a href="/home">Home</a>

                </li>

            </ul>

            <!--                //start here-->

            <div class="container">
                <div class="center">
                    <div class="col-sm-10 col-sm-offset-1" id="logout">
                        <div class="page-header">
                            <a class="center " href="#">
                               <img class="media-object img-circle" src="\img\gallery\user-icon.png" alt="profile">
                            </a>
                        </div>
                        <div class="comment-tabs">
                            <ul class="nav nav-tabs" role="tablist">
                                <li class="active"><a href="#profile" role="tab" data-toggle="tab"><h4 class="reviews text-capitalize">Profile</h4></a></li>
                                <li><a href="#settings" role="tab" data-toggle="tab"><h4 class="reviews text-capitalize">Report</h4></a></li>
                                <li><a href="#freinds" role="tab" data-toggle="tab"><h4 class="reviews text-capitalize">Friends</h4></a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active" id="profile">



                                    <!--                    //profile starts-->

                                    <form class = "well span8">



                                        <div class="form-group">
                                            <label for="userNameInput">User Name</label>
                                            <input type="text" id="userNameInput" class="form-control" placeholder="<%=user.getUsername()%>" disabled>
                                        </div>
                                        <div class="form-group">
                                            <label for="emailInput">Email address</label>
                                            <input type="email" id="emailInput" class="form-control" placeholder="<%=user.getEmail()%>" disabled>
                                        </div>
                                        <div class="form-group">
                                            <label for="dateCreatedInput">Date Created</label>
                                            <input type="text" id="dateCreatedInput" class="form-control" placeholder="<%=new Date(user.getLoginTime()).toString()%>" disabled>
                                        </div>
                                        <div class="form-group">
                                            <label for="userNameInput">Status</label>
                                            <input type="text" id="statusInput" class="form-control" placeholder="<%=user.getStatus()%>" disabled>

                                        </div>

                                        <br>
                                        <div class="form-group">
                                            <a href="/changePasswordPage" id="changePassword" type="submit" class="btn btn-primary" >Change Password</a>
                                        </div>



                                    </form>


                                    <!--

                                                        //profile ends
                                    -->


                                </div>
                                <div class="tab-pane" id="settings">
                                    <!--                    //start settings-->

                                    <form class="well span10" action="/reportMember" style="margin: 0px 0px 10px; height: 400px;" id="formreportmember">

                                        <div class="control-group">
                                            <label class="control-label" for="selectError1"><h3>Report on member</h3></label>
                                            <div class="controls">
                                                <table>
                                                    <col width="250">
                                                    <col width="250">
                                                    <tr>
                                                        <td>


                                                            <select name="reporteeId" id="selectError1" data-rel="chosen">
                                                                <% for (User fuser : forum.getNonGuestMembers()) { %>
                                                                <option name="reporteeId" value="<%=fuser.getId()%>"><%=fuser.getUsername()%></option>
                                                                <% } %>
                                                            </select>
                                                        </td>
                                                        <td>
                                                            <input type="submit" class="btn btn-primary" value="Report">  </input> </td></table>
                                                <br>
                                                <textarea form="formreportmember" name="content" class="form-control" rows="5" placeholder="Write your report here..." style="margin: 0px 0px 10px; width: 496px; height: 100px;"></textarea>



                                            </div>

                                        </div>
                                    </form>


                                    <!--                    //end settings-->
                                </div>
                                <div class="tab-pane" id="freinds">
                                    <!--                   //start f-->
                                    <div class="container">




                                        <!--                    //start serch people-->


                                        <form class="well sapn8" action="/sendFriendRequest" id="idontknow">
                                            <div class="control-group">
                                                <label class="control-label" for="idontknow"><h3>Add new friend</h3></label>
                                                <div class="controls">
                                                    <table>
                                                        <col width="250">
                                                        <col width="250">
                                                        <tr>
                                                            <td>
                                                                <select name="receiverId" id="idontknow2" data-rel="chosen">
                                                                    <% for (User fuser : forum.getNonGuestMembers()) { %>
                                                                    <option name="receiverId"  value="<%=fuser.getId()%>"><%=fuser.getUsername()%></option>

                                                                    <% } %>
                                                                </select>

                                                            </td>
                                                            <td>
                                                                <input type="submit" class="btn btn-primary" value="Send friend request" \>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <br>
                                                    <textarea form="idontknow" name="content" class="form-control" rows="5" placeholder="Write your report here..." style="margin: 0px 0px 10px; width: 496px; height: 100px;"></textarea>
                                                </div>

                                            </div>
                                        </form>

                                        <div class="box span10">
                                            <div class="box-header" data-original-title>
                                                <h2><i class="halflings-icon white user"></i><span class="break"></span>Friends</h2>

                                            </div>


                                            <div class="box-content">
                                                <table class="table table-striped table-bordered bootstrap-datatable">
                                                    <col width="1000">
                                                    <col width="1">
                                                    <thead>
                                                    <tr>
                                                        <!--
                                                                                          <th>Friend</th>
                                                                                          <th></th>
                                                        -->
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <% for (User u : user.getFriends()) { %>
                                                    <tr>
                                                        <form action="/removeFriend" method="get">
                                                            <input type="hidden" name="forumId" value="<%=forum.id%>">
                                                            <input type="hidden" name="friend" value="<%=u.getUsername()%>">
                                                            <input type="hidden" name="friendId" value="<%=u.getId()%>">
                                                            <td class="center"><%=u.getUsername()%></td>
                                                            <td class="center"><input type="submit" class="btn btn-mini btn-danger" value="delete"/></td>
                                                        </form>
                                                    </tr>
                                                    <% } %>

                                                    </tbody>
                                                </table>
                                            </div>
                                        </div><!--/span-->










                                    <!--                    //end f-->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>



                <!--			//done here-->


            </div><!--/.fluid-container-->

            <!-- end: Content -->






        </div><!--/#content.span10-->


    <div class="modal hide fade" id="myModal">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>Freind Request</h3>
        </div>
        <div class="modal-body">
            <p><h3>To:</h3> user name <br><br></p>
            <p><textarea class="form-control" rows="5" placeholder="Write your message here..." style="margin: 0px 0px 10px; width: 496px; height: 100px;"></textarea></p>
        </div>
        <div class="modal-footer">
            <a href="#" class="btn" data-dismiss="modal">Close</a>
            <a href="#" class="btn btn-primary">Send</a>
        </div>
    </div>


    <div class="modal hide fade" id="report">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>Report Member</h3>
        </div>
        <div class="modal-body">
            <p><h3>Sending repot to:</h3> user name <br><br></p>
            <p><textarea class="form-control" rows="5" placeholder="Write your report here..." style="margin: 0px 0px 10px; width: 496px; height: 100px;"></textarea></p>
        </div>
        <div class="modal-footer">
            <a href="#" class="btn" data-dismiss="modal">Close</a>
            <a href="#" class="btn btn-primary">Send</a>
        </div>
    </div>




    <div class="common-modal modal fade" id="common-Modal1" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-content">
            <ul class="list-inline item-details">
                <li><a href="http://themifycloud.com">Admin templates</a></li>
                <li><a href="http://themescloud.org">Bootstrap themes</a></li>
            </ul>
        </div>
    </div></div> </div>

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
