package bridge;

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
    @Override
    public List<SubForum> showSubForumList(Forum forum) {
        return null;
    }

    @Override
    public List<Thread> showThreadsList(SubForum subForum) {
        return null;
    }

    @Override
    public List<Thread> getSubForumThreads(SubForum subForum) {
        return null;
    }

    @Override
    public List<Message> searchMessages(Forum forum, String title, String content, String memberName, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public Forum addForum(String title, User superAdmin, Policy policy) {
        return null;
    }

    @Override
    public Message replyToMessage(Message addTo, String title, String content, User user) {
        return null;
    }

    @Override
    public User registerGuest(Forum forum, String user, String hashedPass) {
        return null;
    }

    @Override
    public User loginUser(Forum forum, String user, String pass) {
        return null;
    }

    @Override
    public boolean logoffUser(Forum forum, User user) {
        return false;
    }

    @Override
    public Thread openThread(SubForum sbfrm, String title, String content, User user) {
        return null;
    }

    @Override
    public boolean editPost(Forum forum, User user, Message msg, String body) {
        return false;
    }

    @Override
    public boolean deletePost(Forum forum, User user, Message msg) {
        return false;
    }

    @Override
    public boolean reportAdmin(User user, User admin) {
        return false;
    }

    @Override
    public boolean reportModerator(User user, User moderator) {
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
    public boolean deleteSubForumMessage(Forum forum, Message message) {
        return false;
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
    public boolean appointNewAdmin(Forum forum, User admin) {
        return false;
    }

    @Override
    public SubForum addSubForum(Forum forum, String title, User mod) {
        return null;
    }

    @Override
    public boolean appointNewModerator(SubForum subForum, User newModerator) {
        return false;
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
    public boolean setModeratorAndAdminsSuspensionPolicy(Forum forum, Policy policy) {
        return false;
    }

    @Override
    public boolean setAppointmentRules(Forum forum, String[] rules) {
        return false;
    }

    @Override
    public boolean initializeForumSystem() {
        return false;
    }

    @Override
    public boolean setMemberSuspensionPolicy(Forum forum, Policy policy) {
        return false;
    }

    @Override
    public boolean changeForumPolicy(Forum forum, ForumPolicy policy) {
        return false;
    }

    @Override
    public boolean sendFriendRequest(User from, User to, String message) {
        return false;
    }

    @Override
    public boolean removeFriend(User user, User friend) {
        return false;
    }

    @Override
    public boolean defineProperties(Forum forum, ForumPolicy policy) {
        return false;
    }

    @Override
    public User enterAsGuest(Forum forum) {
        return null;
    }

    @Override
    public boolean reportModeratorInForum(Forum forum, User reporter, User admin, String title, String content) {
        return false;
    }

    @Override
    public boolean deleteSubForum(Forum forum, SubForum subForum, User user) {
        return false;
    }
}
