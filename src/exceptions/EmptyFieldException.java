package exceptions;

/**
 * Created by Yuval on 6/7/2015.
 */
public class EmptyFieldException extends Exception {
    public EmptyFieldException() {
        super("Enter All Fields!");
    }
}
