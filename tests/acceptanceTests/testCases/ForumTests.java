package acceptanceTests.testCases;

import acceptanceTests.bridge.Driver;
import acceptanceTests.bridge.IForumSystemBridge;
import content.Forum;
import content.Message;
import content.SubForum;
import exceptions.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import policy.ForumPolicy;
import users.FriendRequest;
import users.Notification;
import users.User;
import utils.HibernateUtils;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

public class ForumTests {
	protected static IForumSystemBridge driver;
	public static final String[] FORUM_NAMES = {"YNET", "FXP", "StackOverFlow"};
	public static final String[] ID_QUESTIONS = {"what is your favorite color?", "who's your papa?", "what is the name of your goldfish from seventh grade?"};
	public static final String[] ID_ANSWERS = {"orange", "papa", "twiti"};
	public static final String[] SUB_FORUM_NAMES = {"Games", "Nature", "Physics"};
	public static final String[] USER_NAMES = {"Dani", "John", "Joe", "bb"};
	public static final String[] USER_PASSES = {"DaniDaKing", "JohnDoe", "BazookaJoe", "bBbB"};
	public static final String[] USER_EMAILS = {"roee@gmail.com", "shrimple@gmail.com", "jontreve@gmail.co.il", "bbB@gmail.co.il"};
	public static final String[] THREAD_TITLES ={ "Who wants to play Sims?",
			 "Who wants to play Age of Empires?", "Who wants to play Sims?"};
	public final String[] THREAD_CONTENTS = {"Hello everyone, is there someone who wants to play Sims?",
											"Hello everyone, is there someone who wants to play Age of Empires?",
											"Hello everyone, is there someone who wants to play Sims?"};

	public static final String[] FRIEND_MESSAGES = {"Hi Friend, Would you like to be my friend?",
	"Hi Mom, Why won't you approve me?", "Hi John, Please approve me"};
	
	public static final String[] MESSAGE_TITLES ={ "Who wants to play Sims?",
			 "Who wants to play Age of Empires?", "Who wants to play Sims?"};

	public static final String[] MESSAGE_CONTENTS = {"Yes I would like to!",
			"Does anyone have a recipe for a cheese cake?",
			"No, That game sucks"};

	public static final String[] REPORT_TITLES = {"Mehdal!", "Outrageous!", "OMG!"};

	public static final String[] REPORT_CONTENTS = {"Haziz Vara'am!!",
			"I want to see you behind bars!",
			"I know what you did last summer"};

	protected static final String superAdminUsername = "SuperAdmin";
	protected static final String superAdminPassword = "";
	protected static final String superAdminMail = "";

	protected static Forum theForum;
	protected static User superAdmin;
	protected static ForumPolicy policy;

    protected static final long changePasswordTime = -1;

	@BeforeClass
	public static void setUp() throws UserNotAuthorizedException, NoSuchAlgorithmException {
		driver = Driver.getDriver();
		//system = initializeForumSystem(superAdminUsername, superAdminPassword, superAdminMail);
		superAdmin = initializeForumSystem(superAdminUsername, superAdminPassword, superAdminMail);
		policy = getPolicy(3, ".+", ForumPolicy.HashFunction.MD5);
		theForum = addForum(FORUM_NAMES[0], superAdmin, policy);
	}

	@AfterClass
	public static void tearDown() throws UserNotAuthorizedException {
		HibernateUtils.cleanUp();
	}

	public User createSuperAdmin(String userName, String password, String emailAddress){
		return User.newSuperAdmin(userName, password, emailAddress);
	}
		
	protected static Forum addForum(String forumName, User superAdmin, ForumPolicy policy) throws UserNotAuthorizedException {
		return driver.addForum(forumName, superAdmin, policy);
	}
	
	protected SubForum addSubForum(Forum forum, String title, User user) throws UserNotAuthorizedException{
		return driver.addSubForum(forum, title, user);
	}
	
	protected List<SubForum> showListOfSubForums(Forum forum, User user) throws UserNotAuthorizedException {
			return driver.showSubForumList(forum, user);
	}
	
	protected List<content.Thread> showListOfThreads(SubForum subForum){
		return driver.showThreadsList(subForum);
	}
	
	
	protected static User loginUser(Forum forum, String user, String pass) throws UserAlreadyLoggedInException, UserDoesNotExistsException, WrongPasswordException, NoSuchAlgorithmException, NeedToChangePasswordException {
		return driver.loginUser(forum, user, pass);
	}
	
	protected content.Thread openNewThread(Forum forum, SubForum subForum, String title, String content, User user) throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException {
		return driver.openThread(forum, subForum, title, content, user);
	}
	
	protected Message replyToMessage(Forum forum, Message addTo, String title, String content, User user) throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException {
		return driver.replyToMessage(forum, addTo, title, content, user);
	}
	
	protected List<Message> searchMessages(Forum forum, String title,String content,String memberName, java.sql.Date startDate, java.sql.Date endDate){
		return driver.searchMessages(forum, title, content, memberName, startDate, endDate);
	}

	protected boolean editPost(Forum forum, SubForum subForum, User user, Message msg, String body) throws UserNotAuthorizedException {
		return driver.editPost(forum, subForum, user, msg, body);
	}
	
	protected boolean deletePost(Forum forum, SubForum subForum, User user, Message msg) throws UserNotAuthorizedException {
		return driver.deletePost(forum, subForum, user, msg);
	}
	
	
	protected static User registerToForum(Forum forum, String user, String hashedPass, String emailAddress) throws Exception {
		return driver.registerToForum(forum, user, hashedPass, emailAddress);
	}

	protected FriendRequest sendFriendRequest(Forum forum, User from, User to, String message) throws UserNotAuthorizedException {
		return driver.sendFriendRequest(forum, from, to, message);
	}

	protected boolean removeFriend(Forum forum, User user, User friend) throws UserNotAuthorizedException {
		return driver.removeFriend(forum, user, friend);
	}

	protected boolean changeForumPolicy(Forum forum, ForumPolicy policy, User admin) throws UserNotAuthorizedException {
		return driver.changeForumPolicy(forum, policy, admin);
	}

	protected User enterAsGuest(Forum forum){
		return driver.enterAsGuest(forum);
	}

	protected boolean reportModeratorInForum(Forum forum, User reporter, User admin, String title, String content) throws UserNotAuthorizedException {
		return driver.reportModeratorInForum(forum, reporter, admin, title, content);
	}

	protected boolean cancelSubForum(Forum forum, SubForum subForum, User user) throws UserNotAuthorizedException {
		return driver.deleteSubForum(forum, subForum, user);
	}

	protected static User initializeForumSystem(String user, String pass, String emailAddress) throws NoSuchAlgorithmException {
		return driver.initializeForumSystem(user, pass, emailAddress);
	}

	protected static ForumPolicy getPolicy(int maxMod, String passwordRegex, ForumPolicy.HashFunction func){
		ForumPolicy policy = new ForumPolicy(maxMod,passwordRegex,func);
        policy.setPasswordMaxTime(changePasswordTime);
        return policy;
	}

	protected static boolean changeAdmin(Forum forum, User superAdmin, User admin) throws UserNotAuthorizedException {
		return driver.appointNewAdmin(forum, superAdmin, admin);
	}

	protected boolean changeModetator(Forum forum, SubForum subForum, User admin, User newModerator) throws Exception {
		return driver.appointNewModerator(forum, subForum, admin, newModerator);
	}

	protected static String getHashedPassword(String pass) throws NoSuchAlgorithmException {
		return driver.getHashedPassword(pass);
	}

	protected User logoffUser(Forum forum, User user) throws UserDoesNotExistsException, UserNotLoggedInException {
		return driver.logoffUser(forum, user);
	}

	protected boolean replyToFriendRequest(Forum forum, User user,FriendRequest request, boolean msg) throws UserNotAuthorizedException {
		return driver.replyToFriendRequest(forum, user, request, msg);
	}

	protected boolean addUserStatusType(Forum forum, User admin, String type, int numberOfMessages) throws UserNotAuthorizedException {
		return driver.addUserStatusType(forum, admin, type, numberOfMessages);
	}

// version 2

	protected List<Notification> getPendingNotifications(Forum forum, User user) throws UserNotAuthorizedException{
		return driver.getPendingNotifications(forum, user);
	}

	public boolean appointModerator(Forum forum, SubForum subForum, User admin, User moderator) throws Exception {
		return driver.appointModerator(forum, subForum, admin, moderator);
	}

	public boolean unAppoint(Forum forum, SubForum subForum, User admin, User moderator) throws Exception {
		return driver.unAppoint(forum, subForum, admin, moderator);
	}

	int getReportTotalMessagesInSubForum(Forum forum, User admin, SubForum subForum) throws UserNotAuthorizedException{
		return driver.getReportTotalMessagesInSubForum(forum, admin, subForum);
	}
	List<Message> getReportTotalMessagesOfMember(Forum forum, User admin, User member) throws UserNotAuthorizedException{
		return driver.getReportTotalMessagesOfMember(forum, admin, member);
	}
	Set<User> getReportModeratorList(Forum forum, User admin) throws UserNotAuthorizedException{
		return driver.getReportModeratorList(forum, admin);
	}

	public int getReportNumberOfForums(User superAdmin) throws UserNotAuthorizedException {
		return driver.getReportNumberOfForums(superAdmin);
	}
}
