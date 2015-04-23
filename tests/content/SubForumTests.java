package content;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import users.User;

public class SubForumTests extends TestCase{
	SubForum subforum;
	User user1;
	User user2;
	User user3;
	User user4;
	User user5;
	@Override
	@Before
	protected void setUp() throws Exception {
		user1 = User.newGuest();
		user2 = User.newGuest();
		user3 = User.newGuest();
		user4 = User.newGuest();
		user5 = User.newGuest();
		subforum = new SubForum("forum name",user1,3);
	}

	@Override
	@After
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}

	@Test
	public void testAddExistingMod() {
		assertFalse(subforum.addModerator(user1));
	}

	@Test
	public void testFourthAdditionFails() {
		assertTrue(subforum.addModerator(user2));
		assertTrue(subforum.addModerator(user3));
		assertFalse(subforum.addModerator(user4));
	}

	@Test
	public void testAdditionOfSameModTwiceFails() {
		assertTrue(subforum.addModerator(user2));
		assertFalse(subforum.addModerator(user2));
	}

	@Test
	public void testRemoveFailsWhenOnlyOneMod() {
		assertFalse(subforum.removeModerator(user1));
	}

	@Test
	public void testInitial() {
		assertEquals(subforum.getModerators().get(0), user1);
	}

	@Test
	public void testRemovalOfNoModFails() {
		assertFalse(subforum.removeModerator(user2));
	}

	@Test
	public void testChangeWhenOne() {
		subforum.changeModerator(user1, user2);
		assertEquals(subforum.getModerators().get(0),user2);
	}

	@Test
	public void testChangeWhenMax() {
		subforum.addModerator(user3);
		subforum.addModerator(user4);
		assertTrue(subforum.changeModerator(user1, user2));
		assertEquals(subforum.getModerators().size(),3);
	}

	@Test
	public void testDidUserPostInSubForum() {
		Message openingMessage = new Message("title", "body", user1, null, null);
		subforum.addThread(new content.Thread(user1, openingMessage, subforum));
		assertTrue(subforum.didUserPostHere(user1));
		Message comment1 = new Message("title2","body",user2,null,openingMessage);
		openingMessage.addComment(comment1);
		Message comment2 = new Message("title3","body",user3,null,comment1);
		openingMessage.addComment(comment2);
		Message comment3 = new Message("title3","body",user4,null,comment2);
		openingMessage.addComment(comment3);
		assertTrue(subforum.didUserPostHere(user1));
		assertTrue(subforum.didUserPostHere(user2));
		assertTrue(subforum.didUserPostHere(user3));
		assertTrue(subforum.didUserPostHere(user4));
		assertFalse(subforum.didUserPostHere(user5));

	}


}
