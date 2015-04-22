package testCases;

import static org.junit.Assert.*;

import junit.framework.Assert;
import org.junit.Test;
import policy.ForumPolicy;

public class ForumManagementServicesTests extends ForumTests {

	@Test
	public void test_defineProperties_ValidParameters() {
		ForumPolicy fp = new ForumPolicy(10, "[1-9]^10", ForumPolicy.HashFunction.MD5);
		boolean result = defineProperties(forum, fp);
		Assert.assertTrue(result);
	}

	@Test
	public void test_defineProperties_InvalidParameters() {
		ForumPolicy fp = new ForumPolicy(-100, null, null);
		boolean result = defineProperties(forum, fp);
		Assert.assertFalse(result);
	}
}
