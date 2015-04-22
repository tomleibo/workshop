package controllers;
import content.Forum;
import content.Message;
import content.SubForum;
import exceptions.UserNotAuthorizedException;
import policy.PolicyHandler;
import users.User;

import java.util.List;


public class ModerationController {
	
	public boolean banUser(SubForum subForum, User moderator, User member) throws UserNotAuthorizedException {
		if (PolicyHandler.canBanUser(subForum, moderator, member)) {
			return member.banUser();
		}
		throw new UserNotAuthorizedException("to ban user");
	}
	
	public List<SubForum> subForumOfModerator(User Moderator) {
		String warning = "bivan needs to do it somehow";
		return null;
	}
	
	public boolean editMessage(Forum forum,SubForum subForum, User moderator, Message msg, String title, String content) throws UserNotAuthorizedException {
		// policy in content controller.
		return ContentController.editPost(forum, subForum,moderator, msg, content);
	}
	
	public boolean deleteMessage(Forum forum,SubForum subForum, User moderator, Message msg) throws UserNotAuthorizedException {
		// policy in content controller.
		return ContentController.deletePost(forum, subForum,moderator, msg);
	}
}

