package acceptanceTests.bridge;

import content.*;
import content.Thread;
import controllers.*;
import exceptions.*;
import policy.ForumPolicy;
import policy.UserStatusPolicy;
import users.FriendRequest;
import users.User;
import utils.Cipher;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;


public class Real implements IForumSystemBridge {

    UserController userController;
    ContentController contentController;
    ModerationController moderationController;
    AdminController adminController;
    SuperAdminController superAdminController;

    public Real() {
        userController = new UserController();
        contentController = new ContentController();
        moderationController = new ModerationController();
        adminController = new AdminController();
        superAdminController = new SuperAdminController();
    }

    @Override
    public List<SubForum> showSubForumList(Forum forum, User user) throws UserNotAuthorizedException {
        return UserController.viewSubForumList(forum,user);
    }

    @Override
    public List<Thread> showThreadsList(SubForum subForum) {
        return subForum.viewThreads();
    }

    @Override
    public List<Message> searchMessages(Forum forum, String title, String content, String memberName, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public Forum addForum(String title, User superAdmin, ForumPolicy policy) throws UserNotAuthorizedException {
        return SuperAdminController.createNewForum(superAdmin, policy, title);
    }

    @Override
    public Message replyToMessage(Forum forum, Message addTo, String title, String content, User user) throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException {
        return UserController.reply(forum, addTo, title, content, user);
    }

    @Override
    public User registerToForum(Forum forum, String user, String pass, String emailAddress) throws UsernameAlreadyExistsException, NoSuchAlgorithmException {
        return UserController.register(forum, user, pass, emailAddress);
    }

    @Override
    public User loginUser(Forum forum, String user, String pass) throws NoSuchAlgorithmException, UserAlreadyLoggedInException, UserDoesNotExistsException, WrongPasswordException {
        return UserController.login(forum, user, pass);
    }

    @Override
    public User logoffUser(Forum forum, User user) throws UserDoesNotExistsException, UserNotLoggedInException {
        return UserController.logout(user.getId());
    }

    @Override
    public Thread openThread(Forum forum, SubForum subForum, String title, String content, User user) throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException {
        return UserController.openNewThread(forum, subForum, title, content, user);
    }

    @Override
    public boolean editPost(Forum forum, SubForum subForum, User user, Message msg, String body) throws UserNotAuthorizedException {
        return UserController.editMessage(forum, subForum, user, msg, body);
    }

    @Override
    public boolean deletePost(Forum forum, SubForum subForum, User user, Message msg) throws UserNotAuthorizedException {
        return UserController.deleteMessage(forum, subForum, user, msg);
    }

    @Override
    public boolean reportAdmin(User user, User admin) {
        return false;
    }

    @Override
    public boolean banModerator(Forum forum, User user) {
        return false;
    }

    @Override
    public List<SubForum> getModeratedSubForums(Forum forum, User user) {
        return null;
    }

    @Override
    public boolean appointMemberAsModerator(Forum forum, User moderator) {
        return false;
    }

    @Override
    public boolean suspendSubForumModerator(SubForum subForum, User moderator) {
        return false;
    }

    @Override
    public boolean dismissModerator(SubForum forum) {
        return false;
    }

    @Override
    public boolean appointNewAdmin(Forum forum, User superAdmin, User admin) throws UserNotAuthorizedException {
        return SuperAdminController.changeAdministrator(superAdmin, forum, admin);
    }

    @Override
    public SubForum addSubForum(Forum forum, String title, User admin) throws UserNotAuthorizedException {
        return AdminController.addSubForum(forum, title, admin);
    }

    @Override
    public boolean appointNewModerator(Forum forum, SubForum subForum, User admin, User newModerator) throws UserNotAuthorizedException {
        return AdminController.appointModerator(forum, subForum, admin, newModerator);
    }

    @Override
    public String[] getForumStats(Forum forum) {
        return new String[0];
    }

    @Override
    public boolean isMessageContentMatchesSubForumSubject(SubForum subForum, Message message) {
        return false;
    }

    @Override
    public boolean setModeratorAndAdminsSuspensionPolicy(Forum forum, ForumPolicy policy) {
        return false;
    }

    @Override
    public boolean setAppointmentRules(Forum forum, String[] rules) {
        return false;
    }

    @Override
    public ForumSystem initializeForumSystem(String user, String pass, String emailAddress) throws NoSuchAlgorithmException {
        return SuperAdminController.initializeForumSystem(user, getHashedPassword(pass), emailAddress);
    }

    @Override
    public String getHashedPassword(String pass) throws NoSuchAlgorithmException {
        return Cipher.hashString(pass, Cipher.SHA);
    }

    @Override
    public boolean setMemberSuspensionPolicy(Forum forum, ForumPolicy policy) {
        return false;
    }

    @Override
    public boolean changeForumPolicy(Forum forum, ForumPolicy policy, User superAdmin) throws UserNotAuthorizedException {
        return SuperAdminController.changeForumPolicy(superAdmin, forum, policy);
    }

    @Override
    public FriendRequest sendFriendRequest(Forum forum, User from, User to, String message) throws UserNotAuthorizedException {
        return UserController.sendFriendRequest(forum, from, to, message);
    }

    @Override
    public boolean removeFriend(Forum forum, User user, User friend) throws UserNotAuthorizedException {
        return UserController.removeFriend(forum, user, friend);
    }

    @Override
    public User enterAsGuest(Forum forum) {
        return UserController.enterAsGuest(forum);
    }

    @Override
    public boolean reportModeratorInForum(Forum forum, User reporter, User moderator, String title, String content) throws UserNotAuthorizedException {
        return UserController.report(forum, reporter, moderator, title, content);
    }

    @Override
    public boolean deleteSubForum(Forum forum, SubForum subForum, User user) throws UserNotAuthorizedException {
        return AdminController.deleteSubForum(forum, subForum, user);
    }

    @Override
    public boolean replyToFriendRequest(Forum forum, User user, FriendRequest request, boolean answer) throws UserNotAuthorizedException {
        return UserController.replyToFriendRequest(forum, user, request, answer);
    }

    @Override
    public void tearDownForumSystem(User superAdmin, ForumSystem system) throws UserNotAuthorizedException {
        SuperAdminController.destroyForumSystem(superAdmin,system);
    }

    @Override
    public boolean addUserStatusType(User superAdmin, String type, UserStatusPolicy userStatusPolicy) {
        return SuperAdminController.addUserStatusType(superAdmin,type,userStatusPolicy);
    }
}

