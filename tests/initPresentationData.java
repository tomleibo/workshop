import acceptanceTests.bridge.IForumSystemBridge;
import content.*;
import controllers.AdminController;
import controllers.SuperAdminController;
import controllers.UserController;
import policy.ForumPolicy;
import users.User;
import utils.HibernateUtils;

/**
 * Created by Shai Rippel on 24/06/2015.
 */
public class initPresentationData {

    protected static IForumSystemBridge driver;
    public static final String[] FORUM_NAMES = {"YNET", "StackOverFlow"};
    public static final String[] ID_QUESTIONS = {"what is your favorite color?", "who's your papa?", "what is the name of your goldfish from seventh grade?"};
    public static final String[] ID_ANSWERS = {"orange", "papa", "twiti"};
    public static final String[] SUB_FORUM_NAMES = {"Games", "Jokes", "JAVA", "Interview questions"};
    public static final String[] ADMIN_NAMES = {"admin_of_YNET", "admin_of_StackOverFlow"};
    public static final String[] ADMIN_PASSES = {"ynetrules", "Erjik6ea"};
    public static final String[] ADMIN_EMAILS = {"adminofynet@gmail.com", "adminofstackoverflow@gmail.com"};
    public static final String[] MODERATOR_NAMES = {"mod1GamesYNET", "mod2GamesYNET", "mod1JokesYNET", "mod2JokesYNET", "mod1JAVAstackoverflow", "mod2JAVAstackoverflow", "mod1Interquestackoverflow", "mod2Interquestackoverflow"};
    public static final String[] MODERATOR_PASSES = {"mod1pass", "mod2pass", "mod3pass", "mod4pass","Mod5pass", "Mod6pass", "Mod7pass", "Mod8pass"};
    public static final String[] MODERATOR_EMAILS = {"moderator1ynet@gmail.com", "moderator2ynet@gmail.com","moderator3ynet@gmail.com", "moderator4ynet@gmail.com", "moderator1ofstackoverflow@gmail.com", "moderator2ofstackoverflow@gmail.com", "moderator3ofstackoverflow@gmail.com", "moderator4ofstackoverflow@gmail.com"};
    public static final String[] USER_NAMES = {"user1YNET", "user2YNET", "user1StackOverFlow", "user2StackOverFlow"};
    public static final String[] USER_PASSES = {"usrpass1", "usrpass2", "Usrpass3", "Usrpass4"};
    public static final String[] USER_EMAILS = {"user1ynet@gmail.com", "user2ynet@gmail.com", "user1ofstackoverflow@gmail.com", "user2ofstackoverflow@gmail.com",};


    public static final String[] THREAD_TITLES ={"Sims", "Age of Empires", "knock knock joke", "a good one", "read from file", "logger tests"
            , "google question", "intel question"};
    public static final String[] THREAD_CONTENTS = {"Who wants to play Sims?",
            "Who wants to play Age of Empires?" , "knock knock", "why did the chicken cross the roads?",
            "Does anyone know how to read from a file in java?!?!", "how can i make sure a logger is working in java?",
            "how many windows are there in Israel?", "if you were an animal, what color would you be?"};

//    public static final String[] FRIEND_MESSAGES = {"Hi Friend, Would you like to be my friend?",
//            "Hi Mom, Why won't you approve me?", "Hi John, Please approve me"};

    public static final String[] MESSAGE_TITLES ={"sims is awesome", "age of empieres is not good", "who's there?", "why?", "yes.. read inside", "answer:", "nice", "blue"};

    public static final String[] MESSAGE_CONTENTS = {"Yes I would like to!",
            "No, That game is bad","the cops, the cops who?... mem this isn't a joke, your son died... ha ha! classic", "who are you to question it's motives?!?!",
            "just google it", "just check if it prints", "about 1000000", "blue is a calm color that shows powerful personality and skills, always go for blue"
            };

//    public static final String[] REPORT_TITLES = {"Mehdal!", "Outrageous!", "OMG!"};
//
//    public static final String[] REPORT_CONTENTS = {"Haziz Vara'am!!",
//            "I want to see you behind bars!",
//            "I know what you did last summer"};

    protected static final String superAdminUsername = "SuperAdmin";
    protected static final String superAdminPassword = "Hellcat32";
    protected static final String superAdminMail = "bazinga@gmail.com";
    protected static User superAdmin;

    public static void main(String[]  args) throws Exception{
        User superAdmin = SuperAdminController.initializeForumSystem(superAdminUsername, superAdminPassword, superAdminMail);
        HibernateUtils.cleanUp();
        System.out.println("cleaned up database.");
        superAdmin = SuperAdminController.initializeForumSystem(superAdminUsername, superAdminPassword, superAdminMail);
        System.out.println("superAdmin created:" + superAdminUsername + " password = " + superAdminPassword + " mail = " + superAdminMail);


//        public ForumPolicy(int maxModerators, String passwordRegex, HashFunction hashFunction,
//        boolean doUsersNeedMailVerification, long sessionTimeout, int idleTime,
//        boolean askIdentificationQuestion, long passwordMaxTime, boolean canModeratorEditPosts,
//        int moderatorMinimumNumberOfPosts, long moderatorMinimumSeniority)



        ForumPolicy policyEasy = new ForumPolicy(4, ".+", ForumPolicy.HashFunction.SHA, false, 7 * 24 * 60 * 60 * 1000, 24 * 60 * 60 * 1000, false, -1, true, 0, 0);
        System.out.println("policyEasy created: maxModerators = 4, passwordRegex = .+, hashFunction = SHA, doUsersNeedMailVerification = false,\n " +
                        "sessionTimeout = 7 * 24 * 60 * 60 * 1000, idleTime = 24 * 60 * 60 * 1000, askIdentificationQuestion = false, passwordMaxTime = -1,\n" +
                        "canModeratorEditPosts = true, moderatorMinimumNumberOfPosts = 0, moderatorMinimumSeniority = 0");
//        Description
//        Password matching expression. Password must be at least 4 characters, no more than 8 characters,
//                and must include at least one upper case letter, one lower case letter, and one numeric digit.
//        Matches
//        asD1 | asDF1234 | ASPgo123
//        Non-Matches
//        asdf | 1234 | ASDF12345

        ForumPolicy policyNightmare = new ForumPolicy(2, "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$", ForumPolicy.HashFunction.MD5, false, 7 * 24 * 60 * 60 * 1000, 24 * 60 * 60 * 1000, true, -1, false, 2, 0);
        System.out.println("policyEasy created: maxModerators = 2, passwordRegex = .+, hashFunction = MD5, doUsersNeedMailVerification = true,\n " +
                "sessionTimeout = 7 * 24 * 60 * 60 * 1000, idleTime = 24 * 60 * 60 * 1000, askIdentificationQuestion = true, passwordMaxTime = -1,\n" +
                "canModeratorEditPosts = false, moderatorMinimumNumberOfPosts = 2, moderatorMinimumSeniority = 0");


        // forum creations

        Forum forumEasy = SuperAdminController.createNewForum(superAdmin, policyEasy, FORUM_NAMES[0]);
        System.out.println("forumEasy created: name = " + FORUM_NAMES[0]);

        User admin1 = UserController.register(forumEasy, ADMIN_NAMES[0], ADMIN_PASSES[0], ADMIN_EMAILS[0]);
        System.out.println(ADMIN_NAMES[0] + " registered to " + FORUM_NAMES[0] + ": " + " password = " + ADMIN_PASSES[0] + " email = " + ADMIN_EMAILS[0]);

        //admin1.setState(User.ADMIN);
        forumEasy = (Forum) HibernateUtils.load(Forum.class, forumEasy.id);
        SuperAdminController.changeAdministrator(superAdmin, forumEasy, admin1);
        System.out.println("admin of " + FORUM_NAMES[0] + " was changed to " + ADMIN_NAMES[0]);


        Forum forumNightmare = SuperAdminController.createNewForum(superAdmin, policyNightmare, FORUM_NAMES[1]);
        System.out.println("forumNightmare created: name = " + FORUM_NAMES[1]);

        User admin2 = UserController.register(forumNightmare, ADMIN_NAMES[1], ADMIN_PASSES[1], ADMIN_EMAILS[1], ID_QUESTIONS[0], ID_ANSWERS[0]);
        System.out.println(ADMIN_NAMES[1] + "registered to " + FORUM_NAMES[1] + ": " + " password = " + ADMIN_PASSES[1] + " email = " + ADMIN_EMAILS[1]);

        //admin2.setState(User.ADMIN);
        forumNightmare = (Forum) HibernateUtils.load(Forum.class, forumNightmare.id);
        SuperAdminController.changeAdministrator(superAdmin, forumNightmare, admin2);
        System.out.println("admin of " + FORUM_NAMES[1] + " was changed to " + ADMIN_NAMES[1]);

        // users and moderators creations
        User mod1subEasy1 = UserController.register(forumEasy, MODERATOR_NAMES[0], MODERATOR_PASSES[0], MODERATOR_EMAILS[0]);
        System.out.println(MODERATOR_NAMES[0] + "registered to " + FORUM_NAMES[0] + ": " + " password = " + MODERATOR_PASSES[0] + " email = " + MODERATOR_EMAILS[0]);

        User mod2subEasy1 = UserController.register(forumEasy, MODERATOR_NAMES[1], MODERATOR_PASSES[1], MODERATOR_EMAILS[1]);
        System.out.println(MODERATOR_NAMES[1] + "registered to " + FORUM_NAMES[0] + ": " + " password = " + MODERATOR_PASSES[1] + " email = " + MODERATOR_EMAILS[1]);

        User mod1subEasy2 = UserController.register(forumEasy, MODERATOR_NAMES[2], MODERATOR_PASSES[2], MODERATOR_EMAILS[2]);
        System.out.println(MODERATOR_NAMES[2] + "registered to " + FORUM_NAMES[0] + ": " + " password = " + MODERATOR_PASSES[2] + " email = " + MODERATOR_EMAILS[2]);

        User mod2subEasy2 = UserController.register(forumEasy, MODERATOR_NAMES[3], MODERATOR_PASSES[3], MODERATOR_EMAILS[3]);
        System.out.println(MODERATOR_NAMES[3] + "registered to " + FORUM_NAMES[0] + ": " + " password = " + MODERATOR_PASSES[3] + " email = " + MODERATOR_EMAILS[3]);

        User mod1subNightmare1 = UserController.register(forumNightmare, MODERATOR_NAMES[4], MODERATOR_PASSES[4], MODERATOR_EMAILS[4], ID_QUESTIONS[0], ID_ANSWERS[1]);
        System.out.println(MODERATOR_NAMES[4] + "registered to " + FORUM_NAMES[1] + ": " + " password = " + MODERATOR_PASSES[4] + " email = " + MODERATOR_EMAILS[4]);

        User mod2subNightmare1 = UserController.register(forumNightmare, MODERATOR_NAMES[5], MODERATOR_PASSES[5], MODERATOR_EMAILS[5], ID_QUESTIONS[1], ID_QUESTIONS[1]);
        System.out.println(MODERATOR_NAMES[5] + "registered to " + FORUM_NAMES[1] + ": " + " password = " + MODERATOR_PASSES[5] + " email = " + MODERATOR_EMAILS[5]);

        User mod1subNightmare2 = UserController.register(forumNightmare, MODERATOR_NAMES[6], MODERATOR_PASSES[6], MODERATOR_EMAILS[6], ID_QUESTIONS[2], ID_QUESTIONS[2]);
        System.out.println(MODERATOR_NAMES[6] + "registered to " + FORUM_NAMES[1] + ": " + " password = " + MODERATOR_PASSES[6] + " email = " + MODERATOR_EMAILS[6]);

        User mod2subNightmare2 = UserController.register(forumNightmare, MODERATOR_NAMES[7], MODERATOR_PASSES[7], MODERATOR_EMAILS[7], ID_QUESTIONS[0], ID_QUESTIONS[0]);
        System.out.println(MODERATOR_NAMES[7] + "registered to " + FORUM_NAMES[1] + ": " + " password = " + MODERATOR_PASSES[7] + " email = " + MODERATOR_EMAILS[7]);

        User user1EasyForum = UserController.register(forumEasy, USER_NAMES[0],USER_PASSES[0], USER_EMAILS[0]);
        System.out.println(USER_NAMES[0] + "registered to " + FORUM_NAMES[0] + ": " + " password = " + USER_PASSES[0] + " email = " + USER_EMAILS[0]);

        User user2EasyForum = UserController.register(forumEasy, USER_NAMES[1],USER_PASSES[1], USER_EMAILS[1]);
        System.out.println(USER_NAMES[1] + "registered to " + FORUM_NAMES[0] + ": " + " password = " + USER_PASSES[1] + " email = " + USER_EMAILS[1]);

        User user1NightmareForum =UserController.register(forumNightmare, USER_NAMES[2],USER_PASSES[2], USER_EMAILS[2], ID_QUESTIONS[0], ID_QUESTIONS[0]);
        System.out.println(USER_NAMES[2] + "registered to " + FORUM_NAMES[1] + ": " + " password = " + USER_PASSES[2] + " email = " + USER_EMAILS[2]);

        User user2NightmareForum =UserController.register(forumNightmare, USER_NAMES[3],USER_PASSES[3], USER_EMAILS[3], ID_QUESTIONS[1], ID_QUESTIONS[1]);
        System.out.println(USER_NAMES[3] + "registered to " + FORUM_NAMES[1] + ": " + " password = " + USER_PASSES[3] + " email = " + USER_EMAILS[3]);


        // sub forums creations

        SubForum subEasy1 = AdminController.addSubForum(forumEasy, SUB_FORUM_NAMES[0], admin1);
        System.out.println("subforumEasy1 created: name = " + SUB_FORUM_NAMES[0]);


        SubForum subEasy2 = AdminController.addSubForum(forumEasy, SUB_FORUM_NAMES[1], admin1);
        System.out.println("subforumEasy2 created: name = " + SUB_FORUM_NAMES[1]);


        SubForum subNightmare1 = AdminController.addSubForum(forumNightmare, SUB_FORUM_NAMES[2], admin2);
        System.out.println("subNightmare1 created: name = " + SUB_FORUM_NAMES[2]);


        SubForum subNightmare2 = AdminController.addSubForum(forumNightmare, SUB_FORUM_NAMES[3], admin2);
        System.out.println("subNightmare2 created: name = " + SUB_FORUM_NAMES[3]);

        // threads creation

        content.Thread t1 =  UserController.openNewThread(forumEasy, subEasy1, THREAD_TITLES[0], THREAD_CONTENTS[0], mod1subEasy1);
        Message m1 = t1.getOpeningMessage();

        content.Thread t2 = UserController.openNewThread(forumEasy, subEasy1, THREAD_TITLES[1], THREAD_CONTENTS[1], mod2subEasy1);
        Message m2 = t2.getOpeningMessage();

        content.Thread t3 = UserController.openNewThread(forumEasy, subEasy2, THREAD_TITLES[2], THREAD_CONTENTS[2], mod1subEasy2);
        Message m3 = t3.getOpeningMessage();

        content.Thread t4 = UserController.openNewThread(forumEasy, subEasy2, THREAD_TITLES[3], THREAD_CONTENTS[3], mod2subEasy2);
        Message m4 = t4.getOpeningMessage();

        content.Thread t5 = UserController.openNewThread(forumNightmare, subNightmare1, THREAD_TITLES[4], THREAD_CONTENTS[4], mod1subNightmare1);
        Message m5 = t5.getOpeningMessage();

        content.Thread t6 = UserController.openNewThread(forumNightmare, subNightmare1, THREAD_TITLES[5], THREAD_CONTENTS[5], mod2subNightmare1);
        Message m6 = t6.getOpeningMessage();

        content.Thread t7 = UserController.openNewThread(forumNightmare, subNightmare2, THREAD_TITLES[6], THREAD_CONTENTS[6], mod1subNightmare2);
        Message m7 = t7.getOpeningMessage();

        content.Thread t8 = UserController.openNewThread(forumNightmare, subNightmare2, THREAD_TITLES[7], THREAD_CONTENTS[7], mod2subNightmare2);
        Message m8 = t8.getOpeningMessage();

        System.out.println("thread created");

        // replays creation

        UserController.reply(forumEasy, m1, MESSAGE_TITLES[0], MESSAGE_CONTENTS[0], user1EasyForum);
        UserController.reply(forumEasy, m2, MESSAGE_TITLES[1], MESSAGE_CONTENTS[1], user2EasyForum);
        UserController.reply(forumEasy, m3, MESSAGE_TITLES[2], MESSAGE_CONTENTS[2], user1EasyForum);
        UserController.reply(forumEasy, m4, MESSAGE_TITLES[3], MESSAGE_CONTENTS[3], user2EasyForum);

        UserController.reply(forumNightmare, m5, MESSAGE_TITLES[4], MESSAGE_CONTENTS[4], mod2subNightmare1);
        UserController.reply(forumNightmare, m6, MESSAGE_TITLES[5], MESSAGE_CONTENTS[5], mod1subNightmare1);
        UserController.reply(forumNightmare, m7, MESSAGE_TITLES[6], MESSAGE_CONTENTS[6], mod2subNightmare2);
        UserController.reply(forumNightmare, m8, MESSAGE_TITLES[7], MESSAGE_CONTENTS[7], mod1subNightmare2);

        // moderators replacment and appointment

        subEasy1 = (SubForum) HibernateUtils.load(SubForum.class, subEasy1.id);
        AdminController.replaceModerator(forumEasy, subEasy1, admin1, admin1, mod1subEasy1);
        System.out.println("moderators of " + SUB_FORUM_NAMES[0] + " was changed to " + MODERATOR_NAMES[0]);

        subEasy2 = (SubForum) HibernateUtils.load(SubForum.class, subEasy2.id);
        AdminController.replaceModerator(forumEasy, subEasy2, admin1, admin1, mod1subEasy2);
        System.out.println("moderators of " + SUB_FORUM_NAMES[1] + " was changed to " + MODERATOR_NAMES[2]);


        subNightmare1 = (SubForum) HibernateUtils.load(SubForum.class, subNightmare1.id);
        AdminController.replaceModerator(forumNightmare, subNightmare1, admin2, admin2, mod1subNightmare1);
        System.out.println("moderators of " + SUB_FORUM_NAMES[2] + " was changed to " + MODERATOR_NAMES[4]);

        subNightmare2 = (SubForum) HibernateUtils.load(SubForum.class, subNightmare2.id);
        AdminController.replaceModerator(forumNightmare, subNightmare2, admin2, admin2, mod1subNightmare2);
        System.out.println("moderators of " + SUB_FORUM_NAMES[3] + " was changed to " + MODERATOR_NAMES[6]);

        subEasy1 = (SubForum) HibernateUtils.load(SubForum.class, subEasy1.id);
        AdminController.appointModerator(forumEasy, subEasy1, admin1, mod2subEasy1);
        System.out.println("moderators of " + SUB_FORUM_NAMES[0] + " has grown with " + MODERATOR_NAMES[1]);

        subEasy2 = (SubForum) HibernateUtils.load(SubForum.class, subEasy2.id);
        AdminController.appointModerator(forumEasy, subEasy2, admin1, mod2subEasy2);
        System.out.println("moderators of " + SUB_FORUM_NAMES[1] + " has grown with " + MODERATOR_NAMES[3]);

        subNightmare1 = (SubForum) HibernateUtils.load(SubForum.class, subNightmare1.id);
        AdminController.appointModerator(forumNightmare, subNightmare1, admin2, mod2subNightmare1);
        System.out.println("moderators of " + SUB_FORUM_NAMES[2] + " has grown with " + MODERATOR_NAMES[5]);

        subNightmare2 = (SubForum) HibernateUtils.load(SubForum.class, subNightmare2.id);
        AdminController.appointModerator(forumNightmare, subNightmare2, admin2, mod2subNightmare2);
        System.out.println("moderators of " + SUB_FORUM_NAMES[3] + " has grown with " + MODERATOR_NAMES[7]);
    }
}
