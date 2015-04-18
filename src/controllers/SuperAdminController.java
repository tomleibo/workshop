package controllers;

import content.Message;
import content.SubForum;
import users.Member;

public class SuperAdminController {
	public SubForum createNewForum() {
		return null;
	}
	
	public boolean changeAdministrator(SubForum subForum, Member admin) {
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
