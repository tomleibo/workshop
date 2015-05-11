package servlets;

import content.Forum;
import content.ForumSystem;
import content.SubForum;
import policy.ForumPolicy;
import users.User;
import utils.HibernateUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class MainServlet
 */

@WebServlet(description = "Presents all forums", urlPatterns = { "/supermain" })
public class SuperMainServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SuperMainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Forum> forums = HibernateUtils.getAllForums();

        request.setAttribute("forums", forums);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/superMain.jsp");
        dispatcher.forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

}
