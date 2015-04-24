package controllers;

import content.Forum;
import content.ForumSystem;
import content.Message;
import exceptions.UserNotAuthorizedException;
import policy.ForumPolicy;
import policy.PolicyHandler;
import policy.UserStatusPolicy;
import users.User;
import utils.Cipher;

import java.security.NoSuchAlgorithmException;

public class SuperAdminController {

	public Forum createNewForum(User superAdmin, ForumPolicy policy, String name) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserAddForum(superAdmin)) {
			Forum forum = new Forum(superAdmin, policy, name);
			ForumSystem.getInstance().addForum(forum);
			return forum;
		}
		throw new UserNotAuthorizedException("to create new forum.");
	}
	
	public boolean changeAdministrator(User superAdmin, Forum forum, User admin) throws UserNotAuthorizedException {
		if (PolicyHandler.canReplaceAdmin(superAdmin, forum, admin))
			forum.setAdmin(superAdmin);
		throw new UserNotAuthorizedException("to change administrator.");
	}
	
	public boolean verifyCorrelationOfContent(User superAdmin, Forum forum, Message msg) {
		// TODO
		return false;
	}

	public boolean changeForumPolicy(User superAdmin, Forum forum, ForumPolicy policy) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserChangePolicy(superAdmin, forum))
			return forum.setPolicy(policy);
		throw new UserNotAuthorizedException("to change forum policy.");
	}

	public ForumSystem initializeForumSystem(String username, String password, String email) throws NoSuchAlgorithmException {
		User superAdmin = User.newSuperAdmin(username, Cipher.hashString(password, Cipher.SHA), email);
		return ForumSystem.newForumSystem(superAdmin);
	}

	public boolean addUserStatusType(String type, UserStatusPolicy userStatusPolicy){
		return ForumSystem.getInstance().addUserStatusType(type, userStatusPolicy);
	}

	public boolean removeUserStatusType(String type){
		return ForumSystem.getInstance().removeUserStatusType(type);
	}

}
