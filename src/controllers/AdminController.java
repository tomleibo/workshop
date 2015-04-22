package controllers;

import content.Forum;
import content.SubForum;
import exceptions.UserNotAuthorizedException;
import policy.PolicyHandler;
import users.User;

public class AdminController {
	
	public String viewStatistics (User user) {
		return null;
	}
	
	public boolean appointModerator(Forum forum, SubForum subForum, User admin, User moderator) throws UserNotAuthorizedException {
		if (PolicyHandler.canAppointModerator(forum, subForum, admin, moderator))
			return subForum.addModerator(moderator) && moderator.appoint(subForum);
		throw new UserNotAuthorizedException("to appoint moderator");
	}
	
	public boolean banModerator(Forum forum, SubForum subForum, User admin, User moderator) throws UserNotAuthorizedException {
		if (PolicyHandler.canBanModerator(forum, subForum, admin, moderator))
			return moderator.banModerator();
		throw new UserNotAuthorizedException ("to ban moderator");
	}
	
	public boolean unAppoint(Forum forum, SubForum subForum, User admin, User moderator) throws UserNotAuthorizedException {
		if (PolicyHandler.canUnAppointModerator(forum, subForum, admin, moderator))
			return subForum.removeModerator(moderator) && moderator.unAppoint(subForum);
		throw new UserNotAuthorizedException ("to unappoint moderator");
	}
	
	public boolean replaceModerator(Forum forum, SubForum subForum, User admin, User oldModerator, User newModerator) throws UserNotAuthorizedException {
		if (PolicyHandler.canReplaceModerator(forum, subForum, admin, oldModerator, newModerator))
			return subForum.changeModerator(oldModerator, newModerator) && newModerator.appoint(subForum) && oldModerator.unAppoint(subForum);
		throw new UserNotAuthorizedException ("to replace moderator");
	}
	
	public boolean banMember(Forum forum, User admin, User member) throws UserNotAuthorizedException {
		if (PolicyHandler.canBanMember(forum, admin, member))
			return member.banUser();
		throw new UserNotAuthorizedException ("to ban member");
	}

	public static SubForum addSubForum(Forum forum, String title, User admin) throws UserNotAuthorizedException {
		return ContentController.addSubForum(forum, title, admin);
	}

	public static boolean deleteSubForum(Forum forum, SubForum subForum,User user) throws UserNotAuthorizedException {
		return ContentController.deleteSubForum(forum, subForum, user);
	}
	
}
