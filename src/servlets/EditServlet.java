package servlets;

import content.Forum;
import content.Message;
import content.SubForum;
import content.Thread;
import controllers.ContentController;
import controllers.UserController;
import exceptions.EmptyMessageTitleAndBodyException;
import exceptions.UserNotAuthorizedException;
import policy.ForumPolicy;
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
 * Servlet implementation class ThreadServlet
 */
@WebServlet(description = "A servlet for editing posts", urlPatterns = { "/edit" })
public class EditServlet extends HttpServlet {

	Forum stub=null;

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionLogger.get().log(request.getSession().getId(),"editing message");

		int userId=-1;
		int forumId;
		int subForumId;
		int messageId;
		String title;
		String body;



		try {
			String value = CookieUtils.getCookieValue(request, CookieUtils.USER_ID_COOKIE_NAME);
			if(value!= null)
				userId = Integer.parseInt(value);
			forumId = Integer.parseInt(request.getParameter("forumId"));
			subForumId = Integer.parseInt(request.getParameter("subForumId"));
			messageId = Integer.parseInt(request.getParameter("origMessageId"));
			title = request.getParameter("title");
			body = request.getParameter("body");
		}
		catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			return;
		}

		User user = (User) HibernateUtils.load(User.class, userId);
		Forum forum = (Forum) HibernateUtils.load(User.class, forumId);
		SubForum subForum = (SubForum) HibernateUtils.load(User.class, subForumId);
		Message message = (Message) HibernateUtils.load(User.class, messageId);

		try {
			UserController.editMessage(forum, subForum, user, message, body);
		} catch (UserNotAuthorizedException e) {
			System.out.println(e.getMessage());
		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
