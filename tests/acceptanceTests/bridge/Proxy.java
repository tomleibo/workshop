package acceptanceTests.bridge;

import content.*;
import content.Thread;
import policy.ForumPolicy;
import policy.Policy;
import users.User;

import java.sql.Date;
import java.util.List;

/**
 * Created by Roee on 22-04-15.
 */
public class Proxy implements IForumSystemBridge {

    IForumSystemBridge real = null;

    public void initRealImplement(){
        real = new Real();
    }

    @Override
    public List<SubForum> showSubForumList(Forum forum) {
        if(real != null)
            return real.showSubForumList(forum);

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
    public Forum addForum(String title, User superAdmin, Policy policy) {
        if(real != null)
            return real.addForum(title, superAdmin, policy);

        return null;
    }

    @Override
    public Message replyToMessage(Message addTo, String title, String content, User user) {
        if(real != null)
            return real.replyToMessage(addTo, title, content, user);

        return null;
    }

    @Override
    public User registerGuest(Forum forum, String user, String hashedPass, String emailAddress) {
        if(real != null)
            return real.registerGuest(forum, user, hashedPass, emailAddress);

        return null;
    }

    @Override
    public User loginUser(Forum forum, String user, String pass) {
        if(real != null)
            return real.loginUser(forum, user, pass);

        return null;
    }

    @Override
    public boolean logoffUser(Forum forum, User user) {
        if(real != null)
            return real.logoffUser(forum, user);

        return false;
    }

    @Override
    public Thread openThread(SubForum sbfrm, String title, String content, User user) {
        if(real != null)
            return real.openThread(sbfrm, title, content, user);

        return null;
    }

    @Override
    public boolean editPost(Forum forum, User user, Message msg, String body) {
        if(real != null)
            return real.editPost(forum, user, msg, body);

        return false;
    }

    @Override
    public boolean deletePost(Forum forum, User user, Message msg) {
        if(real != null)
            return real.deletePost(forum, user, msg);

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
    public boolean appointNewAdmin(Forum forum, User admin) {
        if(real != null)
            return real.appointNewAdmin(forum, admin);

        return false;
    }

    @Override
    public SubForum addSubForum(Forum forum, String title, User mod) {
        if(real != null)
            return real.addSubForum(forum, title, mod);

        return null;
    }

    @Override
    public boolean appointNewModerator(SubForum subForum, User newModerator) {
        if(real != null)
            return real.appointNewModerator(subForum, newModerator);

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
    public boolean initializeForumSystem(String user, String pass, String emailAddress) {
        if(real != null)
            return real.initializeForumSystem(user, pass, emailAddress);

        return false;
    }

    @Override
    public boolean setMemberSuspensionPolicy(Forum forum, Policy policy) {
        if(real != null)
            return real.setMemberSuspensionPolicy(forum, policy);

        return false;
    }

    @Override
    public boolean changeForumPolicy(Forum forum, ForumPolicy policy) {
        if(real != null)
            return real.changeForumPolicy(forum, policy);

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
    public boolean defineProperties(Forum forum, ForumPolicy policy) {
        if(real != null)
            return real.defineProperties(forum, policy);

        return false;
    }

    @Override
    public User enterAsGuest(Forum forum) {
        if(real != null)
            return real.enterAsGuest(forum);

        return null;
    }

    @Override
    public boolean reportModeratorInForum(Forum forum, User reporter, User admin, String title, String content) {
        if(real != null)
            return real.reportModeratorInForum(forum, reporter, admin, title, content);

        return false;
    }

    @Override
    public boolean deleteSubForum(Forum forum, SubForum subForum, User user) {
        if(real != null)
            return real.deleteSubForum(forum, subForum, user);

        return false;
    }
}
