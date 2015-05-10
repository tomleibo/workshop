package servlets;

import content.Forum;
import controllers.UserController;
import exceptions.UserNotAuthorizedException;
import users.User;
import utils.HibernateUtils;

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
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/friendRemoved.jsp");

		int forumId, userId, friendId;
		String friendName;

		try {
			forumId = Integer.parseInt(request.getParameter("forum"));
			userId = Integer.parseInt(request.getParameter("userId"));
			friendName = request.getParameter("friend");
			friendId = Integer.parseInt(request.getParameter("friendId"));
		}
		catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			return;
		}

		Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);
		User user = (User) HibernateUtils.load(User.class, userId);
		User friend = (User) HibernateUtils.load(User.class, friendId);

		try {
			UserController.removeFriend(forum, user, friend);
		} catch (UserNotAuthorizedException e) {
			System.out.println(e.getMessage());
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
