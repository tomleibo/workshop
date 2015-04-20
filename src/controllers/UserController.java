package controllers;

import users.User;

public class UserController {
	public User login(String user, String pass) {
		return new User();
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
}
