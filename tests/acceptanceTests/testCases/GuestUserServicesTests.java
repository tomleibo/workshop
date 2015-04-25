package acceptanceTests.testCases;

import exceptions.UsernameAlreadyExistsException;
import org.junit.Assert;
import org.junit.Test;

import users.User;

import java.security.NoSuchAlgorithmException;

public class GuestUserServicesTests extends ForumTests{

	@Test
	public void test_registerGuest_ValidCredentials() throws UsernameAlreadyExistsException, NoSuchAlgorithmException {
		User user = registerToForum(theForum, USER_NAMES[0], USER_PASSES[0], USER_EMAILS[0]);
		Assert.assertNotNull(user);
	}
	
	@Test
	public void test_registerGuest_InvalidCredentials() throws UsernameAlreadyExistsException, NoSuchAlgorithmException {
		User user = registerToForum(theForum, null, null,null);
		Assert.assertNull(user);
	}

	@Test
	public void test_registerGuest_UserAlreadyExists() throws UsernameAlreadyExistsException, NoSuchAlgorithmException {
		User user = registerToForum(theForum, USER_NAMES[0], USER_PASSES[0], USER_EMAILS[0]);
		try {
			User user2 = registerToForum(theForum, USER_NAMES[0], USER_PASSES[0], USER_EMAILS[0]);
		}  catch (UsernameAlreadyExistsException e) {
			Assert.assertTrue(true);
			return;
		}

		Assert.assertTrue(false);
	}

	@Test
	public void test_enterAsGuest_ValidCredentials() {
		User guestUser = enterAsGuest(theForum);
		Assert.assertNotNull(guestUser);
	}

	@Test
	public void test_enterAsGuest_InvalidCredentials() {
		User guestUser = enterAsGuest(null);
		Assert.assertNull(guestUser);
	}

}
