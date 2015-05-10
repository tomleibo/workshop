package servlets;

import content.Forum;
import controllers.UserController;
import exceptions.UsernameAlreadyExistsException;
import utils.HibernateUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Servlet implementation class ThreadServlet
 */
@WebServlet(description = "A servlet for registering", urlPatterns = { "/register" })
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=-1;
		String userName, pass, email;
		try {
			id=Integer.parseInt(request.getParameter("id"));
			userName = request.getParameter("user");
			pass = request.getParameter("pass");
			email = request.getParameter("email");
		}
		catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			return;
		}

		Forum forum = (Forum) HibernateUtils.load(Forum.class, id);

		try {
			UserController.register(forum, userName, pass, email);
		} catch (UsernameAlreadyExistsException | NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
