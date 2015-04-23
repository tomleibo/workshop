package content;

import exceptions.ForumSystemAlreadyExistsException;
import exceptions.ForumSystemNotExistsException;
import exceptions.UserNotAuthorizedException;
import users.User;

import java.util.ArrayList;
import java.util.List;

public class ForumSystem {

    private static ForumSystem forumSystem;

    User superAdmin;
    List<Forum> forums;

    private ForumSystem(User superAdmin) {
        this.superAdmin = superAdmin;
        this.forums = new ArrayList<>();
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
        if (superAdmin.getUsername().equals(username) && superAdmin.getCipheredPassword().equals(cipheredPassword))
            return superAdmin;
        throw new UserNotAuthorizedException("Not authorized");
    }

    public boolean addForum(Forum forum) {
        return forums.add(forum);
    }

    public boolean removeForum(Forum forum) {
        return forums.remove(forum);
    }
}
