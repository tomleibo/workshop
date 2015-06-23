package acceptanceTests.bridge;

import content.Forum;
import content.Message;
import content.SubForum;
import content.Thread;
import exceptions.*;
import policy.ForumPolicy;
import users.FriendRequest;
import users.Notification;
import users.User;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IForumSystemBridge {
	// General Services
	List<SubForum> showSubForumList(Forum forum, User user) throws UserNotAuthorizedException;
	List<content.Thread> showThreadsList(SubForum subForum);
	List<Message> searchMessages(Forum forum, String title, String content, String memberName, java.sql.Date startDate, java.sql.Date endDate);
	Forum addForum(String title, User superAdmin, ForumPolicy policy) throws UserNotAuthorizedException;
	Message replyToMessage(Forum forum, Message addTo, String title, String content, User user) throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException;
	// Member Services
	User registerToForum(Forum forum, String user, String pass, String emailAddress) throws Exception;
	User loginUser(Forum forum, String user, String pass) throws NoSuchAlgorithmException, UserDoesNotExistsException, WrongPasswordException, NeedToChangePasswordException;
	User logoffUser(Forum forum, User user) throws UserDoesNotExistsException, UserNotLoggedInException;
	Thread openThread(Forum forum, SubForum subForum, String title, String content, User user) throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException;
	boolean editPost(Forum forum, SubForum subForum, User user, Message msg, String body) throws UserNotAuthorizedException;
	boolean deletePost(Forum forum, SubForum subForum, User user, Message msg) throws UserNotAuthorizedException;
	boolean reportAdmin(User user, User admin);
	// Admin Services
	boolean banModerator(Forum forum, User user);
	List<SubForum> getModeratedSubForums(Forum forum, User user);
	boolean appointMemberAsModerator(Forum forum, User moderator);
	boolean suspendSubForumModerator(SubForum subForum, User moderator);
	boolean dismissModerator(SubForum subForum);
	boolean appointNewAdmin(Forum forum, User superAdmin, User admin) throws UserNotAuthorizedException;
	SubForum addSubForum(Forum forum, String title, User admin) throws UserNotAuthorizedException;
	boolean appointNewModerator(Forum forum, SubForum subForum, User admin, User newModerator) throws Exception;
	String[] getForumStats(Forum forum);
	//
	boolean isMessageContentMatchesSubForumSubject(SubForum subForum, Message message);
	boolean setModeratorAndAdminsSuspensionPolicy(Forum forum, ForumPolicy policy);
	boolean setAppointmentRules(Forum forum, String[] rules);
	User initializeForumSystem(String user, String pass, String emailAddress) throws NoSuchAlgorithmException;

	String getHashedPassword(String pass) throws NoSuchAlgorithmException;

	boolean setMemberSuspensionPolicy(Forum forum, ForumPolicy policy);
	boolean changeForumPolicy(Forum forum, ForumPolicy policy, User superAdmin) throws UserNotAuthorizedException;
	FriendRequest sendFriendRequest(Forum forum, User from, User to, String message) throws UserNotAuthorizedException;
	boolean removeFriend(Forum forum, User user, User friend) throws UserNotAuthorizedException;
	User enterAsGuest(Forum forum);
	boolean reportModeratorInForum(Forum forum, User reporter, User moderator, String title, String content) throws UserNotAuthorizedException;
	boolean deleteSubForum(Forum forum, SubForum subForum,User user) throws UserNotAuthorizedException;
	boolean replyToFriendRequest(Forum forum, User user, FriendRequest request, boolean answer) throws UserNotAuthorizedException;

	boolean addUserStatusType(Forum forum, User admin, String type, int numberOfMessages) throws UserNotAuthorizedException;
    Map<Integer,String> getUserStatusTypes(Forum forum, User admin) throws UserNotAuthorizedException;
	// Version 2
	List<Notification> getPendingNotifications(Forum forum, User user) throws UserNotAuthorizedException;
	boolean appointModerator(Forum forum, SubForum subForum, User admin, User moderator) throws Exception;
	boolean unAppoint(Forum forum, SubForum subForum, User admin, User moderator) throws Exception;
	int getReportTotalMessagesInSubForum(Forum forum, User admin, SubForum subForum) throws UserNotAuthorizedException;
	List<Message> getReportTotalMessagesOfMember(Forum forum, User admin, User member) throws UserNotAuthorizedException;
	Set<User> getReportModeratorList(Forum forum, User admin) throws UserNotAuthorizedException;
	int getReportNumberOfForums(User superAdmin) throws UserNotAuthorizedException;

}
