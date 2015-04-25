package acceptanceTests.bridge;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import content.*;
import content.Thread;
import exceptions.*;
import policy.ForumPolicy;
import policy.Policy;
import users.User;

public interface IForumSystemBridge {
	// General Services
	List<SubForum> showSubForumList(Forum forum, User user) throws UserNotAuthorizedException;
	List<content.Thread> showThreadsList(SubForum subForum);
	List<Message> searchMessages(Forum forum, String title, String content, String memberName, java.sql.Date startDate, java.sql.Date endDate);
	Forum addForum(String title, User superAdmin, ForumPolicy policy) throws UserNotAuthorizedException;
	Message replyToMessage(Forum forum, Message addTo, String title, String content, User user) throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException;
	// Member Services
	User registerToForum(Forum forum, String user, String pass, String emailAddress) throws UsernameAlreadyExistsException, NoSuchAlgorithmException;
	User loginUser(Forum forum, String user, String pass) throws NoSuchAlgorithmException, UserAlreadyLoggedInException, UserDoesNotExistsException, WrongPasswordException;
	User logoffUser(Forum forum, User user) throws UserDoesNotExistsException, UserNotLoggedInException;
	Thread openThread(Forum forum, SubForum subForum, String title, String content, User user) throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException;
	boolean editPost(Forum forum, User user, Message msg, String body);
	boolean deletePost(Forum forum, SubForum subForum, User user, Message msg) throws UserNotAuthorizedException;
	boolean reportAdmin(User user, User admin);
	// Admin Services
	boolean banModerator(Forum forum, User user);
	List<SubForum> getModeratedSubForums(Forum forum, User user);
	boolean appointMemberAsModerator(Forum forum, User moderator);
	boolean suspendSubForumModerator(SubForum subForum, User moderator);
	boolean dismissModerator(SubForum subForum);
	boolean appointNewAdmin(Forum forum, User superAdmin, User admin);
	SubForum addSubForum(Forum forum, String title, User admin) throws UserNotAuthorizedException;
	boolean appointNewModerator(Forum forum, SubForum subForum, User admin, User newModerator) throws UserNotAuthorizedException;
	String[] getForumStats(Forum forum);
	//
	boolean isMessageContentMatchesSubForumSubject(SubForum subForum, Message message);
	boolean setModeratorAndAdminsSuspensionPolicy(Forum forum, Policy policy);
	boolean setAppointmentRules(Forum forum, String[] rules);
	ForumSystem initializeForumSystem(String user, String pass, String emailAddress) throws NoSuchAlgorithmException;

	String getHashedPassword(String pass) throws NoSuchAlgorithmException;

	boolean setMemberSuspensionPolicy(Forum forum, Policy policy);
	boolean changeForumPolicy(Forum forum, ForumPolicy policy, User superAdmin) throws UserNotAuthorizedException;
	boolean sendFriendRequest(User from, User to, String message);
	boolean removeFriend(User user, User friend);
	User enterAsGuest(Forum forum);
	boolean reportModeratorInForum(Forum forum, User reporter, User moderator, String title, String content) throws UserNotAuthorizedException;
	boolean deleteSubForum(Forum forum, SubForum subForum,User user) throws UserNotAuthorizedException;


}