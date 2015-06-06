package utils;


import content.Forum;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;


public class RequestListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        try {
            String s =CookieUtils.getCookieValue((HttpServletRequest) servletRequestEvent.getServletRequest(), CookieUtils.FORUM_ID_COOKIE_NAME);
            if (s == null) {
                return;
            }
            int forumId = Integer.parseInt(s);
            if (forumId <=0 ) {
                return;
            }
            Forum forum = (Forum) HibernateUtils.load(Forum.class,forumId);
            int maxActiveTime = forum.getPolicy().getSessionTimeout();
            HttpSession session = ((HttpServletRequest) servletRequestEvent.getServletRequest()).getSession();
            if (new Date().getTime() - session.getCreationTime() > maxActiveTime){
                session.invalidate();
            }
            session.setMaxInactiveInterval(forum.getPolicy().getIdleTime()/1000);
        }
        catch (Exception e){

        }
        return;
    }
}
