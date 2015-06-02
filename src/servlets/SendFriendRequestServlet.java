package servlets;

import content.Forum;
import controllers.UserController;
import exceptions.UserNotAuthorizedException;
import policy.ForumPolicy;
import users.User;
import utils.CookieUtils;
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
		description =  "Handles the sending a friend request",
		urlPatterns = {
				"/sendFriendRequest"}
		)
public class SendFriendRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendFriendRequestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/friendRequestSent.jsp");

		int forumId, senderId=-1, receiverId;
		String receiverName, content;

		try {
			String value = CookieUtils.getCookieValue(request, CookieUtils.USER_ID_COOKIE_NAME);
			if(value!= null)
				senderId = Integer.parseInt(value);

			forumId = Integer.parseInt(request.getParameter("forumId"));
			receiverName = request.getParameter("receiver");
			receiverId = Integer.parseInt(request.getParameter("receiverId"));
			content = request.getParameter("content");
		}
		catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			return;
		}

		Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);
		User sender  = (User) HibernateUtils.load(Forum.class, senderId);
		User receiver = (User) HibernateUtils.load(Forum.class, receiverId);

		try {
			UserController.sendFriendRequest(forum, sender, receiver, content);
		} catch (UserNotAuthorizedException e) {

			System.out.println(e.getMessage());
			return;
		}

		request.setAttribute("receiver", receiverName);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
