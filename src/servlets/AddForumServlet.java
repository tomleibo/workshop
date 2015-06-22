package servlets;

import content.Forum;
import content.SubForum;
import controllers.AdminController;
import controllers.SuperAdminController;
import policy.ForumPolicy;
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
		description = "Handles the request of presenting the add forum page",
		urlPatterns = {
				"/addForum"}
		)
public class AddForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddForumServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			String name = request.getParameter("name");
			int maxMods = Integer.parseInt(request.getParameter("maxMods"));
			String passRegex = request.getParameter("passRegex");
			ForumPolicy.HashFunction hash = ForumPolicy.HashFunction.valueOf(request.getParameter("hash"));
			boolean authMail = request.getParameter("authentication").equals("1");
			long timeout = Long.parseLong(request.getParameter("timeout"));
			boolean identifyQ = request.getParameter("identifyQ").equals("1");
			long passExpire = Long.parseLong(request.getParameter("passExpire"));

			SessionLogger.get().log(request.getSession().getId(), "Add Forum");

			String superUserIdStr = CookieUtils.getCookieValue(request, CookieUtils.SUPER_USER_ID_COOKIE_NAME);
			if (superUserIdStr == null) {
				throw new Exception("User Cookie Value doesn't exist");
			}

			int superUserId = Integer.parseInt(superUserIdStr);

			User superAdmin = (User) HibernateUtils.load(User.class, superUserId);

			ForumPolicy policy = new ForumPolicy(maxMods, passRegex, hash, authMail);
			SuperAdminController.createNewForum(superAdmin, policy, name);

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/home");
			request.setAttribute("superAdmin", superAdmin);
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
		doPost(request,response);
	}

}
