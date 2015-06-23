package servlets;

import content.*;
import controllers.UserController;
import exceptions.ContentNotRelevantException;
import exceptions.WordNotApprovedException;
import users.User;
import utils.CookieUtils;
import utils.HibernateUtils;
import utils.ContentDigestUtils;
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
		description =  "Handles the sending a friend request",
		urlPatterns = {
				"/postNewThread"}
		)
public class PostNewThreadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostNewThreadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            SessionLogger.get().log(request.getSession().getId(),"posting a new thread");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
            if (content != null && !content.equals("")) {
                String word = ContentDigestUtils.isTextOk(content);
                if (word != null) {
                    ServletUtils.exitError(this,request,response,new WordNotApprovedException(word));
                    return;
                }
            }
			String cookieValue = CookieUtils.getCookieValue(request, CookieUtils.FORUM_ID_COOKIE_NAME);
			if (cookieValue == null)
				throw new Exception("Forum Cookie Value doesn't exist");

			int forumId = Integer.parseInt(cookieValue);

			cookieValue = CookieUtils.getCookieValue(request, CookieUtils.getUserCookieName(forumId));
			if (cookieValue == null) {
				throw new Exception("User Cookie Value doesn't exist");
			}

			int userId = Integer.parseInt(cookieValue);

			cookieValue = CookieUtils.getCookieValue(request, CookieUtils.SUB_FORUM_ID_COOKIE_NAME);
			if (cookieValue == null)
				throw new Exception("Forum Cookie Value doesn't exist");

			int subForumId = Integer.parseInt(cookieValue);

			Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);
			User user = (User) HibernateUtils.load(User.class, userId);
			SubForum subForum = (SubForum) HibernateUtils.load(SubForum.class, subForumId);
            if (subForum.getNumberOfMessages() > 10) {
                float percentage = ContentDigestUtils.percentageOfRelevance(subForum.viewThreads(),content);
                if (percentage < 0.1) {
                    ServletUtils.exitError(this,request,response,new ContentNotRelevantException());
                    return;
                }
            }
			UserController.openNewThread(forum,subForum, title, content, user);

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/subForum?subForumId="+subForumId);
			dispatcher.forward(request, response);
		}
		catch(Exception e){
			ServletUtils.exitError(this, request,response, e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
