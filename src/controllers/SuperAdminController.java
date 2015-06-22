package controllers;

import content.Forum;
import content.Message;
import exceptions.*;
import org.hibernate.Query;
import policy.ForumPolicy;
import policy.PolicyHandler;
import users.User;
import utils.Cipher;
import utils.ForumLogger;
import utils.HibernateUtils;
import utils.SessionLogger;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class SuperAdminController {

    public static User loginSuperAdmin(String username, String password) throws NoSuchAlgorithmException, UserAlreadyLoggedInException, WrongPasswordException, UserDoesNotExistsException {
        List<User> users = HibernateUtils.getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getState() == User.SUPERADMIN) {
                user = user.login(Cipher.hashString(password, Cipher.SHA));
                HibernateUtils.update(user);
                return user;
            }
        }
        ForumLogger.errorLog("The user " + username + " trying to login but he does not exists as a super user.");
        throw new UserDoesNotExistsException();
    }

    public static void logoutSuperAdmin(User superAdmin) throws UserDoesNotExistsException, UserNotLoggedInException {
        if (superAdmin != null && superAdmin.isSuperAdmin()) {
            superAdmin.logout();
            HibernateUtils.update(superAdmin);
            ForumLogger.actionLog("The super user is logged out successfully");
        }
        ForumLogger.errorLog("The super user was trying to logout but he is not allowed");
        throw new UserDoesNotExistsException();
    }

	public static Forum createNewForum(User superAdmin, ForumPolicy policy, String name) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserAddForum(superAdmin)) {
			Forum forum = new Forum(superAdmin, policy, name);
            if (forum !=null) {
                if (!HibernateUtils.save(forum)) {
                    System.out.println("create new forum failed in hibernate.");
                    ForumLogger.errorLog("create new forum failed in hibernate.");
                }
            }
			return forum;
		}
		ForumLogger.errorLog("The user " + superAdmin.getUsername() + " can't create new forum");
		throw new UserNotAuthorizedException("to create new forum.");
	}

	public static boolean deleteForum(User superAdmin, Forum forum) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserRemoveForum(superAdmin)) {
            return HibernateUtils.del(forum);
		}
		throw new UserNotAuthorizedException("to remove forum.");
	}

	public static boolean deleteAllForums(User superAdmin) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserRemoveForum(superAdmin)) {
            boolean ans = true;
            List<Forum> forumList = HibernateUtils.getAllForums();
            for (Forum forum: forumList) {
                ans &= HibernateUtils.del(forum);
            }
            return ans;
		}
		throw new UserNotAuthorizedException("to remove forum.");
	}

    private static boolean deleteAllUsers(Forum forum) throws UserNotAuthorizedException {
        boolean ans = true;
        for (User user: forum.getMembers()) {
            ans &= HibernateUtils.del(user);
        }
        return ans;
    }

    private static boolean deleteAllSubForumAndThreads(Forum forum) {
        return false;
    }

	public static boolean changeAdministrator(User superAdmin, Forum forum, User admin) throws UserNotAuthorizedException {
		if (PolicyHandler.canReplaceAdmin(superAdmin, forum, admin)) {
			User oldAdmin = forum.getAdmin();
            forum.setAdmin(admin);
            admin.setState(User.ADMIN);
            if (oldAdmin.getState() <= User.ADMIN) {
                oldAdmin.setState(User.MEMBER);
            }
            HibernateUtils.save(oldAdmin);
			return HibernateUtils.save(forum);
		}
		ForumLogger.errorLog("The user " + superAdmin.getUsername() + " can't change administrator");
		throw new UserNotAuthorizedException("to change administrator.");
	}

	public static boolean verifyCorrelationOfContent(User superAdmin, Forum forum, Message msg) {
		// TODO
		return false;
	}

	public static boolean changeForumPolicy(User superAdmin, Forum forum, ForumPolicy policy) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserChangePolicy(superAdmin, forum)) {
            if(forum.setPolicy(policy)){
                return HibernateUtils.save(forum);
            }
            return false;
        }
		ForumLogger.errorLog("The user " + superAdmin.getUsername() + " can't change forum policy");
		throw new UserNotAuthorizedException("to change forum policy.");
	}

	public static User initializeForumSystem(String username, String password, String email) throws NoSuchAlgorithmException {
        HibernateUtils.init();
        User superAdmin = User.newSuperAdmin(username, Cipher.hashString(password, Cipher.SHA), email);
        HibernateUtils.save(superAdmin);
		return superAdmin;
	}

    public static User startForumSystem(String username, String password, String email) throws NoSuchAlgorithmException {
        HibernateUtils.start();
        User superAdmin = User.newSuperAdmin(username, Cipher.hashString(password, Cipher.SHA), email);
        HibernateUtils.save(superAdmin);
        return superAdmin;
    }

    public static int getReportNumberOfForums(User superAdmin) throws UserNotAuthorizedException {
        if (PolicyHandler.canUserGetNumberOfForums(superAdmin)) {
            Query query = HibernateUtils.getQuery("FROM forum");
            return query.list().size();
        }
        throw new UserNotAuthorizedException("to view reports");
    }

    public static Queue<String> getSessionLog(User user,String id) throws UserNotAuthorizedException {
        if (user.isSuperAdmin()) {
            return SessionLogger.get().getSessionLog(id);
        }
        throw new UserNotAuthorizedException("to view session logs");
    }

    public static Set<String> getAllOpenSessions(User user) throws UserNotAuthorizedException {
        if (user.isSuperAdmin()) {
            return SessionLogger.get().getAllActiveSessions();
        }
        throw new UserNotAuthorizedException("to view session logs");
    }

}
