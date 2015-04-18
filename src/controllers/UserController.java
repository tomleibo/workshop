package controllers;

import users.Member;

public class UserController {
	public Member login(String user, String pass) {
		return new Member();
	}
	
	public Member enterAsGuest() {
		return new Member(); //based on session id?
	}
	
	public boolean logout(int userId) {
		return true;
	}
	
	public Member register(String user, String hashedPass /* more fields */) {
		return new Member(); 
	}
	
	public boolean addFriend(Member newFriend) {
		return false;
	}
	
	public boolean removeFriend(Member friend) {
		return false;
	}
	
	public String viewOwnProfile(Member member) {
		return null;
	}
	
	public boolean replyToFriendRequest(boolean answer) {
		return false;
	}
	
	public boolean report(Member memberReporting, Member admin, String title, String body) {
		return false;
	}
	
	public boolean deactivate(Member member) {
		return false;
	}
}
