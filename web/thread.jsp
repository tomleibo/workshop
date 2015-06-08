<%@ page import="content.Thread" %>
<%@ page import="content.Message" %>
<%@ page import="content.Forum" %>
<%@ page import="content.SubForum" %>
<%@ page import="users.User" %>
<%--
  Created by IntelliJ IDEA.
  User: thinkPAD
  Date: 5/6/2015
  Time: 4:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% SubForum sub = (SubForum)request.getAttribute("subForum"); %>
<% Forum forum = (Forum)request.getAttribute("forum"); %>
<% User user = (User) request.getAttribute("user"); %>
<% Thread t = (Thread)request.getAttribute("thread"); %>
<% Message openingMsg = t.getOpeningMessage(); %>

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
      <div class="nav-no-collapse header-nav">
        <ul class="nav pull-right">
          <li class="dropdown hidden-phone">
            <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
              <i class="icon-bell"></i>
								<span class="badge red">
								7 </span>
            </a>
            <ul class="dropdown-menu notifications">
              <li class="dropdown-menu-title">
                <span>You have 11 notifications</span>
                <a href="#refresh"><i class="icon-repeat"></i></a>
              </li>
              <li>
                <a href="#">
                  <span class="icon blue"><i class="icon-user"></i></span>
                  <span class="message">New user registration</span>
                  <span class="time">1 min</span>
                </a>
              </li>
              <li>
                <a href="#">
                  <span class="icon green"><i class="icon-comment-alt"></i></span>
                  <span class="message">New comment</span>
                  <span class="time">7 min</span>
                </a>
              </li>
              <li>
                <a href="#">
                  <span class="icon green"><i class="icon-comment-alt"></i></span>
                  <span class="message">New comment</span>
                  <span class="time">8 min</span>
                </a>
              </li>
              <li>
                <a href="#">
                  <span class="icon green"><i class="icon-comment-alt"></i></span>
                  <span class="message">New comment</span>
                  <span class="time">16 min</span>
                </a>
              </li>
              <li>
                <a href="#">
                  <span class="icon blue"><i class="icon-user"></i></span>
                  <span class="message">New user registration</span>
                  <span class="time">36 min</span>
                </a>
              </li>
              <li>
                <a href="#">
                  <span class="icon yellow"><i class="icon-shopping-cart"></i></span>
                  <span class="message">2 items sold</span>
                  <span class="time">1 hour</span>
                </a>
              </li>
              <li class="warning">
                <a href="#">
                  <span class="icon red"><i class="icon-user"></i></span>
                  <span class="message">User deleted account</span>
                  <span class="time">2 hour</span>
                </a>
              </li>
              <li class="warning">
                <a href="#">
                  <span class="icon red"><i class="icon-shopping-cart"></i></span>
                  <span class="message">New comment</span>
                  <span class="time">6 hour</span>
                </a>
              </li>
              <li>
                <a href="#">
                  <span class="icon green"><i class="icon-comment-alt"></i></span>
                  <span class="message">New comment</span>
                  <span class="time">yesterday</span>
                </a>
              </li>
              <li>
                <a href="#">
                  <span class="icon blue"><i class="icon-user"></i></span>
                  <span class="message">New user registration</span>
                  <span class="time">yesterday</span>
                </a>
              </li>
              <li class="dropdown-menu-sub-footer">
                <a>View all notifications</a>
              </li>
            </ul>
          </li>
          <!-- start: Notifications Dropdown -->
          <li class="dropdown hidden-phone">
            <ul class="dropdown-menu tasks">
              <li class="dropdown-menu-title">
                <span>You have 17 tasks in progress</span>
                <a href="#refresh"><i class="icon-repeat"></i></a>
              </li>
              <li>
                <a href="#">
										<span class="header">
											<span class="title">iOS Development</span>
											<span class="percent"></span>
										</span>
                  <div class="taskProgress progressSlim red">80</div>
                </a>
              </li>
              <li>
                <a href="#">
										<span class="header">
											<span class="title">Android Development</span>
											<span class="percent"></span>
										</span>
                  <div class="taskProgress progressSlim green">47</div>
                </a>
              </li>
              <li>
                <a href="#">
										<span class="header">
											<span class="title">ARM Development</span>
											<span class="percent"></span>
										</span>
                  <div class="taskProgress progressSlim yellow">32</div>
                </a>
              </li>
              <li>
                <a href="#">
										<span class="header">
											<span class="title">ARM Development</span>
											<span class="percent"></span>
										</span>
                  <div class="taskProgress progressSlim greenLight">63</div>
                </a>
              </li>
              <li>
                <a href="#">
										<span class="header">
											<span class="title">ARM Development</span>
											<span class="percent"></span>
										</span>
                  <div class="taskProgress progressSlim orange">80</div>
                </a>
              </li>
              <li>
                <a class="dropdown-menu-sub-footer">View all tasks</a>
              </li>
            </ul>
          </li>
          <!-- end: Notifications Dropdown -->
          <!-- start: Message Dropdown -->
          <li class="dropdown hidden-phone">
            <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
              <i class="icon-envelope"></i>
								<span class="badge red">
								4 </span>
            </a>
            <ul class="dropdown-menu messages">
              <li class="dropdown-menu-title">
                <span>You have 9 messages</span>
                <a href="#refresh"><i class="icon-repeat"></i></a>
              </li>
              <li>
                <a href="#">
                  <span class="avatar"><img src="img/avatar.jpg" alt="Avatar"></span>
										<span class="header">
											<span class="from">
										    	Dennis Ji
										     </span>
											<span class="time">
										    	6 min
										    </span>
										</span>
                                        <span class="message">
                                            Lorem ipsum dolor sit amet consectetur adipiscing elit, et al commore
                                        </span>
                </a>
              </li>
              <li>
                <a href="#">
                  <span class="avatar"><img src="img/avatar.jpg" alt="Avatar"></span>
										<span class="header">
											<span class="from">
										    	Dennis Ji
										     </span>
											<span class="time">
										    	56 min
										    </span>
										</span>
                                        <span class="message">
                                            Lorem ipsum dolor sit amet consectetur adipiscing elit, et al commore
                                        </span>
                </a>
              </li>
              <li>
                <a href="#">
                  <span class="avatar"><img src="img/avatar.jpg" alt="Avatar"></span>
										<span class="header">
											<span class="from">
										    	Dennis Ji
										     </span>
											<span class="time">
										    	3 hours
										    </span>
										</span>
                                        <span class="message">
                                            Lorem ipsum dolor sit amet consectetur adipiscing elit, et al commore
                                        </span>
                </a>
              </li>
              <li>
                <a href="#">
                  <span class="avatar"><img src="img/avatar.jpg" alt="Avatar"></span>
										<span class="header">
											<span class="from">
										    	Dennis Ji
										     </span>
											<span class="time">
										    	yesterday
										    </span>
										</span>
                                        <span class="message">
                                            Lorem ipsum dolor sit amet consectetur adipiscing elit, et al commore
                                        </span>
                </a>
              </li>
              <li>
                <a href="#">
                  <span class="avatar"><img src="img/avatar.jpg" alt="Avatar"></span>
										<span class="header">
											<span class="from">
										    	Dennis Ji
										     </span>
											<span class="time">
										    	Jul 25, 2012
										    </span>
										</span>
                                        <span class="message">
                                            Lorem ipsum dolor sit amet consectetur adipiscing elit, et al commore
                                        </span>
                </a>
              </li>
              <li>
                <a class="dropdown-menu-sub-footer">View all messages</a>
              </li>
            </ul>
          </li>

          <!-- start: User Dropdown -->
          <li class="dropdown">
            <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
              <i class="halflings-icon white user"></i> Dennis Ji
              <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
              <li class="dropdown-menu-title">
                <span>Account Settings</span>
              </li>
              <li><a href="#"><i class="halflings-icon user"></i> Profile</a></li>
              <li><a href="login.html"><i class="halflings-icon off"></i> Logout</a></li>
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
          <a href="/forum?forumId=<%=forum.id%>"><%=forum.getName()%></a>
          <i class="icon-angle-right"></i>
          <a href="/subForum?subForumId=<%=sub.id%>"><%=sub.getName()%></a>
          <i class="icon-angle-right"></i>

        </li>
        <li><a href="#"><%=openingMsg.getTitle()%></a></li>
      </ul>

      <%--//strat here--%>

      <div>
        <p class="pull-right"><%=t.getDate().toString()%></p>
        <h4 class="media-heading text-uppercase reviews"><%=t.getMemberStarted().getUsername()%> </h4>


        <div class="container">

          <div class="row">
            <div class="col-sm-10 col-sm-offset-1" id="logout">
              <div class="page-header">

                <div class="media-body">
                  <h3>Title:</h3>
                  <div class="well well-lg">
                    <%=openingMsg.getTitle()%>
                  </div>

                  <div class="well well-lg">


                    <p class="media-comment">
                      <%=openingMsg.getBody()%>
                    </p>


                  </div>
                  <a class="btn btn-info btn-circle text-uppercase" href="/replyEditRequest?op=reply&threadId=<%=t.id%>&msgId=<%=openingMsg.id%>&title=<%=openingMsg.getTitle()%>&body=<%=openingMsg.getBody()%>" id="reply"><span class="glyphicon glyphicon-share-alt"></span> Reply</a>
                  <%if(user.isAdmin() || t.getMemberStarted().equals(user)){%>
                    <a class="btn btn-info btn-circle text-uppercase" href="/replyEditRequest?op=edit&threadId=<%=t.id%>&msgId=<%=openingMsg.id%>&title=<%=openingMsg.getTitle()%>&body=<%=openingMsg.getBody()%>" id="edit"><span class="glyphicon glyphicon-share-alt"></span>Edit</a>
                    <a class="btn btn-danger btn-danger text-uppercase" href="/deleteMessage?msgId=<%=openingMsg.id%>&deleteThread=1" id="delete"><span class="glyphicon glyphicon-share-alt"></span>Delete</a>
                  <%}%>
                </div>
                <br><br><br>

                <div class = "page-header"></div>
                <h1 class="reviews">Comments:</h1>
              </div>


              <div class="comment-tabs">

                <div class="tab-content">
                  <div class="tab-pane active" id="comments-logout">
                    <ul class="media-list">
                      <%--depth 0 comment--%>
                      <%=printMessageAndComments(user, t,openingMsg,0)%>
                    </ul>
                  </div>

                </div>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="col-sm-10 col-sm-offset-1" id="login">
              <div class="page-header">
                <h3 class="reviews">Main message content</h3>




                <div class="logout">
                  <button class="btn btn-default btn-circle text-uppercase" type="button" onclick="$('#login').hide(); $('#logout').show()">
                    <span class="glyphicon glyphicon-off"></span> Login
                  </button>
                </div>
              </div>
              <div class="comment-tabs">

                <div class="tab-content">
                  <div class="tab-pane active" id="comments-login">
                    <ul class="media-list">
                      <% for(Message comment : openingMsg.getComments()){%>
                          <%= printMessageAndComments(user,t,comment,0)%>
                      <%}%>
                    </ul>
                  </div>

                  <%--<div class="tab-pane" id="add-comment-disabled">--%>
                    <%--<div class="alert alert-info alert-dismissible" role="alert">--%>
                      <%--<button type="button" class="close" data-dismiss="alert">--%>
                        <%--<span aria-hidden="true">×</span><span class="sr-only">Close</span>--%>
                      <%--</button>--%>
                      <%--<strong>Hey!</strong> If you already have an account <a href="#" class="alert-link">Login</a> now to make the comments you want. If you do not have an account yet you're welcome to <a href="#" class="alert-link"> create an account.</a>--%>
                    <%--</div>--%>
                    <%--<form action="#" method="post" class="form-horizontal" id="commentForm" role="form">--%>
                      <%--<div class="form-group">--%>
                        <%--<label for="email" class="col-sm-2 control-label">Comment</label>--%>
                        <%--<div class="col-sm-10">--%>
                          <%--<textarea class="form-control" name="addComment" id="addComment" rows="5" disabled></textarea>--%>
                        <%--</div>--%>
                      <%--</div>--%>
                      <%--<div class="form-group">--%>
                        <%--<label for="uploadMedia" class="col-sm-2 control-label">Upload media</label>--%>
                        <%--<div class="col-sm-10">--%>
                          <%--<div class="input-group">--%>
                            <%--<div class="input-group-addon">http://</div>--%>
                            <%--<input type="text" class="form-control" name="uploadMedia" id="uploadMedia" disabled>--%>
                          <%--</div>--%>
                        <%--</div>--%>
                      <%--</div>--%>
                      <%--<div class="form-group">--%>
                        <%--<div class="col-sm-offset-2 col-sm-10">--%>
                          <%--<button class="btn btn-success btn-circle text-uppercase disabled" type="submit" id="submitComment"><span class="glyphicon glyphicon-send"></span> Summit comment</button>--%>
                        <%--</div>--%>
                      <%--</div>--%>
                    <%--</form>--%>
                  <%--</div>--%>
                  <%--<div class="tab-pane" id="new-account">--%>
                    <%--<form action="#" method="post" class="form-horizontal" id="commentForm" role="form">--%>
                      <%--<div class="form-group">--%>
                        <%--<label for="name" class="col-sm-2 control-label">Name</label>--%>
                        <%--<div class="col-sm-10">--%>
                          <%--<input type="text" class="form-control" name="name" id="name">--%>
                        <%--</div>--%>
                      <%--</div>--%>
                      <%--<div class="form-group">--%>
                        <%--<label for="email" class="col-sm-2 control-label">Email</label>--%>
                        <%--<div class="col-sm-10">--%>
                          <%--<input type="email" class="form-control" name="email" id="email" required>--%>
                        <%--</div>--%>
                      <%--</div>--%>
                      <%--<div class="form-group">--%>
                        <%--<label for="password" class="col-sm-2 control-label">Password</label>--%>
                        <%--<div class="col-sm-10">--%>
                          <%--<input type="password" class="form-control" name="password" id="password">--%>
                        <%--</div>--%>
                      <%--</div>--%>
                      <%--<div class="form-group">--%>
                        <%--<div class="checkbox">--%>
                          <%--<label for="agreeTerms" class="col-sm-offset-2 col-sm-10">--%>
                            <%--<input type="checkbox" name="agreeTerms" id="agreeTerms"> I agree all <a href="#">Terms & Conditions</a>--%>
                          <%--</label>--%>
                        <%--</div>--%>
                      <%--</div>--%>
                      <%--<div class="form-group">--%>
                        <%--<div class="col-sm-offset-2 col-sm-10">--%>
                          <%--<button class="btn btn-primary btn-circle text-uppercase" type="submit" id="submit">Create an account</button>--%>
                        <%--</div>--%>
                      <%--</div>--%>
                    <%--</form>--%>
                  <%--</div>--%>
                </div>
              </div>
            </div>
          </div>

        </div>



      </div>



      //done here

    </div><!--/row-->






    <!-- end: Content -->






  </div><!--/#content.span10-->
</div><!--/fluid-row-->

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


<%!
  public String printMessageAndComments(User user, Thread t, Message msg, int depth) {
    StringBuilder sb= new StringBuilder();
    if (msg == null) {
      return "";
    }

    sb.append("<li class=\"media");
    if(depth > 0) {
      sb.append(" media-replied");
    }
    sb.append("\">\n");
    sb.append("<div class=\"media-body\">\n");
    sb.append("<h4 class=\"media-heading text-uppercase reviews\">"+msg.getUser().getUsername()+"</h4>\n");
    sb.append("<ul class=\"media-date text-uppercase reviews list-inline\">\n");
    sb.append("<p>"+msg.getDate().toString()+"</p>\n");
    sb.append("</ul>\n");
    sb.append("<p class=\"media-comment\">\n");
    sb.append("<h3>"+msg.getTitle()+"</h3>\n");
    sb.append("</p>\n");
    sb.append("<p class=\"media-comment\">\n");
    sb.append(msg.getBody()+"\n");
    sb.append("</p>\n");

    sb.append("<a class=\"btn btn-info btn-circle text-uppercase\" href=\"/replyEditRequest?op=reply&threadId="+t.id+"&msgId="+msg.id+"&title="+msg.getTitle()+"&body="+msg.getBody()+"\" id=\"reply\"><span class=\"glyphicon glyphicon-share-alt\"></span> Reply</a>\n");
    if(user.isAdmin() || t.getMemberStarted().equals(user)){
      sb.append("<a class=\"btn btn-info btn-circle text-uppercase\" href=\"/replyEditRequest?op=reply&threadId="+t.id+"&msgId="+msg.id+"&title="+msg.getTitle()+"&body="+msg.getBody()+"\" id=\"edit\"><span class=\"glyphicon glyphicon-share-alt\"></span> Edit</a>\n");
      sb.append("<a class=\"btn btn-info btn-circle text-uppercase\" href=\"/deleteMessage?&msgId="+msg.id+"&deleteThread=1\" id=\"delete\"><span class=\"glyphicon glyphicon-share-alt\"></span> Delete</a>\n");
      if(!msg.getComments().isEmpty()){
        sb.append("<a class=\"btn btn-warning btn-circle text-uppercase\" data-toggle=\"collapse\" href=\"#replyOne\"><span class=\"glyphicon glyphicon-comment\"></span>"+msg.getComments().size()+"comments</a>\n");
      }
    }

    sb.append("</div>\n");
    sb.append("</div>\n");
    sb.append("\t\t\t\t\t  <div class=\"collapse\" id=\"replyOne\">\n");
    sb.append("                          <ul class=\"media-list\">\n");
    for(Message c : msg.getComments()) {
      sb.append(printMessageAndComments(user, t, msg, depth + 1));
    }

    sb.append("                          </ul>\n");
    sb.append("                        </div>\n");

    // end of comment
    sb.append("</li>\n");

    return sb.toString();
  }
%>

