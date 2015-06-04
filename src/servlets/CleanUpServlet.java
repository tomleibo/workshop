package servlets;

import content.Forum;
import controllers.UserController;
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
 * Servlet implementation class ForumServlet
 */
@WebServlet(description = "Presents all sub forums", urlPatterns = { "/cleanup" })
public class CleanUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CleanUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String forumIdString = request.getParameter("forumId");
			int forumId = Integer.parseInt(forumIdString);
			Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);

			for(User user : forum.getMembers()){
				HibernateUtils.del(user);
			}

			forum.getMembers().clear();
			HibernateUtils.update(forum);
			CookieUtils.deleteCookie(request, response, CookieUtils.USER_ID_COOKIE_NAME);
			CookieUtils.deleteCookie(request, response, CookieUtils.FORUM_ID_COOKIE_NAME);

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/forum?forumId="+forumId);
			dispatcher.forward(request, response);
		}
		catch (Exception e) {
			ServletUtils.exitError(this, request, response, e.getMessage());
		}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
