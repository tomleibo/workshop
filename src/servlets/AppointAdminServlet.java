package servlets;

import content.Forum;
import content.SubForum;
import controllers.AdminController;
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
				"/changeAdmin"}
		)
public class AppointAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppointAdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			SessionLogger.get().log(request.getSession().getId(),"appoint moderator");
			int adminId = Integer.parseInt(request.getParameter("userId"));
			int forumId = Integer.parseInt(request.getParameter("forumId"));

			String superAdminId = CookieUtils.getCookieValue(request, CookieUtils.SUPER_USER_ID_COOKIE_NAME);
			if (superAdminId == null) {
				throw new Exception("SuperAdmin Cookie Doesn't Exist!");
			}

			User superAdmin = (User) HibernateUtils.load(User.class, Integer.parseInt(superAdminId));
			Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);
			User admin = (User) HibernateUtils.load(User.class, adminId);

			SuperAdminController.changeAdministrator(superAdmin, forum, admin);

			// TODO: fix success page
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/systemManagement");
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
