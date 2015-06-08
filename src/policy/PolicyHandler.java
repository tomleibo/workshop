package policy;

import content.Forum;
import content.Message;
import content.SubForum;
import users.FriendRequest;
import users.User;

import java.util.Date;
import java.util.List;

public class PolicyHandler {

	public static boolean canUserDeleteComment(Forum forum, SubForum subForum, User user, Message comment) {
		//For now only the user who wrote the message can delete it, but it can change by forumPolicy.
		if(forum.getSubForums().contains(subForum) && forum.getMembers().contains(user) && comment.getUser().equals(user) && !(user.isGuest()) && user.isActive())
			return true;
		return false;
	}

	public static boolean canUserEditComment(Forum forum, SubForum subForum, User user, Message msg) {
		//For now only the user who wrote the message can edit it, but it can change by forumPolicy.
		if(forum.getSubForums().contains(subForum) && forum.getMembers().contains(user) && msg.getUser().equals(user) && !(user.isGuest()) && user.isActive())
			return true;
		return false;
	}
	
	public static boolean canUserOpenThread(Forum forum, User user) {
		if(forum.getMembers().contains(user) && !(user.isGuest()) && user.isActive())
			return true;
		return false;

	}

	public static boolean canUserViewSubForums(Forum forum, User user) {
		return true;
	}

	public static boolean canUserDeleteSubForum(Forum forum, User admin) {
		if(forum.getAdmin().equals(admin) || admin.isSuperAdmin())
			return true;
		return false;
	}

	public static boolean canUserAddSubForum(Forum forum, User admin) {
		if(forum.getAdmin().equals(admin) || admin.isSuperAdmin())
			return true;
		return false;
	}
	
	public static boolean canBanModerator(Forum forum, SubForum subForum, User admin, User mod) {
		if(forum.getSubForums().contains(subForum) && forum.getAdmin().equals(admin) && subForum.getModerators().contains(mod))
			return true;
		return false;
	}

	public static boolean canReplaceModerator(Forum forum, SubForum subForum, User admin, User oldModerator, User newModerator) {
		if(subForum.getModerators().contains(oldModerator) && canAppointModerator(forum, subForum, admin, newModerator))
			return true;
		return false;
	}

	public static boolean canUnAppointModerator(Forum forum, SubForum subForum, User admin, User moderator) {
		//and depends on ForumPolicy
		if(forum.getSubForums().contains(subForum) && forum.getAdmin().equals(admin))
			return true;
		return  false;

	}

	public static boolean canAppointModerator(Forum forum, SubForum subForum, User admin, User moderator) {
		if(forum.getSubForums().contains(subForum) && forum.getAdmin().equals(admin) && moderator.isActive())
			return true;
		return  false;
	}

	public static boolean canUserHaveFriends(Forum forum, User user) {
		if(!user.isGuest())
			return true;
		return false;
	}

	public static boolean canUserBanMember(SubForum subForum, User moderator, User member) {
		if(subForum.getModerators().contains(moderator) && !member.isGuest() && moderator.isActive())
			return true;
		return false;

	}

	public static boolean canUserReply(Forum forum, User user) {
		if(user.isActive() && !user.isGuest())
			return true;
		return false;
	}

	public static boolean canUserAddForum(User superAdmin) {
		if(superAdmin.isSuperAdmin())
			return true;
		return false;

	}

	public static boolean canUserRemoveForum(User superAdmin) {
		if(superAdmin.isSuperAdmin())
			return true;
		return false;
	}

	public static boolean canReplaceAdmin(User superAdmin, Forum forum, User admin) {
		if(forum.getAdmin().equals(superAdmin))
			return true;
		return false;
	}

	public static boolean canUserChangePolicy(User superAdmin, Forum forum) {
		if(forum.getAdmin().equals(superAdmin))
			return true;
		return false;
	}

	public static boolean canUserReplyToFriendRequest(Forum forum, User user, FriendRequest request) {
		if(!user.isGuest() && user.isActive())
			return true;
		return false;
	}

	public static boolean canUserReportAdmin(Forum forum, User reporter, User admin) {
		if(!reporter.isGuest() && reporter.isActive() && forum.getMembers().contains(reporter) && (forum.getAdmin().equals(admin) || didUserPostInModeratorSubForums(admin, reporter)))
			return true;
		return false;
	}

	private static boolean didUserPostInModeratorSubForums(User moderator, User member) {
		if (moderator.isMod()) {
			List<SubForum> subForums = moderator.getManagedSubForums();
			for (SubForum subForum : subForums)
				if (subForum.hasModerator(moderator) && subForum.didUserPostHere(member))
					return true;
		}
		return false;
	}

	public static boolean canUserBeDeactivated(User user) {
		if(!user.isGuest())
			return true;
		return false;
	}

	public static boolean canUserDestroyForumSystem(User superAdmin) {
		return superAdmin.isSuperAdmin();
	}

    public static boolean canUserGetNumberOfMessagesInSubForum(Forum forum, User admin, SubForum subForum) {
        return (admin.isSuperAdmin() || (forum.getAdmin().equals(admin) & forum.hasSubForum(subForum)));
    }

    public static boolean canUserGetNumberOfMessagesOfMember(Forum forum, User admin, User member) {
        return (admin.isSuperAdmin() || (forum.getAdmin().equals(admin) & forum.hasSubMember(member)));
    }

    public static boolean canUserGetModeratorList(Forum forum, User admin) {
        return (admin.isSuperAdmin() || forum.getAdmin().equals(admin));
    }

    public static boolean canUserGetNumberOfForums(User superAdmin) {
        return superAdmin.isSuperAdmin();
    }
    public static boolean shouldUserChangePassword(Forum forum, User user) {
        if (forum.getPolicy().getPasswordMaxTime() <= 0)
            return false;
        try {
            long passwordSetTime = user.getPasswordSetDate().getTime();
            long passwordMaxTime = forum.getPolicy().getPasswordMaxTime() * 24 * 60 * 60 * 1000;
            return (new Date()).after(new Date(passwordSetTime + passwordMaxTime));
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean canUserGetNotifications() {
        return true;
    }
}
