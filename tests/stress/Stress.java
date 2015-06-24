package stress;

import content.Forum;
import content.SubForum;
import content.Thread;
import controllers.AdminController;
import controllers.SuperAdminController;
import controllers.UserController;
import exceptions.UserNotAuthorizedException;
import policy.ForumPolicy;
import users.User;
import utils.HibernateUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

/**
 * Created by thinkPAD on 6/23/2015.
 */
public class Stress {
    protected static final String superAdminUsername = "SuperAdmin";
    protected static final String superAdminPassword = "";
    protected static final String superAdminMail = "";
    private static final int X = 1;
    volatile public static List<content.Thread> threads;

    public static void main(String[] args) throws Exception {
        threads= new Vector<>();
        StressUser[] stressUsers = new StressUser[X];
        User superAdmin = SuperAdminController.initializeForumSystem(superAdminUsername, superAdminPassword, superAdminMail);
        HibernateUtils.cleanUp();
        System.out.println("cleaned up database.");
        ForumPolicy policy = new ForumPolicy(3, ".+", ForumPolicy.HashFunction.MD5);
        Forum forum = SuperAdminController.createNewForum(superAdmin, policy, "forumname");
        SubForum sub = AdminController.addSubForum(forum,"subname",superAdmin);
        for (int i=0; i<X; i++){
            User user = UserController.register(forum,"user"+i,"a","sdf"+i+"@gmail.com");
            stressUsers[i] = new StressUser(user,forum,sub);
        }
        System.out.println("created "+X+" users. starting to run.");
        ExecutorService executor = Executors.newFixedThreadPool(X);
        for (int i=0;i<X;i++) {
            executor.execute(stressUsers[i]);
            sleep(500);
        }
    }


    static class StressUser implements Runnable {

        User user;
        Forum forum;
        SubForum subforum;

        public StressUser(User user, Forum forum, SubForum subforum) {
            this.user = user;
            this.forum = forum;
            this.subforum = subforum;
        }

        @Override
        public void run() {
            for (int i=0; i<1;i++) {
                createThread();
                viewThread();
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void createThread() {
            System.out.println(user.getUsername()+" creating thread.");
            String title = user.getUsername()+(new Date().getTime());
            String body = user.getUsername()+(new Date().getTime());
            try {
                Thread t =UserController.openNewThread(forum, subforum, title, body, user);
                Stress.threads.add(t);
                System.out.println("created thread number "+(Stress.threads.size()-1));
            }
            catch (UserNotAuthorizedException e) {
                System.out.println("not authorized");
            }
            catch (Exception e) {
                //
            }
        }

        public void viewThread() {
            try {
                Random rand = new Random();
                int randomNum = rand.nextInt((Stress.threads.size()) +1);
                System.out.println(user.getUsername()+" is viewing thread number: "+randomNum);
                threads.get(randomNum).getOpeningMessage();
            }
            catch (Exception e) {

            }

        }
    }
}
