package servlets;

import content.Forum;
import policy.ForumPolicy;
import users.User;
import users.userState.SuperAdminState;

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
		description = "An entry point for user actions: login, register, guest entry, user profile etc.",
		urlPatterns = {
				"/sendFriendRequest"}
		)
public class SendFriendRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendFriendRequestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/friendRequestSent.jsp");

		String forum = request.getParameter("forum");
		String senderId = request.getParameter("senderId");
		String receiver = request.getParameter("receiver");
		String receiverId = request.getParameter("receiverId");
		String content = request.getParameter("content");

		// SendFriendRequest(forum,sender,receiver, content);
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
