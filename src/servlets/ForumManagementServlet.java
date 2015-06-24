package servlets;

import content.Forum;
import controllers.AdminController;
import controllers.UserController;
import users.Report;
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
 * Servlet implementation class ForumServlet
 */
@WebServlet(description = "Presents all sub forums", urlPatterns = { "/forumManagement" })
public class ForumManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForumManagementServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionLogger.get().log(request.getSession().getId(),"showing forum management page");
		try {

			String cookieValue = CookieUtils.getCookieValue(request, CookieUtils.FORUM_ID_COOKIE_NAME);
			if (cookieValue == null)
				throw new Exception("Forum Cookie Value doesn't exist");

			int forumId = Integer.parseInt(cookieValue);

			cookieValue = CookieUtils.getCookieValue(request, CookieUtils.getUserCookieName(forumId));
			if (cookieValue == null) {
				throw new Exception("User Cookie Value doesn't exist");
			}

			int userId = Integer.parseInt(cookieValue);

			Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);
			User user = (User) HibernateUtils.load(User.class,userId);
			List<Report> reports =  AdminController.viewForumReports(forum, user);

			request.setAttribute("reports", reports);
			request.setAttribute("forum", forum);
			request.setAttribute("user", user);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/forumManagement.jsp");
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
