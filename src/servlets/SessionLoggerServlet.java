package servlets;

import controllers.SuperAdminController;
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
import java.util.Queue;
import java.util.Set;

/**
 * Created by thinkPAD on 6/7/2015.
 */
@WebServlet(name = "SessionLoggerServlet",urlPatterns = "/openSession")
public class SessionLoggerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            int userId = -1;
            String value = CookieUtils.getCookieValue(request, CookieUtils.USER_ID_COOKIE_NAME);
            String sid= request.getParameter("sid");
            userId = Integer.parseInt(value);
            User user = (User) HibernateUtils.load(User.class,userId);
            if (sid == null) {
                Set<String> openSessionIds = SuperAdminController.getAllOpenSessions(user);
                request.setAttribute("sessions", openSessionIds);
                request.setAttribute("user",user);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/openSessions.jsp");
                dispatcher.forward(request,response);
            }
            else {
                Queue<String> log = SuperAdminController.getSessionLog(user,sid);
                request.setAttribute("sid", sid);
                request.setAttribute("log", log);
                request.setAttribute("user",user);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/sessionLog.jsp");
                dispatcher.forward(request,response);
            }
        }
        catch (Exception e) {
            ServletUtils.exitError(this, request, response, e.getMessage());
        }
    }
}
