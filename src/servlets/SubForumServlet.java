package servlets;

import content.*;
import content.Thread;
import controllers.ContentController;
import exceptions.EmptyMessageTitleAndBodyException;
import policy.ForumPolicy;
import users.User;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SubForumServlet
 */
@WebServlet(description = "Shows the list of threads in the subforum", urlPatterns = { "/subforum" })
public class SubForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubForumServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ForumPolicy policy = new ForumPolicy(3,".",ForumPolicy.HashFunction.MD5,false);

		User user1 = User.newGuest();
		SubForum sub= new SubForum("forum name",user1,3);
		Forum forum = new Forum(user1, policy, "olamHaNextShelTom");
		try {
			Thread t= ContentController.openNewThread(forum, sub, "Charlie", "ring ring", user1);
			Thread t2= ContentController.openNewThread(forum, sub, "thread2", "sdasd", user1);
		}
		catch (EmptyMessageTitleAndBodyException e) {
			e.printStackTrace();
		}
		request.setAttribute("subforum",sub);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/subforum.jsp");
		dispatcher.forward(request,response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
