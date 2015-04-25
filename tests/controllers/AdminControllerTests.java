package controllers;

import content.Forum;
import content.ForumSystem;
import content.SubForum;
import exceptions.UserNotAuthorizedException;
import exceptions.UsernameAlreadyExistsException;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import policy.ForumPolicy;
import users.User;

import java.security.NoSuchAlgorithmException;

public class AdminControllerTests {

    private static String superUsername = "superUsername";
    private static String firstModeratorUsername = "firstModeratorUsername";
    private static String secondModeratorUsername = "secondModeratorUsername";
    private static String hashedPassword = "hashedPassword";
    private static String mail = "mail";
    private static String forumName = "forumName";
    private static String subForumName = "subForumName";

    private static ForumSystem forumSystem;
    private ForumPolicy policy;
    private Forum forum;
    private SubForum subForum;
    private User superAdmin;
    private User firstModerator;
    private User secondModerator;

    @BeforeClass
    public static void setup() throws NoSuchAlgorithmException {
        forumSystem = SuperAdminController.initializeForumSystem(superUsername, hashedPassword, mail);
    }

    @AfterClass
    public static void afterClass() {
        forumSystem.destroy();
    }

    @Before
    public void beforeMethod() throws UserNotAuthorizedException, UsernameAlreadyExistsException, NoSuchAlgorithmException {
        superAdmin = forumSystem.getSuperAdmin(superUsername, hashedPassword);
        policy = new ForumPolicy(2, "", ForumPolicy.HashFunction.SHA, false);
        forum = SuperAdminController.createNewForum(superAdmin, policy, forumName);
        firstModerator = UserController.register(forum, firstModeratorUsername, hashedPassword, mail);
        secondModerator = UserController.register(forum, secondModeratorUsername, hashedPassword, mail);
    }

    @Test
    public void appointModeratorTest() throws UserNotAuthorizedException {
        subForum = AdminController.addSubForum(forum, subForumName, superAdmin);
        AdminController.appointModerator(forum, subForum, superAdmin, firstModerator);
        Assert.assertTrue(subForum.getModerators().contains(firstModerator));
        Assert.assertTrue(firstModerator.getState().isModerator());
    }

    @Test
    public void banModeratorTest() throws UserNotAuthorizedException {
        // TODO - When use case will be implemented.
    }

    @Test
    public void unAppointTest() throws UserNotAuthorizedException {
        subForum = AdminController.addSubForum(forum, subForumName, superAdmin);
        AdminController.appointModerator(forum, subForum, superAdmin, firstModerator);
        AdminController.unAppoint(forum, subForum, superAdmin, firstModerator);
        Assert.assertFalse(subForum.getModerators().contains(firstModerator));
        Assert.assertFalse(firstModerator.getState().isModerator());
    }

    @Test
    public void replaceModeratorTest() throws UserNotAuthorizedException {
        subForum = AdminController.addSubForum(forum, subForumName, superAdmin);
        AdminController.appointModerator(forum, subForum, superAdmin, firstModerator);
        AdminController.replaceModerator(forum, subForum, superAdmin, firstModerator, secondModerator);
        Assert.assertFalse(subForum.getModerators().contains(firstModerator));
        Assert.assertFalse(firstModerator.getState().isModerator());
        Assert.assertTrue(subForum.getModerators().contains(secondModerator));
        Assert.assertTrue(secondModerator.getState().isModerator());
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
