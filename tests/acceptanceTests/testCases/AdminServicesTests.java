package acceptanceTests.testCases;

import com.sun.xml.internal.ws.policy.Policy;
import content.Forum;
import content.SubForum;
import exceptions.*;
import junit.framework.Assert;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import policy.ForumPolicy;
import users.User;
import utils.HibernateUtils;

import java.security.NoSuchAlgorithmException;


public class AdminServicesTests extends ForumTests{

	static User admin;
	static User user;


	@BeforeClass
	public static void setState() throws Exception {
		admin = registerToForum(theForum,USER_NAMES[1],USER_PASSES[1], USER_EMAILS[1]);
		admin.setState(User.ADMIN);
		changeAdmin(theForum, superAdmin, admin);
		admin = loginUser(theForum, USER_NAMES[1], USER_PASSES[1]);

		registerToForum(theForum, USER_NAMES[2], USER_PASSES[2], USER_EMAILS[2]);
		user = loginUser(theForum, USER_NAMES[2], USER_PASSES[2]);
	}

	@After
	public void afterMethod(){
		for (SubForum sub: theForum.getSubForums()){
			HibernateUtils.del(sub);
		}
		theForum.getSubForums().clear();
		HibernateUtils.update(theForum);
	}

	@Test // 1.1
	public void test_cancelSubForum_ExistingSubForum() throws UserNotAuthorizedException {
		SubForum sf1 = addSubForum(theForum, SUB_FORUM_NAMES[0], admin);
		SubForum sf2 = addSubForum(theForum, SUB_FORUM_NAMES[1], admin);
		SubForum sf3 = addSubForum(theForum, SUB_FORUM_NAMES[2], admin);

		boolean result = cancelSubForum(theForum, sf1, superAdmin);
		Assert.assertTrue(result);
		Assert.assertEquals(showListOfSubForums(theForum, admin).size(),2);

		result = cancelSubForum(theForum, sf2, admin);
		Assert.assertTrue(result);
		Assert.assertEquals(showListOfSubForums(theForum, admin).size(),1);

		result = cancelSubForum(theForum, sf3, admin);
		Assert.assertTrue(result);
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
	public void test_appoint_moderator() throws UserNotAuthorizedException {
		SubForum sf1 = addSubForum(theForum, SUB_FORUM_NAMES[0], admin);

		Assert.assertTrue(appointModerator(theForum,sf1, admin, user));
		Assert.assertTrue(sf1.getModerators().contains(user));


		user.setState(User.MEMBER);
	}

	@Test // 1.7
	public void test_appoint_user_to_subforum_of_not_the_same_forum() throws Exception {
		Forum f1 = addForum(FORUM_NAMES[1], superAdmin, policy);

		//creating a new forum f1 with a new admin1
		User admin1 = registerToForum(f1, USER_NAMES[1], USER_PASSES[1], USER_EMAILS[1]);
		admin1.setState(User.ADMIN);
		changeAdmin(f1, superAdmin, admin1);
		admin1 = loginUser(f1, USER_NAMES[1], USER_PASSES[1]);

		SubForum sf1 = addSubForum(f1, SUB_FORUM_NAMES[0], admin1);

		Assert.assertFalse(appointModerator(f1,sf1, admin1, user));
		//Assert.assertTrue(sf1.getModerators().contains(user));


		//user.setState(User.MEMBER);
	}






}
