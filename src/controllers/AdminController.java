package controllers;

import content.SubForum;
import users.User;

public class AdminController {
	
	public String viewStatistics () {
		return null;
	}
	
	public boolean appointModerator(User moderator, SubForum subforum) {
		//by policy.
		return false;
	}
	
	public boolean banModerator() {
		//by policy
		return false;
	}
	
	public boolean unAppoint() {
		return false;
	}
	
	public boolean replaceModerator(User thisModerator, User newModerator) {
		return false;
	}
	
	public boolean banMember() {
		return false;
	}
	
}
