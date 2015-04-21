package exceptions;

/**
 * Created by Yuval on 4/21/2015.
 */
public class UsernameAlreadyExistsException extends Exception {

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
