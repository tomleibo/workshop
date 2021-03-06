package acceptanceTests.testCases;

import acceptanceTests.bridge.Driver;
import content.Forum;
import content.Message;
import content.SubForum;
import content.Thread;
import exceptions.*;
import org.junit.*;
import policy.ForumPolicy;
import users.FriendRequest;
import users.User;
import utils.HibernateUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MemberUserServicesTests extends ForumTests {

	static User user1;
	static User user2;

	@BeforeClass
	public static void register() throws Exception {
		user1 = registerToForum(theForum, USER_NAMES[0], USER_PASSES[0], USER_EMAILS[0]);
		user2 = registerToForum(theForum, USER_NAMES[1], USER_PASSES[1], USER_EMAILS[1]);
	}

	@After
	public void afterMethod() {
		theForum.getSubForums().clear();
		HibernateUtils.update(theForum);
	}
	
	@Test // 5.1
	public void test_login_ExistingUser() throws Exception {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		Assert.assertNotNull(user1);
	}

	@Test // 5.2
	public void test_login_NonExistingUser() throws UserAlreadyLoggedInException, NoSuchAlgorithmException, WrongPasswordException, NeedToChangePasswordException, UserNotLoggedInException {
		try {
			user1 = loginUser(theForum, USER_NAMES[2], USER_PASSES[2]);
			Assert.fail();
		} catch (UserDoesNotExistsException e) {
			Assert.assertTrue(true);
		}
	}

	@Test // 5.3
	public void test_login_WrongPassword() throws NoSuchAlgorithmException, UserDoesNotExistsException, WrongPasswordException, UserAlreadyLoggedInException, NeedToChangePasswordException, UserNotLoggedInException {
		try {
			user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[1]);
			Assert.fail();
		} catch (WrongPasswordException e) {
			Assert.assertTrue(true);
		}
	}


//	@Test // 5.4
//	 public void test_login_ExistingUser_DoubleLogin() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, NeedToChangePasswordException, UserNotLoggedInException {
//		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
//
//		try {
//			User userLoginsTwice = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
//		} catch (UserAlreadyLoggedInException e) {
//			Assert.assertTrue(true);
//			return;
//		}
//
//		Assert.assertTrue(false);
//		user1.logout();
//	}

	@Test // 5.5
	public void test_logoff_UserLoggedIn() throws UserNotLoggedInException, UserDoesNotExistsException, UserAlreadyLoggedInException, NoSuchAlgorithmException, WrongPasswordException, NeedToChangePasswordException {
		loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		user1 = logoffUser(theForum, user1);
		Assert.assertNotNull(user1);
	}

//	@Ignore
//	@Test // 5.6
//	public void test_logoff_UserNotLoggedIn() throws UserDoesNotExistsException {
//		try {
//			user1 = logoffUser(theForum, user1);
//		} catch (UserNotLoggedInException e) {
//			Assert.assertTrue(true);
//			return;
//		}
//
//		Assert.assertTrue(false);
//	}


	@Test // 5.7
	public void test_post_validParameters() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UserNotAuthorizedException, EmptyMessageTitleAndBodyException, NeedToChangePasswordException, UserNotLoggedInException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);

		Thread t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user1);
		Message msg = t.getOpeningMessage();


		Assert.assertNotNull(msg);
		Assert.assertEquals(msg.getBody(), THREAD_CONTENTS[0]);

		msg.deleteSelf();
	}

	@Test // 5.8
	public void test_post_emptyMessage() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UserNotAuthorizedException, NeedToChangePasswordException, UserNotLoggedInException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);

		try {
			Thread thread = openNewThread(theForum, sf, "", "", user1);
			Assert.fail();
		} catch (EmptyMessageTitleAndBodyException e) {
			Assert.assertTrue(true);
		}
	}
	
	@Test // 5.9
	public void test_editPost_ExistingMessage() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UserNotAuthorizedException, EmptyMessageTitleAndBodyException, NeedToChangePasswordException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);

		Thread t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user1);
		Message msg = t.getOpeningMessage();

		boolean result = editPost(theForum, sf, user1, msg, THREAD_CONTENTS[1]);
		msg = t.getOpeningMessage();

		Assert.assertTrue(result);
		Assert.assertEquals(msg.getBody(), THREAD_CONTENTS[1]);

		msg.deleteSelf();
	}
	
	
	@Test // 5.10
	public void test_deletePost_ExistingMessage() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UserNotAuthorizedException, EmptyMessageTitleAndBodyException, NeedToChangePasswordException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);

		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);
		
		Thread t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user1);
		Message msg = t.getOpeningMessage();
		
		boolean result = deletePost(theForum, sf, user1, msg);
		
		List<Thread> threads = showListOfThreads(sf);

		Assert.assertTrue(result);
		Assert.assertTrue(threads.isEmpty());

	}

	@Test // 5.11
	public void test_deletePost_NotTheOwnerOfMessage() throws EmptyMessageTitleAndBodyException, WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UserNotAuthorizedException, NeedToChangePasswordException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		user2 = loginUser(theForum, USER_NAMES[1], USER_PASSES[1]);
		user2.setState(User.MEMBER);

		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);

		Thread thread = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user1);
		Message msg = thread.getOpeningMessage();

		try {
			boolean result = deletePost(theForum,sf, user2, msg);
			Assert.fail();
		} catch (UserNotAuthorizedException e) {
			Assert.assertTrue(true);
			msg.deleteSelf();
		}
	}


	@Test // 5.12
	public void test_replyToMessage_UserLoggedIn() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UserNotAuthorizedException, EmptyMessageTitleAndBodyException, NeedToChangePasswordException {
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

		reply1.deleteSelf();
		reply2.deleteSelf();
		msg.deleteSelf();

	}

//	@Ignore
//	@Test // 5.13
//	public void test_replyToMessage_UserLoggedOff() throws Exception {
//		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
//		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);
//
//		Thread t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user1);
//		Message msg = t.getOpeningMessage();
//		try{
//			Message reply1 = replyToMessage(theForum, msg, THREAD_TITLES[1], THREAD_CONTENTS[1], user2);
//		}
//
//		catch(Exception e){
//			// TODO change to UserNotLoggedInException
//			Assert.assertTrue(true);
//			return;
//		}
//
//		Assert.assertTrue(false);
//	}
	

	@Test // 5.14
	public void test_addFriend_FriendExists() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UsernameAlreadyExistsException, UserNotAuthorizedException, NeedToChangePasswordException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		user2 = loginUser(theForum, USER_NAMES[1], USER_PASSES[1]);

		FriendRequest friendRequest = sendFriendRequest(theForum, user1, user2, FRIEND_MESSAGES[0]);
		Assert.assertNotNull(friendRequest);
		boolean result = replyToFriendRequest(theForum, user2, friendRequest, true);
		Assert.assertTrue(result);
		Assert.assertEquals(user1.getFriends().size(), 1);
		Assert.assertEquals(user2.getFriends().size(),1);
	}

	@Test // 5.15
	public void test_removeFriend_IsAFriend() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UsernameAlreadyExistsException, UserNotAuthorizedException, NeedToChangePasswordException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		user2 = loginUser(theForum, USER_NAMES[1], USER_PASSES[1]);

		FriendRequest friendRequest = sendFriendRequest(theForum, user1, user2, FRIEND_MESSAGES[0]);
		replyToFriendRequest(theForum, user2, friendRequest, true);

		boolean result = removeFriend(theForum, user1, user2);
		Assert.assertTrue(result);
		Assert.assertTrue(user1.getFriends().isEmpty());
		Assert.assertTrue(user2.getFriends().isEmpty());
	}

	@Test // 5.16
	public void test_removeFriend_IsntAFriend() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UsernameAlreadyExistsException, UserNotAuthorizedException, NeedToChangePasswordException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		user2 = loginUser(theForum, USER_NAMES[1], USER_PASSES[1]);

		boolean result = removeFriend(theForum, user1, user2);
		Assert.assertFalse(result);
	}

	@Test // 5.17
	public void test_reportModerator_SameForum_UserHasPostedBefore() throws Exception {
		HibernateUtils.cleanUp();
		driver = Driver.getDriver();
		superAdmin = initializeForumSystem(superAdminUsername, superAdminPassword, superAdminMail);
		policy = getPolicy(3, ".+", ForumPolicy.HashFunction.MD5);
		theForum = addForum(FORUM_NAMES[0], superAdmin, policy);
		user1 = registerToForum(theForum, USER_NAMES[0], USER_PASSES[0], USER_EMAILS[0]);
		user2 = registerToForum(theForum, USER_NAMES[1], USER_PASSES[1], USER_EMAILS[1]);

		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);

		Thread th = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user1);

		user2.setState(User.MODERATOR);
		changeModetator(theForum, sf, superAdmin, user2);

		boolean result = reportModeratorInForum(theForum, user1, user2, REPORT_TITLES[0], REPORT_CONTENTS[0]);
		Assert.assertTrue(result);

		th.getOpeningMessage().deleteSelf();
	}

	@Test // 5.18
	public void test_reportModerator_DifferentForum() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UsernameAlreadyExistsException, UserNotAuthorizedException, NeedToChangePasswordException {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		User otherSuperAdmin = User.newSuperAdmin(superAdminUsername, superAdminPassword, superAdminMail);

		Forum otherForum = addForum("Other Forum", otherSuperAdmin, getPolicy(10, ".", ForumPolicy.HashFunction.MD5));
		SubForum sf2 = addSubForum(otherForum, SUB_FORUM_NAMES[0], otherSuperAdmin);

		user2.setState((User.MODERATOR));

		try {
			boolean result = reportModeratorInForum(theForum, user1, user2, REPORT_TITLES[0], REPORT_CONTENTS[0]);
			Assert.fail();
		} catch (UserNotAuthorizedException e) {
			Assert.assertTrue(true);
		}
	}

	@Test // 5.19
	public void test_reportModerator_SameForum_UserHasntPostedBefore() throws Exception {
		HibernateUtils.cleanUp();
		driver = Driver.getDriver();
		superAdmin = initializeForumSystem(superAdminUsername, superAdminPassword, superAdminMail);
		policy = getPolicy(3, ".+", ForumPolicy.HashFunction.MD5);
		theForum = addForum(FORUM_NAMES[0], superAdmin, policy);
		user1 = registerToForum(theForum, USER_NAMES[0], USER_PASSES[0], USER_EMAILS[0]);
		user2 = registerToForum(theForum, USER_NAMES[1], USER_PASSES[1], USER_EMAILS[1]);

		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);

		user2.setState(User.MODERATOR);
		changeModetator(theForum, sf, superAdmin, user2);

		try {
			boolean result = reportModeratorInForum(theForum, user1, user2, REPORT_TITLES[0], REPORT_CONTENTS[0]);
			Assert.fail();
		} catch (UserNotAuthorizedException e) {
			Assert.assertTrue(true);
		}
	}

	@Test // 5.20
	public void test_logger() throws Exception {
		String line;
		registerToForum(theForum, "specialuserforloggertests", "specialpassword",  "specialuserforloggertests@gmail.com");
		user1 = loginUser(theForum, "specialuserforloggertests", "specialpassword");
		logoffUser(theForum, user1);

		FileReader fileReader =	new FileReader("action_log.log");
		BufferedReader bufferedReader =	new BufferedReader(fileReader);

		while((line = bufferedReader.readLine()) != null) {
			if (line.contains("specialuserforloggertests is now logged in")){
				Assert.assertTrue(true);
				return;
			}
		}

		// Always close files.
		bufferedReader.close();
	}
}
