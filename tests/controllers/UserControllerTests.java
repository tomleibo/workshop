package controllers;
import content.Forum;
import exceptions.UsernameAlreadyExistsException;
import org.junit.Assert;
import org.junit.Test;
import policy.ForumPolicy;
import users.User;

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
}