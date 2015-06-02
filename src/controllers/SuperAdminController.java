package controllers;

import content.Forum;
import content.ForumSystem;
import content.Message;
import exceptions.UserNotAuthorizedException;
import org.hibernate.Query;
import policy.ForumPolicy;
import policy.PolicyHandler;
import policy.UserStatusPolicy;
import users.User;
import utils.ForumLogger;
import utils.HibernateUtils;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class SuperAdminController {

	public static Forum createNewForum(User superAdmin, ForumPolicy policy, String name) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserAddForum(superAdmin)) {
			Forum forum = new Forum(superAdmin, policy, name);
            //TODO: delete forum system?
			//ForumSystem.getInstance().addForum(forum);
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
			// ForumSystem.getInstance().removeForum(forum);
            return HibernateUtils.del(forum);
		}
		throw new UserNotAuthorizedException("to remove forum.");
	}

	public static boolean deleteAllForums(User superAdmin) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserRemoveForum(superAdmin)) {
            boolean ans = true;
            List<Forum> forumList = HibernateUtils.getQuery("from Forum").list();
            for (Forum forum: forumList) {
                ans &= HibernateUtils.del(forum);
            }
            return ans;
		}
		throw new UserNotAuthorizedException("to remove forum.");
	}

	public static boolean changeAdministrator(User superAdmin, Forum forum, User admin) throws UserNotAuthorizedException {
		if (PolicyHandler.canReplaceAdmin(superAdmin, forum, admin)) {
			User oldAdmin = forum.getAdmin();
            forum.setAdmin(admin);
            admin.setState(User.ADMIN);
            oldAdmin.setState(User.MEMBER);
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

	public static ForumSystem initializeForumSystem(String username, String hashedPassword, String email) throws NoSuchAlgorithmException {
        HibernateUtils.init();
        User superAdmin = User.newSuperAdmin(username, hashedPassword, email);
        HibernateUtils.save(superAdmin);
		return ForumSystem.newForumSystem(superAdmin);
        //TODO: change this.
	}

	public static void destroyForumSystem(User superAdmin, ForumSystem forumSystem) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserDestroyForumSystem(superAdmin)){
            HibernateUtils.getQuery("drop database forum_system").executeUpdate();
            //TODO: anything else?
            //forumSystem.destroy();
			return;
		}

		ForumLogger.errorLog("The user " + superAdmin.getUsername() + " can't destroy forum system");
		throw new UserNotAuthorizedException("to destroy forum system.");
	}

    public static int getReportNumberOfForums(User superAdmin) throws UserNotAuthorizedException {
        if (PolicyHandler.canUserGetNumberOfForums(superAdmin)) {
            Query query = HibernateUtils.getQuery("FROM forum");
            return query.list().size();
        }
        throw new UserNotAuthorizedException("to view reports");
    }

	public static boolean addUserStatusType(User superAdmin, String type, UserStatusPolicy userStatusPolicy){
		//TODO: save in sql?
        return ForumSystem.getInstance().addUserStatusType(type, userStatusPolicy);
	}

	public static boolean removeUserStatusType(User superAdmin, String type){
        //TODO: save in sql?
		return ForumSystem.getInstance().removeUserStatusType(type);
	}

}
