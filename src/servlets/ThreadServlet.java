package servlets;

import content.*;
import content.Thread;
import controllers.ContentController;
import controllers.SuperAdminController;
import exceptions.EmptyMessageTitleAndBodyException;
import policy.ForumPolicy;
import users.User;
import utils.CookieUtils;
import utils.HibernateUtils;

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

		int threadId;

		try {
			threadId = Integer.parseInt(request.getParameter("threadId"));
		}
		catch(NumberFormatException e){
			System.out.println(e.getMessage());
			return;
		}

		Thread t = (Thread) HibernateUtils.load(Thread.class, threadId);
		User user = null;
		String userId = CookieUtils.getCookieValue(request, CookieUtils.USER_ID_COOKIE_NAME);
		if(userId != null) {
			user = (User) HibernateUtils.load(User.class, Integer.parseInt(userId));
		}

		request.setAttribute("user", user);
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
