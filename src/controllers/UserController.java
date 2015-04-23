package controllers;

import content.Forum;
import content.Message;
import content.SubForum;
import content.Thread;
import exceptions.*;
import policy.PolicyHandler;
import users.FriendRequest;
import users.Report;
import users.User;
import utils.Cipher;
import utils.MailAuthenticator;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserController {

	private static String mailHost ="imap.gmail.com";
	private static String mailUsername = "sadnase2015@gmail.com";
	private static String mailPassword = "sadna2015";

	public static User register(Forum forum, String username, String password, String emailAddress) throws UsernameAlreadyExistsException, NoSuchAlgorithmException {
		if (getUserFromForum(username, forum) != null)
			throw new UsernameAlreadyExistsException("Username: " + username + " already exists in forum: " + forum.getName() + ".");
		User member = User.newMember(username, Cipher.hashString(password, Cipher.SHA), emailAddress);
		if (forum.isSecured()) {
			MailAuthenticator authenticator = new MailAuthenticator(mailHost, mailUsername, mailPassword);
			authenticator.sendVerificationMail(emailAddress, username);
			if (authenticator.authorizedMailIncome(emailAddress)) {
				if (forum.addMember(member))
					return member;
			}
		} else {
			if (forum.addMember(member))
				return member;
		}
		return null;
	}

	public static User login(Forum forum, String username, String password) throws NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, WrongPasswordException {
		User user = getUserFromForum(username, forum);
		if (user == null)
			throw new UserDoesNotExistsException();
		return user.login(Cipher.hashString(password, Cipher.SHA));
	}

	public static User enterAsGuest(Forum forum) {
		User guest = User.newGuest();
		try {
			return guest.loginAsGuest();
		} catch (UserAlreadyLoggedInException | WrongPasswordException e) {
			e.printStackTrace();
			return guest;
		}
	}
	
	public static User logout(Forum forum, String username) throws UserDoesNotExistsException, UserNotLoggedInException {
		User user = getUserFromForum(username, forum);
		if (user != null)
			return user.logout();
		throw new UserDoesNotExistsException();
	}

	public static String viewOwnProfile(User user) {
		return user.toString();
	}
	
	public static boolean sendFriendRequest(Forum forum, User from, User to, String message) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserHaveFriends(forum, from) & PolicyHandler.canUserHaveFriends(forum, to)) {
			FriendRequest request = new FriendRequest(from, to, message);
			return to.addFriendRequest(request);
		}
		throw new UserNotAuthorizedException("to send or receive friend request.");
	}
	
	public static boolean removeFriend(Forum forum, User user, User friend) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserHaveFriends(forum, user) & PolicyHandler.canUserHaveFriends(forum, friend)) {
			return friend.deleteFriend(user) && user.deleteFriend(friend);
		}
		throw new UserNotAuthorizedException("to remove friends.");
	}
	
	public static boolean replyToFriendRequest(Forum forum, User user, FriendRequest request, boolean answer) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserReplyToFriendRequest(forum, user, request)) {
			if (answer) {
				User requesting = request.getRequestingMember();
				User receiving = request.getReceivingMember();
				return requesting.addFriend(receiving) && receiving.addFriend(requesting);
			}
			return false;
		}
		throw new UserNotAuthorizedException("to reply to friend request.");
	}
	
	public static boolean report(Forum forum, User reporter, User admin, String title, String content) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserReportAdmin(forum, reporter, admin)) {
			Report report = new Report(title, content, reporter, admin);
			return forum.addReport(report) && reporter.addSentReport(report);
		}
		throw new UserNotAuthorizedException("to send a report.");
	}

	public static boolean deactivate(User user) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserBeDeactivated(user)) {
			return user.deactivate();
		}
		throw new UserNotAuthorizedException("to deactivate itself.");
	}

	public static boolean deletePost(Forum forum, SubForum subForum, User user, Message msg) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserDeleteComment(forum, subForum, user, msg)) {
			return ContentController.deletePost(msg);
		}
		throw new UserNotAuthorizedException("to delete post.");
	}

	public static Thread openNewThread(Forum forum, SubForum subforum, String title, String content, User user) throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException {
		if (PolicyHandler.canUserOpenThread(forum, user)){
			return ContentController.openNewThread(forum, subforum, title, content, user);
		}
		throw new UserNotAuthorizedException("to open thread.");
	}

	public static Message reply(Forum forum, Message addTo, String title,String content,User user) throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException {
		if (PolicyHandler.canUserReply(forum, user)) {
			return ContentController.reply(forum, addTo, title, content, user);
		}
		throw new UserNotAuthorizedException("to reply.");
	}

	public static List<SubForum> viewSubForumList(Forum forum, User user) throws UserNotAuthorizedException {
		if(PolicyHandler.canUserViewSubForums(forum, user)) {
			return ContentController.viewSubForumList(forum);
		}
		throw new UserNotAuthorizedException("to view sub forums list.");
	}

	public static List<Thread> viewThreads(Forum forum, SubForum subForum, User user) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserViewSubForums(forum, user)) {
			return ContentController.viewThreads(subForum);
		}
		throw new UserNotAuthorizedException("to view threads.");
	}

	private static User getUserFromForum(String username, Forum forum) {
		List<User> members = forum.getMembers();
		for (User member : members) {
			if (member.getUsername().equals(username))
				return member;
		}
		return null;
	}

}
