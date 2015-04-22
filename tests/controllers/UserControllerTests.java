package controllers;
import content.Forum;
import exceptions.UsernameAlreadyExistsException;
import org.junit.Assert;
import org.junit.Test;
import policy.ForumPolicy;
import users.FriendRequest;
import users.User;
import users.userState.GuestState;

import java.security.NoSuchAlgorithmException;


public class UserControllerTests {
    UserController userController;

    @Test
    public void test_register() throws NoSuchAlgorithmException {
        try {
            userController = new UserController();
            User admin = new User("hadar", "1234", "polad.hadar@gmail.com");
            Forum forum  = new Forum(admin, new ForumPolicy(2,"1234",ForumPolicy.HashFunction.MD5), "name");
            User user = userController.register(forum, "Hadar Polad", "1234", "polad.hadar@gmail.com");
            Assert.assertTrue(user != null);
        } catch (UsernameAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_enterAsGuest() {
        userController = new UserController();
        User admin = new User("hadar", "1234", "polad.hadar@gmail.com");
        Forum forum  = new Forum(admin, new ForumPolicy(2,"1234",ForumPolicy.HashFunction.MD5), "name");
        User user = userController.enterAsGuest(forum);
        Assert.assertTrue(user.getState() instanceof GuestState);
    }

    @Test
    public void test_sendFriendRequest() {
        userController = new UserController();
        User admin = new User("Lior", "1234", "lior@gmail.com");
        User user1 = new User("hadar", "1234", "polad.hadar@gmail.com");
        User user2 = new User("Yuval", "1234", "yuvalapidot@gmail.com");
        Forum forum  = new Forum(admin, new ForumPolicy(2,"1234",ForumPolicy.HashFunction.MD5), "name");
        forum.addMember(user1);
        forum.addMember(user2);
        Assert.assertTrue(userController.sendFriendRequest(forum, user1, user2, "hi"));
    }


    @Test
    public void test_replyToFriendRequest() {
        userController = new UserController();
        User admin = new User("Lior", "1234", "lior@gmail.com");
        User user1 = new User("hadar", "1234", "polad.hadar@gmail.com");
        User user2 = new User("Yuval", "1234", "yuvalapidot@gmail.com");
        Forum forum  = new Forum(admin, new ForumPolicy(2,"1234",ForumPolicy.HashFunction.MD5), "name");
        forum.addMember(user1);
        forum.addMember(user2);
        FriendRequest fr = new FriendRequest(user1,user2,"hi");
        userController.replyToFriendRequest(fr, true);
        Assert.assertTrue(user1.getFriends().contains(user2) && user2.getFriends().contains(user1));
    }


    @Test
    public void test_removeFriend() {
        userController = new UserController();
        User user1 = new User("hadar", "1234", "polad.hadar@gmail.com");
        User user2 = new User("Yuval", "1234", "yuvalapidot@gmail.com");
        user1.addFriend(user2);
        user2.addFriend(user1);
        userController.removeFriend(user1, user2);
        Assert.assertFalse(user1.getFriends().contains(user2) && user2.getFriends().contains(user1));
    }




}