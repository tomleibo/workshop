package content;

import exceptions.ForumSystemAlreadyExistsException;
import exceptions.ForumSystemNotExistsException;
import exceptions.UserNotAuthorizedException;
import policy.UserStatusPolicy;
import users.User;
import utils.ForumLogger;

import java.util.*;

public class ForumSystem {

    private static ForumSystem forumSystem;

    User superAdmin;
    List<Forum> forums;
    Map<String, UserStatusPolicy> userStatusTypes;

    private ForumSystem(User superAdmin) {
        this.superAdmin = superAdmin;
        this.forums = new ArrayList<>();
        this.userStatusTypes = new HashMap<>();
    }

    public static synchronized ForumSystem newForumSystem(User superAdmin) throws ForumSystemAlreadyExistsException {
        if (forumSystem != null)
            throw new ForumSystemAlreadyExistsException();
        forumSystem = new ForumSystem(superAdmin);
        return forumSystem;
    }

    public static ForumSystem getInstance() throws ForumSystemNotExistsException {
        if (forumSystem == null)
            throw  new ForumSystemNotExistsException();
        return forumSystem;
    }

    public User getSuperAdmin(String username, String cipheredPassword) throws UserNotAuthorizedException {
        if (superAdmin.getUsername().equals(username) && superAdmin.getHashedPassword().equals(cipheredPassword))
            return superAdmin;
        throw new UserNotAuthorizedException("Not authorized");
    }

    public boolean addForum(Forum forum) {
        return forums.add(forum);
    }

    public boolean removeForum(Forum forum) {
        return forums.remove(forum);
    }

    public boolean addUserStatusType(String type, UserStatusPolicy userStatusPolicy){
        if(userStatusTypes.put(type, userStatusPolicy) != null) {
            ForumLogger.actionLog("The type " + type + " added to the state types");
            return true;
        }
        return false;
    }

    public boolean removeUserStatusType(String type){
        if(userStatusTypes.remove(type)!=null) {
            for(Forum forum:forums){
                for(User user: forum.getMembers()) {
                    if(user.getState().getStatus().equals(type)){
                        user.getState().setStatus("");
                    }
                }
            }
            ForumLogger.actionLog("The type " + type + " removed from the state types");
            return true;
        }
        return false;
    }
}
