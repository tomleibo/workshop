package content;

import junit.framework.Assert;
import org.junit.Test;
import users.User;

/**
 * Created by Yuval on 6/24/2015.
 */
public class ForumTests {

    @Test
    public void statusManipulationTest() {
        User user = new User(0);
        Forum forum = new Forum(user, null, "name");
        Assert.assertTrue(forum.hasStatusType("Gold"));
        Assert.assertTrue(forum.removeStatusType("Gold"));
        Assert.assertFalse(forum.hasStatusType("Gold"));
        Assert.assertTrue(forum.hasStatusType("Silver"));
        Assert.assertTrue(forum.removeStatusType("Silver"));
        Assert.assertFalse(forum.hasStatusType("Silver"));
        Assert.assertTrue(forum.hasStatusType("Regular"));
        Assert.assertTrue(forum.removeStatusType("Regular"));
        Assert.assertFalse(forum.hasStatusType("Regular"));
        Assert.assertFalse(forum.removeStatusType("Check"));
        Assert.assertTrue(forum.addStatusType("Gold", 20));
        Assert.assertTrue(forum.hasStatusType("Gold"));
    }
}
