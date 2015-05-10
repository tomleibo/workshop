package servlets;

import content.*;
import content.Thread;
import controllers.ContentController;
import exceptions.EmptyMessageTitleAndBodyException;
import policy.ForumPolicy;
import users.User;
import utils.HibernateUtils;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ForumServlet
 */
@WebServlet(description = "Presents all sub forums", urlPatterns = { "/forum" })
public class ForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForumServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int forumId;

		try{
			forumId = Integer.parseInt(request.getParameter("forumId"));
		}

		catch(NumberFormatException e) {
			System.out.println(e.getMessage());
			return;
		}

		Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);

		request.setAttribute("forum",forum);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/forum.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
