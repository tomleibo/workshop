package users.userState;

import content.SubForum;

public abstract class UserState {

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isSuperAdmin(){
		return false;
	}

	public boolean isAdmin(){
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

	public boolean addManagedSubForum(SubForum subForum) {
		return false;
	}

	public boolean removeManagedSubForum(SubForum subForum) {
		return false;
	}

	public int getNumberOfManagedSubForums() {
		return -1;
	}

	@Override
	public abstract String toString();

}
