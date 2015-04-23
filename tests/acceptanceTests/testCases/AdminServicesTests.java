package acceptanceTests.testCases;

import content.SubForum;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import users.User;
import users.userState.UserStates;


public class AdminServicesTests extends ForumTests{

	User admin;
	User moderator;

	@Before
	public void setState(){
		registerToForum(forum,USER_NAMES[1],USER_PASSES[1], USER_EMAILS[1]);
		admin.setState(UserStates.newState(UserStates.ADMIN));
		admin = loginUser(forum, USER_NAMES[1], USER_PASSES[1]);

		moderator = registerToForum(forum,USER_NAMES[2],USER_PASSES[2],USER_EMAILS[2]);
		moderator.setState(UserStates.newState(UserStates.MODERATOR));
	}

	@Test
	public void test_cancelSubForum_ExistingSubForum() {
		SubForum sf1 = addSubForum(forum, SUB_FORUM_NAMES[0], moderator);
		SubForum sf2 = addSubForum(forum, SUB_FORUM_NAMES[1], moderator);
		SubForum sf3 = addSubForum(forum, SUB_FORUM_NAMES[2], moderator);

		boolean result = cancelSubForum(forum, sf1, admin);
		Assert.assertTrue(result);
		Assert.assertEquals(showListOfSubForums(forum).size(), 2);

		result = cancelSubForum(forum, sf2, admin);
		Assert.assertTrue(result);
		Assert.assertEquals(showListOfSubForums(forum).size(), 1);

		result = cancelSubForum(forum, sf3, admin);
		Assert.assertTrue(result);
		Assert.assertEquals(showListOfSubForums(forum).size(),0);
	}

	@Test
	public void test_cancelSubForum_NonExistingSubForum() {
		boolean result = cancelSubForum(forum, null, admin);
		Assert.assertFalse(result);
	}

	@Test
	public void test_cancelSubForum_UnAuthorizedUser() {
		admin.setState(UserStates.newState(UserStates.GUEST));
		SubForum sf1 = addSubForum(forum, SUB_FORUM_NAMES[0], moderator);

		boolean result = cancelSubForum(forum, sf1, admin);
		Assert.assertFalse(result);
	}

}
