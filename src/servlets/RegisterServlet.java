package servlets;

import content.Forum;
import controllers.UserController;
import exceptions.UsernameAlreadyExistsException;
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
@WebServlet(description = "A servlet for registering", urlPatterns = { "/register" })
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id;
		String userName, pass, email;
		try {
            SessionLogger.get().log(request.getSession().getId(),"registering");
			id=Integer.parseInt(request.getParameter("forumId"));
			userName = request.getParameter("username");
			pass = request.getParameter("pass");
			email = request.getParameter("email");

			if(email == null)
				email = "email;";

			Forum forum = (Forum) HibernateUtils.load(Forum.class, id);

			UserController.register(forum, userName, pass, email);
			User user = UserController.login(forum, userName, pass);

			if(user == null)
				throw new Exception("User is null");

			CookieUtils.changeCookieValue(request, response, CookieUtils.USER_ID_COOKIE_NAME, Integer.toString(user.getId()));

			request.setAttribute("forumId", id);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/forum");
			dispatcher.forward(request, response);


		} catch (Exception e) {
			ServletUtils.exitError(this, request,response,e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
