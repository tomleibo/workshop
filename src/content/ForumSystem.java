package content;

import exceptions.UserNotAuthorizedException;
import users.User;

import java.util.ArrayList;
import java.util.List;

public class ForumSystem {

    User superAdmin;
    List<Forum> forums;

    public ForumSystem(User superAdmin) {
        this.superAdmin = superAdmin;
        this.forums = new ArrayList<>();
    }

    public User getSuperAdmin(String username, String cipheredPassword) throws UserNotAuthorizedException {
        if (superAdmin.getUserName().equals(username) && superAdmin.getCipheredPassword().equals(cipheredPassword))
            return superAdmin;
        throw new UserNotAuthorizedException("Not authorized");
    }
}
