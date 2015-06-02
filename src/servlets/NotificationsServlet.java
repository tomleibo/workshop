package servlets;

import content.Forum;
import controllers.UserController;
import exceptions.UserNotAuthorizedException;
import users.User;
import utils.CookieUtils;
import utils.HibernateUtils;

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
		description = "Handles the request of remove a friend from friends list",
		urlPatterns = {
				"/notificationsPage"}
		)
public class NotificationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotificationsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/notifications.jsp");

		int userId=-1;

		try {
			String value = CookieUtils.getCookieValue(request, CookieUtils.USER_ID_COOKIE_NAME);
			if(value!= null)
				userId = Integer.parseInt(value);
		}
		catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			return;
		}

		User user = (User) HibernateUtils.load(User.class, userId);

		request.setAttribute("user", user);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
