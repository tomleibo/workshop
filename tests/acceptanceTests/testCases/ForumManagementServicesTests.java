package acceptanceTests.testCases;

import acceptanceTests.bridge.Driver;
import content.Forum;
import exceptions.*;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;
import policy.ForumPolicy;
import users.User;
import utils.HibernateUtils;

import java.security.NoSuchAlgorithmException;

import static controllers.SuperAdminController.deleteForum;

public class ForumManagementServicesTests extends ForumTests {

	@After
	public void afterMethod() throws Exception{
//		for (SubForum sub: theForum.getSubForums()){
//			HibernateUtils.del(sub);
//		}
//		theForum.getSubForums().clear();
//		HibernateUtils.update(theForum);
//
//		changeForumPolicy(theForum, policy, admin);
//		admin.setState(User.ADMIN);
//		admin.logout();
//		user.logout();
//		user5.logout();
//
//		admin = loginUser(theForum, USER_NAMES[1], USER_PASSES[1]);
//		user = loginUser(theForum, USER_NAMES[2], USER_PASSES[2]);
//		user5 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
//		sleep(500);
		HibernateUtils.cleanUp();

		driver = Driver.getDriver();
		//system = initializeForumSystem(superAdminUsername, superAdminPassword, superAdminMail);
		superAdmin = initializeForumSystem(superAdminUsername, superAdminPassword, superAdminMail);
		policy = getPolicy(3, ".+", ForumPolicy.HashFunction.MD5);
		theForum = addForum(FORUM_NAMES[0], superAdmin, policy);
	}

	@Test // 2.1
	public void test_defineProperties_ValidParameters() throws UserNotAuthorizedException {
		ForumPolicy policy = theForum.getPolicy();
        ForumPolicy fp = getPolicy(10, "[1-9]^10", ForumPolicy.HashFunction.MD5);
		boolean result = changeForumPolicy(theForum, fp, superAdmin);
		Assert.assertTrue(result);
        changeForumPolicy(theForum, policy, superAdmin);
	}

//	@Ignore // 2.2
//	@Test
//	public void test_defineProperties_InvalidParameters() throws UserNotAuthorizedException {
//		ForumPolicy fp = new ForumPolicy(-100, null, null);
//		boolean result = changeForumPolicy(theForum, fp, superAdmin);
//		Assert.assertFalse(result);
//	}

	@Test // 2.3
	public void test_defineProperties_UnAuthorizedUser() throws Exception {
		ForumPolicy fp = getPolicy(10, "[1-9]^10", ForumPolicy.HashFunction.MD5);
		registerToForum(theForum, USER_NAMES[0], USER_PASSES[0], USER_EMAILS[0]);
		User user1 = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);

		try {
			boolean result = changeForumPolicy(theForum, fp, user1);
			Assert.fail();
		} catch (UserNotAuthorizedException e) {
			Assert.assertTrue(true);
		}
	}

	@Test // 2.4
	public void test_initialization_SystemExists() throws NoSuchAlgorithmException {
		if(HibernateUtils.getSession()!= null){
			Assert.assertTrue(true);
		}
		else{
			Assert.assertTrue(false);
		}

//		try{
//		User otherSuperAdmin = initializeForumSystem(USER_NAMES[2], USER_PASSES[2], USER_EMAILS[2]);
//		}
//		catch(ForumSystemAlreadyExistsException e){
//			Assert.assertTrue(true);
//			return;
//		}
//
//		Assert.assertTrue(false);
	}

	@Test // 2.5
	public void test_addNewForum_FreeName() throws Exception{
		Forum otherForum = null;
		otherForum = addForum(FORUM_NAMES[1], superAdmin, getPolicy(10, "[1-9]^10", ForumPolicy.HashFunction.MD5));
		Assert.assertNotNull(otherForum);
		Assert.assertEquals(otherForum.getName(), FORUM_NAMES[1]);
		Assert.assertTrue(deleteForum(superAdmin, otherForum));
	}

//	@Ignore // 2.6
//	@Test
//	 public void test_addNewForum_ForumAlreadyExists(){
//		try{
//			Forum otherForum = addForum(FORUM_NAMES[0], superAdmin, getPolicy(3, ".", ForumPolicy.HashFunction.MD5));
//		}
//		catch(Exception e){
//			// TODO change to ForumAlreadyExistsException
//		}
//	}

	@Test // 2.7
	public void test_addNewForum_UserUnAuthorized() throws Exception {
		registerToForum(theForum, USER_NAMES[2], USER_PASSES[2], USER_EMAILS[2]);
		User user = loginUser(theForum, USER_NAMES[2], USER_PASSES[2]);

		try {
			Forum otherForum = addForum(FORUM_NAMES[1], user, getPolicy(3, ".", ForumPolicy.HashFunction.MD5));
			Assert.fail();
		} catch (UserNotAuthorizedException e) {
			Assert.assertTrue(true);
		}
	}

//	@Test // 2.8
//	public void test_addNewMemberStatus(){
//		addUserStatusType(superAdmin, "gold", new UserStatusPolicy(1, 0, 100));
//		Assert.assertTrue(system.hasUserStateType("gold"));
//
//	}

	//version 2
	//	public int getReportNumberOfForums(User superAdmin) throws UserNotAuthorizedException {

	@Test // 2.9
	public void test_report_forum_number() throws Exception{
		Assert.assertEquals(1, getReportNumberOfForums(superAdmin));
	}



}
