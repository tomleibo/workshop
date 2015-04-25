package controllers;
import content.SubForum;
import exceptions.UserNotAuthorizedException;
import policy.PolicyHandler;
import users.User;
import utils.ForumLogger;

import java.util.List;


public class ModerationController {
	
	public static boolean banUser(SubForum subForum, User moderator, User member) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserBanMember(subForum, moderator, member)) {
			return member.banUser();
		}
		ForumLogger.errorLog("The user " + moderator.getUsername() + " can't ban user");
		throw new UserNotAuthorizedException("to ban user");
	}
	
	public static List<SubForum> subForumOfModerator(User moderator) {
		// TODO
		return null;
	}
}

