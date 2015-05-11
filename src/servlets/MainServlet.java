package servlets;

        import com.sun.corba.se.spi.protocol.RequestDispatcherDefault;
        import content.Forum;
        import content.ForumSystem;
        import content.SubForum;
        import policy.ForumPolicy;
        import users.User;
        import utils.HibernateUtils;

        import java.io.IOException;
        import java.io.Writer;
        import java.util.List;
        import javax.servlet.RequestDispatcher;
        import javax.servlet.ServletException;
        import javax.servlet.annotation.WebServlet;
        import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MainServlet
 */

@WebServlet(description = "Presents all forums", urlPatterns = { "/main" })
public class MainServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Forum> forums = HibernateUtils.getAllForums();

        request.setAttribute("forums", forums);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/main.jsp");
        dispatcher.forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

}
