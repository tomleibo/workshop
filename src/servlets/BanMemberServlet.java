package servlets;

import content.SubForum;
import controllers.ModerationController;
import controllers.UserController;
import exceptions.UserNotAuthorizedException;
import users.User;
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
		description = "Handles the ban member request",
		urlPatterns = {
				"/banMember"}
		)
public class BanMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BanMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/memberBanned.jsp");

		int subForumId, userId, bannedId;
		String bannedName;

		try{
			subForumId = Integer.parseInt(request.getParameter("subForum"));
			userId = Integer.parseInt(request.getParameter("userId"));
			bannedName = request.getParameter("banned");
			bannedId = Integer.parseInt(request.getParameter("bannedId"));
		}

		catch(NumberFormatException e) {
			System.out.println(e.getMessage());
			return;
		}

		SubForum subForum = (SubForum) HibernateUtils.load(SubForum.class, subForumId);
		User moderator = (User) HibernateUtils.load(User.class, userId);
		User banned = (User) HibernateUtils.load(User.class, bannedId);

		try {
			ModerationController.banUser(subForum, moderator, banned);
		} catch (UserNotAuthorizedException e) {
			// TODO add error screen?
			e.printStackTrace();
			return;
		}

		// for success message
		request.setAttribute("banned", bannedName);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
