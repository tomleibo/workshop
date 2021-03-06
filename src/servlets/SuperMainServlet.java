package servlets;

import content.Forum;
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
        try{
           SessionLogger.get().log(request.getSession().getId(),"viewing the list of forums");
            List<Forum> forums = HibernateUtils.getAllForums();

            request.setAttribute("forums", forums);

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/superMain.jsp");
            dispatcher.forward(request, response);
        }
        catch (Exception e) {
            ServletUtils.exitError(this, request,response,e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

}
