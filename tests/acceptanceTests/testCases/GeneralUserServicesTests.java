package acceptanceTests.testCases;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import exceptions.*;
import org.junit.*;

import users.User;
import content.Message;
import content.SubForum;
import content.Thread;
import users.userState.UserStates;

public class GeneralUserServicesTests extends ForumTests{
	static User moderator;
	static User user;
	
	@BeforeClass
	public static void beforeTest() throws UsernameAlreadyExistsException, NoSuchAlgorithmException, UserAlreadyLoggedInException, UserDoesNotExistsException, WrongPasswordException {
		registerToForum(theForum, USER_NAMES[0], USER_PASSES[0], USER_EMAILS[0]);
		registerToForum(theForum, USER_NAMES[1], USER_PASSES[1], USER_EMAILS[1]);
		user = loginUser(theForum, USER_NAMES[0], USER_PASSES[0]);
		moderator = loginUser(theForum, USER_NAMES[1], USER_PASSES[1]);
		moderator.setState(UserStates.newState(UserStates.MODERATOR));
	}

	@After
	public void afterMethod() throws UserNotLoggedInException, UserDoesNotExistsException {
		theForum.getSubForums().clear();
	}

	@Test
	public void test_showListOfSubForums_AddMultipleSubForums() throws UserNotAuthorizedException {

		List<SubForum> subForums = new ArrayList<>();

		for(String sf : SUB_FORUM_NAMES){
			subForums.add(addSubForum(theForum, sf, superAdmin));
		}
		
		List<SubForum> addedSubForums = showListOfSubForums(theForum, superAdmin);

		for(SubForum sf : addedSubForums){
			if(!subForums.contains(sf)){
				Assert.assertFalse(true);
			}
		}
	}
	
	@Test
	public void test_showListOfSubForumsTests_AddNone() throws UserNotAuthorizedException {
		List<SubForum> addedSubForums = showListOfSubForums(theForum, superAdmin);
		Assert.assertTrue(addedSubForums.isEmpty());
	}
	
	@Test
	public void test_showListOfThreads_AddMultiple() throws UserNotAuthorizedException, WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, EmptyMessageTitleAndBodyException {
		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);
		
		List<Thread> threads = new ArrayList<>();

		for(int i=0; i< THREAD_TITLES.length; i++){
			threads.add(openNewThread(theForum, sf, THREAD_TITLES[i], THREAD_CONTENTS[i], user));
		}
		
		List<Thread> addedThreads = showListOfThreads(sf);
		for(Thread t : addedThreads){
			if(!threads.contains(t)){
				Assert.assertFalse(true);
			}
		}
	}
	
	@Test
	public void test_showListOfThreads_AddNone() throws UserNotAuthorizedException {
		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);
		List<Thread> addedThreads = showListOfThreads(sf);
		Assert.assertTrue(addedThreads.isEmpty());
		}

	@Ignore
	@Test
	public void test_searchMessages_ValidAnswer() throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException, WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException {
		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);
		
		Thread t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], moderator);
		Message msg = t.getOpeningMessage();
		
		for(int i=0; i < MESSAGE_TITLES.length; i++){
			replyToMessage(theForum, msg, MESSAGE_TITLES[i], MESSAGE_CONTENTS[i], user);
		}
		
		Date startDate = new Date(0);
		Date endDate = new Date(System.currentTimeMillis()+100);
		
		List<Message> messages = searchMessages(theForum, MESSAGE_TITLES[0], MESSAGE_CONTENTS[0], user.getUsername(), startDate, endDate);
		Assert.assertNotNull(messages);
		Assert.assertFalse(messages.isEmpty());
	}

	@Ignore
	@Test
	public void test_searchMessages_InvalidAnswer() throws WrongPasswordException, NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, UserNotAuthorizedException, EmptyMessageTitleAndBodyException {
		SubForum sf = addSubForum(theForum, SUB_FORUM_NAMES[0], superAdmin);
		
		Thread t = openNewThread(theForum, sf, THREAD_TITLES[0], THREAD_CONTENTS[0], moderator);
		Message msg = t.getOpeningMessage();
		
		replyToMessage(theForum, msg, MESSAGE_TITLES[0], MESSAGE_CONTENTS[0], user);
		
		Date startDate = new Date(0);
		Date endDate = new Date(System.currentTimeMillis()+100);
		
		List<Message> messages = searchMessages(theForum, MESSAGE_TITLES[0], MESSAGE_CONTENTS[0], user.getUsername(), startDate, endDate);
		Assert.assertTrue(messages.isEmpty());
		}
	
}
