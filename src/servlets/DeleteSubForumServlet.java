package servlets;

import content.Forum;
import content.SubForum;
import controllers.AdminController;
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
		description = "Handles the request of appoint a moderator to a sub forum",
		urlPatterns = {
				"/deleteSubForum"}
		)
public class DeleteSubForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteSubForumServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			int subForumId = Integer.parseInt(request.getParameter("subForumId"));

			String cookieValue = CookieUtils.getCookieValue(request, CookieUtils.USER_ID_COOKIE_NAME);
			if (cookieValue == null)
				throw new Exception("User Cookie Value doesn't exist");

			int userId = Integer.parseInt(cookieValue);

			cookieValue = CookieUtils.getCookieValue(request, CookieUtils.FORUM_ID_COOKIE_NAME);
			if (cookieValue == null)
				throw new Exception("Forum Cookie Value doesn't exist");

			int forumId = Integer.parseInt(cookieValue);

			Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);
			User user = (User) HibernateUtils.load(User.class, userId);
			SubForum subForum = (SubForum) HibernateUtils.load(SubForum.class, subForumId);

			AdminController.deleteSubForum(forum, subForum, user);

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/forum.jsp");
			request.setAttribute("user", user);
			request.setAttribute("forum", forum);
			dispatcher.forward(request, response);
		}
		catch (Exception e){
			ServletUtils.exitError(this, request,response,e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
