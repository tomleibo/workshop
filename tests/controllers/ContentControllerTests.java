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
		assertEquals(cc.viewSubForumList(forum,user1).size(),1);
	}

	@Test
	public void testDeleteSubForum() throws UserNotAuthorizedException {
		subForum = cc.addSubForum(forum, "blaaaaa", user1);
		cc.deleteSubForum(forum, subForum,user1);
		assertEquals(cc.viewSubForumList(forum,user1).size(),0);
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
		} catch (UserNotAuthorizedException | EmptyMessageTitleAndBodyException e) {
			fail();
		}
		assertNotNull(t);
		assertEquals(t,t.getOpeningMessage().getThread());
		assertEquals(cc.viewThreads(forum,subForum,user1).get(0),t);
	}

	@Test
	public void testRemoveNewThread() throws UserNotAuthorizedException {
		Thread t  =null;
		try {
			t=cc.openNewThread(forum,subForum, "Bivan's subForum", "hello i am Bivan", user1);
		}
		catch (UserNotAuthorizedException | EmptyMessageTitleAndBodyException e) {
			fail();
		}
		cc.deletePost(forum, subForum,user1, t.getOpeningMessage());
		assertEquals(cc.viewThreads(forum,subForum,user1).size(),0);
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
		catch (UserNotAuthorizedException | EmptyMessageTitleAndBodyException e) {
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
			cc.deletePost(forum, subForum,user2, msg);
			assertFalse(t.getOpeningMessage().getComments().contains(msg));
		}
		catch (UserNotAuthorizedException | EmptyMessageTitleAndBodyException e) {
			fail();
		}
	}

	@Test
	public void testRemovalOfOpeningMsgRemovesThreadFromSubforum() {
		User user2 = User.newGuest();
		Thread t  =null;
		try {
			t=cc.openNewThread(forum,subForum, "Charlie", "ring ring", user1);
			cc.deletePost(forum,subForum, user2, t.getOpeningMessage());
			assertFalse(cc.viewThreads(forum,subForum,user1).contains(t));
		}
		catch (UserNotAuthorizedException | EmptyMessageTitleAndBodyException e) {
			fail();
		}
	}

	@Test
	public void testEditPost() {
		User user2 = User.newGuest();
		Thread t  =null;
		try {
			t=cc.openNewThread(forum,subForum, "Charlie", "ring ring", user1);
			cc.editPost(forum,subForum,user2,t.getOpeningMessage(),"hello");
			assertTrue(t.getOpeningMessage().getBody().equals("hello"));
		}
		catch (UserNotAuthorizedException | EmptyMessageTitleAndBodyException e) {
			fail();
		}
	}

	@Test
	public void testSearchMessages() throws EmptyMessageTitleAndBodyException {
		forum.addSubForum(subForum);
		User user15 = new User(15);
		SubForum sub2 = new SubForum("sub2", user15, 3);
		forum.addSubForum(sub2);
		try {
			Thread t1 = cc.openNewThread(forum, subForum, "ring", "reeng", user1);
			Message msg = cc.reply(forum,t1.getOpeningMessage(), "hello", "...", user15);
			Message msg1 = cc.reply(forum,msg, "ring", "reeng", user1);
			Message msg2 = cc.reply(forum,msg1, "hel", "hello!", user1);
			Message msg3 = cc.reply(forum,t1.getOpeningMessage(), "No service", "Available", user15);
		} catch (UserNotAuthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		try {
			Thread t2 = cc.openNewThread(forum, sub2, "war", "ha", user1);
			Message msga = cc.reply(forum,t2.getOpeningMessage(), "hoo", "...", user15);
			Message msgb = cc.reply(forum,msga, "what", "is", user1);
			Message msgc = cc.reply(forum,msgb, "it", "good for!", user1);
			Message msgd = cc.reply(forum,t2.getOpeningMessage(), "obsolutly", "nothing", user15);
		} catch (UserNotAuthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//	System.out.println(sub2.viewThreads().size());
		//System.out.println(subForum.viewThreads().size());

		try{
			assertEquals(cc.searchMessages(forum, "war", null, null, null, null).size(), 1);
			assertEquals(cc.searchMessages(forum, null, "h", null, null, null).size(), 2);
			assertEquals(cc.searchMessages(forum, null, null, "15", null, null).size(), 4);
			assertEquals(cc.searchMessages(forum, null, null, null, new java.sql.Date(System.currentTimeMillis() - 1000*5) , new java.sql.Date(System.currentTimeMillis())).size(), 10);
		}
		catch (Exception t) {
			t.printStackTrace(System.err);
			fail();
		}
	}


}