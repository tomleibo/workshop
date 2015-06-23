package servlets;

import exceptions.*;

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
    private static final String INFO_ATTRIBUTE = "info";
    private static final String EXCEPTION_ATTRIBUTE = "exception";
    public static void exitSuccess(HttpServlet servlet,HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute(MESSAGE_ATTRIBUTE, message);
        RequestDispatcher successDispatcher = servlet.getServletContext().getRequestDispatcher(SUCCESS_PAGE);
        successDispatcher.forward(request,response);
    }

    public static void exitError(HttpServlet servlet,HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException, IOException {
        if (e instanceof EmptyFieldException) {
            request.setAttribute(MESSAGE_ATTRIBUTE, "You must fill all fields before submitting the form.");
        }
        else if(e instanceof EmptyMessageTitleAndBodyException) {
            request.setAttribute(MESSAGE_ATTRIBUTE, "You must fill the body or thr title of the message.");
        }
        else if(e instanceof IdentificationQuestionMissingException) {
            request.setAttribute(MESSAGE_ATTRIBUTE, "Registration requires security question(s). Please fill all required fields.");
        }
        else if(e instanceof NeedToChangePasswordException) {
            request.setAttribute(MESSAGE_ATTRIBUTE, "Your password is about to expire. Please change it now. " +
                    "<a href=\"\\changePassword?user="+((NeedToChangePasswordException) e).getUser().getId()+"\">here</a>");
        }
        else if(e instanceof PasswordAlreadyUsedException) {
            request.setAttribute(MESSAGE_ATTRIBUTE, "You can not use a previous password as a new password");
        }
        else if(e instanceof PasswordNotMatchesRegexException) {
            request.setAttribute(MESSAGE_ATTRIBUTE, "Input doesn't match the required format.");
        }
        else if(e instanceof UserAlreadyLoggedInException) {
            request.setAttribute(MESSAGE_ATTRIBUTE, "User already logged in.");
        }
        else if(e instanceof UserDoesNotExistsException) {
            request.setAttribute(MESSAGE_ATTRIBUTE, "User does not exists.");
        }
        else if(e instanceof UsernameAlreadyExistsException) {
            request.setAttribute(MESSAGE_ATTRIBUTE, "Username Already Exists. Please choose a different nickname.");
        }
        else if(e instanceof UserNotAuthorizedException) {
            request.setAttribute(MESSAGE_ATTRIBUTE, "You are not authorized to perform this action. If you are, maybe you were disconnected.");
        }
        else if(e instanceof WrongPasswordException) {
            request.setAttribute(MESSAGE_ATTRIBUTE, "Password is wrong. Please try again.");
        }
        else if(e instanceof SubForumMustHaveModeratorException) {
            request.setAttribute(MESSAGE_ATTRIBUTE, "Sub Forum have only one moderator - can not un appoint.");
        }
        else if(e instanceof UserCantBeModeratorException) {
            request.setAttribute(MESSAGE_ATTRIBUTE, "This user can not be appointed to moderator.");
        }/*
        else if(e instanceof ) {
            request.setAttribute(MESSAGE_ATTRIBUTE, "");
        }*/
        else {
            request.setAttribute(MESSAGE_ATTRIBUTE, "An error occured.");
        }
        request.setAttribute(INFO_ATTRIBUTE,e.getMessage());
        request.setAttribute(EXCEPTION_ATTRIBUTE,getStackTraceAsString(e));
        RequestDispatcher errorDispatcher = servlet.getServletContext().getRequestDispatcher(ERROR_PAGE);
        errorDispatcher.forward(request,response);
    }

    private static String getStackTraceAsString(Exception e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement el : e.getStackTrace()){
            sb.append(el);
            sb.append("\n");
        }
        return sb.toString();
    }
}
