package acceptanceTests.testCases;

import org.junit.Assert;
import org.junit.Test;

import users.User;

public class GuestUserServicesTests extends ForumTests{

	@Test
	public void test_registerGuest_ValidCredentials() {
		User user = registerToForum(forum, USER_NAMES[0], USER_PASSES[0], USER_EMAILS[0]);
		Assert.assertNotNull(user);
	}
	
	@Test
	public void test_registerGuest_InvalidCredentials() {
		User user = registerToForum(forum, null, null,null);
		Assert.assertNull(user);
	}

	@Test
	public void test_enterAsGuest_ValidCredentials() {
		User guestUser = enterAsGuest(forum);
		Assert.assertNotNull(guestUser);
	}

	@Test
	public void test_enterAsGuest_InvalidCredentials() {
		User guestUser = enterAsGuest(null);
		Assert.assertNull(guestUser);
	}

}
