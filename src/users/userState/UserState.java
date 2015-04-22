package users.userState;

public abstract class UserState {

	private String stateon;

	public static UserState newState(UserStates type) {
		switch (type) {
			case SUPER_ADMIN: return new SuperAdminState();
			case ADMIN: return new AdminState();
			case MODERATOR: return new ModeratorState();
			case MEMBER: return new MemberState();
			case GUEST: return new GuestState();
			default: return new MemberState();
		}
	}

	public Boolean isSuperAdmin(){
		return false;
	}

	public boolean isAdmine(){
		return false;
	}

	public boolean isModerator(){
		return false;
	}

	public boolean isMember(){
		return false;
	}

	public boolean isGuest(){
		return false;
	}

	public String getStateon() {
		return stateon;
	}

	public void setStateon(String stateon) {
		this.stateon = stateon;
	}

	@Override
	public abstract String toString();

}
