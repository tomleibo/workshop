package servlets;

import content.Forum;
import controllers.UserController;
import users.Notification;
import users.User;
import utils.CookieUtils;
import utils.HibernateUtils;
import utils.SessionLogger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class UserProfileServlet
 */
@WebServlet(
		description = "Shows the user's notifications",
		urlPatterns = {
				"/notificationsPage"}
)
public class NotificationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NotificationsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			SessionLogger.get().log(request.getSession().getId(),"viewing notifications");
			String cookieValue = CookieUtils.getCookieValue(request, CookieUtils.FORUM_ID_COOKIE_NAME);
			if (cookieValue == null)
				throw new Exception("Forum Cookie Value doesn't exist");

			int forumId = Integer.parseInt(cookieValue);

			cookieValue = CookieUtils.getCookieValue(request, CookieUtils.getUserCookieName(forumId));
			if (cookieValue == null) {
				throw new Exception("User Cookie Value doesn't exist");
			}

			int userId = Integer.parseInt(cookieValue);

			User user = (User) HibernateUtils.load(User.class, userId);
			Forum forum = (Forum) HibernateUtils.load(Forum.class, forumId);
			List<Notification> notifications = UserController.getPendingNotifications(forum, user);
			List<Notification> unviewedNotifications = new ArrayList<>();
			for(Notification notification : notifications){
				if(!notification.isViewed()) {
					unviewedNotifications.add(notification);
				}
				UserController.markNotificationAsRead(user, notification);
			}

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/notifications.jsp");

			request.setAttribute("notifications", unviewedNotifications);
			request.setAttribute("user", user);
			request.setAttribute("forum", forum);
			dispatcher.forward(request,response);
		}
		catch (Exception e){
			ServletUtils.exitError(this, request,response,e);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
