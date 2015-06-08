package servlets;

import content.Forum;
import controllers.UserController;
import exceptions.UserAlreadyLoggedInException;
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
            SessionLogger.get().log(request.getSession().getId(),"logined");
			int id=Integer.parseInt(request.getParameter("forumId"));
			String userName = request.getParameter("user");
			String pass = request.getParameter("pass");

			Forum forum = (Forum) HibernateUtils.load(Forum.class, id);

			User user = UserController.login(forum, userName, pass);
			if (user == null)
				throw new Exception("User is null");

			String userId = CookieUtils.getCookieValue(request, CookieUtils.USER_ID_COOKIE_NAME);
			if (userId != null) {
				CookieUtils.changeCookieValue(request, response, CookieUtils.USER_ID_COOKIE_NAME, Integer.toString(user.getId()));

			} else {
				CookieUtils.addInfiniteCookie(response, CookieUtils.USER_ID_COOKIE_NAME, Integer.toString(user.getId()));
			}

			request.setAttribute("forumId", id);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/forum");
			dispatcher.forward(request, response);
		}
		catch (Exception e) {
			ServletUtils.exitError(this, request,response, e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
