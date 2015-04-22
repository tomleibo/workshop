package exceptions;

public class UserNotAuthorizedException extends Exception {
	private static final long serialVersionUID = 1L;
	public UserNotAuthorizedException() {
		// TODO Auto-generated constructor stub
	}
	
	public UserNotAuthorizedException(String msg) {
		super(msg);
	}
}
