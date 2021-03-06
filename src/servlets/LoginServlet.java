package servlets;

import content.Forum;
import controllers.UserController;
import exceptions.NeedToChangePasswordException;
import exceptions.UserAlreadyLoggedInException;
import exceptions.UserDoesNotExistsException;
import exceptions.WrongPasswordException;
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
import java.security.NoSuchAlgorithmException;

/**
 * Servlet implementation class ThreadServlet
 */
@WebServlet(description = "A servlet for registering", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
            SessionLogger.get().log(request.getSession().getId(), "logined");

			String cookieValue = CookieUtils.getCookieValue(request, CookieUtils.FORUM_ID_COOKIE_NAME);
			if (cookieValue == null)
				throw new Exception("Forum Cookie Value doesn't exist");

			int forumId = Integer.parseInt(cookieValue);

			String userName = request.getParameter("user");
			String pass = request.getParameter("pass");

			Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);

			User user = null;

			try {
				user = UserController.login(forum, userName, pass);
			}
			catch (NeedToChangePasswordException e) {
				request.setAttribute("user", e.getUser());
				request.setAttribute("forum", forum);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/changePassword.jsp");
				dispatcher.forward(request, response);
				return;
			}

		if (user == null)
				throw new Exception("Error occurred while loging ing");

			String userCookieName = CookieUtils.getUserCookieName(forumId);
			String userId = CookieUtils.getCookieValue(request, userCookieName);

			if (userId != null) {
				CookieUtils.changeCookieValue(request, response, userCookieName, Integer.toString(user.getId()));

			} else {
				CookieUtils.addInfiniteCookie(response, userCookieName, Integer.toString(user.getId()));
			}

			request.setAttribute("user", user);
			request.setAttribute("forum", forum);
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
