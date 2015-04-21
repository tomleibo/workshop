package testCases;

import org.junit.Assert;
import org.junit.Test;

import users.User;

public class GuestUserServicesTests extends ForumTests{

	@Test
	public void test_registerGuest_ValidCredentials() {
		User user = registerToForum(forum, USER_NAMES[0], USER_PASSES[0]);
		Assert.assertTrue(user != null && user.getUserName() == USER_NAMES[0]);
	}
	
	@Test
	public void test_registerGuest_InvalidCredentials() {
		User user = registerToForum(forum, USER_NAMES[0], USER_PASSES[0]);
		Assert.assertTrue(user == null);
	}

}
