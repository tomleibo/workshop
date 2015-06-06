package utils;


import content.Forum;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by thinkPAD on 6/6/2015.
 */
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        try {
            String s =CookieUtils.getCookieValue((HttpServletRequest)servletRequestEvent.getServletRequest(),CookieUtils.FORUM_ID_COOKIE_NAME);
            if (s == null) {
                return;
            }
            int forumId = Integer.parseInt(s);
            if (forumId <=0 ) {
                return;
            }
            Forum forum = (Forum) HibernateUtils.load(Forum.class,forumId);
            //TODO: check forum policy vs active sesssion time (session.getCreationTime - new Date()) .

        }
        catch (Exception e){
            return;
        }
    }
}
