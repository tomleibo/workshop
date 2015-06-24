package servlets;

import content.Forum;
import content.SubForum;
import controllers.AdminController;
import controllers.UserController;
import exceptions.UserNotAuthorizedException;
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
				"/appointModerator"}
		)
public class AppointModeratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppointModeratorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
            SessionLogger.get().log(request.getSession().getId(),"appoint moderator");
			int moderatorId = Integer.parseInt(request.getParameter("userId"));
			int forumId = Integer.parseInt(request.getParameter("forumId"));
			int subForumId = Integer.parseInt(request.getParameter("subForumId"));

			String cookieValue = CookieUtils.getCookieValue(request, CookieUtils.getUserCookieName(forumId));
			if (cookieValue == null) {
				throw new Exception("User Cookie Value doesn't exist");
			}

			int userId = Integer.parseInt(cookieValue);

			Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);
			User user = (User) HibernateUtils.load(User.class, userId);
			User moderator = (User) HibernateUtils.load(User.class, moderatorId);
			SubForum subForum = (SubForum) HibernateUtils.load(SubForum.class, subForumId);

			AdminController.appointModerator(forum, subForum, user, moderator);

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/forumManagement");
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
