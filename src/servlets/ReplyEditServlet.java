package servlets;

import content.Forum;
import content.Message;
import content.SubForum;
import controllers.UserController;
import users.FriendRequest;
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
		description = "Handles the request of replying to a friend request",
		urlPatterns = {
				"/replyEdit"}
		)
public class ReplyEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReplyEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            SessionLogger.get().log(request.getSession().getId(),"edit or replying on a message");
			int msgId = Integer.parseInt(request.getParameter("msgId"));
			int threadId = Integer.parseInt(request.getParameter("threadId"));
			String title = request.getParameter("title");
			String body = request.getParameter("body");
			String op = request.getParameter("op");

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

			User user = (User) HibernateUtils.load(User.class, userId);
			Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);
			SubForum subForum = (SubForum) HibernateUtils.load(SubForum.class, subForumId);
			Thread thread = (Thread) HibernateUtils.load(Thread.class, threadId);
			Message message = (Message)HibernateUtils.load(Message.class, msgId);

			switch(op){
				case "reply":
					UserController.reply(forum, message, title, body, user);
					break;
				case "edit":
					UserController.editMessage(forum, subForum, user, message, body);
					break;
				default:
					throw new Exception("Unknown operation");
			}

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/thread?threadId="+threadId);
			request.setAttribute("user", user);
			request.setAttribute("forum", forum);
			request.setAttribute("subForum", subForum);
			request.setAttribute("thread", thread);
			request.setAttribute("message", message);
			request.setAttribute("op", op);
			request.setAttribute("title", title);
			request.setAttribute("body", body);
			dispatcher.forward(request, response);
		}
		catch(Exception e){
            ServletUtils.exitError(this, request,response,e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
