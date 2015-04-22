package controllers;

import content.Forum;
import content.ForumSystem;
import content.Message;
import exceptions.UserNotAuthorizedException;
import policy.ForumPolicy;
import policy.PolicyHandler;
import users.User;
import users.userState.UserState;
import users.userState.UserStates;
import utils.Cipher;

import java.security.NoSuchAlgorithmException;

public class SuperAdminController {

	public Forum createNewForum(User superAdmin, ForumPolicy policy, String name) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserAddSubForum(superAdmin)) {
			Forum forum = new Forum(superAdmin, policy, name);
			ForumSystem.getInstance().addForum(forum);
			return forum;
		}
		throw new UserNotAuthorizedException();
	}
	
	public boolean changeAdministrator(User superAdmin, Forum forum, User admin) throws UserNotAuthorizedException {
		if (PolicyHandler.canReplaceAdmin(superAdmin, forum, admin))
			forum.setAdmin(superAdmin);
		throw new UserNotAuthorizedException();
	}
	
	public boolean verifyCorrelationOfContent(User superAdmin, Forum forum, Message msg) {
		return false;
	}

	public boolean changeForumPolicy(User superAdmin, Forum forum, ForumPolicy policy) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserChangePolicy(superAdmin, forum))
			return forum.setPolicy(policy);
		throw new UserNotAuthorizedException();
	}

	public ForumSystem initializeForumSystem(String username, String password, String email) throws NoSuchAlgorithmException {
		User superAdmin = new User(username, Cipher.cipherString(password, "SHA"), email);
		superAdmin.setState(UserState.newState(UserStates.SUPER_ADMIN));
		return ForumSystem.newForumSystem(superAdmin);
	}

}
