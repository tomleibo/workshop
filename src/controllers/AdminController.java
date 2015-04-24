package controllers;

import content.Forum;
import content.SubForum;
import exceptions.UserNotAuthorizedException;
import policy.PolicyHandler;
import users.User;

public class AdminController {
	
	public static String viewStatistics (User user) {
		// TODO
		return null;
	}
	
	public static boolean appointModerator(Forum forum, SubForum subForum, User admin, User moderator) throws UserNotAuthorizedException {
		if (PolicyHandler.canAppointModerator(forum, subForum, admin, moderator))
			return subForum.addModerator(moderator) && moderator.appoint(subForum);
		throw new UserNotAuthorizedException("to appoint moderator");
	}
	
	public static boolean banModerator(Forum forum, SubForum subForum, User admin, User moderator) throws UserNotAuthorizedException {
		if (PolicyHandler.canBanModerator(forum, subForum, admin, moderator))
			return moderator.banModerator();
		throw new UserNotAuthorizedException ("to ban moderator");
	}
	
	public static boolean unAppoint(Forum forum, SubForum subForum, User admin, User moderator) throws UserNotAuthorizedException {
		if (PolicyHandler.canUnAppointModerator(forum, subForum, admin, moderator))
			return subForum.removeModerator(moderator) && moderator.unAppoint(subForum);
		throw new UserNotAuthorizedException ("to un-appoint moderator");
	}
	
	public static boolean replaceModerator(Forum forum, SubForum subForum, User admin, User oldModerator, User newModerator) throws UserNotAuthorizedException {
		if (PolicyHandler.canReplaceModerator(forum, subForum, admin, oldModerator, newModerator))
			return subForum.changeModerator(oldModerator, newModerator) && newModerator.appoint(subForum) && oldModerator.unAppoint(subForum);
		throw new UserNotAuthorizedException ("to replace moderator");
	}


	public static SubForum addSubForum(Forum forum, String title, User admin) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserAddSubForum(forum, admin)) {
			return ContentController.addSubForum(forum, title, admin);
		}
		throw new UserNotAuthorizedException("to add subForum.");
	}

	public static boolean deleteSubForum(Forum forum, SubForum subForum, User admin) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserDeleteSubForum(forum, admin)) {
			return ContentController.deleteSubForum(forum, subForum);
		}
		throw new UserNotAuthorizedException("to delete sub forum");
	}
	
}
