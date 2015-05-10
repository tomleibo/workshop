package utils;

import content.*;
import content.Thread;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import policy.ForumPolicy;
import policy.UserStatusPolicy;
import users.FriendRequest;
import users.Notification;
import users.Report;
import users.User;
import users.userState.UserState;

public class HibernateUtils {

    private static Configuration cfg;
    public static Session session=null;
    public static boolean configure(boolean init) {
        if (session!=null) {
            throw new RuntimeException("Hibernate utils already started!");
        }
        int id=1;
        cfg = new Configuration()
                .addAnnotatedClass(Forum.class)
                .addAnnotatedClass(Message.class)
                .addAnnotatedClass(SubForum.class)
                .addAnnotatedClass(Thread.class)
                .addAnnotatedClass(ForumPolicy.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Report.class)
                .addAnnotatedClass(Notification.class)
                .addAnnotatedClass(FriendRequest.class)
                .addAnnotatedClass(UserState.class)
                .addAnnotatedClass(UserStatusPolicy.class)

                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.connection.driver_class","com.mysql.jdbc.Driver")
                .setProperty("hibernate.connection.url","jdbc:mysql://localhost:3306/forum_system")
                .setProperty("hibernate.connection.username","root")
                .setProperty("hibernate.password", "");
        if (init) {
            cfg.setProperty("hibernate.hbm2ddl.auto", "create");
        }
        else {
            cfg.setProperty("hibernate.hbm2ddl.auto", "true");
        }
        cfg.configure();
        return cfg!=null;
    }

    public static boolean start() {
        if (configure(false)){
            startSession();
            return true;
        }
        return false;
    }

    public static void stop() {
        closeSession();
    }

    private static boolean startSession() {
        SessionFactory sf =cfg.buildSessionFactory();
        session = sf.openSession();
        return session!=null;
    }

    private static void closeSession() {
        session.close();
    }

    public static boolean init() {
        boolean ans = configure(true) && startSession();
        if (ans) {
            closeSession();
        }
        return ans;
    }


    /*
    public static Session getSession() {
        if (session==null) {
            throw new RuntimeException("Session is not initialized. init() should be called before getSession");
        }
        return session;
    }*/

    public static Query getQuery(String query) {
        return session.createQuery(query);
    }

    public static boolean save(Object o) {
//        try {
//            session.save(o);
//            return true;
//        }
//        catch(HibernateException e) {
//            ForumLogger.errorLog(e.toString());
//            return false;
//        }
        Transaction tx = null;
        boolean res = false;
        try {
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            res = true;
        }
        catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
        finally {
            session.close();

        }
        return res;
    }

    public static boolean del(Object o) {
        try {
            session.delete(o);
            return true;
        }
        catch(HibernateException e) {
            ForumLogger.errorLog(e.toString());
            return false;
        }
    }

    public static Object load(Class c,int id) {
        try {
            return session.load(c,id);
        }
        catch(HibernateException e) {
            ForumLogger.errorLog(e.toString());
            return false;
        }
    }

    public static void main (String args[]) {
        HibernateUtils.init();
    }
}
