package servlets;

import content.Forum;
import controllers.SuperAdminController;
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
 * Servlet implementation class UserProfileServlet
 */
@WebServlet(
		description = "Handles the request of appoint a moderator to a sub forum",
		urlPatterns = {
				"/appointAdminRequest"}
)
public class AppointAdminRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AppointAdminRequestServlet() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			SessionLogger.get().log(request.getSession().getId(), "appoint admin request");
			int forumId = Integer.parseInt(request.getParameter("forumId"));

			String superAdminId = CookieUtils.getCookieValue(request, CookieUtils.SUPER_USER_ID_COOKIE_NAME);
			if (superAdminId == null)
				throw new Exception("Super Admin Cookie Doesn't Exist");

			User superAdmin = (User) HibernateUtils.load(User.class, Integer.parseInt(superAdminId));
			Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);

			request.setAttribute("forum", forum);
			request.setAttribute("superAdmin", superAdmin);
			request.setAttribute("role", "admin");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/appointUser.jsp");
			dispatcher.forward(request,response);
		}
		catch(Exception e) {
			ServletUtils.exitError(this, request, response, e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
