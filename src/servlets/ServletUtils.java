package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Roee on 03-06-15.
 */
public class ServletUtils {

    private static final String SUCCESS_PAGE = "/success.jsp";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String MESSAGE_ATTRIBUTE = "message";

    public static void exitSuccess(HttpServlet servlet,HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute(MESSAGE_ATTRIBUTE, message);
        RequestDispatcher successDispatcher = servlet.getServletContext().getRequestDispatcher(SUCCESS_PAGE);
        successDispatcher.forward(request,response);
    }

    public static void exitError(HttpServlet servlet,HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException, IOException {
        request.setAttribute(MESSAGE_ATTRIBUTE, e.getMessage());
        RequestDispatcher errorDispatcher = servlet.getServletContext().getRequestDispatcher(ERROR_PAGE);
        errorDispatcher.forward(request,response);
    }


}
