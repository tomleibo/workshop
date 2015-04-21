package users.userState;

public abstract class UserState {

	public UserState newState(UserStates type) {
		switch (type) {
			case SUPER_ADMIN: return new SuperAdminState();
			case ADMIN: return new AdminState();
			case MODERATOR: return new ModeratorState();
			case MEMBER: return new MemberState();
			case GUEST: return new GuestState();
			default: return new MemberState();
		}
	}

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

	@Override
	public abstract String toString();
}
