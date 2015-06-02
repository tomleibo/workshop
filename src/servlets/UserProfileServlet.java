package servlets;

import content.Forum;
import content.ForumSystem;
import content.SubForum;
import controllers.SuperAdminController;
import controllers.UserController;
import exceptions.UserNotAuthorizedException;
import exceptions.UsernameAlreadyExistsException;
import policy.ForumPolicy;
import users.FriendRequest;
import users.User;
import utils.Cipher;
import utils.CookieUtils;
import utils.HibernateUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserProfileServlet
 */
@WebServlet(
		description = "An entry point for user profile actions: send, remove and reply friend request, report member, ban member",
		urlPatterns = { 
				"/profile"
		})
public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/userProfile.jsp");

		int forumId, userId = -1;

		try {
			forumId = Integer.parseInt(request.getParameter("forumId"));
			String value = CookieUtils.getCookieValue(request, CookieUtils.USER_ID_COOKIE_NAME);
			if(value!= null)
				userId = Integer.parseInt(value);
		}
		catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			return;
		}

		Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);
		User user  = (User) HibernateUtils.load(Forum.class, userId);

		request.setAttribute("forum", forum);
		request.setAttribute("user", user);

		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
