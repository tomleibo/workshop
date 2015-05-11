package servlets;

import content.Forum;
import controllers.UserController;
import exceptions.UserNotAuthorizedException;
import users.FriendRequest;
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
		description = "Handles the request of replying to a friend request",
		urlPatterns = {
				"/replyToFriendRequest"}
		)
public class ReplyToFriendRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReplyToFriendRequestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/friendRequestReplied.jsp");

		int forumId, userId=-1, friendId, friendReqId;
		String friendName;
		String answerStr;
		boolean answer = false;

		try{

			forumId = Integer.parseInt(request.getParameter("forumId"));
			String value = CookieUtils.getCookieValue(request, CookieUtils.USER_ID_COOKIE_NAME);
			if(value!= null)
				userId = Integer.parseInt(value);
			friendName = request.getParameter("friend");
			friendId = Integer.parseInt(request.getParameter("friendId"));
			friendReqId = Integer.parseInt(request.getParameter("friendReqId"));
			answerStr = request.getParameter("answer");
			if(request.getParameter("answer").equals("1")) {
				answer = true;
			}
		}
		catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			return;
		}

		Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);
		User user = (User) HibernateUtils.load(User.class, userId);
		User friend = (User) HibernateUtils.load(User.class, friendId);
		FriendRequest fr = (FriendRequest) HibernateUtils.load(User.class, friendReqId);

		try {
			UserController.replyToFriendRequest(forum, user, fr, answer);
		} catch (UserNotAuthorizedException e) {
			System.out.println(e.getMessage());
			return;
		}

		request.setAttribute("friend", friendName);
		request.setAttribute("answer", answerStr);

		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
