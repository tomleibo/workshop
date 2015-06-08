package controllers;

import content.Forum;
import content.Message;
import content.SubForum;
import content.Thread;
import exceptions.*;
import policy.PolicyHandler;
import users.FriendRequest;
import users.Notification;
import users.Report;
import users.User;
import utils.Cipher;
import utils.ForumLogger;
import utils.HibernateUtils;
import utils.MailAuthenticator;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserController {

	private static String mailHost ="imap.gmail.com";
	private static String mailUsername = "sadnase2015@gmail.com";
	private static String mailPassword = "sadna2015";

	public static User register(Forum forum, String username, String password, String emailAddress) throws UsernameAlreadyExistsException, NoSuchAlgorithmException, PasswordNotMatchesRegexException, EmptyFieldException {
		if (!password.matches(forum.getPolicy().getPasswordRegex())) {
            throw new PasswordNotMatchesRegexException();
        }
        if (username.isEmpty() | password.isEmpty() | emailAddress.isEmpty()) {
            throw new EmptyFieldException();
        }
        if (getUserFromForum(forum, username, password) != null)
			throw new UsernameAlreadyExistsException("Username: " + username + " already exists in forum: " + forum.getName() + ".");
		User member = User.newMember(username, Cipher.hashString(password, Cipher.SHA), emailAddress);
		if (forum.isSecured()) {
			MailAuthenticator authenticator = new MailAuthenticator(mailHost, mailUsername, mailPassword);
			authenticator.sendVerificationMail(emailAddress, username);
			ForumLogger.actionLog("Authentication in the forum " + forum.getName() + "is needed, authentication mail is sent to address: " + emailAddress);
			if (authenticator.authorizedMailIncome(emailAddress)) {
				ForumLogger.actionLog("A response mail has arrived, the user reliability approved!");
				if (forum.addMember(member)) {
					HibernateUtils.save(member);
                    HibernateUtils.update(forum);
                    return member;
                }
			}
		} else {
			if (forum.addMember(member)) {
                HibernateUtils.save(member);
                HibernateUtils.update(forum);
                return member;
            }
		}
		return null;
	}

    public static void changePassword(User user, String oldPassword, String newPassword) throws NoSuchAlgorithmException, WrongPasswordException, PasswordAlreadyUsedException {
        if (!user.isRightPassword(Cipher.hashString(oldPassword, Cipher.SHA))) {
            throw new WrongPasswordException();
        }
        String newHashedPassword = Cipher.hashString(newPassword, Cipher.SHA);
        if (user.alreadyUsedPassword(newHashedPassword)) {
            throw new PasswordAlreadyUsedException();
        }
        user.setHashedPassword(newHashedPassword);
        HibernateUtils.update(user);
    }

	public static User login(Forum forum, String username, String password) throws NoSuchAlgorithmException, UserDoesNotExistsException, UserAlreadyLoggedInException, WrongPasswordException, NeedToChangePasswordException {
        User user = getUserFromForum(forum, username, password);
		if (user == null) {
			ForumLogger.errorLog("The user " + username + " trying to login but he is not existing in the forum " + forum.getName());
			throw new UserDoesNotExistsException();
		}
        if (PolicyHandler.shouldUserChangePassword(forum, user)) {
            throw new NeedToChangePasswordException(user);
        }
		user = user.login(Cipher.hashString(password, Cipher.SHA));
		HibernateUtils.update(user);
		return user;
	}

	public static User enterAsGuest(Forum forum) {
		User guest = User.newGuest();
        if (forum.addMember(guest)) {
            HibernateUtils.save(guest);
            HibernateUtils.update(forum);
        }
		try {
			return guest.loginAsGuest();
		} catch (UserAlreadyLoggedInException | WrongPasswordException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static User logout(int id) throws UserDoesNotExistsException, UserNotLoggedInException {
		User user = getUserFromForum(id);
		if (user != null) {
            if(user.isGuest()) {
                HibernateUtils.del(user);
            }
			User guestUser = user.logout();
			HibernateUtils.update(user);
			HibernateUtils.save(guestUser);
            ForumLogger.actionLog("The user " + id + "is logged out successfully");
			return guestUser;
		}
		ForumLogger.errorLog("The user " + id + " trying to logout but he is not existing in the forum ");
		throw new UserDoesNotExistsException();
	}

	public static String viewOwnProfile(User user) {
		return user.toString();
	}
	
	public static FriendRequest sendFriendRequest(Forum forum, User from, User to, String message) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserHaveFriends(forum, from) & PolicyHandler.canUserHaveFriends(forum, to)) {
			FriendRequest request = new FriendRequest(from, to, message);
			to.addFriendRequest(request);
            HibernateUtils.save(request);
			return request;
		}
		ForumLogger.errorLog("The user " + to.getUsername() + "or the user " + from.getUsername() + " has no permissions to remove friends");
		throw new UserNotAuthorizedException("to send or receive friend request.");
	}
	
	public static boolean removeFriend(Forum forum, User user, User friend) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserHaveFriends(forum, user) & PolicyHandler.canUserHaveFriends(forum, friend)) {
			if(friend.deleteFriend(user) && user.deleteFriend(friend)){
                HibernateUtils.update(friend);
                HibernateUtils.update(user);
                return true;
            }
            return false;
		}
		ForumLogger.errorLog("The user " + user.getUsername() + "has no permissions to remove friends");
		throw new UserNotAuthorizedException("to remove friends.");
	}
	
	public static boolean replyToFriendRequest(Forum forum, User user, FriendRequest request, boolean answer) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserReplyToFriendRequest(forum, user, request)) {
			if (answer) {
				ForumLogger.actionLog("The user " + user.getUsername() + " accepted the friend request from " + request.getRequestingMember().getUsername());
                User requesting = request.getRequestingMember();
                User receiving = request.getReceivingMember();
                if (requesting.addFriend(receiving) && receiving.addFriend(requesting)){
					request.setViewed(true);
					HibernateUtils.update(receiving);
					HibernateUtils.update(requesting);
					HibernateUtils.update(request);
					return true;
                }
                return false;
			}
			ForumLogger.errorLog("The user " + user.getUsername() + " did not accept the friend request from " + request.getRequestingMember().getUsername());
			return false;
		}
		ForumLogger.errorLog("The user " + user.getUsername() + " has no permissions to reply a friend request");
		throw new UserNotAuthorizedException("to reply to friend request.");
	}
	
	public static boolean report(Forum forum, User reporter, User admin, String title, String content) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserReportAdmin(forum, reporter, admin)) {
			Report report = new Report(title, content, reporter, admin);
			if( forum.addReport(report) && reporter.addSentReport(report)){
                return HibernateUtils.save(forum) && HibernateUtils.save(reporter);
            }
            return false;
		}
		ForumLogger.errorLog("The user " + reporter.getUsername() + " has no permissions to send a report");
		throw new UserNotAuthorizedException("to send a report.");
	}

	public static boolean deactivate(User user) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserBeDeactivated(user)) {
			if(user.deactivate()){
                return HibernateUtils.save(user);
            }
		}
		ForumLogger.errorLog("The user " + user.getUsername() + " has no permissions to deactivate itself");
		throw new UserNotAuthorizedException("to deactivate itself.");
	}

	public static boolean editMessage(Forum forum,SubForum subForum, User user, Message msg, String content) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserEditComment(forum, subForum, user, msg)) {
			if(ContentController.editPost(msg, content)){
                return HibernateUtils.save(msg);
            }
		}
		ForumLogger.errorLog("The user " + user.getUsername() + " can't edit post");
		throw new UserNotAuthorizedException("to edit post.");
	}

	public static boolean deleteMessage(Forum forum, SubForum subForum, User user, Message msg) throws UserNotAuthorizedException {
		if(PolicyHandler.canUserDeleteComment(forum, subForum, user, msg)) {
			if(ContentController.deletePost(msg)){
                return HibernateUtils.save(msg);
            }
		}
		ForumLogger.errorLog("The user " + user.getUsername() + " can't delete post");
		throw new UserNotAuthorizedException("to delete post.");
	}

	public static Thread openNewThread(Forum forum, SubForum subforum, String title, String content, User user) throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException {
		if (PolicyHandler.canUserOpenThread(forum, user)){
			Thread t= ContentController.openNewThread(forum, subforum, title, content, user);
            if (t == null) {
                ForumLogger.errorLog("Open new thread failed in hibernate");
                return null;
            }
            return t;
		}
		ForumLogger.errorLog("The user " + user.getUsername() + " has no permissions to open thread");
		throw new UserNotAuthorizedException("to open thread.");
	}

	public static Message reply(Forum forum, Message addTo, String title,String content,User user) throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException {
		if (PolicyHandler.canUserReply(forum, user)) {
			Message msg = ContentController.reply(forum, addTo, title, content, user);
            if (msg!=null) {
                HibernateUtils.save(msg);
            }
            return msg;
		}
		ForumLogger.errorLog("The user " + user.getUsername() + " has no permissions to reply");
		throw new UserNotAuthorizedException("to reply.");
	}

	public static List<SubForum> viewSubForumList(Forum forum, User user) throws UserNotAuthorizedException {
		if(PolicyHandler.canUserViewSubForums(forum, user)) {
			return ContentController.viewSubForumList(forum);
		}
		ForumLogger.errorLog("The user " + user.getUsername() + " has no permissions to view sub forums list");
		throw new UserNotAuthorizedException("to view sub forums list.");
	}

	public static List<Thread> viewThreads(Forum forum, SubForum subForum, User user) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserViewSubForums(forum, user)) {
			return ContentController.viewThreads(subForum);
		}
		ForumLogger.errorLog("The user " + user.getUsername() + " has no permissions to view threads");
		throw new UserNotAuthorizedException("to view threads.");
	}

    public static List<Notification> getPendingNotifications(Forum forum, User user) throws UserNotAuthorizedException {
        if (PolicyHandler.canUserGetNotifications()) {
            return user.getPendingNotifications();
        }
        throw new UserNotAuthorizedException("to view notifications.");
    }

	private static User getUserFromForum(int id) {
        return (User)HibernateUtils.load(User.class,id);
	}

    private static User getUserFromForum(Forum forum, String user, String pass) {
        for(User u : forum.getMembers()){
           if(u.getUsername()!= null && u.getUsername().equals(user))
               return u;
        }
        return null;
    }

}