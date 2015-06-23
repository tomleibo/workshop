package servlets;

import content.*;
import content.Thread;
import controllers.ContentController;
import controllers.UserController;
import exceptions.EmptyMessageTitleAndBodyException;
import org.hibernate.dialect.CUBRIDDialect;
import policy.ForumPolicy;
import users.User;
import utils.CookieUtils;
import utils.HibernateUtils;
import utils.SessionLogger;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ForumServlet
 */
@WebServlet(description = "Presents all sub forums", urlPatterns = { "/forum" })
public class ForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForumServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int i =1;
		SessionLogger.get().log(request.getSession().getId(),"showing forum page");
		try {
			String forumIdString = request.getParameter("forumId");
			int forumId = Integer.parseInt(forumIdString);
			Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);

			String forumCookieId = CookieUtils.getCookieValue(request, CookieUtils.FORUM_ID_COOKIE_NAME);
			if (forumCookieId != null) {
				CookieUtils.changeCookieValue(request, response, CookieUtils.FORUM_ID_COOKIE_NAME, forumIdString);
			} else {
				CookieUtils.addInfiniteCookie(response, CookieUtils.FORUM_ID_COOKIE_NAME, forumIdString);
			}


			User user;
			String userCookieName = CookieUtils.getUserCookieName(forumCookieId);
			String superAdminId = CookieUtils.getCookieValue(request, CookieUtils.SUPER_USER_ID_COOKIE_NAME);
			if (superAdminId != null) {
				user = (User) HibernateUtils.load(User.class, Integer.parseInt(superAdminId));
				if(CookieUtils.getCookieValue(request, userCookieName) != null){
					CookieUtils.changeCookieValue(request, response, userCookieName, String.valueOf(user.getId()));
				}
				else{
					CookieUtils.addInfiniteCookie(response, CookieUtils.getUserCookieName(forumCookieId), Integer.toString(user.getId()));
				}
			}

			else{
				String userId = CookieUtils.getCookieValue(request, userCookieName);
				if (userId != null) {
					user = (User) HibernateUtils.load(User.class, Integer.parseInt(userId));
					if(user == null){
						user = UserController.enterAsGuest(forum);
						CookieUtils.changeCookieValue(request, response, userCookieName, String.valueOf(user.getId()));
					}

				} else {
					user = UserController.enterAsGuest(forum);
					CookieUtils.addInfiniteCookie(response, CookieUtils.getUserCookieName(forumCookieId), Integer.toString(user.getId()));
				}
			}

			request.setAttribute("forum", forum);
			request.setAttribute("user", user);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/forum.jsp");
			dispatcher.forward(request, response);
		}
		catch (Exception e) {
			ServletUtils.exitError(this, request, response, e);
		}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
