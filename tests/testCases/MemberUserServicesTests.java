package testCases;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import users.User;
import content.Message;
import content.SubForum;
import content.Thread;
import users.userState.UserState;
import users.userState.UserStates;

public class MemberUserServicesTests extends ForumTests {

	User user;
	
	/*
	 * 3.1	התחברות לפורום.
3.2	התנתקות מהפורום.
3.3	פתיחת דיון.
3.4	שינוי תוכן הודעה עצמית.
3.5	מחיקת הודעה עצמית.
3.6	ניהול רשימת ידידים:
3.6.1	הוספת ידיד.
3.6.2	הסרת ידיד
3.7	הגשת תלונה:
3.7.1	על מנהל פורום.
3.7.2	על מנחה.

	 * */
	
	@Before
	public void register(){
		user = registerToForum(forum, USER_NAMES[0], USER_PASSES[0]);
		moderator = registerToForum(forum, USER_NAMES[1], USER_PASSES[1]);
		moderator.setState(UserState.newState(UserStates.MODERATOR));
	}
	
	@Test
	public void test_login_ExistingUser() {
		user = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		Assert.assertTrue(user != null && user.isLoggedIn());
	}
	
	@Test
	public void test_login_ExistingUser_DoubleLogin() {
		user = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		
		User user_logins_twice = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		
		Assert.assertTrue(user != null && user.isLoggedIn());
		Assert.assertTrue(user_logins_twice == null);
	}
	
	@Test
	public void test_editPost_ExistingMessage() {
		user = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		SubForum sf = addSubForum(forum, SUB_FORUM_NAMES[0], moderator);
		
		Thread t = openNewThread(sf, THREAD_TITLES[0], THREAD_CONTENTS[0], moderator);
		Message msg = t.getOpeningMessage();
		
		boolean result = editPost(forum, user, msg, THREAD_CONTENTS[1]);
		msg = t.getOpeningMessage();
		
		Assert.assertTrue(result && msg.getBody() == THREAD_CONTENTS[1]);
	}
	
	
	@Test
	public void test_deletePost_ExistingMessage() {
		user = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		SubForum sf = addSubForum(forum, SUB_FORUM_NAMES[0], moderator);
		
		Thread t = openNewThread(sf, THREAD_TITLES[0], THREAD_CONTENTS[0], moderator);
		Message msg = t.getOpeningMessage();
		
		boolean result = deletePost(forum, user, msg);
		
		List<Thread> threads = showListOfThreads(sf);
		
		Assert.assertTrue(result && (threads == null || threads.isEmpty()));
	}
	
	@Test
	public void test_deletePost_NonExistingMessage() {
		user = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		SubForum sf = addSubForum(forum, SUB_FORUM_NAMES[0], moderator);
		
		openNewThread(sf, THREAD_TITLES[0], THREAD_CONTENTS[0], moderator);
		Message msg = new Message(THREAD_TITLES[1], THREAD_CONTENTS[1], user, null, null);
		
		boolean result = deletePost(forum, user, msg);
		Assert.assertFalse(result);
	}

	@Test
	public void test_addFriend() {
		user = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		User friend = registerToForum(forum, USER_NAMES[1], USER_PASSES[1]);
		boolean result = sendFriendRequest(user, friend, FRIEND_MESSAGES[0]);

		Assert.assertTrue(result);
	}

	@Test
	public void test_removeFriend() {
		user = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		User friend = registerToForum(forum, USER_NAMES[1], USER_PASSES[1]);
		boolean result = removeFriend(user, friend);

		Assert.assertTrue(result);
	}

}
