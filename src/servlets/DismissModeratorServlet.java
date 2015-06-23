package servlets;

import content.Forum;
import content.SubForum;
import controllers.AdminController;
import exceptions.SubForumMustHaveModeratorException;
import exceptions.UserNotAuthorizedException;
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
				"/dismissModerator"}
		)
public class DismissModeratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DismissModeratorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionLogger.get().log(request.getSession().getId(),"dismissing moderator");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/moderatorOperationCompleted.jsp");

		int forumId, userId=-1, subForumId, moderatorId;
		String moderatorName;

		try {
			forumId = Integer.parseInt(request.getParameter("forumId"));
			subForumId = Integer.parseInt(request.getParameter("subForumId"));
			String value = CookieUtils.getCookieValue(request, CookieUtils.getUserCookieName(forumId));

			if(value!= null)
				userId = Integer.parseInt(value);
			moderatorName = request.getParameter("moderator");
			moderatorId = Integer.parseInt(request.getParameter("moderatorId"));
		}
		catch (NumberFormatException e) {
            ServletUtils.exitError(this, request, response, e);
			return;
		}

		Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);
		SubForum subForum = (SubForum) HibernateUtils.load(Forum.class, subForumId);
		User user = (User) HibernateUtils.load(User.class, userId);
		User moderator = (User) HibernateUtils.load(User.class, moderatorId);

		try {
			AdminController.unAppoint(forum, subForum, user, moderator);
		} catch (UserNotAuthorizedException | SubForumMustHaveModeratorException e) {
            ServletUtils.exitError(this, request, response, e);
			return;
		}

        request.setAttribute("moderator", moderatorName);
		request.setAttribute("op", "dismiss");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
