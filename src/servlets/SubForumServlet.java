package servlets;

import com.sun.media.sound.SF2GlobalRegion;
import content.*;
import content.Thread;
import controllers.ContentController;
import controllers.UserController;
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
 * Servlet implementation class SubForumServlet
 */
@WebServlet(description = "Shows the list of threads in the subforum", urlPatterns = { "/subForum" })
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
		try {
			String subForumIdString = request.getParameter("subForumId");
			int subForumId = Integer.parseInt(subForumIdString);

			SubForum subForum = (SubForum) HibernateUtils.load(SubForum.class, subForumId);

			User user = null;
			String userId = CookieUtils.getCookieValue(request, CookieUtils.USER_ID_COOKIE_NAME);
			if (userId != null) {
				user = (User) HibernateUtils.load(User.class, Integer.parseInt(userId));
			}
			else
				throw new Exception("Empty Cookie");

			String subForumCookieId = CookieUtils.getCookieValue(request, CookieUtils.SUB_FORUM_ID_COOKIE_NAME);
			if (subForumCookieId != null) {
				CookieUtils.changeCookieValue(request, response, CookieUtils.SUB_FORUM_ID_COOKIE_NAME, subForumIdString);
			} else {
				CookieUtils.addInfiniteCookie(response, CookieUtils.SUB_FORUM_ID_COOKIE_NAME, subForumIdString);
			}

			request.setAttribute("subForum", subForum);
			request.setAttribute("user", user);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/subforum.jsp");
			dispatcher.forward(request, response);
		}
		catch (Exception e){
			ServletUtils.exitError(this, request,response, e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
