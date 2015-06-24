package servlets;

import content.Forum;
import controllers.SuperAdminController;
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
import java.util.List;

/**
 * Servlet implementation class SubForumServlet
 */
@WebServlet(description = "Set up request for system management", urlPatterns = { "/systemManagement" })
public class SystemManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SystemManagementServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            SessionLogger.get().log(request.getSession().getId(),"setting up system management");

			String superAdminId = CookieUtils.getCookieValue(request, CookieUtils.SUPER_USER_ID_COOKIE_NAME);
			if (superAdminId == null) {
				throw new Exception("SuperAdmin Cookie Doesn't Exist!");
			}

			User superAdmin = (User) HibernateUtils.load(User.class, Integer.parseInt(superAdminId));

			Integer numberOfForums = SuperAdminController.getReportNumberOfForums(superAdmin);

			List<Forum> forums = HibernateUtils.getAllForums();
			request.setAttribute("forums", forums);
			request.setAttribute("superAdmin", superAdmin);
			request.setAttribute("numberOfForums", numberOfForums);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/systemManagement.jsp");
			dispatcher.forward(request, response);
		}
		catch (Exception e){
			ServletUtils.exitError(this, request,response, e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
