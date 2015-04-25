package controllers;

import content.Forum;
import content.ForumSystem;
import content.Message;
import exceptions.UserNotAuthorizedException;
import policy.ForumPolicy;
import policy.PolicyHandler;
import policy.UserStatusPolicy;
import users.User;
import utils.ForumLogger;


import java.security.NoSuchAlgorithmException;

public class SuperAdminController {

	public static Forum createNewForum(User superAdmin, ForumPolicy policy, String name) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserAddForum(superAdmin)) {
			Forum forum = new Forum(superAdmin, policy, name);
			ForumSystem.getInstance().addForum(forum);
			return forum;
		}
		ForumLogger.errorLog("The user " + superAdmin.getUsername() + " can't create new forum");
		throw new UserNotAuthorizedException("to create new forum.");
	}

	public static boolean deleteForum(User superAdmin, Forum forum) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserRemoveForum(superAdmin)) {
			return ForumSystem.getInstance().removeForum(forum);
		}
		throw new UserNotAuthorizedException("to remove forum.");
	}

	public static boolean deleteAllForums(User superAdmin) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserRemoveForum(superAdmin)) {
			ForumSystem.getInstance().removeAllForums();
		}
		throw new UserNotAuthorizedException("to remove forum.");
	}

	public static boolean changeAdministrator(User superAdmin, Forum forum, User admin) throws UserNotAuthorizedException {
		if (PolicyHandler.canReplaceAdmin(superAdmin, forum, admin)) {
			forum.setAdmin(admin);
			return true;
		}
		ForumLogger.errorLog("The user " + superAdmin.getUsername() + " can't change administrator");
		throw new UserNotAuthorizedException("to change administrator.");
	}

	public static boolean verifyCorrelationOfContent(User superAdmin, Forum forum, Message msg) {
		// TODO
		return false;
	}

	public static boolean changeForumPolicy(User superAdmin, Forum forum, ForumPolicy policy) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserChangePolicy(superAdmin, forum))
			return forum.setPolicy(policy);
		ForumLogger.errorLog("The user " + superAdmin.getUsername() + " can't change forum policy");
		throw new UserNotAuthorizedException("to change forum policy.");
	}

	public static ForumSystem initializeForumSystem(String username, String hashedPassword, String email) throws NoSuchAlgorithmException {
		User superAdmin = User.newSuperAdmin(username, hashedPassword, email);
		return ForumSystem.newForumSystem(superAdmin);
	}

	public static void destroyForumSystem(User superAdmin, ForumSystem forumSystem) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserDestroyForumSystem(superAdmin))
			forumSystem.destroy();
		ForumLogger.errorLog("The user " + superAdmin.getUsername() + " can't destroy forum system");
		throw new UserNotAuthorizedException("to destroy forum system.");
	}

	public static boolean addUserStatusType(User superAdmin, String type, UserStatusPolicy userStatusPolicy){
		return ForumSystem.getInstance().addUserStatusType(type, userStatusPolicy);
	}

	public static boolean removeUserStatusType(User superAdmin, String type){
		return ForumSystem.getInstance().removeUserStatusType(type);
	}

}
