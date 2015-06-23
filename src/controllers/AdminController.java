package controllers;

import content.Forum;
import content.Message;
import content.SubForum;
import exceptions.UserCantBeModeratorException;
import exceptions.UserNotAuthorizedException;
import policy.PolicyHandler;
import users.User;
import utils.ForumLogger;
import utils.HibernateUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdminController {
	
	public static String viewStatistics (User user) {
		// TODO
		return null;
	}
	
	public static boolean appointModerator(Forum forum, SubForum subForum, User admin, User moderator) throws UserNotAuthorizedException, UserCantBeModeratorException {
		if (PolicyHandler.canAppointModerator(forum, subForum, admin, moderator)) {
            if (PolicyHandler.canUserBeModerator(moderator, forum, subForum)) {
                boolean b =subForum.addModerator(moderator) && moderator.appoint(subForum);
                if (b) {
                    HibernateUtils.update(subForum);
                    HibernateUtils.update(moderator);
                }
                return b;
            }
			throw new UserCantBeModeratorException("User does not meet requirements for being this sub forum moderator");
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
				HibernateUtils.update(subForum);
				HibernateUtils.update(moderator);
			}
			return b;
		}
		ForumLogger.errorLog("The user " + admin.getUsername() + " can't un-appoint moderator");
		throw new UserNotAuthorizedException ("to un-appoint moderator");
	}
	
	public static boolean replaceModerator(Forum forum, SubForum subForum, User admin, User oldModerator, User newModerator) throws UserNotAuthorizedException {
		if (PolicyHandler.canReplaceModerator(forum, subForum, admin, oldModerator, newModerator)) {
			boolean b = subForum.changeModerator(oldModerator, newModerator)
                    && newModerator.appoint(subForum)
					&& oldModerator.unAppoint(subForum);
				if (b) {
					HibernateUtils.update(subForum);
					HibernateUtils.update(oldModerator);
					HibernateUtils.update(newModerator);
				}
			return  b;
		}
		ForumLogger.errorLog("The user " + admin.getUsername() + " can't replace moderator");
		throw new UserNotAuthorizedException ("to replace moderator");
	}


	public static SubForum addSubForum(Forum forum, String title, User admin) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserAddSubForum(forum, admin)) {
			SubForum sub = ContentController.addSubForum(forum, title, admin);
//			HibernateUtils.save(sub);
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
				HibernateUtils.update(forum);
			}
			return b;
		}
		ForumLogger.errorLog("The user " + admin.getUsername() + " can't delete sub forum");
		throw new UserNotAuthorizedException("to delete sub forum");
	}

    public static int getReportTotalMessagesInSubForum(Forum forum, User admin, SubForum subForum) throws UserNotAuthorizedException {
        if (PolicyHandler.canUserGetNumberOfMessagesInSubForum(forum, admin, subForum)) {
            return subForum.getNumberOfMessages();
        }
        throw new UserNotAuthorizedException("to view reports");
    }

    public static List<Message> getReportTotalMessagesOfMember(Forum forum, User admin, User member) throws UserNotAuthorizedException {
        if (PolicyHandler.canUserGetNumberOfMessagesOfMember(forum, admin, member)) {
            return HibernateUtils.getUsersMessages(member.getId());
        }
        throw new UserNotAuthorizedException("to view reports");
    }

    public static Set<User> getReportModeratorList(Forum forum, User admin) throws UserNotAuthorizedException {
        if (PolicyHandler.canUserGetModeratorList(forum, admin)) {
            Set<User> moderators = new HashSet<>();
            for (SubForum subForum : forum.getSubForums()) {
                moderators.addAll(subForum.getModerators());
            }
            return moderators;
        }
        throw new UserNotAuthorizedException("to view reports");
    }

    public static List getModeratorSubForumList(Forum forum, User admin, User moderator) throws UserNotAuthorizedException {
        if (PolicyHandler.canGetModeratorSubForumList(forum, admin, moderator)) {
            return moderator.getManagedSubForums();
        }
        throw new UserNotAuthorizedException("to view moderator sub forum list");
    }

    public static boolean addUserStatusType(Forum forum, User admin, String type) throws UserNotAuthorizedException {
        if (PolicyHandler.canUserAddRemoveStatusType(forum, admin)) {
            return forum.addStatusType(type);
        }
        throw new UserNotAuthorizedException("to add user status");
    }

    public static boolean removeUserStatusType(Forum forum, User admin, String type) throws UserNotAuthorizedException {
        if (PolicyHandler.canUserAddRemoveStatusType(forum, admin)) {
            return forum.removeStatusType(type);
        }
        throw new UserNotAuthorizedException("to add user status");
    }

    public static List<String> getUserStatusTypes(Forum forum, User admin) throws UserNotAuthorizedException {
        if (PolicyHandler.canUserAddRemoveStatusType(forum, admin)) {
            return forum.getStatusTypes();
        }
        throw new UserNotAuthorizedException("to add user status");
    }

}
