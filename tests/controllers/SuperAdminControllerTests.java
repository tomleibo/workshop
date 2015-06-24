package controllers;

import content.Forum;
import exceptions.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import policy.ForumPolicy;
import users.User;
import utils.HibernateUtils;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class SuperAdminControllerTests {

    private static String superUsername = "superUsername";
    private static String username = "username";
    private static String username2 = "username2";
    private static String username3 = "username3";
    private static String hashedPassword = "hashedPassword";
    private static String mail = "mail";
    private static String forumName = "forumName";
    private static String forumName2 = "forumName2";
    private static String subForumName = "subForumName";

    private ForumPolicy policy;
    private Forum forum;
    private Forum forum2;
    private User superAdmin;


    @Before
    public void beforeMethod() throws UserNotAuthorizedException, UsernameAlreadyExistsException, NoSuchAlgorithmException {
        superAdmin = SuperAdminController.startForumSystem(superUsername, hashedPassword, mail);
        policy = new ForumPolicy(2, ".+", ForumPolicy.HashFunction.SHA, false);
    }

    @Test
    public void createNewForumTest() throws UserNotAuthorizedException {
        forum = SuperAdminController.createNewForum(superAdmin, policy, forumName);
        List<Forum> forums = HibernateUtils.getAllForums();
        boolean found = false;
        for (Forum forum : forums) {
            if (forum.getName().equals(forumName)) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found);
    }

    @Test
    public void changeAdministratorTest() throws Exception {
        forum = SuperAdminController.createNewForum(superAdmin, policy, forumName);
        User user = UserController.register(forum, username, hashedPassword, mail);
        SuperAdminController.changeAdministrator(superAdmin, forum, user);
        Assert.assertEquals(user, forum.getAdmin());
    }

//    @Test
//    public void changeAdministratorPersistenceTest() throws Exception {
//        forum2 = SuperAdminController.createNewForum(superAdmin, policy, forumName2);
//        Forum forum2Loaded = (Forum) HibernateUtils.load(Forum.class, forum2.id);
//        User user1 = UserController.register(forum2Loaded, username2, hashedPassword, mail);
//        User user2 = UserController.register(forum2Loaded, username3, hashedPassword, mail);
//        User user1Loaded = (User) HibernateUtils.load(User.class, user1.getId());
//        User user2Loaded = (User) HibernateUtils.load(User.class, user2.getId());
//        SuperAdminController.changeAdministrator(superAdmin, forum2, user1Loaded);
//        forum2Loaded = (Forum) HibernateUtils.load(Forum.class, forum2Loaded.id);
//        user1Loaded = (User) HibernateUtils.load(User.class, user1Loaded.getId());
//        Assert.assertTrue(user1Loaded.isAdmin());
//        Assert.assertEquals(user1Loaded, forum2Loaded.getAdmin());
//        SuperAdminController.changeAdministrator(superAdmin, forum2, user2Loaded);
//        forum2Loaded = (Forum) HibernateUtils.load(Forum.class, forum2Loaded.id);
//        user1Loaded = (User) HibernateUtils.load(User.class, user1Loaded.getId());
//        user2Loaded = (User) HibernateUtils.load(User.class, user2Loaded.getId());
//        Assert.assertTrue(user2Loaded.isAdmin());
//        Assert.assertFalse(user1Loaded.isAdmin());
//        Assert.assertEquals(user2Loaded, forum2Loaded.getAdmin());
//    }

//    @Test
//    public void loinAdminTest() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException {
//        User loggedInSuperAdmin = SuperAdminController.loginSuperAdmin(superUsername, hashedPassword);
//        Assert.assertTrue(loggedInSuperAdmin.isSuperAdmin());
//        Assert.assertTrue(loggedInSuperAdmin.isLoggedIn());
//    }

//    @Test
//    public void changeForumPolicyTest() {
//        // TODO
//    }

//    @Test
//    public void addUserStatusTest() {
//        SuperAdminController.addUserStatusType(superAdmin, "type", new UserStatusPolicy(1, 0, 100));
//        Assert.assertTrue(forumSystem.hasUserStateType("type"));
//    }

//    @Test
//    public void removeUserStatusTest() {
//        SuperAdminController.addUserStatusType(superAdmin, "type", new UserStatusPolicy(1, 0 , 100));
//        SuperAdminController.removeUserStatusType(superAdmin, "type");
//        Assert.assertFalse(forumSystem.hasUserStateType("type"));
//    }
}
