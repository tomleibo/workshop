package users;

import content.SubForum;
import exceptions.UserAlreadyLoggedInException;
import exceptions.UserNotLoggedInException;
import exceptions.WrongPasswordException;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Yuval on 4/23/2015.
 */
public class UserTests {

    private String username = "username";
    private String adminName = "admin";
    private String hashedPassword = "hashedPassword";
    private String wrongHashedPassword = "wrongHashedPassword";
    private String emailAddress = "emailAddress";

    private String subForumName = "subForumName";

    @Test
    public void newGuestTest() {
        User guest = User.newGuest();
        Assert.assertTrue(guest.getState().isGuest());
    }

    @Test
    public void newMemberTest() {
        User member = User.newMember(username, hashedPassword, emailAddress);
        Assert.assertTrue(member.getState().isMember());
    }

    @Test
    public void newSuperAdmin() {
        User superAdmin = User.newSuperAdmin(username, hashedPassword, emailAddress);
        Assert.assertTrue(superAdmin.getState().isSuperAdmin());
    }

    @Test
    public void loginTest() throws UserAlreadyLoggedInException, WrongPasswordException {
        User member = User.newMember(username, hashedPassword, emailAddress);
        User loggedInMember = member.login(hashedPassword);
        Assert.assertEquals(member, loggedInMember);
        Assert.assertTrue(loggedInMember.isLoggedIn());
    }

    @Test
    public void loginWrongPasswordTest() throws UserAlreadyLoggedInException {
        User member = User.newMember(username, hashedPassword, emailAddress);
        try {
            User loggedInMember = member.login(wrongHashedPassword);
            Assert.fail();
        } catch (WrongPasswordException e) {
            Assert.assertFalse(member.isLoggedIn());
        }
    }

    @Test
    public void loginUserAlreadyLoggedInTest() throws UserAlreadyLoggedInException, WrongPasswordException {
        User member = User.newMember(username, hashedPassword, emailAddress);
        User firstLoggedInMember = member.login(hashedPassword);
        try {
            User secondLoggedInMember = member.login(hashedPassword);
            Assert.fail();
        } catch (UserAlreadyLoggedInException e) {
            Assert.assertEquals(e.getUser(), member);
            Assert.assertTrue(e.getUser().isLoggedIn());
        }
    }

    @Test
    public void loginAsGuestTest() throws UserAlreadyLoggedInException, WrongPasswordException {
        User guest = User.newGuest();
        User loggedInGuest = guest.loginAsGuest();
        Assert.assertEquals(guest, loggedInGuest);
        Assert.assertTrue(guest.isLoggedIn());
    }

    @Test
    public void logOutTest() throws UserAlreadyLoggedInException, WrongPasswordException, UserNotLoggedInException {
        User member = User.newMember(username, hashedPassword, emailAddress);
        User loggedInMember = member.login(hashedPassword);
        User guest = loggedInMember.logout();
        Assert.assertTrue(guest.getState().isGuest());
        Assert.assertFalse(member.isLoggedIn());
    }

    @Test
    public void logOutNotLoggedInTest() {
        User member = User.newMember(username, hashedPassword, emailAddress);
        try {
            User guest = member.logout();
            Assert.fail();
        } catch (UserNotLoggedInException e) {
            Assert.assertFalse(member.isLoggedIn());
        }
    }

    @Test
    public void deactivateTest() {
        User member = User.newMember(username, hashedPassword, emailAddress);
        Assert.assertTrue(member.deactivate());
        Assert.assertFalse(member.isActive());
    }

    @Test
    public void deactivateAlreadyDeactivatedTest() {
        User member = User.newMember(username, hashedPassword, emailAddress);
        member.deactivate();
        Assert.assertFalse(member.deactivate());
        Assert.assertFalse(member.isActive());
    }

    @Test
    public void activateTest() {
        User member = User.newMember(username, hashedPassword, emailAddress);
        member.deactivate();
        Assert.assertTrue(member.activate());
        Assert.assertTrue(member.isActive());
    }

    @Test
    public void activateAlreadyActivatedTest() {
        User member = User.newMember(username, hashedPassword, emailAddress);
        Assert.assertFalse(member.activate());
        Assert.assertTrue(member.isActive());
    }

    @Test
    public void banTest() {
        User member = User.newMember(username, hashedPassword, emailAddress);
        Assert.assertTrue(member.banUser());
        Assert.assertTrue(member.isBanned());
    }

    @Test
    public void banAlreadyBannedTest() {
        User member = User.newMember(username, hashedPassword, emailAddress);
        member.banUser();
        Assert.assertFalse(member.banUser());
        Assert.assertTrue(member.isBanned());
    }

    @Test
    public void unBanTest() {
        User member = User.newMember(username, hashedPassword, emailAddress);
        member.banUser();
        Assert.assertTrue(member.unBanUser());
        Assert.assertFalse(member.isBanned());
    }

    @Test
    public void unBanAlreadyUnBannedTest() {
        User member = User.newMember(username, hashedPassword, emailAddress);
        Assert.assertFalse(member.unBanUser());
        Assert.assertFalse(member.isBanned());
    }

    @Test
    public void appointModeratorTest() {
        User admin = User.newMember(adminName, hashedPassword, emailAddress);
        User member = User.newMember(username, hashedPassword, emailAddress);
        SubForum subForum = new SubForum(subForumName, admin, 2);
        Assert.assertTrue(member.appoint(subForum));
        Assert.assertTrue(member.getState().isModerator());
    }

    @Test
    public void appointModeratorToAlreadyContainedSubForumTest() {
        User admin = User.newMember(adminName, hashedPassword, emailAddress);
        User member = User.newMember(username, hashedPassword, emailAddress);
        SubForum subForum = new SubForum(subForumName, admin, 2);
        member.appoint(subForum);
        Assert.assertTrue(member.appoint(subForum));
    }

    @Test
    public void unAppointModeratorTest() {
        User admin = User.newMember(adminName, hashedPassword, emailAddress);
        User member = User.newMember(username, hashedPassword, emailAddress);
        SubForum subForum = new SubForum(subForumName, admin, 2);
        member.appoint(subForum);
        Assert.assertTrue(member.unAppoint(subForum));
        Assert.assertFalse(member.getState().isModerator());
    }

    @Test
    public void unAppointModeratorOfTwoSubForumsTest() {
        User admin = User.newMember(adminName, hashedPassword, emailAddress);
        User member = User.newMember(username, hashedPassword, emailAddress);
        SubForum subForum1 = new SubForum(subForumName, admin, 2);
        SubForum subForum2 = new SubForum(subForumName, admin, 2);
        member.appoint(subForum1);
        member.appoint(subForum2);
        Assert.assertTrue(member.unAppoint(subForum1));
        Assert.assertTrue(member.getState().isModerator());
        Assert.assertTrue(member.unAppoint(subForum2));
        Assert.assertFalse(member.getState().isModerator());
    }

    @Test
    public void unAppointNonModerator() {
        User admin = User.newMember(adminName, hashedPassword, emailAddress);
        User member = User.newMember(username, hashedPassword, emailAddress);
        SubForum subForum = new SubForum(subForumName, admin, 1);
        Assert.assertFalse(member.unAppoint(subForum));
        Assert.assertFalse(member.getState().isModerator());
    }
}
