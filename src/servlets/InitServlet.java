package servlets;

import controllers.SuperAdminController;
import exceptions.UserNotAuthorizedException;
import utils.HibernateUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by thinkPAD on 6/22/2015.
 */
@WebServlet(name = "InitServlet",urlPatterns = "/init")
public class InitServlet extends HttpServlet {
    private static final String CODE = "bivanbivanbivan";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        String email = request.getParameter("email");
        if (code.equals(CODE)) {
            try {
                SuperAdminController.initializeForumSystem(username,password,email);
            }
            catch (NoSuchAlgorithmException e) {
                Exception e2 = new Exception("in init servlet. this should never happened unless bivan fucked something");
                ServletUtils.exitError(this,request,response,e2);
            }
            ServletUtils.exitSuccess(this, request, response, "You have successfully initialized a new forum-system.");
        }
        else {
            ServletUtils.exitError(this,request,response,new UserNotAuthorizedException("To initalize forum."));
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
