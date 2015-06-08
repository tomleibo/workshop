package acceptanceTests.testCases;

import exceptions.UsernameAlreadyExistsException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import users.User;
import utils.HibernateUtils;

public class GuestUserServicesTests extends ForumTests{


	@After
	public void afterMethod(){
		for (User usr: theForum.getMembers()){
			HibernateUtils.del(usr);
		}
		HibernateUtils.update(theForum);

		int i=0;
		while(theForum.getMembers().size() > 1){
			if(theForum.getMembers().get(i).equals(superAdmin)){
				i++;
				continue;
			}
			theForum.getMembers().remove(i);
		}
	}

	@Test // 4.1
	public void test_registerGuest_ValidCredentials() throws Exception {
            User user = registerToForum(theForum, USER_NAMES[0], USER_PASSES[0], USER_EMAILS[0]);
	}

	@Test // 4.2
	public void test_registerGuest_InvalidCredentials() {
		try {
            User user = registerToForum(theForum, "", "","");
            Assert.fail();
        } catch (Exception e) {
        }
	}

	@Test // 4.3
	public void test_registerGuest_UserAlreadyExists() throws Exception {
		User user = registerToForum(theForum, USER_NAMES[0], USER_PASSES[0], USER_EMAILS[0]);
		try {
			User user2 = registerToForum(theForum, USER_NAMES[0], USER_PASSES[0], USER_EMAILS[0]);
		}  catch (UsernameAlreadyExistsException e) {
			Assert.assertTrue(true);
			return;
		}

		Assert.assertTrue(false);
	}

	@Test // 4.4
	public void test_enterAsGuest_ValidCredentials() {
		User guestUser = enterAsGuest(theForum);
		Assert.assertNotNull(guestUser);
	}

//	@Ignore
//	@Test // 4.5
//	public void test_enterAsGuest_InvalidCredentials() {
//		User guestUser = enterAsGuest(null);
//		Assert.assertNull(guestUser);
//	}

}
