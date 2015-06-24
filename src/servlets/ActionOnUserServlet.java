package servlets;

import content.Forum;
import content.SubForum;
import controllers.SuperAdminController;
import users.User;
import utils.CookieUtils;
import utils.HibernateUtils;
import utils.SessionLogger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class UserProfileServlet
 */
@WebServlet(
		description = "Handles the request of appoint a moderator to a sub forum",
		urlPatterns = {
				"/actionOnUser"}
)
public class ActionOnUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ActionOnUserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			SessionLogger.get().log(request.getSession().getId(), "action on user set up");
			String action = request.getParameter("action");
			int forumId = Integer.parseInt(request.getParameter("forumId"));
			String returnPage="";
/*
			* Change Admin - choose from a list of members = forum
			* (User superAdmin, Forum forum, User admin)
			*
			* Appoint Moderator - choose from a list of members = Forum, subForum
			* appointModerator(Forum forum, SubForum subForum, User admin, User moderator)
			*
			* Dismiss Moderator - choose from a list of members = Forum, subForum
			*unAppoint(Forum forum, SubForum subForum, User admin, User moderator)
			*
			* */

			switch(action){
				case "changeAdmin":
					returnPage = "/systemManagement";

					break;

				case "appointModerator":
				case "dismissModerator":
					int subForumId = Integer.parseInt(request.getParameter("subForumId"));
					SubForum subForum = (SubForum) HibernateUtils.load(SubForum.class, subForumId);
					request.setAttribute("subForum", subForum);
					returnPage = "/forumManagement";
					break;
			}

			Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);

			request.setAttribute("forum", forum);
			request.setAttribute("action", action);
			request.setAttribute("returnPage", returnPage);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/actionOnUser.jsp");
			dispatcher.forward(request,response);
		}
		catch(Exception e) {
			ServletUtils.exitError(this, request, response, e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
