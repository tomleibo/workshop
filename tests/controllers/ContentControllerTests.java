package controllers;

import content.Forum;
import content.Message;
import content.SubForum;
import content.Thread;
import exceptions.EmptyMessageTitleAndBodyException;
import exceptions.UserNotAuthorizedException;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import policy.ForumPolicy;
import users.User;
import utils.HibernateUtils;

@SuppressWarnings("unused")
public class ContentControllerTests extends TestCase{
	SubForum subForum;
	Forum forum;
	User user1;
	ForumPolicy policy;
	ContentController cc;

	@Override
	@Before
	protected void setUp() throws Exception {
        HibernateUtils.start();
		user1 = User.newGuest();
		subForum = new SubForum("forum name",user1,3);
		policy = new ForumPolicy(3,".",ForumPolicy.HashFunction.MD5,false);
		forum = new Forum(user1, policy, "olamHaNextShelTom");
		cc = new ContentController();
	}

	@Override
	@After
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}

	@Test
	public void testAddSubForum() throws UserNotAuthorizedException {
		assertNotNull(cc.addSubForum(forum, "blaaaaa", user1));
		assertEquals(cc.viewSubForumList(forum).size(),1);
	}

	@Test
	public void testDeleteSubForum() throws UserNotAuthorizedException {
		subForum = cc.addSubForum(forum, "blaaaaa", user1);
		cc.deleteSubForum(forum, subForum);
		assertEquals(cc.viewSubForumList(forum).size(), 0);
	}

	@Test
	public void testAddSameSubForumFails() throws UserNotAuthorizedException {
		SubForum subForum1 = cc.addSubForum(forum, "pokemon", user1);
		assertNull(cc.addSubForum(forum, "pokemon", user1));
	}
	@Test
	public void testopenNewThread() throws UserNotAuthorizedException {
		Thread t=null;
		try {
			t = cc.openNewThread(forum,subForum, "Bivan's subForum", "hello i am Bivan", user1);
		} catch (EmptyMessageTitleAndBodyException e) {
			fail();
		}
		assertNotNull(t);
		assertEquals(t, t.getOpeningMessage().getThread());
		assertEquals(cc.viewThreads(subForum).get(0),t);
	}

	@Test
	public void testRemoveNewThread() throws UserNotAuthorizedException {
		Thread t  =null;
		try {
			t=cc.openNewThread(forum,subForum, "Bivan's subForum", "hello i am Bivan", user1);
		}
		catch (EmptyMessageTitleAndBodyException e) {
			fail();
		}
		cc.deletePost(t.getOpeningMessage());
		assertEquals(cc.viewThreads(subForum).size(),0);
	}

	@Test
	public void testReply() {
		User user2 = User.newGuest();
		Thread t  =null;
		try {
			t=cc.openNewThread(forum,subForum, "Charlie", "ring ring", user1);
			Message msg = cc.reply(forum,t.getOpeningMessage(), "comment", "hello", user2);
			assertEquals(msg, t.getOpeningMessage().getComments().get(0));
		}
		catch (EmptyMessageTitleAndBodyException e) {
			fail();
		}
	}

	@Test
	public void testReplyAndRemoval() {
		User user2 = User.newGuest();
		Thread t  =null;
		try {
			t=cc.openNewThread(forum,subForum, "Charlie", "ring ring", user1);
			Message msg = cc.reply(forum,t.getOpeningMessage(), "comment", "hello", user2);
			assertEquals(msg, t.getOpeningMessage().getComments().get(0));
			cc.deletePost(msg);
			assertFalse(t.getOpeningMessage().getComments().contains(msg));
		}
		catch (EmptyMessageTitleAndBodyException e) {
			fail();
		}
	}

	@Test
	public void testRemovalOfOpeningMsgRemovesThreadFromSubforum() {
		User user2 = User.newGuest();
		Thread t  =null;
		try {
			t=cc.openNewThread(forum,subForum, "Charlie", "ring ring", user1);
			cc.deletePost(t.getOpeningMessage());
			assertFalse(cc.viewThreads(subForum).contains(t));
		}
		catch (EmptyMessageTitleAndBodyException e) {
			fail();
		}
	}

	@Test
	public void testEditPost() {
		User user2 = User.newGuest();
		Thread t  =null;
		try {
			t=cc.openNewThread(forum,subForum, "Charlie", "ring ring", user1);
			cc.editPost(t.getOpeningMessage(),"hello");
			assertTrue(t.getOpeningMessage().getBody().equals("hello"));
		}
		catch (EmptyMessageTitleAndBodyException e) {
			fail();
		}
	}


}