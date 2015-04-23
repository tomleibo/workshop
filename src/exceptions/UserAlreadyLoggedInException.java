package exceptions;

import users.User;

/**
 * Created by Yuval on 4/23/2015.
 */
public class UserAlreadyLoggedInException extends Throwable {

    private User user;

    public UserAlreadyLoggedInException(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
