package controllers;

import content.Forum;
import content.SubForum;
import exceptions.UserNotAuthorizedException;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import policy.ForumPolicy;
import users.User;

public class ModerationControllerTests {

    private static String superUsername = "superUsername";
    private static String moderatorUsername = "moderatorUsername";
    private static String username = "username";
    private static String hashedPassword = "hashedPassword";
    private static String mail = "mail";
    private static String forumName = "forumName";
    private static String subForumName = "subForumName";

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
    public void beforeMethod() throws Exception {
        superAdmin = SuperAdminController.startForumSystem(superUsername, hashedPassword, mail);
        policy = new ForumPolicy(2, ".+", ForumPolicy.HashFunction.SHA, false);
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

    @Test
    public void banUserTest() throws UserNotAuthorizedException {
        ModerationController.banUser(subForum, moderator, member);
        Assert.assertTrue(member.isBanned());
    }

}
