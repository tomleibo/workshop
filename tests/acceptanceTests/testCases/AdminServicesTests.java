package acceptanceTests.testCases;

import com.sun.xml.internal.ws.policy.Policy;
import content.*;
import content.Thread;
import exceptions.*;
import junit.framework.Assert;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import policy.ForumPolicy;
import users.User;
import utils.HibernateUtils;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

import static controllers.AdminController.replaceModerator;
import static java.lang.Thread.sleep;


public class AdminServicesTests extends ForumTests{

	static User admin;
	static User user;
	static User user5;


	@BeforeClass
	public static void setState() throws Exception {
		admin = registerToForum(theForum,USER_NAMES[1],USER_PASSES[1], USER_EMAILS[1]);
		admin.setState(User.ADMIN);
		changeAdmin(theForum, superAdmin, admin);
		admin = loginUser(theForum, USER_NAMES[1], USER_PASSES[1]);

		registerToForum(theForum, USER_NAMES[2], USER_PASSES[2], USER_EMAILS[2]);
		user = loginUser(theForum, USER_NAMES[2], USER_PASSES[2]);

		registerToForum(theForum, USER_NAMES[0], USER_PASSES[0], USER_EMAILS[0]);
		user5 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
	}

	@After
	public void afterMethod() throws Exception{
		for (SubForum sub: theForum.getSubForums()){
			HibernateUtils.del(sub);
		}
		theForum.getSubForums().clear();
		HibernateUtils.update(theForum);

		changeForumPolicy(theForum, policy, admin);
		admin.setState(User.ADMIN);
		admin.logout();
		user.logout();
		user5.logout();

		admin = loginUser(theForum, USER_NAMES[1], USER_PASSES[1]);
		user = loginUser(theForum, USER_NAMES[2], USER_PASSES[2]);
		user5 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);




	}

	@Test // 1.1
	public void test_cancelSubForum_ExistingSubForum() throws UserNotAuthorizedException {
		SubForum sf1 = addSubForum(theForum, SUB_FORUM_NAMES[0], admin);
		SubForum sf2 = addSubForum(theForum, SUB_FORUM_NAMES[1], admin);
		SubForum sf3 = addSubForum(theForum, SUB_FORUM_NAMES[2], admin);

		boolean result = cancelSubForum(theForum, sf1, superAdmin);
//		Assert.assertTrue(result);
		Assert.assertEquals(showListOfSubForums(theForum, admin).size(), 2);

		result = cancelSubForum(theForum, sf2, admin);
//		Assert.assertTrue(result);
		Assert.assertEquals(showListOfSubForums(theForum, admin).size(), 1);

		result = cancelSubForum(theForum, sf3, admin);
//		Assert.assertTrue(result);
		Assert.assertEquals(showListOfSubForums(theForum, admin).size(), 0);
	}

	@Test // 1.2
	public void test_cancelSubForum_NonExistingSubForum() throws UserNotAuthorizedException {
		boolean result = cancelSubForum(theForum, null, admin);
		Assert.assertFalse(result);
	}

	@Test // 1.3
	public void test_cancelSubForum_UnAuthorizedUser() throws UsernameAlreadyExistsException, NoSuchAlgorithmException, UserNotAuthorizedException {
		SubForum sf1 = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);

		try{
			boolean result = cancelSubForum(theForum, sf1, user);
		}
		catch(UserNotAuthorizedException e){
			Assert.assertTrue(true);
			return;
		}

		Assert.assertTrue(false);
	}

	@Test // 1.4
	public void test_addSubForum_ValidParameters() throws UserNotAuthorizedException {
		SubForum sf1 = addSubForum(theForum, SUB_FORUM_NAMES[0], admin);
		Assert.assertNotNull(sf1);
		Assert.assertEquals(sf1.getName(), SUB_FORUM_NAMES[0]);
	}

	@Test // 1.5
	public void test_addSubForum_UnAuthorizedUser() throws UsernameAlreadyExistsException, NoSuchAlgorithmException, UserAlreadyLoggedInException, UserDoesNotExistsException, WrongPasswordException {
		try{
			SubForum sf1 = addSubForum(theForum, SUB_FORUM_NAMES[0], user);
		}
		catch(UserNotAuthorizedException e){
			Assert.assertTrue(true);
			return;
		}

		Assert.assertTrue(false);
	}

	// version 2
	@Test // 1.6
	public void test_appoint_moderator() throws Exception {
		SubForum sf1 = addSubForum(theForum, SUB_FORUM_NAMES[0], admin);

		Assert.assertTrue(appointModerator(theForum,sf1, admin, user));
		Assert.assertTrue(sf1.getModerators().contains(user));


		user.setState(User.MEMBER);
	}

	@Test // 1.7
	public void test_appoint_user_to_subforum_of_not_the_same_forum() throws Exception {
		Forum f1 = addForum(FORUM_NAMES[1], superAdmin, policy);

		//creating a new forum f1 with a new admin1
		User admin1 = registerToForum(f1, USER_NAMES[3], USER_PASSES[3], USER_EMAILS[3]);
		admin1.setState(User.ADMIN);
		changeAdmin(f1, superAdmin, admin1);
		admin1 = loginUser(f1, USER_NAMES[3], USER_PASSES[3]);

		SubForum sf1 = addSubForum(f1, SUB_FORUM_NAMES[0], admin1);

        try {
            Assert.assertFalse(appointModerator(f1,sf1, admin1, user));
            Assert.fail();
        } catch (UserNotAuthorizedException e) {
            Assert.assertTrue(true);
        }
	}

	@Test // 1.8
	public void test_unappoint_moderator() throws Exception {
		SubForum sf1 = addSubForum(theForum, SUB_FORUM_NAMES[0], admin);

		Assert.assertTrue(appointModerator(theForum, sf1, admin, user));
		Assert.assertTrue(sf1.getModerators().contains(user));
		Assert.assertTrue(unAppoint(theForum, sf1, admin, user));
		Assert.assertFalse(sf1.getModerators().contains(user));

		user.setState(User.MEMBER);
	}

	@Test // 1.9
	public void test_unappoint_regular_user() throws Exception{
		SubForum sf1 = addSubForum(theForum, SUB_FORUM_NAMES[0], admin);

		Assert.assertFalse(sf1.getModerators().contains(user));
		try {
			unAppoint(theForum, sf1, admin, user);
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
		Assert.assertFalse(sf1.getModerators().contains(user));

		user.setState(User.MEMBER);
	}

	@Test // 1.10
	public void test_unappoint_moderator_by_admin_not_the_same_forum() throws Exception {
		Forum f1 = addForum(FORUM_NAMES[1], superAdmin, policy);

		//creating a new forum f1 with a new admin1
		User admin1 = registerToForum(f1, USER_NAMES[3], USER_PASSES[3], USER_EMAILS[3]);
		admin1.setState(User.ADMIN);
		changeAdmin(f1, superAdmin, admin1);
		admin1 = loginUser(f1, USER_NAMES[3], USER_PASSES[3]);

		SubForum sf1 = addSubForum(theForum, SUB_FORUM_NAMES[0], admin);

		Assert.assertTrue(appointModerator(theForum, sf1, admin, user));

		try {
			Assert.assertFalse(unAppoint(theForum, sf1, admin1, user));
			Assert.fail();
		} catch (UserNotAuthorizedException e) {
			Assert.assertTrue(true);
		}
		Assert.assertTrue(sf1.getModerators().contains(user));
	}

	@Test // 1.11
	public void test_report_number_of_mesg_in_subforum() throws Exception{
		SubForum sf1 = addSubForum(theForum, SUB_FORUM_NAMES[0], admin);

		Assert.assertEquals(0, getReportTotalMessagesInSubForum(theForum, admin, sf1));

		content.Thread t = openNewThread(theForum, sf1, THREAD_TITLES[0], THREAD_CONTENTS[0], user);
		Message msg = t.getOpeningMessage();

		Assert.assertEquals(1, getReportTotalMessagesInSubForum(theForum, admin, sf1));

		deletePost(theForum, sf1, user, msg);
		//msg.deleteSelf();
		Assert.assertEquals(0, getReportTotalMessagesInSubForum(theForum, admin, sf1));
	}

	@Test // 1.12
	public void test_report_msg_from_member() throws Exception{
		SubForum sf1 = addSubForum(theForum, SUB_FORUM_NAMES[0], admin);

		content.Thread t = openNewThread(theForum, sf1, THREAD_TITLES[0], THREAD_CONTENTS[0], user);
		Message msg = t.getOpeningMessage();

		List<Message> msgsOfUser = getReportTotalMessagesOfMember(theForum, admin, user);

		Assert.assertEquals(1, msgsOfUser.size());
		Assert.assertTrue(msgsOfUser.contains(msg));

		deletePost(theForum, sf1, user, msg);
		//msg.deleteSelf();
	}


	@Test // 1.13
	public void test_report_moderator_list() throws Exception{
		SubForum sf1 = addSubForum(theForum, SUB_FORUM_NAMES[0], admin);
		appointModerator(theForum, sf1, admin, user);

		Set<User> modList = getReportModeratorList(theForum, admin);

		Assert.assertEquals(2, modList.size());
		Assert.assertTrue(modList.contains(user));

		user.setState(User.MEMBER);
	}

	@Test // 1.14
	public void test_delete_permissions() throws Exception{
		ForumPolicy ownerAndManagerPol = new ForumPolicy(1, ".+", ForumPolicy.HashFunction.MD5, false, 7 * 24 * 60 * 60 * 1000, 24 * 60 * 60 * 1000, false, -1, false, 0, 0);
		ForumPolicy ownerAndManagerAndModPol = new ForumPolicy(1, ".+", ForumPolicy.HashFunction.MD5, false, 7 * 24 * 60 * 60 * 1000, 24 * 60 * 60 * 1000, false, -1, true, 0, 0);
		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], admin);
		appointModerator(theForum, sf, admin, user5);

		changeForumPolicy(theForum, ownerAndManagerPol , admin);

		//owner deletion

		Thread t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user);
		Message msg = t.getOpeningMessage();

		boolean result = deletePost(theForum, sf, user, msg);
		Assert.assertTrue(result);

		List<Thread> threads = showListOfThreads(sf);

		Assert.assertTrue(result);
		Assert.assertTrue(threads.isEmpty());

		//manager deletion
		t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user);
		msg = t.getOpeningMessage();

		try {
			result = deletePost(theForum, sf, user5, msg);
			Assert.fail();
		} catch (UserNotAuthorizedException e) {
			Assert.assertTrue(true);
		}

		result = deletePost(theForum, sf, admin, msg);
		Assert.assertTrue(result);

		threads = showListOfThreads(sf);

		Assert.assertTrue(result);
		Assert.assertTrue(threads.isEmpty());

		//moderator deletion

		changeForumPolicy(theForum, ownerAndManagerAndModPol , admin);

		t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user);
		msg = t.getOpeningMessage();

		result = deletePost(theForum, sf, user5, msg);
		Assert.assertTrue(result);

		threads = showListOfThreads(sf);

		Assert.assertTrue(result);
		Assert.assertTrue(threads.isEmpty());


		//manager deletion

		t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user);
		msg = t.getOpeningMessage();

		result = deletePost(theForum, sf, admin, msg);
		Assert.assertTrue(result);

		threads = showListOfThreads(sf);

		Assert.assertTrue(result);
		Assert.assertTrue(threads.isEmpty());

		//owner deletion

		t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user);
		msg = t.getOpeningMessage();

		result = deletePost(theForum, sf, user, msg);
		Assert.assertTrue(result);

		threads = showListOfThreads(sf);

		Assert.assertTrue(result);
		Assert.assertTrue(threads.isEmpty());
	}

	@Test //1.15
	public void test_add_moderator_policy_msgs() throws Exception{
		ForumPolicy modVeteckAndMinMsgsPol = new ForumPolicy(1, ".+", ForumPolicy.HashFunction.MD5, false, 7 * 24 * 60 * 60 * 1000, 24 * 60 * 60 * 1000, false, -1, false, 1, 0);
		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], admin);

		changeForumPolicy(theForum, modVeteckAndMinMsgsPol , admin);
		user5 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);

		try {
			appointModerator(theForum, sf, admin, user5);
			Assert.fail();
		} catch (UserCantBeModeratorException e) {
			Assert.assertTrue(true);
		}

		Thread t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], user5);
		Message msg = t.getOpeningMessage();

		Assert.assertTrue(appointModerator(theForum, sf, admin, user5));

		boolean result = deletePost(theForum, sf, admin, msg);
		Assert.assertTrue(result);
		unAppoint(theForum,sf,admin,user5);
	}

	@Test //1.16
	public void test_add_moderator_policy() throws Exception{
		ForumPolicy modVeteckAndMinMsgsPol = new ForumPolicy(1, ".+", ForumPolicy.HashFunction.MD5, false, 7 * 24 * 60 * 60 * 1000, 24 * 60 * 60 * 1000, false, -1, false, 0, 10000);
		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], admin);

		changeForumPolicy(theForum, modVeteckAndMinMsgsPol , admin);
		user5 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);

		try {
			appointModerator(theForum, sf, admin, user5);
			Assert.fail();
		} catch (UserCantBeModeratorException e) {
			Assert.assertTrue(true);
		}

		sleep(8000);

		Assert.assertTrue(appointModerator(theForum, sf, admin, user5));
		unAppoint(theForum,sf,admin,user5);
	}

	@Test // 1.8
	public void test_unappoint_single_moderator()throws Exception {
		SubForum sf1 = addSubForum(theForum, SUB_FORUM_NAMES[0], admin);


		replaceModerator(theForum, sf1, admin, admin, user);

		Assert.assertTrue(sf1.getModerators().contains(user));

		try {
			unAppoint(theForum, sf1, admin, user);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertTrue(true);
		}

		Assert.assertTrue(sf1.getModerators().contains(user));

		user.setState(User.MEMBER);
	}
}
