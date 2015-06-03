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
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/success.jsp");

		int forumId, userId=-1, subForumId;
		String subForumName;

		try {
			forumId = Integer.parseInt(request.getParameter("forumId"));
			subForumId = Integer.parseInt(request.getParameter("subForumId"));
			String value = CookieUtils.getCookieValue(request, CookieUtils.USER_ID_COOKIE_NAME);
			if(value!= null)
				userId = Integer.parseInt(value);

			subForumName = request.getParameter("subForum");
		}
		catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			return;
		}

		Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);
		SubForum subForum = (SubForum) HibernateUtils.load(Forum.class, subForumId);
		User user = (User) HibernateUtils.load(User.class, userId);

		try {
			AdminController.deleteSubForum(forum, subForum, user);
		} catch (UserNotAuthorizedException e) {
			System.out.println(e.getMessage());
			return;
		}

		request.setAttribute("subForum", subForumName);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
