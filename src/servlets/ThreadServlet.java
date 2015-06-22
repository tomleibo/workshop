package servlets;

import content.*;
import content.Thread;
import controllers.ContentController;
import controllers.SuperAdminController;
import exceptions.EmptyMessageTitleAndBodyException;
import policy.ForumPolicy;
import users.User;
import utils.CookieUtils;
import utils.HibernateUtils;
import utils.SessionLogger;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ThreadServlet
 */
@WebServlet(description = "A servlet for returning a thread, posting, editing or deleting a message", urlPatterns = { "/thread" })
public class ThreadServlet extends HttpServlet {

	Forum stub=null;

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThreadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try{
            SessionLogger.get().log(request.getSession().getId(),"viewing thread");
			int threadId = Integer.parseInt(request.getParameter("threadId"));

			String cookieValue = CookieUtils.getCookieValue(request, CookieUtils.USER_ID_COOKIE_NAME);
			if (cookieValue == null) {
				throw new Exception("User Cookie Value doesn't exist");
			}

			int userId = Integer.parseInt(cookieValue);

			cookieValue = CookieUtils.getCookieValue(request, CookieUtils.FORUM_ID_COOKIE_NAME);
			if (cookieValue == null) {
				throw new Exception("Forum Cookie Value doesn't exist");
			}

			int forumId = Integer.parseInt(cookieValue);

			cookieValue = CookieUtils.getCookieValue(request, CookieUtils.SUB_FORUM_ID_COOKIE_NAME);
			if (cookieValue == null) {
				throw new Exception("Sub Forum Cookie Value doesn't exist");
			}

			int subForumId = Integer.parseInt(cookieValue);

			Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);
			User user = (User) HibernateUtils.load(User.class,userId);
			SubForum subForum = (SubForum) HibernateUtils.load(SubForum.class, subForumId);
			Thread t = (Thread) HibernateUtils.load(Thread.class, threadId);

			request.setAttribute("user", user);
			request.setAttribute("forum", forum);
			request.setAttribute("subForum", subForum);
			request.setAttribute("thread", t);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/thread.jsp");
			dispatcher.forward(request,response);
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
