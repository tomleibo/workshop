package acceptanceTests.testCases;

import content.Forum;
import content.Message;
import content.SubForum;
import content.Thread;
import exceptions.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import policy.ForumPolicy;
import users.User;
import users.userState.UserStates;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MemberUserServicesTests extends ForumTests {

	User user1;
	User user2;
	
	@Before
	public void register() throws UsernameAlreadyExistsException, NoSuchAlgorithmException {
		user1 = registerToForum(theForum, USER_NAMES[0], USER_PASSES[0], USER_EMAILS[0]);
		user2 = registerToForum(theForum, USER_NAMES[1], USER_PASSES[1], USER_EMAILS[1]);
//		moderator = registerToForum(theForum, USER_NAMES[1], USER_PASSES[1], USER_EMAILS[1]);
//		moderator.setState(UserStates.newState(UserStates.MODERATOR));
	}
	
	@Test
	public void test_login_ExistingUser() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		Assert.assertNotNull(user1);
		Assert.assertTrue(user1.isLoggedIn());
	}

	@Test
	public void test_login_NonExistingUser() throws UserAlreadyLoggedInException, NoSuchAlgorithmException, WrongPasswordException {
		try {
			user1 = loginUser(theForum, USER_NAMES[2], USER_PASSES[2]);
		} catch (UserDoesNotExistsException e) {
			Assert.assertTrue(true);
			return;
		}

		Assert.assertTrue(false);
	}

	@Test
	public void test_login_WrongPassword() throws NoSuchAlgorithmException, UserDoesNotExistsException, WrongPasswordException, UserAlreadyLoggedInException {
		try {
			user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[1]);
		} catch (WrongPasswordException e) {
			Assert.assertTrue(true);
			return;
		}

		Assert.assertTrue(false);
	}


	@Test
	 public void test_login_ExistingUser_DoubleLogin() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);

		try {
			User userLoginsTwice = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		} catch (UserAlreadyLoggedInException e) {
			Assert.assertTrue(true);
			return;
		}

		Assert.assertTrue(false);
	}

	@Test
	public void test_logoff_UserLoggedIn() {
		//TODO
	}

	@Test
	public void test_logoff_UserNotLoggedIn() {
		//TODO
	}

	@Test
	public void test_logoff_WrongPassword() {
		//TODO
	}

	@Test
	public void test_post_validParameters() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UserNotAuthorizedException, EmptyMessageTitleAndBodyException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);

		Thread t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user1);
		Message msg = t.getOpeningMessage();

		Assert.assertNotNull(msg);
		Assert.assertEquals(msg.getBody(), THREAD_CONTENTS[0]);
	}

	@Test
	public void test_post_emptyMessage() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UserNotAuthorizedException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);

		try {
			Thread thread = openNewThread(theForum, sf, "", "", user1);
		} catch (EmptyMessageTitleAndBodyException e) {
			Assert.assertTrue(true);
			return;
		}

		Assert.assertTrue(false);

	}
	
	@Test
	public void test_editPost_ExistingMessage() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UserNotAuthorizedException, EmptyMessageTitleAndBodyException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);
		
		Thread t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user1);
		Message msg = t.getOpeningMessage();
		
		boolean result = editPost(theForum, user1, msg, THREAD_CONTENTS[1]);
		msg = t.getOpeningMessage();

		Assert.assertTrue(result);
		Assert.assertEquals(msg.getBody(), THREAD_CONTENTS[1]);
	}
	
	
	@Test
	public void test_deletePost_ExistingMessage() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UserNotAuthorizedException, EmptyMessageTitleAndBodyException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);

		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);
		
		Thread t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user1);
		Message msg = t.getOpeningMessage();
		
		boolean result = deletePost(theForum, sf, user1, msg);
		
		List<Thread> threads = showListOfThreads(sf);

		Assert.assertTrue(result);
		Assert.assertTrue(threads.isEmpty());
	}

	@Test
	public void test_deletePost_NonExistingMessage() throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException, WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);

		openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user1);
		Message msg = new Message(THREAD_TITLES[1], THREAD_CONTENTS[1], user1, null, null);

		boolean result = deletePost(theForum,sf, user1, msg);
		// TODO different exception?
		Assert.assertFalse(result);
	}


	@Test
	public void test_replyToMessage_UserLoggedIn() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UserNotAuthorizedException, EmptyMessageTitleAndBodyException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		user2 = loginUser(theForum, USER_NAMES[1], USER_PASSES[1]);

		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);

		Thread t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user1);
		Message msg = t.getOpeningMessage();
		Message reply1 = replyToMessage(theForum, msg, THREAD_TITLES[1], THREAD_CONTENTS[1], user2);
		Message reply2 = replyToMessage(theForum, msg, THREAD_TITLES[2], THREAD_CONTENTS[2], user2);

		Assert.assertNotNull(reply1);
		Assert.assertNotNull(reply2);
		Assert.assertEquals(msg.getComments().size(), 2);
	}

	@Test
	public void test_replyToMessage_UserLoggedOff() throws UserNotAuthorizedException, WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, EmptyMessageTitleAndBodyException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);

		Thread t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user1);
		Message msg = t.getOpeningMessage();
		try{
			Message reply1 = replyToMessage(theForum, msg, THREAD_TITLES[1], THREAD_CONTENTS[1], user2);
		}
		catch(Exception e){
			// TODO change to UserNotLoggedInException
			Assert.assertTrue(true);
			return;
		}

		Assert.assertTrue(false);
	}
	

	@Test
	public void test_addFriend() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UsernameAlreadyExistsException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		User friend = registerToForum(theForum, USER_NAMES[1], USER_PASSES[1], USER_EMAILS[1]);
		boolean result = sendFriendRequest(user1, friend, FRIEND_MESSAGES[0]);

		Assert.assertTrue(result);
	}

	@Test
	public void test_removeFriend() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UsernameAlreadyExistsException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		User friend = registerToForum(theForum, USER_NAMES[1], USER_PASSES[1], USER_EMAILS[1]);
		boolean result = removeFriend(user1, friend);

		Assert.assertTrue(result);
	}

	@Test
	public void test_reportModerator_SameForum_UserHasPostedBeofre() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UserNotAuthorizedException, UsernameAlreadyExistsException, EmptyMessageTitleAndBodyException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);

		openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user1);

		User moderator = registerToForum(theForum, USER_NAMES[1], USER_PASSES[1], USER_EMAILS[1]);
		moderator.setState(UserStates.newState(UserStates.MODERATOR));
		changeModetator(theForum, sf, superAdmin, moderator);

		boolean result = reportModeratorInForum(theForum, user1, moderator, REPORT_TITLES[0], REPORT_CONTENTS[0]);
		Assert.assertTrue(result);
	}

	@Test
	public void test_reportModerator_DifferentForum() throws UserNotAuthorizedException, WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UsernameAlreadyExistsException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		User otherSuperAdmin = User.newSuperAdmin(superAdminUsername, superAdminPassword, superAdminMail);

		Forum otherForum = addForum("Other Forum", otherSuperAdmin, getPolicy(10, ".", ForumPolicy.HashFunction.MD5));
		SubForum sf2 = addSubForum(otherForum, SUB_FORUM_NAMES[0], otherSuperAdmin);

		User moderator = registerToForum(otherForum, USER_NAMES[1], USER_PASSES[1], USER_EMAILS[1]);
		moderator.setState(UserStates.newState(UserStates.MODERATOR));
		changeModetator(otherForum, sf2, otherSuperAdmin, moderator);

		boolean result = reportModeratorInForum(theForum, user1, moderator, REPORT_TITLES[0], REPORT_CONTENTS[0]);
		Assert.assertFalse(result);
	}

	@Test
	public void test_reportModerator_SameForum_UserHasntPostedBeofre() throws UserNotAuthorizedException, UsernameAlreadyExistsException, NoSuchAlgorithmException, UserAlreadyLoggedInException, UserDoesNotExistsException, WrongPasswordException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);

		User moderator = registerToForum(theForum, USER_NAMES[1], USER_PASSES[1], USER_EMAILS[1]);
		moderator.setState(UserStates.newState(UserStates.MODERATOR));
		changeModetator(theForum, sf, superAdmin, moderator);

		boolean result = reportModeratorInForum(theForum, user1, moderator, REPORT_TITLES[0], REPORT_CONTENTS[0]);
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
