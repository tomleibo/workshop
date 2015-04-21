package exceptions;

public class UserNotAuthorized extends Exception {
	private static final long serialVersionUID = 1L;
	public UserNotAuthorized() {
		// TODO Auto-generated constructor stub
	}
	
	public UserNotAuthorized(String msg) {
		super(msg);
	}
}
