package controllers;

import content.Forum;
import content.SubForum;
import exceptions.UserNotAuthorizedException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import policy.ForumPolicy;
import users.User;

public class AdminControllerTests {

    private static String superUsername = "superUsername";
    private static String firstModeratorUsername = "firstModeratorUsername";
    private static String secondModeratorUsername = "secondModeratorUsername";
    private static String hashedPassword = "hashedPassword";
    private static String mail = "mail";
    private static String forumName = "forumName";
    private static String subForumName = "subForumName";

    private ForumPolicy policy;
    private Forum forum;
    private SubForum subForum;
    private User superAdmin;
    private User firstModerator;
    private User secondModerator;

//    @BeforeClass
//    public static void setup() throws NoSuchAlgorithmException {
//        forumSystem = SuperAdminController.initializeForumSystem(superUsername, hashedPassword, mail);
//    }

    @Before
    public void beforeMethod() throws Exception {
        superAdmin = SuperAdminController.startForumSystem(superUsername, hashedPassword, mail);
        policy = new ForumPolicy(2, ".+", ForumPolicy.HashFunction.SHA, false);
        forum = SuperAdminController.createNewForum(superAdmin, policy, forumName);
        firstModerator = UserController.register(forum, firstModeratorUsername, hashedPassword, mail);
        secondModerator = UserController.register(forum, secondModeratorUsername, hashedPassword, mail);
    }

    @Test
    public void appointModeratorTest() throws Exception {
        subForum = AdminController.addSubForum(forum, subForumName, superAdmin);
        AdminController.appointModerator(forum, subForum, superAdmin, firstModerator);
        Assert.assertTrue(subForum.getModerators().contains(firstModerator));
        Assert.assertTrue(firstModerator.isMod());
    }

    @Test
    public void banModeratorTest() throws UserNotAuthorizedException {
        // TODO - When use case will be implemented.
    }

    @Test
    public void unAppointTest() throws Exception {
        subForum = AdminController.addSubForum(forum, subForumName, superAdmin);
        AdminController.appointModerator(forum, subForum, superAdmin, firstModerator);
        AdminController.unAppoint(forum, subForum, superAdmin, firstModerator);
        Assert.assertFalse(subForum.getModerators().contains(firstModerator));
        Assert.assertFalse(firstModerator.isMod());
    }

    @Test
    public void replaceModeratorTest() throws Exception {
        subForum = AdminController.addSubForum(forum, subForumName, superAdmin);
        AdminController.appointModerator(forum, subForum, superAdmin, firstModerator);
        AdminController.replaceModerator(forum, subForum, superAdmin, firstModerator, secondModerator);
        Assert.assertFalse(subForum.getModerators().contains(firstModerator));
        Assert.assertFalse(firstModerator.isMod());
        Assert.assertTrue(subForum.getModerators().contains(secondModerator));
        Assert.assertTrue(secondModerator.isMod());
    }

    @Test
    public void addSubForumTest() throws UserNotAuthorizedException {
        subForum = AdminController.addSubForum(forum, subForumName, superAdmin);
        Assert.assertTrue(forum.hasSubForum(subForum));
    }

    @Test
    public void removeSubForumTest() throws UserNotAuthorizedException {
        subForum = AdminController.addSubForum(forum, subForumName, superAdmin);
        AdminController.deleteSubForum(forum, subForum, superAdmin);
        Assert.assertFalse(forum.hasSubForum(subForum));
    }
}
