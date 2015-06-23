package acceptanceTests.testCases;

import content.Forum;
import content.Message;
import content.SubForum;
import content.Thread;
import controllers.UserController;
import exceptions.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import policy.ForumPolicy;
import users.Notification;
import users.User;
import utils.HibernateUtils;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static controllers.UserController.changePassword;
import static java.lang.Thread.sleep;


public class MemberUserServicesTests2 extends ForumTests {

	static User user1;
	static User user2;

	@BeforeClass
	public static void register() throws Exception {
		user1 = registerToForum(theForum, USER_NAMES[0], USER_PASSES[0], USER_EMAILS[0]);
		user2 = registerToForum(theForum, USER_NAMES[1], USER_PASSES[1], USER_EMAILS[1]);
//		moderator = registerToForum(theForum, USER_NAMES[1], USER_PASSES[1], USER_EMAILS[1]);
//		moderator.setState(UserStates.newState(UserStates.MODERATOR));
	}

	@After
	public void afterMethod() {
		for(User u : theForum.getMembers()){
			u.getPendingNotifications().clear();
		}
		theForum.getSubForums().clear();
		HibernateUtils.update(theForum);
	}

	@Test // 6.1 - uc 16
	public void test_post_NotificationTooRelevant() throws Exception {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		user2 = loginUser(theForum, USER_NAMES[1], USER_PASSES[1]);
		User guest = enterAsGuest(theForum);

		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);

		Thread t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user1);
		Message msg = t.getOpeningMessage();

		//check user2 and guest have appending notifications
		List<Notification> notifList = getPendingNotifications(theForum,user2);
		Assert.assertEquals(notifList.size(), 1);

		List<Notification> notifList2 = getPendingNotifications(theForum,guest);
		Assert.assertEquals(notifList2.size(), 1);

		Message reply1 = replyToMessage(theForum, msg, THREAD_TITLES[1], THREAD_CONTENTS[1],user2);

		//check user1 and guest have appending notifications
		notifList = getPendingNotifications(theForum,user1);
		Assert.assertEquals(notifList.size(), 2);

		notifList2 = getPendingNotifications(theForum,guest);
		Assert.assertEquals(notifList2.size(), 2);

		reply1.deleteSelf();
		msg.deleteSelf();
	}

	@Test // 6.2 - uc 17
	public void test_change_content_post_NotificationTooRelevant() throws Exception {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		user2 = loginUser(theForum, USER_NAMES[1], USER_PASSES[1]);
		User guest = enterAsGuest(theForum);

		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);

		Thread t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user1);
		Message msg = t.getOpeningMessage();

		Message reply1 = replyToMessage(theForum, msg, THREAD_TITLES[1], THREAD_CONTENTS[1], user2);
		//Message reply2 = replyToMessage(theForum, msg, THREAD_TITLES[2], THREAD_CONTENTS[2], guest);

		Boolean result = editPost(theForum, sf, user1, msg, THREAD_CONTENTS[1]);

		//check user2 and guest have appending notifications
		List<Notification> notifList = getPendingNotifications(theForum, user2);
		Assert.assertEquals(notifList.size(), 3);

		List<Notification> notifList2 = getPendingNotifications(theForum, guest);
		Assert.assertEquals(notifList2.size(), 2);


		//check user1 didn't get a notification
		notifList = getPendingNotifications(theForum,user1);
		Assert.assertEquals(notifList.size(), 2);


		reply1.deleteSelf();
		//reply2.deleteSelf();
		msg.deleteSelf();
	}

	@Test // 6.3 - uc 16
	public void test_poster_get_Notified() throws Exception {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);

		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);

		Thread t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user1);
		Message msg = t.getOpeningMessage();

		List<Notification> notifList = getPendingNotifications(theForum,user1);
		Assert.assertEquals(notifList.size(), 1);

		msg.deleteSelf();
	}

	@Test // 6.4 - uc 17
	public void test_change_delete_post_NotificationTooRelevant() throws Exception {
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		user2 = loginUser(theForum, USER_NAMES[1], USER_PASSES[1]);
		User guest = enterAsGuest(theForum);

		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);

		Thread t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user1);
		Message msg = t.getOpeningMessage();

		Message reply1 = replyToMessage(theForum, msg, THREAD_TITLES[1], THREAD_CONTENTS[1], user2);
		//Message reply2 = replyToMessage(theForum, msg, THREAD_TITLES[2], THREAD_CONTENTS[2], guest);

		Boolean result = deletePost(theForum, sf, user1, msg);

		//check user2 and guest have appending notifications
		List<Notification> notifList = getPendingNotifications(theForum, user2);
		Assert.assertEquals(notifList.size(), 3);

		List<Notification> notifList2 = getPendingNotifications(theForum, guest);
		Assert.assertEquals(notifList2.size(), 2);


		//check user1 didn't get a notification
		notifList = getPendingNotifications(theForum,user1);
		Assert.assertEquals(notifList.size(), 2);


//		reply1.deleteSelf();
//		reply2.deleteSelf();
//		msg.deleteSelf();
	}


	@Test // 6.5
	public void test_change_to_the_same_password() throws Exception{
		user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		user2 = loginUser(theForum, USER_NAMES[1], USER_PASSES[1]);

		try {
			changePassword(user1, USER_PASSES[0], USER_PASSES[0]);
			Assert.fail();
		} catch (PasswordAlreadyUsedException e) {
			Assert.assertTrue(true);
		}
	}

//	public static User register(Forum forum, String username, String password, String emailAddress) throws UsernameAlreadyExistsException, NoSuchAlgorithmException, PasswordNotMatchesRegexException, EmptyFieldException, IdentificationQuestionMissingException {
//	public static User register(Forum forum, String username, String password, String emailAddress, String question, String answer) throws UsernameAlreadyExistsException, NoSuchAlgorithmException, PasswordNotMatchesRegexException, EmptyFieldException, IdentificationQuestionMissingException {

		@Test // 6.6
			 public void test_identifing_questions() throws Exception{

		policy = new ForumPolicy(1, ".+", ForumPolicy.HashFunction.MD5, false, 7 * 24 * 60 * 60 * 1000, 24 * 60 * 60 * 1000, true, -1, true, 0, 0);

		Forum theForum2 = addForum(FORUM_NAMES[1], superAdmin, policy);
		User user3;
		try {
			user3 = registerToForum(theForum2, USER_NAMES[2], USER_PASSES[2], USER_EMAILS[2]);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertTrue(true);
		}

		user3 = UserController.register(theForum2, USER_NAMES[2], USER_PASSES[2], USER_EMAILS[2], ID_QUESTIONS[0], ID_ANSWERS[0]);
		Assert.assertNotNull(user3);



	}

	@Test // 6.7
	public void test_password_expiration() throws Exception{
		// set forum policy to have a one milisecond expiration password
		policy = new ForumPolicy(1, ".+", ForumPolicy.HashFunction.MD5, false, 7 * 24 * 60 * 60 * 1000, 24 * 60 * 60 * 1000, false, 3000, true, 0, 0);

		Forum theForum2 = addForum(FORUM_NAMES[1], superAdmin, policy);
		User user3 = registerToForum(theForum2, USER_NAMES[2], USER_PASSES[2], USER_EMAILS[2]);
		user3.logout();
		sleep(3000);
		try {
			user3 = loginUser(theForum2, USER_NAMES[2], USER_PASSES[2]);
			Assert.fail();
		} catch (NeedToChangePasswordException e) {
			Assert.assertTrue(true);
		}
		changePassword(user3,USER_PASSES[2],USER_PASSES[1]);
		user3 = null;
		user3 = loginUser(theForum2, USER_NAMES[2], USER_PASSES[1]);
		Assert.assertNotNull(user3);
	}
}
