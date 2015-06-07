package controllers;

import content.Forum;
import content.ForumSystem;
import content.SubForum;
import exceptions.UserNotAuthorizedException;
import exceptions.UsernameAlreadyExistsException;
import junit.framework.Assert;
import org.junit.*;
import policy.ForumPolicy;
import users.User;

import java.security.NoSuchAlgorithmException;

public class ModerationControllerTests {

    private static String superUsername = "superUsername";
    private static String moderatorUsername = "moderatorUsername";
    private static String username = "username";
    private static String hashedPassword = "hashedPassword";
    private static String mail = "mail";
    private static String forumName = "forumName";
    private static String subForumName = "subForumName";

    private static ForumSystem forumSystem;
    private ForumPolicy policy;
    private Forum forum;
    private SubForum subForum;
    private User superAdmin;
    private User moderator;
    private User member;

//    @BeforeClass
//    public static void setup() throws NoSuchAlgorithmException {
//        forumSystem = SuperAdminController.initializeForumSystem(superUsername, hashedPassword, mail);
//    }

    @Before
    public void beforeMethod() throws UserNotAuthorizedException, UsernameAlreadyExistsException, NoSuchAlgorithmException {
        superAdmin = forumSystem.getSuperAdmin(superUsername, hashedPassword);
        policy = new ForumPolicy(2, "", ForumPolicy.HashFunction.SHA, false);
        forum = SuperAdminController.createNewForum(superAdmin, policy, forumName);
        moderator = UserController.register(forum, moderatorUsername, hashedPassword, mail);
        subForum = AdminController.addSubForum(forum, subForumName, superAdmin);
        AdminController.appointModerator(forum, subForum, superAdmin, moderator);
        member = UserController.register(forum, username, hashedPassword, mail);
    }

    @After
    public void afterMethod() throws UserNotAuthorizedException {
        SuperAdminController.deleteForum(superAdmin, forum);
    }

    @AfterClass
    public static void afterClass() {
        forumSystem.destroy();
    }

    @Test
    public void banUserTest() throws UserNotAuthorizedException {
        ModerationController.banUser(subForum, moderator, member);
        Assert.assertTrue(member.isBanned());
    }

}
