package controllers;

import content.SubForum;
import users.Member;

public class AdminController {
	
	public String viewStatistics () {
		return null;
	}
	
	public boolean appointModerator(Member moderator, SubForum subforum) {
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
	
	public boolean replaceModerator(Member thisModerator, Member newModerator) {
		return false;
	}
	
	public boolean banMember() {
		return false;
	}
	
}
