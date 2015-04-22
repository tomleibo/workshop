package testCases;

import content.Forum;
import content.Message;
import content.SubForum;
import content.Thread;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import policy.ForumPolicy;
import users.User;
import users.userState.UserState;
import users.userState.UserStates;

import java.util.List;

public class MemberUserServicesTests extends ForumTests {

	User user;
	
	@Before
	public void register(){
		user = registerToForum(forum, USER_NAMES[0], USER_PASSES[0]);
		moderator = registerToForum(forum, USER_NAMES[1], USER_PASSES[1]);
		moderator.setState(UserState.newState(UserStates.MODERATOR));
	}
	
	@Test
	public void test_login_ExistingUser() {
		user = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		Assert.assertNotNull(user);
		Assert.assertTrue(user.isLoggedIn());
	}
	
	@Test
	public void test_login_ExistingUser_DoubleLogin() {
		user = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		
		User userLoginsTwice = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		Assert.assertNotNull(user);
		Assert.assertTrue(user.isLoggedIn());
		Assert.assertNull(userLoginsTwice);
	}
	
	@Test
	public void test_editPost_ExistingMessage() {
		user = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		SubForum sf = addSubForum(forum, SUB_FORUM_NAMES[0], moderator);
		
		Thread t = openNewThread(sf, THREAD_TITLES[0], THREAD_CONTENTS[0], moderator);
		Message msg = t.getOpeningMessage();
		
		boolean result = editPost(forum, user, msg, THREAD_CONTENTS[1]);
		msg = t.getOpeningMessage();

		Assert.assertTrue(result);
		Assert.assertEquals(msg.getBody(), THREAD_CONTENTS[1]);
	}
	
	
	@Test
	public void test_deletePost_ExistingMessage() {
		user = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		SubForum sf = addSubForum(forum, SUB_FORUM_NAMES[0], moderator);
		
		Thread t = openNewThread(sf, THREAD_TITLES[0], THREAD_CONTENTS[0], moderator);
		Message msg = t.getOpeningMessage();
		
		boolean result = deletePost(forum, moderator, msg);
		
		List<Thread> threads = showListOfThreads(sf);

		Assert.assertTrue(result);
		Assert.assertTrue(threads.isEmpty());
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
	public void test_replyToMessage_UserLoggedIn() {
		user = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		SubForum sf = addSubForum(forum, SUB_FORUM_NAMES[0], moderator);

		Thread t = openNewThread(sf, THREAD_TITLES[0], THREAD_CONTENTS[0], moderator);
		Message msg = t.getOpeningMessage();
		Message reply1 = replyToMessage(msg, THREAD_TITLES[1], THREAD_CONTENTS[1], user);
		Message reply2 = replyToMessage(msg, THREAD_TITLES[2], THREAD_CONTENTS[2], user);

		Assert.assertNotNull(reply1);
		Assert.assertNotNull(reply2);
		Assert.assertEquals(msg.getComments().size(), 2);
	}

	@Test
	public void test_replyToMessage_UserLoggedOff() {
		SubForum sf = addSubForum(forum, SUB_FORUM_NAMES[0], moderator);

		Thread t = openNewThread(sf, THREAD_TITLES[0], THREAD_CONTENTS[0], moderator);
		Message msg = t.getOpeningMessage();
		Message reply1 = replyToMessage(msg, THREAD_TITLES[1], THREAD_CONTENTS[1], user);
		Message reply2 = replyToMessage(msg, THREAD_TITLES[2], THREAD_CONTENTS[2], user);

		Assert.assertNull(reply1);
		Assert.assertNull(reply2);
		Assert.assertEquals(msg.getComments().size(), 2);
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

	@Test
	public void test_reportModerator_SameForum_UserHasPostedBeofre() {
		user = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		SubForum sf = addSubForum(forum, SUB_FORUM_NAMES[0], moderator);

		openNewThread(sf,THREAD_TITLES[0],THREAD_CONTENTS[0],user);
		boolean result = reportModeratorInForum(forum, user, moderator, REPORT_TITLES[0], REPORT_CONTENTS[0]);
		Assert.assertTrue(result);
	}

	@Test
	public void test_reportModerator_DifferentForum() {
		Forum otherForum = addForum("Other Forum", User.getSuperAdmin(), new ForumPolicy(10, ".", ForumPolicy.HashFunction.MD5,false));
		user = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		boolean result = reportModeratorInForum(otherForum, user, moderator, REPORT_TITLES[0], REPORT_CONTENTS[0]);
		Assert.assertFalse(result);
	}

	@Test
	public void test_reportModerator_SameForum_UserHasntPostedBeofre() {
		user = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		boolean result = reportModeratorInForum(forum, user,moderator, REPORT_TITLES[0], REPORT_CONTENTS[0]);
		Assert.assertFalse(result);
	}

	@Test
	public void test_emailNotification_ValidEmailAddress() {
		// TODO
	}

	@Test
	public void test_emailNotification_InvalidEmailAddress() {
		// TODO
	}

}
