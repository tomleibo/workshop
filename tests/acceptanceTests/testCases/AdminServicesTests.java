package acceptanceTests.testCases;

import content.SubForum;
import exceptions.*;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import users.User;
import users.userState.UserStates;

import java.security.NoSuchAlgorithmException;


public class AdminServicesTests extends ForumTests{

	static User admin;
	static User user;


	@BeforeClass
	public static void setState() throws UsernameAlreadyExistsException, NoSuchAlgorithmException, UserAlreadyLoggedInException, UserDoesNotExistsException, WrongPasswordException, UserNotAuthorizedException {
		admin = registerToForum(theForum,USER_NAMES[1],USER_PASSES[1], USER_EMAILS[1]);
		admin.setState(UserStates.newState(UserStates.ADMIN));
		changeAdmin(theForum, superAdmin, admin);
		admin = loginUser(theForum, USER_NAMES[1], USER_PASSES[1]);

		registerToForum(theForum, USER_NAMES[2], USER_PASSES[2], USER_EMAILS[2]);
		user = loginUser(theForum, USER_NAMES[2], USER_PASSES[2]);
	}

	@After
	public void afterMethod(){
		theForum.getSubForums().clear();
	}

	@Test
	public void test_cancelSubForum_ExistingSubForum() throws UserNotAuthorizedException {
		SubForum sf1 = addSubForum(theForum, SUB_FORUM_NAMES[0], admin);
		SubForum sf2 = addSubForum(theForum, SUB_FORUM_NAMES[1], admin);
		SubForum sf3 = addSubForum(theForum, SUB_FORUM_NAMES[2], admin);

		boolean result = cancelSubForum(theForum, sf1, superAdmin);
		Assert.assertTrue(result);
		Assert.assertEquals(showListOfSubForums(theForum, admin).size(), 2);

		result = cancelSubForum(theForum, sf2, admin);
		Assert.assertTrue(result);
		Assert.assertEquals(showListOfSubForums(theForum, admin).size(), 1);

		result = cancelSubForum(theForum, sf3, admin);
		Assert.assertTrue(result);
		Assert.assertEquals(showListOfSubForums(theForum, admin).size(),0);
	}

	@Test
	public void test_cancelSubForum_NonExistingSubForum() throws UserNotAuthorizedException {
		boolean result = cancelSubForum(theForum, null, admin);
		Assert.assertFalse(result);
	}

	@Test
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

	@Test
	public void test_addSubForum_ValidParameters() throws UserNotAuthorizedException {
		SubForum sf1 = addSubForum(theForum, SUB_FORUM_NAMES[0], admin);
		Assert.assertNotNull(sf1);
		Assert.assertEquals(sf1.getName(), SUB_FORUM_NAMES[0]);
	}

	@Test
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


}
