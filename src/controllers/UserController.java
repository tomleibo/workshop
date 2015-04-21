package controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import content.Forum;
import users.User;

public class UserController {
	public User login(Forum forum, String username, String password) {
		User user = getUserFromForum(username, forum);
		if (user == null)
			return null;
		
	}
	
	public User enterAsGuest() {
		return new User(); //based on session id?
	}
	
	public boolean logout(int userId) {
		return true;
	}
	
	public User register(String user, String hashedPass /* more fields */) {
		return new User(); 
	}
	
	public boolean addFriend(User newFriend) {
		return false;
	}
	
	public boolean removeFriend(User friend) {
		return false;
	}
	
	public String viewOwnProfile(User member) {
		return null;
	}
	
	public boolean replyToFriendRequest(boolean answer) {
		return false;
	}
	
	public boolean report(User memberReporting, User admin, String title, String body) {
		return false;
	}
	
	public boolean deactivate(User member) {
		return false;
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
			return md.digest().toString();
		} catch (NoSuchAlgorithmException e) {
			return string;
		}
	}
}
