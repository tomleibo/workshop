package exceptions;

import users.User;

/**
 * Created by Yuval on 6/5/2015.
 */
public class NeedToChangePasswordException extends Exception {

    User user;

    public NeedToChangePasswordException(User user) {
        super();
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
