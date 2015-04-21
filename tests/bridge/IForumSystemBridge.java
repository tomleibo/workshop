package bridge;

import java.util.List;

import exceptions.UserNotAuthorized;
import policy.ForumPolicy;
import policy.Policy;
import users.Report;
import users.User;
import content.Forum;
import content.Message;
import content.SubForum;
import content.Thread;
import users.userState.UserStates;

public interface IForumSystemBridge {
	// General Services
	List<SubForum> showSubForumList(Forum forum);
	List<content.Thread> showThreadsList(SubForum subForum);
	List<content.Thread> getSubForumThreads(SubForum subForum);
	List<Message> searchMessages(Forum forum, String title, String content, String memberName, java.sql.Date startDate, java.sql.Date endDate);
	Forum addForum(String title, User superAdmin, Policy policy);
	Message replyToMessage(Message addTo, String title, String content, User user);
	// Member Services
	User registerGuest(Forum forum, String user, String hashedPass);
	User loginUser(Forum forum, String user, String pass);
	boolean logoffUser(Forum forum, User user);
	Thread openThread(SubForum sbfrm, String title, String content, User user);
	boolean editPost(Forum forum, User user, Message msg, String body);
	boolean deletePost(Forum forum, User user, Message msg);
	boolean reportAdmin(User user, User admin);
	boolean reportModerator(User user, User moderator);
	// 
	boolean banModerator(Forum forum, User user);
	List<SubForum> getModeratedSubForums(Forum forum, User user);
	boolean deleteSubForumMessage(Forum forum, Message message);
	boolean appointMemberAsModerator(Forum forum, User moderator);
	boolean suspendSubForumModerator(SubForum subForum, User moderator);
	boolean dismissModerator(SubForum forum);
	boolean appointNewAdmin(Forum forum, User admin);
	SubForum addSubForum(Forum forum, String title, User mod);
	boolean appointNewModerator(SubForum subForum, User newModerator);
	String[] getForumStats(Forum forum);
	//
	boolean isMessageContentMatchesSubForumSubject(SubForum subForum, Message message);
	boolean setModeratorAndAdminsSuspensionPolicy(Forum forum, Policy policy);
	boolean setAppointmentRules(Forum forum, String[] rules);
	boolean initializeForumSystem();
	boolean setMemberSuspensionPolicy(Forum forum, Policy policy);
	boolean changeForumPolicy(Forum forum, ForumPolicy policy);
	boolean sendFriendRequest(User from, User to, String message);
	boolean removeFriend(User user, User friend);
	boolean defineProperties(Forum forum, ForumPolicy policy);
	User enterAsGuest(Forum forum);
	boolean reportModeratorInForum(Forum forum, User reporter, User admin, String title, String content);
	boolean deleteSubForum(Forum forum, SubForum subForum,User user);

	}