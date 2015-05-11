package servlets;

import content.Forum;
import controllers.UserController;
import exceptions.UserAlreadyLoggedInException;
import users.User;
import utils.CookieUtils;
import utils.HibernateUtils;

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
		int id;
		String userName, pass;
		try {
			id=Integer.parseInt(request.getParameter("forumId"));
			userName = request.getParameter("username");
			pass = request.getParameter("pass");
		}
		catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			return;
		}

		Forum forum = (Forum) HibernateUtils.load(Forum.class, id);

		try {
			User user = UserController.login(forum, userName, pass);
			if (user == null)
				throw new Exception("User is null");

			CookieUtils.changeCookieValue(request, CookieUtils.USER_ID_COOKIE_NAME, Integer.toString(user.getId()));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} catch (UserAlreadyLoggedInException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
