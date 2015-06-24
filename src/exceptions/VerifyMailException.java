package exceptions;

import content.Forum;
import users.User;

public class VerifyMailException extends Exception {

    User user;
    Forum forum;

    public VerifyMailException(Forum forum, User user) {
        this.forum = forum;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Forum getForum() {
        return forum;
    }
}
