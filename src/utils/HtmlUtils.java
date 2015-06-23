package utils;

import users.Notification;
import users.User;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Roee on 11-05-15.
 */
public class HtmlUtils {

    public static SimpleDateFormat notificationsFormat = new SimpleDateFormat("yyyy-MM-dd");
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
                "                  url : \"/numberOfNotificationsAndFriendRequests\",\n" +
                "                  data : {uid:1},\n" +
                "                  type : \"GET\",\n" +
                "                  dataType : \"html\",\n" +
                "                  success : function (json) {\n" +
                "                  var jsonParsed = JSON.parse(json); "+
                "                      $(\"#notificationsButton\").text(jsonParsed.notifs);\n" +
                "                      $(\"#requestsButton\").text(jsonParsed.frs);\n" +
//                "                      $(\"#notificationsButton\").value=\"you have \"+html+ \" notifications!<br>\";\n" +
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

    /*
public class Test{

    public static void main(String[] args) {
        long val = 1346524199000l;
        Date date=new Date(val);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
        String dateText = df2.format(date);
        System.out.println(dateText);
    }*/

    public static String parseDate(Date date){
        return notificationsFormat.format(date);
    }

}
