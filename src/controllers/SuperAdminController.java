package controllers;

import content.Forum;
import content.ForumSystem;
import content.Message;
import policy.Policy;
import users.User;

public class SuperAdminController {

	public Forum createNewForum(User superAdmin, User admin, Policy policy, String name) {
		return null;
	}
	
	public boolean changeAdministrator(User superAdmin, Forum forum, User admin) {
		//by policy/
		return false;
	}
	
	public boolean verifyCorrelationOfContent(User superAdmin, Forum forum, Message msg) {
		return false;
	}
	
	public boolean changeModeratorSuspensionPolicy(User superAdmin, Policy policy) {
		return false;
	}
	
	public boolean changeAdminSuspensionPolicy(User superAdmin, Policy policy) {
		return false;
	}
	
	public boolean changeModeratorAppointmentPolicy(User superAdmin, Policy policy) {
		return false;
	}
	
	public boolean changeAdminAppointmentPolicy(User superAdmin, Policy policy) {
		return false;
	}

	public boolean changeMemberSuspensionPolicy(User superAdmin, Policy policy) {
		return false;
	}

	public ForumSystem initializeForumSystem(String username, String Password, String email) {
		return null;
	}

}
