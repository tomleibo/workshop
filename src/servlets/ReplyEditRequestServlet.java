package servlets;

import content.Forum;
import content.Message;
import content.SubForum;
import content.Thread;
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
				"/replyEditRequest"}
		)
public class ReplyEditRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReplyEditRequestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            SessionLogger.get().log(request.getSession().getId(),"edit or replying on a message");
			String op = request.getParameter("op");
			String title = request.getParameter("title");
			String body = request.getParameter("body");
			int msgId = Integer.parseInt(request.getParameter("msgId"));
			int threadId = Integer.parseInt(request.getParameter("threadId"));

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

			if(op.equals("reply")){
				title = "";
				body = "";
			}

			Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);
			SubForum subForum = (SubForum) HibernateUtils.load(SubForum.class, subForumId);
			User user = (User) HibernateUtils.load(User.class, userId);
			Thread thread = (Thread)HibernateUtils.load(Thread.class, threadId);
			Message message = (Message)HibernateUtils.load(Message.class, msgId);

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/replyEdit.jsp");
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
