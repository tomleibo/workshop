package controllers;
import content.Message;
import content.SubForum;
import users.User;

import java.util.List;


public class ModerationConroller {
	
	public boolean banUser(SubForum subForum, User Moderator, User member) {
		return false;
	}
	
	public List<SubForum> subForumOfModerator(User Moderator) {
		//for moderators.
		return null;
	}
	
	public boolean editMessage(SubForum subForum, User Moderator, Message msg, String title, String content) {
		//by policy
		return false;
	}
	
	public boolean deleteMessage(SubForum subForum, User moderator, Message msg) {
		//by policy.
		return false;
	}
}

