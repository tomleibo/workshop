package controllers;

import content.Forum;
import exceptions.UsernameAlreadyExistsException;
import users.FriendRequest;
import users.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class UserController {
	public User login(Forum forum, String username, String password) {
		User user = getUserFromForum(username, forum);
		if (user == null)
			return null;
		if (!user.getHashedPassword().equals(hashString(password)))
			return null;
		return user;
	}
	
	public User enterAsGuest(Forum forum) {
		return User.getGuestUser(forum);
	}
	
	public boolean logout(Forum forum, String username) {
		User user = getUserFromForum(username, forum);
		return user != null && user.logout();
	}

	public User register(Forum forum, String username, String password) throws UsernameAlreadyExistsException {
		if (getUserFromForum(username, forum) != null)
			throw new UsernameAlreadyExistsException("Username: " + username + " already exists in forum: " + forum.getName() + ".");
		User newUser = new User(forum, username, hashString(password));
		// TODO need to add mail validation here!
		forum.addMember(newUser);
		return newUser;
	}
	
	public boolean sendFriendRequest(User from, User to, String message) {
		return from.sendFriendRequest(to, message) != null;
	}
	
	public boolean removeFriend(User user, User friend) {
		return friend.deleteFriend(user) && user.deleteFriend(friend);
	}
	
	public String viewOwnProfile(User user) {
		return user.toString();
	}
	
	public boolean replyToFriendRequest(FriendRequest request, boolean answer) {
		if (answer) {
			User requesting = request.getRequestingMember();
			User receiving = request.getReceivingMember();
			if (receiving.addFriend(requesting))
				return requesting.addFriend(receiving);
		}
		return false;
	}
	
	public boolean report(User reporter, User admin, String title, String content) {
		return reporter.sendReport(admin, title, content) != null;
	}
	
	public boolean deactivate(User member) {
		return member.deactivate();
	}
	
	private User getUserFromForum(String username, Forum forum) {
		List<User> members = forum.getMembers();
		for (User member : members) {
			if (member.getUserName().equals(username))
				return member;
		}
		return null;
	}
	
	private String hashString(String string) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(string.getBytes());
			return Arrays.toString(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return string;
		}
	}
}
