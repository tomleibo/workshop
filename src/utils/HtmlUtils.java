package utils;

import users.Notification;
import users.User;

import java.util.List;

/**
 * Created by Roee on 11-05-15.
 */
public class HtmlUtils {

    public static String getNotificationsLink(User user){

        List<Notification> notifications = user.getPendingNotifications();
        String numberOfNotifications;

        numberOfNotifications = (notifications.isEmpty() ? "no" : Integer.toString(notifications.size()));

        String button = "@<form id=\"frm\" action=\"${pageContext.request.contextPath}/notifications.jsp\" method=\"get\">\n" +
                "  <input type=\"submit\" value=\"You have "+numberOfNotifications+" notifications!\">\n" +
                "</form><br>";

        return button;
    }
}
