package controllers;
import content.Forum;
import content.SubForum;
import exceptions.UserNotAuthorizedException;
import exceptions.UsernameAlreadyExistsException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import policy.ForumPolicy;
import users.FriendRequest;
import users.User;


public class UserControllerTests {

    private static String superUsername = "superUsername";
    private static String hashedPassword = "hashedPassword";
    private static String mail = "mail";
    private static String forumName = "forumName";
    private static String subForumName = "subForumName";

    private ForumPolicy policy;
    private Forum forum;
    private SubForum subForum;
    private User superAdmin;

    @Before
    public void beforeMethod() throws Exception {
        superAdmin = SuperAdminController.initializeForumSystem(superUsername, hashedPassword, mail);
        policy = new ForumPolicy(2, ".+", ForumPolicy.HashFunction.SHA, false);
        forum = SuperAdminController.createNewForum(superAdmin, policy, forumName);
    }

    @Test
    public void test_register() throws Exception {
        try {
            User admin = User.newMember("hadar", "1234", "polad.hadar@gmail.com");
            Forum forum  = new Forum(admin, new ForumPolicy(2,"1234",ForumPolicy.HashFunction.MD5), "name");
            User user = UserController.register(forum, "Hadar Polad", "1234", "polad.hadar@gmail.com");
            Assert.assertTrue(user != null);
        } catch (UsernameAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_enterAsGuest() {
        User admin = User.newMember("hadar", "1234", "polad.hadar@gmail.com");
        Forum forum  = new Forum(admin, new ForumPolicy(2,"1234",ForumPolicy.HashFunction.MD5), "name");
        User user = UserController.enterAsGuest(forum);
        Assert.assertTrue(user.getState() == User.GUEST);
    }

    @Test
    public void test_sendFriendRequest() throws UserNotAuthorizedException {
        User admin = User.newMember("Lior", "1234", "lior@gmail.com");
        User user1 = User.newMember("hadar", "1234", "polad.hadar@gmail.com");
        User user2 = User.newMember("Yuval", "1234", "yuvalapidot@gmail.com");
        Forum forum  = new Forum(admin, new ForumPolicy(2,"1234",ForumPolicy.HashFunction.MD5), "name");
        forum.addMember(user1);
        forum.addMember(user2);
        Assert.assertTrue(UserController.sendFriendRequest(forum, user1, user2, "hi") != null);
    }


    @Test
    public void test_replyToFriendRequest() throws UserNotAuthorizedException {
        User admin = User.newMember("Lior", "1234", "lior@gmail.com");
        User user1 = User.newMember("hadar", "1234", "polad.hadar@gmail.com");
        User user2 = User.newMember("Yuval", "1234", "yuvalapidot@gmail.com");
        Forum forum  = new Forum(admin, new ForumPolicy(2,"1234",ForumPolicy.HashFunction.MD5), "name");
        forum.addMember(user1);
        forum.addMember(user2);
        FriendRequest fr = new FriendRequest(user1,user2,"hi");
        UserController.replyToFriendRequest(forum, user1, fr, true);
        Assert.assertTrue(user1.getFriends().contains(user2) && user2.getFriends().contains(user1));
    }


    @Test
    public void test_removeFriend() throws UserNotAuthorizedException {
        User admin = User.newMember("Lior", "1234", "lior@gmail.com");
        User user1 = User.newMember("hadar", "1234", "polad.hadar@gmail.com");
        User user2 = User.newMember("Yuval", "1234", "yuvalapidot@gmail.com");
        Forum forum  = new Forum(admin, new ForumPolicy(2,"1234",ForumPolicy.HashFunction.MD5), "name");
        forum.addMember(user1);
        forum.addMember(user2);
        user1.addFriend(user2);
        user2.addFriend(user1);
        UserController.removeFriend(forum, user1, user2);
        Assert.assertFalse(user1.getFriends().contains(user2) && user2.getFriends().contains(user1));
    }




}