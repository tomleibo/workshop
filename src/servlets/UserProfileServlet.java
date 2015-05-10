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
import users.userState.SuperAdminState;
import utils.Cipher;

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
		description = "An entry point for user actions: login, register, guest entry, user profile etc.", 
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
		String url = request.getRequestURL().toString();
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/userProfile.jsp");

		User superAdmin = User.newMember("SuperAdmin", "SuperAdminPassword", "super@admin.com");
		superAdmin.setState(new SuperAdminState());

		ForumPolicy policy = new ForumPolicy(3, ".", ForumPolicy.HashFunction.MD5);

		Forum theForum = new Forum(superAdmin, policy, "My_Forum");
		SubForum subForum = new SubForum("subForum", superAdmin, 5);
		String email = "mail@theinternet.com";
		User user1 = User.newMember("Roee", "Roee1", email);
		User user2 = User.newMember("Shai", "Shai1", email);
		User user3 = User.newMember("Tom", "Tom1", email);
//		user1.getFriendRequests().add(new FriendRequest(user2, user1, "lalala"));
//		user1.getFriendRequests().add(new FriendRequest(user3, user1, "lalala"));

		theForum.addMember(user1);
		theForum.addMember(user2);
		theForum.addMember(user3);

		request.setAttribute("path", url);
		request.setAttribute("forum", theForum);
		request.setAttribute("subForum", subForum);
		request.setAttribute("user", user1);

		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
