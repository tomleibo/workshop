package controllers;

import content.Forum;
import content.SubForum;
import policy.PolicyHandler;
import users.User;

public class AdminController {
	
	public String viewStatistics (User user) {
		return null;
	}
	
	public boolean appointModerator(Forum forum, SubForum subForum, User admin, User moderator) {
		if (PolicyHandler.canAppointModerator(forum, subForum, admin, moderator))
			return subForum.appointModerator(moderator) && moderator.appoint(subForum);
		return false;
	}
	
	public boolean banModerator(Forum forum, SubForum subForum, User admin, User moderator) {
		if (PolicyHandler.canBanModerator(forum, subForum, admin, moderator))
			return moderator.banModerator();
		return false;
	}
	
	public boolean unAppoint(Forum forum, SubForum subForum, User admin, User moderator) {
		if (PolicyHandler.canUnAppointModerator(forum, subForum, admin, moderator))
			return subForum.unAppointModerator(moderator) && moderator.unAppoint(subForum);
		return false;
	}
	
	public boolean replaceModerator(Forum forum, SubForum subForum, User admin, User oldModerator, User newModerator) {
		if (PolicyHandler.canReplaceModerator(forum, subForum, admin, oldModerator, newModerator))
			return subForum.replaceModerator(oldModerator, newModerator) && newModerator.appoint(subForum) && oldModerator.unAppoint(subForum);
		return false;
	}
	
	public boolean banMember(Forum forum, User admin, User member) {
		if (PolicyHandler.canBanMember(forum, admin, member))
			return member.banUser();
		return false;
	}
	
}
