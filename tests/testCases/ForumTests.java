package testCases;

import java.util.List;

import org.junit.BeforeClass;

import users.User;
import bridge.Driver;
import bridge.IForumSystemBridge;
import content.Forum;
import content.Message;
import content.SubForum;

public class ForumTests {
	protected IForumSystemBridge bridge;
	public final String FORUM_NAME = "YNET";
	public final String[] FORUM_NAMES = {"YNET"};
	public final String[] SUB_FORUM_NAMES = {"Games", "Nature", "Physics"};
	public final String[] USER_NAMES = {"Dani", "John", "Joe"};
	public final String[] USER_PASSES = {"DaniDaKing", "JohnDoe", "BazookaJoe"};
	public final String[] THREAD_TITLES ={ "Who wants to play Sims?",
			 "Who wants to play Age of Empires?", "Who wants to play Sims?"};
	public final String[] THREAD_CONTENTS = {"Hello everyone, is there someone who wants to play Sims?",
											"Hello everyone, is there someone who wants to play Age of Empires?",
											"Hello everyone, is there someone who wants to play Sims?"};
	
	
	public final String[] MESSAGE_TITLES ={ "Who wants to play Sims?", 
			 "Who wants to play Age of Empires?", "Who wants to play Sims?"};

	public final String[] MESSAGE_CONTENTS = {"Yes I would like to!",
			"Does anyone have a recipe for a cheese cake?",
			"No, That game sucks"};
	
	public ForumTests(){
		bridge = Driver.getDriver();
	}
	
	
	protected Forum forum;
	protected User moderator;
	
	@BeforeClass
	public void setUp(){
		forum = addForum(FORUM_NAME);
	}
		
	protected Forum addForum(String forumName){
		return bridge.addForum(forumName);
	}
	
	protected SubForum addSubForum(Forum forum, String title, User user){
		return bridge.addSubForum(forum, title, user);
	}
	
	protected List<SubForum> showListOfSubForums(Forum forum){
		return bridge.showSubForumList(forum);
	}
	
	protected List<content.Thread> showListOfThreads(SubForum subForum){
		return bridge.showThreadsList(subForum);
	}
	
	
	protected User loginUser(Forum forum, String user, String pass){
		return bridge.loginUser(forum, user, pass);
	}
	
	protected content.Thread openNewThread(SubForum sbfrm, String title, String content, User user){
		return bridge.openThread(sbfrm, title, content, user);
	}
	
	protected Message replyToMessage(Message addTo, String title, String content, User user){
		return bridge.replyToMessage(addTo, title, content, user);
	}
	
	protected List<Message> searchMessages(Forum forum, String title,String content,String memberName, java.sql.Date startDate, java.sql.Date endDate){
		return bridge.searchMessages(forum, title, content, memberName, startDate, endDate);
	}

	protected boolean editPost(Forum forum, User user, Message msg, String body){
		return bridge.editPost(forum, user, msg, body);
	}
	
	protected boolean deletePost(Forum forum, User user, Message msg){
		return bridge.deletePost(forum, user, msg);
	}
	
	
	protected User registerToForum(Forum forum, String user, String hashedPass) {
		return bridge.registerGuest(forum, user, hashedPass);
	}
	
}
