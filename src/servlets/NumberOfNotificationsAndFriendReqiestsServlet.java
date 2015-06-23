package servlets;

import users.FriendRequest;
import users.User;
import utils.CookieUtils;
import utils.HibernateUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by thinkPAD on 6/5/2015.
 */
@WebServlet(name = "NumberOfNotificationsAndFriendReqiestsServlet", urlPatterns = "/numberOfNotificationsAndFriendRequests")
public class NumberOfNotificationsAndFriendReqiestsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{

            String cookieValue = CookieUtils.getCookieValue(request, CookieUtils.FORUM_ID_COOKIE_NAME);
            if (cookieValue == null)
                throw new Exception("Forum Cookie Value doesn't exist");

            int forumId = Integer.parseInt(cookieValue);

            cookieValue = CookieUtils.getCookieValue(request, CookieUtils.getUserCookieName(forumId));
            if (cookieValue == null) {
                throw new Exception("User Cookie Value doesn't exist");
            }

            int userId = Integer.parseInt(cookieValue);

            User user = (User) HibernateUtils.load(User.class, userId);
            if(user == null)
                throw new Exception("Error loading user");

            String notifs = String.valueOf(user.getPendingNotifications().size());
//            String notifs = String.valueOf(System.currentTimeMillis());

            int numOfFriendRequests = 0;
            for (FriendRequest fr : user.getFriendRequests()){
                if(!fr.isViewed()){
                    numOfFriendRequests++;
                }
            }

            String requests = String.valueOf(numOfFriendRequests);
//            String requests = String.valueOf(System.currentTimeMillis());
            String json = "{\"notifs\":\""+notifs+"\", \"frs\":\""+requests+"\"}";

            response.setContentType("application/json");
            response.getWriter().write(json);
        }

        catch (Exception e){
            ServletUtils.exitError(this, request,response,e);
        }
    }
}
