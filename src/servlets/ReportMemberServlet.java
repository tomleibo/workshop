package servlets;

import content.Forum;
import controllers.ModerationController;
import controllers.UserController;
import exceptions.UserNotAuthorizedException;
import users.User;
import utils.CookieUtils;
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
		description = "Handles the request of reporting a member",
		urlPatterns = {
				"/reportMember"}
		)
public class ReportMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/success.jsp");

		int forumId, senderId = -1, reporteeId;
		String reporteeName, title, content;

		try {
			forumId = Integer.parseInt(request.getParameter("forumId"));
			String value = CookieUtils.getCookieValue(request, CookieUtils.USER_ID_COOKIE_NAME);
			if(value!= null)
				senderId = Integer.parseInt(value);
			reporteeName = request.getParameter("reportee");
			reporteeId = Integer.parseInt(request.getParameter("reporteeId"));
			title = request.getParameter("title");
			content = request.getParameter("content");
		}
		catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			return;
		}

		Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);
		User sender  = (User) HibernateUtils.load(Forum.class, senderId);
		User reportee = (User) HibernateUtils.load(Forum.class, reporteeId);

		try {
			UserController.report(forum, sender, reportee, title, content);
		} catch (UserNotAuthorizedException e) {
			System.out.println(e.getMessage());
			return;
		}

		request.setAttribute("reportee", reporteeName);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
