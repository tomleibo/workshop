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
 * Servlet implementation class ThreadServlet
 */
@WebServlet(description = "A servlet for returning a thread, posting, editing or deleting a message", urlPatterns = { "/thread" })
public class ThreadServlet extends HttpServlet {

	Forum stub=null;

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThreadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ForumPolicy policy = new ForumPolicy(3,".",ForumPolicy.HashFunction.MD5,false);
		content.Thread t=null,t2=null;
		User user1 = User.newGuest();
		SubForum sub= new SubForum("forum name",user1,3);
		Forum forum = new Forum(user1, policy, "olamHaNextShelTom");
		try {
			t= ContentController.openNewThread(forum, sub, "Charlie", "ring ring", user1);
			//t2= ContentController.openNewThread(forum, sub, "thread2", "sdasd", user1);
			ContentController.reply(forum,t.getOpeningMessage(),"hello","he he hello",user1);
			ContentController.reply(forum,t.getOpeningMessage().getComments().get(0),"bla bla","asdfsdfdsf",user1);
			ContentController.reply(forum,t.getOpeningMessage(),"the second top level comment","the content of the 2nd",user1);
			ContentController.reply(forum,t.getOpeningMessage().getComments().get(0),"the second top level comment","the content of the 2nd",user1);
			//ContentController.reply(forum,t2.getOpeningMessage(),"thread2 comment","he he hello",user1);
		}
		catch (EmptyMessageTitleAndBodyException e) {
			e.printStackTrace();
		}

		request.setAttribute("thread", t);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/thread.jsp");
		dispatcher.forward(request,response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
