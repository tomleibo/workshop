package controllers;

import content.Forum;
import content.Message;
import content.SubForum;
import policy.Policy;
import users.User;

public class SuperAdminController {

	public Forum createNewForum(User user, Policy policy, String name) {
		return null;
	}
	
	public boolean changeAdministrator(SubForum subForum, User admin) {
		//by policy/
		return false;
	}
	
	public boolean verifyCorrelationOfContent(Message msg) {
		return false;
	}
	
	public boolean changeModeratorSuspensionPolicy() {
		return false;
	}
	
	public boolean changeAdminSuspensionPolicy() {
		return false;
	}
	
	public boolean changeModeratorAppointmentPolicy() {
		return false;
	}
	
	public boolean changeAdminAppointmentPolicy() {
		return false;
	}
	
	public boolean initializeForum() {
		return false;
	}
	
	public boolean changeMemberSuspensionPolicy() {
		return false;
	}
}
