package controllers;
import java.util.List;

import content.Message;
import content.SubForum;
import users.Member;


public class ModerationConroller {
	
	public boolean banUser(Member userToBan, long time) {
		return false;
	}
	
	public List<SubForum> subForumOfModerator() {
		//for moderators.
		return null;
	}
	
	public boolean editMessage(Message msg, String content, String title) {
		//by policy
		return false;
	}
	
	public boolean deleteMessage(Message msg) {
		//by policy.
		return false;
	}
}

