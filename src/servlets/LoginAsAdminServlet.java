package servlets;

import content.Forum;
import controllers.SuperAdminController;
import controllers.UserController;
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
import java.util.List;

/**
 * Servlet implementation class ThreadServlet
 */
@WebServlet(description = "A servlet for registering", urlPatterns = { "/loginAsAdmin" })
public class LoginAsAdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginAsAdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
            SessionLogger.get().log(request.getSession().getId(),"logined");
			String userName = request.getParameter("user");
			String pass = request.getParameter("pass");

			User user = SuperAdminController.loginSuperAdmin(userName, pass);
			String userId = CookieUtils.getCookieValue(request, CookieUtils.SUPER_USER_ID_COOKIE_NAME);
			if(userId != null)
				CookieUtils.changeCookieValue(request, response, CookieUtils.SUPER_USER_ID_COOKIE_NAME, Integer.toString(user.getId()));
			else
				CookieUtils.addInfiniteCookie(response, CookieUtils.SUPER_USER_ID_COOKIE_NAME, Integer.toString(user.getId()));


			List<Forum> forums = HibernateUtils.getAllForums();
			request.setAttribute("forums", forums);
			request.setAttribute("superAdmin", user);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/main.jsp");
			dispatcher.forward(request, response);
		}
		catch (Exception e) {
			ServletUtils.exitError(this, request,response, e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
