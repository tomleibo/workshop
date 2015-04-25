package acceptanceTests.testCases;

import exceptions.UsernameAlreadyExistsException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import users.User;

import java.security.NoSuchAlgorithmException;

public class GuestUserServicesTests extends ForumTests{


	@After
	public void afterMethod(){
		int i=0;
		while(theForum.getMembers().size() > 1){
			if(theForum.getMembers().get(i).equals(superAdmin)){
				i++;
				continue;
			}

			theForum.getMembers().remove(i);
		}
	}

	@Test
	public void test_registerGuest_ValidCredentials() throws UsernameAlreadyExistsException, NoSuchAlgorithmException {
		User user = registerToForum(theForum, USER_NAMES[0], USER_PASSES[0], USER_EMAILS[0]);
		Assert.assertNotNull(user);
	}

	@Ignore
	@Test
	public void test_registerGuest_InvalidCredentials() throws UsernameAlreadyExistsException, NoSuchAlgorithmException {
		User user = registerToForum(theForum, "", "","");
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

	@Ignore
	@Test
	public void test_enterAsGuest_InvalidCredentials() {
		User guestUser = enterAsGuest(null);
		Assert.assertNull(guestUser);
	}

}
