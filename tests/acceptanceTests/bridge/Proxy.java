package acceptanceTests.bridge;

import content.*;
import content.Thread;
import exceptions.*;
import policy.ForumPolicy;
import policy.Policy;
import users.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;


public class Proxy implements IForumSystemBridge {

    IForumSystemBridge real = null;

    public void initRealImplement(){
        real = new Real();
    }

    @Override
    public List<SubForum> showSubForumList(Forum forum, User user) throws UserNotAuthorizedException {
        if(real != null)
            return real.showSubForumList(forum, user);

        return null;
    }

    @Override
    public List<Thread> showThreadsList(SubForum subForum) {
        if(real != null)
            return real.showThreadsList(subForum);

        return null;
    }

    @Override
    public List<Message> searchMessages(Forum forum, String title, String content, String memberName, Date startDate, Date endDate) {
        if(real != null)
            return real.searchMessages(forum, title, content, memberName, startDate, endDate);

        return null;
    }

    @Override
    public Forum addForum(String title, User superAdmin, ForumPolicy policy) throws UserNotAuthorizedException {
        if(real != null)
            return real.addForum(title, superAdmin, policy);

        return null;
    }

    @Override
    public Message replyToMessage(Forum forum, Message addTo, String title, String content, User user) throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException {
        if(real != null)
            return real.replyToMessage(forum, addTo, title, content, user);

        return null;
    }

    @Override
    public User registerToForum(Forum forum, String user, String pass, String emailAddress) throws UsernameAlreadyExistsException, NoSuchAlgorithmException {
        if(real != null)
            return real.registerToForum(forum, user, pass, emailAddress);

        return null;
    }

    @Override
    public User loginUser(Forum forum, String user, String pass) throws NoSuchAlgorithmException, UserAlreadyLoggedInException, UserDoesNotExistsException, WrongPasswordException {
        if(real != null)
            return real.loginUser(forum, user, pass);

        return null;
    }

    @Override
    public User logoffUser(Forum forum, User user) throws UserDoesNotExistsException, UserNotLoggedInException {
        if(real != null)
            return real.logoffUser(forum, user);

        return null;
    }

    @Override
    public Thread openThread(Forum forum, SubForum subForum, String title, String content, User user) throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException {
        if(real != null)
            return real.openThread(forum, subForum, title, content, user);

        return null;
    }

    @Override
    public boolean editPost(Forum forum, User user, Message msg, String body) {
        if(real != null)
            return real.editPost(forum, user, msg, body);

        return false;
    }

    @Override
    public boolean deletePost(Forum forum, SubForum subForum, User user, Message msg) throws UserNotAuthorizedException {
        if(real != null)
            return real.deletePost(forum, subForum, user, msg);

        return false;
    }

    @Override
    public boolean reportAdmin(User user, User admin) {
        if(real != null)
            return real.reportAdmin(user, admin);

        return false;
    }


    @Override
    public boolean banModerator(Forum forum, User user) {
        if(real != null)
            return real.banModerator(forum, user);

        return false;
    }

    @Override
    public List<SubForum> getModeratedSubForums(Forum forum, User user) {
        if(real != null)
            return real.getModeratedSubForums(forum, user);

        return null;
    }

    @Override
    public boolean appointMemberAsModerator(Forum forum, User moderator) {
        if(real != null)
            return real.appointMemberAsModerator(forum, moderator);

        return false;
    }

    @Override
    public boolean suspendSubForumModerator(SubForum subForum, User moderator) {
        if(real != null)
            return real.suspendSubForumModerator(subForum, moderator);

        return false;
    }

    @Override
    public boolean dismissModerator(SubForum forum) {
        if(real != null)
            return real.dismissModerator(forum);

        return false;
    }

    @Override
    public boolean appointNewAdmin(Forum forum, User superAdmin, User admin) {
        if(real != null)
            return real.appointNewAdmin(forum, superAdmin, admin);

        return false;
    }

    @Override
    public SubForum addSubForum(Forum forum, String title, User admin) throws UserNotAuthorizedException {
        if(real != null)
            return real.addSubForum(forum, title, admin);

        return null;
    }

    @Override
    public boolean appointNewModerator(Forum forum, SubForum subForum, User admin, User newModerator) throws UserNotAuthorizedException {
        if(real != null)
            return real.appointNewModerator(forum, subForum, admin, newModerator);

        return false;
    }

    @Override
    public String[] getForumStats(Forum forum) {
        if(real != null)
            return real.getForumStats(forum);

        return new String[0];
    }

    @Override
    public boolean isMessageContentMatchesSubForumSubject(SubForum subForum, Message message) {
        if(real != null)
            return real.isMessageContentMatchesSubForumSubject(subForum, message);

        return false;
    }

    @Override
    public boolean setModeratorAndAdminsSuspensionPolicy(Forum forum, Policy policy) {
        if(real != null)
            return real.setModeratorAndAdminsSuspensionPolicy(forum, policy);

        return false;
    }

    @Override
    public boolean setAppointmentRules(Forum forum, String[] rules) {
        if(real != null)
            return real.setAppointmentRules(forum, rules);

        return false;
    }

    @Override
    public ForumSystem initializeForumSystem(String user, String pass, String emailAddress) throws NoSuchAlgorithmException {
        if(real != null)
            return real.initializeForumSystem(user, pass, emailAddress);

        return null;
    }

    @Override
    public String getHashedPassword(String pass) throws NoSuchAlgorithmException {
        if(real != null)
            return real.getHashedPassword(pass);

        return null;
    }

    @Override
    public boolean setMemberSuspensionPolicy(Forum forum, Policy policy) {
        if(real != null)
            return real.setMemberSuspensionPolicy(forum, policy);

        return false;
    }

    @Override
    public boolean changeForumPolicy(Forum forum, ForumPolicy policy, User superAdmin) throws UserNotAuthorizedException {
        if(real != null)
            return real.changeForumPolicy(forum, policy, superAdmin);

        return false;
    }

    @Override
    public boolean sendFriendRequest(User from, User to, String message) {
        if(real != null)
            return real.sendFriendRequest(from, to, message);

        return false;
    }

    @Override
    public boolean removeFriend(User user, User friend) {
        if(real != null)
            return real.removeFriend(user, friend);

        return false;
    }

    @Override
    public User enterAsGuest(Forum forum) {
        if(real != null)
            return real.enterAsGuest(forum);

        return null;
    }

    @Override
    public boolean reportModeratorInForum(Forum forum, User reporter, User moderator, String title, String content) throws UserNotAuthorizedException {
        if(real != null)
            return real.reportModeratorInForum(forum, reporter, moderator, title, content);

        return false;
    }

    @Override
    public boolean deleteSubForum(Forum forum, SubForum subForum, User user) throws UserNotAuthorizedException {
        if(real != null)
            return real.deleteSubForum(forum, subForum, user);

        return false;
    }
}
