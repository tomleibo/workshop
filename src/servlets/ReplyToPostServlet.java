package servlets;

import content.Forum;
import content.Message;
import content.SubForum;
import controllers.UserController;
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
				"/replyToPost"}
		)
public class ReplyToPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReplyToPostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int addToMsgId = Integer.parseInt(request.getParameter("msgId"));
			String title = request.getParameter("title");
			String content = request.getParameter("body");

			String cookieValue = CookieUtils.getCookieValue(request, CookieUtils.USER_ID_COOKIE_NAME);
			if (cookieValue == null)
				throw new Exception("User Cookie Value doesn't exist");

			int userId = Integer.parseInt(cookieValue);

			cookieValue = CookieUtils.getCookieValue(request, CookieUtils.FORUM_ID_COOKIE_NAME);
			if (cookieValue == null)
				throw new Exception("Forum Cookie Value doesn't exist");

			int forumId = Integer.parseInt(cookieValue);

			cookieValue = CookieUtils.getCookieValue(request, CookieUtils.SUB_FORUM_ID_COOKIE_NAME);
			if (cookieValue == null)
				throw new Exception("Forum Cookie Value doesn't exist");

			int subForumId = Integer.parseInt(cookieValue);


			Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);
			SubForum subForum = (SubForum) HibernateUtils.load(SubForum.class, subForumId);
			User user = (User) HibernateUtils.load(User.class, userId);
			Message addTo = (Message)HibernateUtils.load(Message.class, addToMsgId);

			UserController.reply(forum, addTo, title, content, user);

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/subforum.jsp");
			request.setAttribute("subForum", subForum);
			request.setAttribute("user", user);
			dispatcher.forward(request, response);
		}
		catch(Exception e){
			ServletUtils.exitError(this, request,response, e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
