package exceptions;

/**
 * Created by Yuval on 6/22/2015.
 */
public class UserCantBeModeratorException extends Exception {
    public UserCantBeModeratorException(String message) {
        super(message);
    }
}
