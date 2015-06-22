package utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by thinkPAD on 6/6/2015.
 */
public class ForumSystemContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            HibernateUtils.start();
        }
        catch (Exception e) {
            //should be ignored if database is not inited.
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
