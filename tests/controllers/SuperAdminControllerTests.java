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
    private static String hashedPassword = "hashedPassword";
    private static String mail = "mail";
    private static String forumName = "forumName";
    private static String subForumName = "subForumName";

    private ForumPolicy policy;
    private Forum forum;
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
