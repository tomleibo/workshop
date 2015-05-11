package controllers;

import content.Forum;
import content.SubForum;
import exceptions.UserNotAuthorizedException;
import org.hibernate.Query;
import policy.PolicyHandler;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import users.User;
import utils.ForumLogger;
import utils.HibernateUtils;

import java.util.List;

public class AdminController {
	
	public static String viewStatistics (User user) {
		// TODO
		return null;
	}
	
	public static boolean appointModerator(Forum forum, SubForum subForum, User admin, User moderator) throws UserNotAuthorizedException {
		if (PolicyHandler.canAppointModerator(forum, subForum, admin, moderator)) {
			boolean b =subForum.addModerator(moderator) && moderator.appoint(subForum);
			if (b) {
				HibernateUtils.save(subForum);
				HibernateUtils.save(moderator);
			}
			return b;
		}
		ForumLogger.errorLog("The user " + admin.getUsername() + " can't appoint moderator");
		throw new UserNotAuthorizedException("to appoint moderator");
	}
	
	public static boolean banModerator(Forum forum, SubForum subForum, User admin, User moderator) throws UserNotAuthorizedException {
		if (PolicyHandler.canBanModerator(forum, subForum, admin, moderator)) {
			boolean b = moderator.banModerator() && subForum.banModerator(moderator);
			if (b) {
				HibernateUtils.save(subForum);
				HibernateUtils.save(moderator);
			}
			return b;
		}
		ForumLogger.errorLog("The user " + admin.getUsername() + " can't ban moderator");
		throw new UserNotAuthorizedException ("to ban moderator");
	}
	
	public static boolean unAppoint(Forum forum, SubForum subForum, User admin, User moderator) throws UserNotAuthorizedException {
		if (PolicyHandler.canUnAppointModerator(forum, subForum, admin, moderator)) {
			boolean b=subForum.removeModerator(moderator) && moderator.unAppoint(subForum);
			if (b) {
				HibernateUtils.save(subForum);
				HibernateUtils.save(moderator);
			}
			return b;
		}
		ForumLogger.errorLog("The user " + admin.getUsername() + " can't un-appoint moderator");
		throw new UserNotAuthorizedException ("to un-appoint moderator");
	}
	
	public static boolean replaceModerator(Forum forum, SubForum subForum, User admin, User oldModerator, User newModerator) throws UserNotAuthorizedException {
		if (PolicyHandler.canReplaceModerator(forum, subForum, admin, oldModerator, newModerator)) {
			boolean b = subForum.changeModerator(oldModerator, newModerator) && newModerator.appoint(subForum)
					&& oldModerator.unAppoint(subForum);
				if (b) {
					HibernateUtils.save(subForum);
					HibernateUtils.save(oldModerator);
					HibernateUtils.save(newModerator);
				}
			return  b;
		}
		ForumLogger.errorLog("The user " + admin.getUsername() + " can't replace moderator");
		throw new UserNotAuthorizedException ("to replace moderator");
	}


	public static SubForum addSubForum(Forum forum, String title, User admin) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserAddSubForum(forum, admin)) {
			SubForum sub = ContentController.addSubForum(forum, title, admin);
			HibernateUtils.save(sub);
			return sub;
		}
		ForumLogger.errorLog("The user " + admin.getUsername() + " can't add subForum");
		throw new UserNotAuthorizedException("to add subForum.");
	}

	public static boolean deleteSubForum(Forum forum, SubForum subForum, User admin) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserDeleteSubForum(forum, admin)) {
			boolean b = ContentController.deleteSubForum(forum, subForum);
			if (b) {
				HibernateUtils.del(subForum);
				HibernateUtils.save(forum);
			}
			return b;
		}
		ForumLogger.errorLog("The user " + admin.getUsername() + " can't delete sub forum");
		throw new UserNotAuthorizedException("to delete sub forum");
	}

    public static int getReportTotalMessagesInForum(Forum forum, User admin, SubForum subForum) throws UserNotAuthorizedException {
        if (PolicyHandler.canUserGetNumberOfMessagesInSubForum(forum, admin, subForum)) {
            return subForum.getNumberOfMessages();
        }
        throw new UserNotAuthorizedException("to view reports");
    }

    public static int getReportTotalMessagesOfMember(Forum forum, User admin, User member) throws UserNotAuthorizedException {
        if (PolicyHandler.canUserGetNumberOfMessagesOfMember(forum, admin, member)) {
            Query query = HibernateUtils.getQuery("FROM message M WHERE M.publisher = " + member.getId());
            return query.list().size();
        }
        throw new UserNotAuthorizedException("to view reports");
    }

    public static List getReportModeratorList(Forum forum, User admin) throws UserNotAuthorizedException {
        if (PolicyHandler.canUserGetModeratorList(forum, admin)) {
            // TODO
            throw new NotImplementedException();
        }
        throw new UserNotAuthorizedException("to view reports");
    }
	
}
