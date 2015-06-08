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

import java.util.List;

public class HibernateUtils {

    private static Configuration cfg;

    private static SessionFactory sessionFactory =null;

    public static boolean configure(boolean init) {
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
                .addAnnotatedClass(UserStatusPolicy.class)
                .setProperty("hibernate.current_session_context_class","thread")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.connection.driver_class","com.mysql.jdbc.Driver")
                .setProperty("hibernate.connection.url","jdbc:mysql://localhost:3306/forum_system")
                .setProperty("hibernate.current_session_context_class","thread")
                .setProperty("hibernate.connection.username","root")
                .setProperty("hibernate.password", "");
        if (init) {
            cfg.setProperty("hibernate.hbm2ddl.auto", "create");
        }
//        else {
//            cfg.setProperty("hibernate.hbm2ddl.auto", "validate");
//        }
        sessionFactory = cfg.configure().buildSessionFactory();
        return cfg!=null;
    }

    public synchronized static boolean start() {
        return configure(false);
    }

    private static void closeSession() {

    }

    public static boolean init() {
        return configure(true);
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static Query getQuery(String query) {
        return getSession().createQuery(query);
    }

    public synchronized static boolean save(Object o) {
        return saveReturnId(o) > 0;
    }

    public synchronized static boolean update(Object o) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.getTransaction().begin();
            session.update(o);
            session.getTransaction().commit();
            return true;
        }
        catch(HibernateException e) {
            System.out.println(e);
            ForumLogger.errorLog(e.toString());
            return false;
        }
        finally{
            if(session.isOpen()) {
                session.close();
            }
        }
    }


    public synchronized static boolean merge(Object o) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.getTransaction().begin();
            session.merge(o);
            session.getTransaction().commit();
            return true;
        }
        catch(HibernateException e) {
            System.out.println(e);
            ForumLogger.errorLog(e.toString());
            return false;
        }
        finally{
            if(session.isOpen()) {
                session.close();
            }
        }
    }

    public synchronized static int saveReturnId(Object o) {
        //Transaction tx = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            /*if(!session.isOpen() || !session.isConnected()){
                System.out.println("Session is closed!!!!");
            }
            tx=session.beginTransaction();
            */
            session.getTransaction().begin();

            int id= (int)session.save(o);

            session.getTransaction().commit();
            //tx.commit();
            return id;
        }
        catch(HibernateException e) {
            System.out.println(e);
//            tx.rollback();
            ForumLogger.errorLog(e.toString());
            return -1;
        }
        finally{
            if(session.isOpen()) {
                session.close();
            }
        }
    }

    public synchronized static boolean del(Object o) {
       // Transaction tx = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            /*tx=session.beginTransaction();
            session.delete(o);
            tx.commit();*/
            session.getTransaction().begin();
            session.delete(o);
            session.getTransaction().commit();
            return true;
        }
        catch(HibernateException e) {
//            tx.rollback();
            System.out.println(e);
            ForumLogger.errorLog(e.toString());
            return false;
        }
        finally{
            if(session.isOpen()) {
                session.close();
            }
        }
    }

    public synchronized static Object load(Class c,int id) {
        Object o=null;
        Session session = sessionFactory.getCurrentSession();
        try {
            session.getTransaction().begin();
            o=session.get(c, id);
            session.getTransaction().commit();

            return  o;
        }
        catch(HibernateException e) {
            System.out.println(e);
            ForumLogger.errorLog(e.toString());
            return null;
        }
        finally{
            if(session.isOpen()) {
                session.close();
            }
        }
    }

    public static void main (String args[]) {
        //HibernateUtils.configure(false);
        //System.out.println(cleanUp());
        HibernateUtils.init();
    }

    public static List<Forum> getAllForums() {
        String hql = "FROM Forum";
        Session session = HibernateUtils.getSession();
        Query query = session.createQuery(hql);
        List<Forum> results = query.list();
        if (session.isOpen()) {
            session.close();
        }
        return results;
    }

    public static List<User> getAllUsers() {
        String hql = "FROM User";
        Session session = HibernateUtils.getSession();
        Query query = session.createQuery(hql);
        List<User> results = query.list();
        if (session.isOpen()) {
            session.close();
        }
        return results;
    }

    public static boolean runSql(Session session,String query) {
        Query q = session.createSQLQuery(query);
        System.out.println("running query: \'"+query+"\'");
        int rowsAffected = q.executeUpdate();
        System.out.println("result: "+rowsAffected+" rows affected");
        return  rowsAffected> 0;
    }

    public static boolean cleanUp() {
        /*Session session = sessionFactory.getCurrentSession();
        Transaction t = session.beginTransaction();
        boolean ans = runSql(session,"update forum set admin=NULL;") &&
        runSql(session,"update user set state=NULL;") &&
        runSql(session,"delete from userstate;") &&
        runSql(session,"delete from user;") &&
        runSql(session,"delete from forum;") &&
        runSql(session,"delete from forumpolicy;");
        t.commit();
        if(session.isOpen()) {
            session.close();
        }
        return ans;*/
        return false;
    }
}
