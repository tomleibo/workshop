package controllers;
import content.Forum;
import content.Message;
import content.SubForum;
import exceptions.UserNotAuthorizedException;
import policy.PolicyHandler;
import users.User;

import java.util.List;


public class ModerationController {
	
	public static boolean banUser(SubForum subForum, User moderator, User member) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserBanMember(subForum, moderator, member)) {
			return member.banUser();
		}
		throw new UserNotAuthorizedException("to ban user");
	}
	
	public static List<SubForum> subForumOfModerator(User moderator) {
		// TODO
		return null;
	}
	
	public static boolean editMessage(Forum forum,SubForum subForum, User moderator, Message msg, String title, String content) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserEditComment(forum, subForum, moderator, msg)) {
			return ContentController.editPost(msg, content);
		}
		throw new UserNotAuthorizedException("to edit post.");
	}
	
	public static boolean deleteMessage(Forum forum, SubForum subForum, User moderator, Message msg) throws UserNotAuthorizedException {
		if(PolicyHandler.canUserDeleteComment(forum, subForum, moderator, msg)) {
			return ContentController.deletePost(msg);
		}
		throw new UserNotAuthorizedException("to delete post.");
	}
}

