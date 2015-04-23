package exceptions;

public class UserNotAuthorizedException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserNotAuthorizedException() {
	}
	
	public UserNotAuthorizedException(String msg) {
		super(msg);
	}
}
