package controllers;
import content.SubForum;
import exceptions.UserNotAuthorizedException;
import policy.PolicyHandler;
import users.User;
import utils.ForumLogger;
import utils.HibernateUtils;

import java.util.List;


public class ModerationController {
	
	public static boolean banUser(SubForum subForum, User moderator, User member) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserBanMember(subForum, moderator, member)) {
			boolean b = member.banUser();
            //TODO: no subforum given in signature; subforum does not hold banned users.
            //TODO: These conclude that there is no way to enforce ban.
            if (b) {
                return HibernateUtils.update(member);
            }
            return false;
		}
		ForumLogger.errorLog("The user " + moderator.getUsername() + " can't ban user");
		throw new UserNotAuthorizedException("to ban user");
	}
	
	public static List<SubForum> subForumOfModerator(User moderator) {
		// TODO
		return null;
	}
}

