package testCases;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import users.User;
import content.Forum;
import content.Message;
import content.SubForum;
import content.Thread;
import users.userState.UserState;
import users.userState.UserStates;

public class GeneralUserServicesTests extends ForumTests {
	User moderator;
	User user;
	
	@Before
	public void beforeTest(){
		user = registerToForum(forum, USER_NAMES[0], USER_PASSES[0]);
		moderator = registerToForum(forum, USER_NAMES[1], USER_PASSES[1]);
		moderator.setState(UserState.newState(UserStates.MODERATOR));
	}
	
	@Test
	public void test_showListOfSubForums_AddMultipleSubForums() {
		
		List<SubForum> subForums = new ArrayList<SubForum>();
		
		for(String sf : SUB_FORUM_NAMES){
			subForums.add(addSubForum(forum, sf, moderator));
		}
		
		List<SubForum> addedSubForums = showListOfSubForums(forum);
		for(SubForum sf : addedSubForums){
			if(!subForums.contains(sf)){
				Assert.assertFalse(true);
			}
		}
	}
	
	@Test
	public void test_showListOfSubForumsTests_AddNone() {
		List<SubForum> addedSubForums = showListOfSubForums(forum);
		Assert.assertTrue(addedSubForums.isEmpty());
	}
	
	@Test
	public void test_showListOfThreads_AddMultiple() {
		SubForum sf = addSubForum(forum, SUB_FORUM_NAMES[0], moderator);
		
		List<Thread> threads = new ArrayList<Thread>();
		
		user = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		
		for(int i=0; i< THREAD_TITLES.length; i++){
			threads.add(openNewThread(sf, THREAD_TITLES[i], THREAD_CONTENTS[i], user));
		}
		
		List<Thread> addedThreads = showListOfThreads(sf);
		for(Thread t : addedThreads){
			if(!threads.contains(t)){
				Assert.assertFalse(true);
			}
		}
	}
	
	@Test
	public void test_showListOfThreads_AddNone() {
		SubForum sf = addSubForum(forum, SUB_FORUM_NAMES[0], moderator);
		List<Thread> addedThreads = showListOfThreads(sf);
		Assert.assertTrue(addedThreads.isEmpty());
		}
	
	@Test
	public void test_searchMessages_ValidAnswer() {
		user = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		moderator = loginUser(forum, USER_NAMES[1], USER_PASSES[1]);
		
		SubForum sf = addSubForum(forum, SUB_FORUM_NAMES[0], moderator);
		
		Thread t = openNewThread(sf, THREAD_TITLES[0], THREAD_CONTENTS[0], moderator);
		Message msg = t.getOpeningMessage();
		
		for(int i=0; i < MESSAGE_TITLES.length; i++){
			replyToMessage(msg, MESSAGE_TITLES[i], MESSAGE_CONTENTS[i], user);
		}
		
		Date startDate = new Date(0);
		Date endDate = new Date(System.currentTimeMillis());
		
		List<Message> messages = searchMessages(forum, MESSAGE_TITLES[0], MESSAGE_CONTENTS[0], user.getUserName(), startDate, endDate);
		Assert.assertTrue(messages != null && !messages.isEmpty());
	}
	
	@Test
	public void test_searchMessages_InvalidAnswer() {
		user = loginUser(forum, USER_NAMES[0], USER_PASSES[0]);
		moderator = loginUser(forum, USER_NAMES[1], USER_PASSES[1]);
		
		SubForum sf = addSubForum(forum, SUB_FORUM_NAMES[0], moderator);
		
		Thread t = openNewThread(sf, THREAD_TITLES[0], THREAD_CONTENTS[0], moderator);
		Message msg = t.getOpeningMessage();
		
		replyToMessage(msg, MESSAGE_TITLES[0], MESSAGE_CONTENTS[0], user);
		
		Date startDate = new Date(0);
		Date endDate = new Date(System.currentTimeMillis());
		
		List<Message> messages = searchMessages(forum, MESSAGE_TITLES[0], MESSAGE_CONTENTS[0], user.getUserName(), startDate, endDate);
		Assert.assertTrue(messages == null || messages.isEmpty());
		
		}
	
}
