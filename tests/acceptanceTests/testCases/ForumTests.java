package acceptanceTests.testCases;

import acceptanceTests.bridge.Driver;
import acceptanceTests.bridge.IForumSystemBridge;
import content.Forum;
import content.Message;
import content.SubForum;
import junit.framework.TestCase;
import org.junit.Before;
import policy.ForumPolicy;
import policy.Policy;
import users.User;

import java.util.List;

public class ForumTests extends TestCase{
	protected IForumSystemBridge driver;
	public static final String FORUM_NAME = "YNET";
	public static final String[] FORUM_NAMES = {"YNET"};
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

	public static final String[] REPORT_CONTENTS = {"Hazia Vara'am!!",
			"I want to see you behind bars!",
			"I know what you did last summer"};

	public ForumTests(){
		driver = Driver.getDriver();
	}

	private final String superAdminUsername = "SuperAdmin";
	private final String superAdminPassword = "";
	private final String superAdminMail = "";

	protected Forum forum;
	protected User moderator;
	protected User superAdmin;
	protected ForumPolicy policy;

	@Before
	public void setUp(){
		superAdmin = User.newSuperAdmin(superAdminUsername, superAdminPassword, superAdminMail);
		policy = new ForumPolicy(3,".",ForumPolicy.HashFunction.MD5);
		forum = addForum(FORUM_NAME, superAdmin, policy);
	}
		
	protected Forum addForum(String forumName, User superAdmin, Policy policy){
		return driver.addForum(forumName, superAdmin,policy);
	}
	
	protected SubForum addSubForum(Forum forum, String title, User user){
		return driver.addSubForum(forum, title, user);
	}
	
	protected List<SubForum> showListOfSubForums(Forum forum){
		return driver.showSubForumList(forum);
	}
	
	protected List<content.Thread> showListOfThreads(SubForum subForum){
		return driver.showThreadsList(subForum);
	}
	
	
	protected User loginUser(Forum forum, String user, String pass){
		return driver.loginUser(forum, user, pass);
	}
	
	protected content.Thread openNewThread(SubForum sbfrm, String title, String content, User user){
		return driver.openThread(sbfrm, title, content, user);
	}
	
	protected Message replyToMessage(Message addTo, String title, String content, User user){
		return driver.replyToMessage(addTo, title, content, user);
	}
	
	protected List<Message> searchMessages(Forum forum, String title,String content,String memberName, java.sql.Date startDate, java.sql.Date endDate){
		return driver.searchMessages(forum, title, content, memberName, startDate, endDate);
	}

	protected boolean editPost(Forum forum, User user, Message msg, String body){
		return driver.editPost(forum, user, msg, body);
	}
	
	protected boolean deletePost(Forum forum, User user, Message msg){
		return driver.deletePost(forum, user, msg);
	}
	
	
	protected User registerToForum(Forum forum, String user, String hashedPass, String emailAddress) {
		return driver.registerGuest(forum, user, hashedPass, emailAddress);
	}

	protected boolean sendFriendRequest(User from, User to, String message){
		return driver.sendFriendRequest(from, to, message);
	}

	protected boolean removeFriend(User user, User friend){
		return driver.removeFriend(user, friend);
	}

	protected boolean defineProperties(Forum forum, ForumPolicy policy){
		return driver.defineProperties(forum, policy);
	}

	protected User enterAsGuest(Forum forum){
		return driver.enterAsGuest(forum);
	}

	protected boolean reportModeratorInForum(Forum forum, User reporter, User admin, String title, String content){
		return driver.reportModeratorInForum(forum, reporter, admin, title, content);
	}

	protected boolean cancelSubForum(Forum forum, SubForum subForum, User user){
		try{
			return driver.deleteSubForum(forum, subForum, user);
		}

		catch(Exception e){
			return false;
		}
	}

	protected boolean initializeForumSystem(String user, String pass, String emailAddress) {
		return driver.initializeForumSystem(user, pass, emailAddress);
	}
}
