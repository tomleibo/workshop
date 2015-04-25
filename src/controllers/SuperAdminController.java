package controllers;

import content.Forum;
import content.ForumSystem;
import content.Message;
import exceptions.UserNotAuthorizedException;
import policy.ForumPolicy;
import policy.PolicyHandler;
import users.User;
import utils.Cipher;

import java.security.NoSuchAlgorithmException;

public class SuperAdminController {

	public static Forum createNewForum(User superAdmin, ForumPolicy policy, String name) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserAddSubForum(superAdmin)) {
			Forum forum = new Forum(superAdmin, policy, name);
			ForumSystem.getInstance().addForum(forum);
			return forum;
		}
		throw new UserNotAuthorizedException("to create new forum.");
	}
	
	public static boolean changeAdministrator(User superAdmin, Forum forum, User admin) throws UserNotAuthorizedException {
		if (PolicyHandler.canReplaceAdmin(superAdmin, forum, admin))
			forum.setAdmin(superAdmin);
		throw new UserNotAuthorizedException("to change administrator.");
	}
	
	public static boolean verifyCorrelationOfContent(User superAdmin, Forum forum, Message msg) {
		// TODO
		return false;
	}

	public static boolean changeForumPolicy(User superAdmin, Forum forum, ForumPolicy policy) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserChangePolicy(superAdmin, forum))
			return forum.setPolicy(policy);
		throw new UserNotAuthorizedException("to change forum policy.");
	}

	public static ForumSystem initializeForumSystem(String username, String password, String email) throws NoSuchAlgorithmException {
		User superAdmin = User.newSuperAdmin(username, Cipher.hashString(password, Cipher.SHA), email);
		return ForumSystem.newForumSystem(superAdmin);
	}

}
