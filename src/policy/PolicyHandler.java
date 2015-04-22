package policy;

import users.User;
import content.Forum;
import content.Message;
import content.SubForum;
@SuppressWarnings("unused")
public class PolicyHandler {
	public PolicyHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean canUserDeleteComment(Forum forum, SubForum subForum, User user, Message comment) {
		return true;
	}

	public static boolean canUserEditComment(Forum forum, SubForum subForum, User user, Message msg) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public static boolean canUserOpenThread(Forum forum, User user) {
		return true;
	}

	public static boolean canUserViewSubForums(Forum forum, User user) {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean canUserDeleteSubForum(Forum forum, User user) {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean canUserAddSubForum(Forum forum, User mod) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public static boolean canBanModerator(Forum forum, SubForum sub, User admin, User mod) {
		return true;
	}


	public static boolean canBanMember(Forum forum, User admin, User member) {
		return true;
	}

	public static boolean canReplaceModerator(Forum forum, SubForum subForum, User admin, User oldModerator, User newModerator) {
		return true;
	}

	public static boolean canUnAppointModerator(Forum forum, SubForum subForum, User admin, User moderator) {
		return true;
	}

	public static boolean canAppointModerator(Forum forum, SubForum subForum, User admin, User moderator) {
		return true;
	}

	public static boolean canUserHaveFriends(Forum forum, User from) {
		return true;
	}

	public static boolean canBanUser(SubForum subForum, User moderator,	User member) {
		return true;
	}

	public static boolean canUserReply(Forum forum, User user) {
		return true;
	}

	public static boolean canUserAddSubForum(User superAdmin) {
		return true;
	}

	public static boolean canReplaceAdmin(User superAdmin, Forum forum, User admin) {
		return true;
	}
}
