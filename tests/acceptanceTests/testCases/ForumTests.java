package acceptanceTests.testCases;

import acceptanceTests.bridge.Driver;
import acceptanceTests.bridge.IForumSystemBridge;
import content.Forum;
import content.ForumSystem;
import content.Message;
import content.SubForum;
import exceptions.*;
import junit.framework.TestCase;
import org.junit.Before;
import policy.ForumPolicy;
import users.User;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ForumTests {
	protected IForumSystemBridge driver;
	public static final String[] FORUM_NAMES = {"YNET", "FXP", "StackOverFlow"};
	public static final String[] SUB_FORUM_NAMES = {"Games", "Nature", "Physics"};
	public static final String[] USER_NAMES = {"Dani", "John", "Joe"};
	public static final String[] USER_PASSES = {"DaniDaKing", "JohnDoe", "BazookaJoe"};
	public static final String[] USER_EMAILS = {"roee@gmail.com", "shrimple@gmail.com", "jontreve@gmail.co.il"};
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

	public ForumTests(){
		driver = Driver.getDriver();
	}

	protected final String superAdminUsername = "SuperAdmin";
	protected final String superAdminPassword = "";
	protected final String superAdminMail = "";

	protected Forum theForum;
	protected User superAdmin;
	protected ForumPolicy policy;

	@Before
	public void setUp() throws UserNotAuthorizedException, NoSuchAlgorithmException {
		ForumSystem system = initializeForumSystem(superAdminUsername, superAdminPassword, superAdminMail);
		superAdmin = system.getSuperAdmin(superAdminUsername, superAdminPassword);
		policy = getPolicy(3, ".", ForumPolicy.HashFunction.MD5);

		theForum = addForum(FORUM_NAMES[0], superAdmin, policy);
	}

	public User createSuperAdmin(String userName, String password, String emailAddress){
		return User.newSuperAdmin(userName, password, emailAddress);
	}
		
	protected Forum addForum(String forumName, User superAdmin, ForumPolicy policy) throws UserNotAuthorizedException {
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
	
	
	protected User loginUser(Forum forum, String user, String pass) throws UserAlreadyLoggedInException, UserDoesNotExistsException, WrongPasswordException, NoSuchAlgorithmException {
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

	protected boolean editPost(Forum forum, User user, Message msg, String body){
		return driver.editPost(forum, user, msg, body);
	}
	
	protected boolean deletePost(Forum forum, SubForum subForum, User user, Message msg) throws UserNotAuthorizedException {
		return driver.deletePost(forum, subForum, user, msg);
	}
	
	
	protected User registerToForum(Forum forum, String user, String hashedPass, String emailAddress) throws NoSuchAlgorithmException, UsernameAlreadyExistsException {
		return driver.registerToForum(forum, user, hashedPass, emailAddress);
	}

	protected boolean sendFriendRequest(User from, User to, String message){
		return driver.sendFriendRequest(from, to, message);
	}

	protected boolean removeFriend(User user, User friend){
		return driver.removeFriend(user, friend);
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

	protected ForumSystem initializeForumSystem(String user, String pass, String emailAddress) throws NoSuchAlgorithmException {
		return driver.initializeForumSystem(user, pass, emailAddress);
	}

	protected ForumPolicy getPolicy(int maxMod, String passwordRegex, ForumPolicy.HashFunction func){
		return new ForumPolicy(maxMod,passwordRegex,func);
	}

	protected boolean changeAdmin(Forum forum, User superAdmin, User admin) {
		return driver.appointNewAdmin(forum, superAdmin, admin);
	}

	protected boolean changeModetator(Forum forum, SubForum subForum, User admin, User newModerator) throws UserNotAuthorizedException {
		return driver.appointNewModerator(forum, subForum, admin, newModerator);
	}


}
