package servlets;

import content.Forum;
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
		description = "Handles the request of remove a friend from friends list",
		urlPatterns = {
				"/removeFriend"}
		)
public class RemoveFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveFriendServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		// TODO change to SUCCESS ERROR
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/profile");

		int forumId, userId=-1, friendId;
		String friendName;

		try {
            SessionLogger.get().log(request.getSession().getId(),"removing friend");
			forumId = Integer.parseInt(request.getParameter("forumId"));
			String value = CookieUtils.getCookieValue(request, CookieUtils.getUserCookieName(forumId));
			if(value!= null)
				userId = Integer.parseInt(value);
			friendName = request.getParameter("friend");
			friendId = Integer.parseInt(request.getParameter("friendId"));
		}
		catch (NumberFormatException e) {
            ServletUtils.exitError(this, request, response, e);
			return;
		}

		Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);
		User user = (User) HibernateUtils.load(User.class, userId);
		User friend = (User) HibernateUtils.load(User.class, friendId);

		try {
			UserController.removeFriend(forum, user, friend);
		} catch (UserNotAuthorizedException e) {
            ServletUtils.exitError(this, request, response, e);
			return;
		}

		request.setAttribute("friend", friendName);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
