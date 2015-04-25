package acceptanceTests.testCases;

import content.Forum;
import content.ForumSystem;
import exceptions.*;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import policy.ForumPolicy;
import users.User;

import java.security.NoSuchAlgorithmException;

public class ForumManagementServicesTests extends ForumTests {

	@Test
	public void test_defineProperties_ValidParameters() throws UserNotAuthorizedException {
		ForumPolicy fp = getPolicy(10, "[1-9]^10", ForumPolicy.HashFunction.MD5);
		boolean result = changeForumPolicy(theForum, fp, superAdmin);
		Assert.assertTrue(result);
	}

//	@Ignore
//	@Test
//	public void test_defineProperties_InvalidParameters() throws UserNotAuthorizedException {
//		ForumPolicy fp = new ForumPolicy(-100, null, null);
//		boolean result = changeForumPolicy(theForum, fp, superAdmin);
//		Assert.assertFalse(result);
//	}

	@Test
	public void test_defineProperties_UnAuthorizedUser() throws UsernameAlreadyExistsException, NoSuchAlgorithmException, UserAlreadyLoggedInException, UserDoesNotExistsException, WrongPasswordException {
		ForumPolicy fp = getPolicy(10, "[1-9]^10", ForumPolicy.HashFunction.MD5);
		registerToForum(theForum, USER_NAMES[0], USER_PASSES[0], USER_EMAILS[0]);
		User user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);

		try {
			boolean result = changeForumPolicy(theForum, fp, user1);
		} catch (UserNotAuthorizedException e) {
			org.junit.Assert.assertTrue(true);
			return;
		}

		org.junit.Assert.assertTrue(false);
	}

	@Test
	public void test_initialization_SystemExists() throws NoSuchAlgorithmException {
		try{
		ForumSystem otherSystem = initializeForumSystem(USER_NAMES[2], USER_PASSES[2], USER_EMAILS[2]);
		}
		catch(ForumSystemAlreadyExistsException e){
			Assert.assertTrue(true);
			return;
		}

		Assert.assertTrue(false);
	}

	@Test
	public void test_addNewForum_FreeName() {
		Forum otherForum = null;
		try {
			otherForum = addForum(FORUM_NAMES[1], superAdmin, getPolicy(10, "[1-9]^10", ForumPolicy.HashFunction.MD5));
		} catch (UserNotAuthorizedException e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(otherForum);
		org.junit.Assert.assertEquals(otherForum.getName(), FORUM_NAMES[1]);
	}

//	@Ignore
//	@Test
//	 public void test_addNewForum_ForumAlreadyExists(){
//		try{
//			Forum otherForum = addForum(FORUM_NAMES[0], superAdmin, getPolicy(3, ".", ForumPolicy.HashFunction.MD5));
//		}
//		catch(Exception e){
//			// TODO change to ForumAlreadyExistsException
//		}
//	}

	@Test
	public void test_addNewForum_UserUnAuthorized() throws UsernameAlreadyExistsException, NoSuchAlgorithmException, UserAlreadyLoggedInException, UserDoesNotExistsException, WrongPasswordException {
		registerToForum(theForum, USER_NAMES[2], USER_PASSES[2], USER_EMAILS[2]);
		User user = loginUser(theForum, USER_NAMES[2], USER_PASSES[2]);

		try {
			Forum otherForum = addForum(FORUM_NAMES[1], user, getPolicy(3, ".", ForumPolicy.HashFunction.MD5));
		} catch (UserNotAuthorizedException e) {
			org.junit.Assert.assertTrue(true);
			return;
		}

		org.junit.Assert.assertTrue(false);
	}

	@Test
	public void test_addNewMemberStatus(){
		// TODO
	}

}
