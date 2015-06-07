package controllers;

import content.Forum;
import content.ForumSystem;
import exceptions.UserNotAuthorizedException;
import exceptions.UsernameAlreadyExistsException;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import policy.ForumPolicy;
import policy.UserStatusPolicy;
import users.User;

import java.security.NoSuchAlgorithmException;

public class SuperAdminControllerTests {

    private static String superUsername = "superUsername";
    private static String username = "username";
    private static String hashedPassword = "hashedPassword";
    private static String mail = "mail";
    private static String forumName = "forumName";
    private static String subForumName = "subForumName";

    private static ForumSystem forumSystem;
    private ForumPolicy policy;
    private Forum forum;
    private User superAdmin;

//    @BeforeClass
//    public static void setup() throws NoSuchAlgorithmException {
//        forumSystem = SuperAdminController.initializeForumSystem(superUsername, hashedPassword, mail);
//    }

    @AfterClass
    public static void afterClass() {
        forumSystem.destroy();
    }

    @Before
    public void beforeMethod() throws UserNotAuthorizedException, UsernameAlreadyExistsException, NoSuchAlgorithmException {
        superAdmin = forumSystem.getSuperAdmin(superUsername, hashedPassword);
        policy = new ForumPolicy(2, "", ForumPolicy.HashFunction.SHA, false);
    }

    @Test
    public void createNewForumTest() throws UserNotAuthorizedException {
        forum = SuperAdminController.createNewForum(superAdmin, policy, forumName);
        Assert.assertTrue(forumSystem.hasForum(forum));
    }

    @Test
    public void changeAdministratorTest() throws UserNotAuthorizedException, NoSuchAlgorithmException, UsernameAlreadyExistsException {
        forum = SuperAdminController.createNewForum(superAdmin, policy, forumName);
        User user = UserController.register(forum, username, hashedPassword, mail);
        SuperAdminController.changeAdministrator(superAdmin, forum, user);
        Assert.assertEquals(user, forum.getAdmin());
    }

    @Test
    public void changeForumPolicyTest() {
        // TODO
    }

    @Test
    public void addUserStatusTest() {
        SuperAdminController.addUserStatusType(superAdmin, "type", new UserStatusPolicy(1, 0, 100));
        Assert.assertTrue(forumSystem.hasUserStateType("type"));
    }

    @Test
    public void removeUserStatusTest() {
        SuperAdminController.addUserStatusType(superAdmin, "type", new UserStatusPolicy(1, 0 , 100));
        SuperAdminController.removeUserStatusType(superAdmin, "type");
        Assert.assertFalse(forumSystem.hasUserStateType("type"));
    }
}
