package servlets;

import content.Forum;
import content.SubForum;
import controllers.AdminController;
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
		description = "Handles the request of adding a sub forum",
		urlPatterns = {
				"/newSubForum"}
		)
public class AddSubForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddSubForumServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int forumId = Integer.parseInt(request.getParameter("forumId"));
			String title = request.getParameter("title");

			Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);

			String userId = CookieUtils.getCookieValue(request, CookieUtils.USER_ID_COOKIE_NAME);
			User user;
			if (userId != null) {
				user = (User) HibernateUtils.load(User.class, Integer.parseInt(userId));
			}
			else{
				throw new Exception("Empty Cookie");
			}

			SubForum subForum = AdminController.addSubForum(forum, title, user);
			if(!HibernateUtils.save(subForum))
				throw new Exception("Sub Forum Not Saved!");

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/forum.jsp");
			request.setAttribute("user", user);
			request.setAttribute("forum", forum);
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
