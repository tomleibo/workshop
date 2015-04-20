package policy;

import users.User;
import content.Forum;
import content.Message;

public class PolicyHandler {
	public PolicyHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean canUserDeleteComment(Forum forum, User user, Message comment) {
		return true;
	}

	public static boolean canUserEditComment(Forum forum, User user, Message msg) {
		// TODO Auto-generated method stub
		return true;
	}
}
