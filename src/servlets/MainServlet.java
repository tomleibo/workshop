package servlets;

        import content.Forum;
        import users.User;
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
 * Servlet implementation class MainServlet
 */

@WebServlet(description = "Presents all forums", urlPatterns = { "/home" })
public class MainServlet extends HttpServlet {
    private static boolean initialized=false;
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User superAdmin = (User) request.getAttribute("superAdmin");

            List<Forum> forums = HibernateUtils.getAllForums();
            request.setAttribute("forums", forums);
            request.setAttribute("superAdmin", superAdmin);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/main.jsp");
            dispatcher.forward(request, response);
        }

        catch(Exception e){
            ServletUtils.exitError(this, request, response, e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

}
