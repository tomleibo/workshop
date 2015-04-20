package users.userState;

public abstract class UserState {

	public boolean canSendReport() {
		return true;
	}
	
	public boolean canGetReport() {
		return true;
	}
	
	public boolean canSendFriendRequest() {
		return true;
	}
	
	public boolean canGetFriendRequest() {
		return true;
	}
}
