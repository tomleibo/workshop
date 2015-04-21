package  controllers;
import content.Forum;
import exceptions.UsernameAlreadyExistsException;
import org.junit.Assert;
import org.junit.Test;
import policy.ForumPolicy;
import users.User;


public class controllerTest {
    UserController userController;

    @Test
    public void test_register() {
        try {
            userController = new UserController();
            User admin = new User(null, "hadar", "1234", "polad.hadar@gmail.com");
            Forum forum  = new Forum(admin, new ForumPolicy(2));
            User user = userController.register(forum, "lior malovani", "1234", "polad.hadar@gmail.com");
            Assert.assertTrue(user != null);
        } catch (UsernameAlreadyExistsException e) {
            e.printStackTrace();
        }
    }
}