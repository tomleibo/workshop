package servlets;

import content.Forum;
import content.ForumSystem;
import controllers.SuperAdminController;
import exceptions.UserNotAuthorizedException;
import policy.ForumPolicy;
import users.User;
import users.userState.SuperAdminState;
import utils.Cipher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Servlet implementation class UserProfileServlet
 */
@WebServlet(
		description = "Handles the request for sending a friend request",
		urlPatterns = { 
				"/friendRequest"
		})
public class FriendRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendRequestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/friendRequest.jsp");

		String forum = request.getParameter("forum");
		String sender = request.getParameter("sender");
		String receiver = request.getParameter("receiver");


		request.setAttribute("forum", forum);
		request.setAttribute("sender", sender);
		request.setAttribute("receiver", receiver);

		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
