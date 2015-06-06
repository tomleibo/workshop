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

        String button = "<form id=\"frm\" action=\"\\notificationsPage\" method=\"get\">\n" +
                "  <input id=\"notificationsButton\"  type=\"submit\" value=\"You have "+numberOfNotifications+" notifications!\">\n" +
                "</form><br>";

        return button;
    }

    public static String getAjaxScript() {
        return "<script src=\"jquery.js\"></script>\n" +
                "      <script>\n" +
                "          var intId;\n" +
                "          $(document).ready(function(){\n" +
                "              intId = setInterval(getNotis, 1500);\n" +
                "          });\n" +
                "\n" +
                "          function getNotis() {\n" +
                "              $.ajax({\n" +
                "                  url : \"/numberOfNotifications\",\n" +
                "                  data : {uid:1},\n" +
                "                  type : \"GET\",\n" +
                "                  dataType : \"html\",\n" +
                "                  success : function (html) {\n" +
                "                      $(\"#notificationsButton\").replace(\"you have \"+html+ \" notifications!<br>\");\n" +
                "                  },\n" +
                "                  error : function (xhr,status,errorT) {\n" +
                "\n" +
                "                  },\n" +
                "                  cache:false,\n" +
                "                  timeout : 10000\n" +
                "              });\n" +
                "          }\n" +
                "      </script>";
    }

}
