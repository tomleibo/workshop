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

import java.util.List;

public class HibernateUtils {

    private static Configuration cfg;
    private static Session session=null;
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
//        else {
//            cfg.setProperty("hibernate.hbm2ddl.auto", "validate");
//        }
        cfg.configure();
        return cfg!=null;
    }

    public synchronized static boolean start() {
        if (session==null) {
            if (configure(false)) {
                startSession();
                return true;
            }
            return false;
        }
        return true;

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



    public static Session getSession() {
        if (session==null) {
            throw new RuntimeException("Session is not initialized. init() should be called before getSession");
        }
        return session;
    }

    public static Query getQuery(String query) {
        Transaction tx = null;
        return session.createQuery(query);
    }

    public static boolean save(Object o) {
        Transaction tx = null;
        try {
            tx=session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        }
        catch(HibernateException e) {
            System.out.println(e);
            tx.rollback();
            ForumLogger.errorLog(e.toString());
            return false;
        }
    }

    public static int save(Object o,Object o2) {
        Transaction tx = null;
        try {
            tx=session.beginTransaction();
            int id= (int)session.save(o);
            tx.commit();
            return id;
        }
        catch(HibernateException e) {
            System.out.println(e);
            tx.rollback();
            ForumLogger.errorLog(e.toString());
            return -1;
        }
    }

    public static boolean del(Object o) {
        Transaction tx = null;
        try {
            tx=session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        }
        catch(HibernateException e) {
            tx.rollback();
            ForumLogger.errorLog(e.toString());
            return false;
        }
    }

    public static Object load(Class c,int id) {
        Object o=null;
        try {
            o=session.get(c,id);
            return  o;
        }
        catch(HibernateException e) {
            ForumLogger.errorLog(e.toString());
            return null;
        }
    }

    public static void test() {
        Transaction tx = null;
        try{
            tx=session.beginTransaction();
            User u=new User();
            session.save(new Message("","",u,null,null));
            tx.commit();
        }
        catch (HibernateException e) {
            tx.rollback();
        }

    }

    public static void main (String args[]) {
//        HibernateUtils.start();
//        HibernateUtils.test();
        HibernateUtils.init();
    }

    public static List<Forum> getAllForums() {
        return null;
    }
}
