package users;

import content.SubForum;
import users.userState.MemberState;
import users.userState.UserState;
import users.userState.UserStates;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
	
	private int id;
	private UserState state;
	private String userName;
	private String hashedPassword;
	private boolean active;
	private boolean banned;
	private Set<User> friends;
	private List<Notification> pendingNotifications;
	private List<FriendRequest> friendRequests;
	private List<Report> sentReports;
	private boolean loggedIn;
	private String emailAddress;

	private static final Object superAdminLock = new Object();
	private static User superAdmin;

	private User(UserState state) {
		this.state = state;
		initializeFlags();
	}

	public User(String username, String hashedPassword, String emailAddress) {
		this.userName = username;
		this.hashedPassword = hashedPassword;
		this.state = new MemberState();
		this.emailAddress = emailAddress;
		friends = new HashSet<>();
		friendRequests = new ArrayList<>();
		pendingNotifications = new  ArrayList<>();
		sentReports = new ArrayList<>();
		initializeFlags();
	}

	private void initializeFlags() {
		active = true;
		banned = false;
		loggedIn = false;
	}

	public static User newGuestUser() {
		return new User(UserState.newState(UserStates.GUEST));
	}

	public static User getSuperAdmin() {
		if (superAdmin == null) {
			synchronized (superAdminLock) {
				if (superAdmin == null) {
					superAdmin = new User(UserState.newState(UserStates.SUPER_ADMIN));
				}
			}
		}
		return superAdmin;
	}

	public boolean getFriendRequest(FriendRequest request) {
		return friendRequests.add(request);
	}
	
	public boolean addFriend(User user) {
		return friends.add(user);
	}
	
	public boolean deleteFriend(User user) {
		return friends.remove(user);
	}
	
	public boolean deactivate() {
		if (!active)
			return false;
		active = false;
		return true;
	}
	
	public boolean banUser() {
		if(banned)
			return false;
		banned = true;
		return true;
	}

	public String getUserName() {
		return userName;
	}	
	
	public boolean isActive() {
		return (active & !banned);
	}
	
	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	public boolean logout() {
		if(!isLoggedIn())
			return false;
		loggedIn = false;
		return true;
	}
	
	public boolean login() {
		if(isLoggedIn())
			return false;
		loggedIn = true;
		return true;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public UserState getState() {
		return state;
	}

	public void setState(UserState state) {
		this.state = state;
	}

	public boolean addSentReport(Report report) {
		return sentReports.add(report);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id != user.id) return false;
		if (state != null ? !state.equals(user.state) : user.state != null) return false;
		if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
		if (hashedPassword != null ? !hashedPassword.equals(user.hashedPassword) : user.hashedPassword != null)
			return false;
		return !(emailAddress != null ? !emailAddress.equals(user.emailAddress) : user.emailAddress != null);

	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (state != null ? state.hashCode() : 0);
		result = 31 * result + (userName != null ? userName.hashCode() : 0);
		result = 31 * result + (hashedPassword != null ? hashedPassword.hashCode() : 0);
		result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "User{" +
				"userName : '" + userName + '\'' +
				", state : " + state +
				((pendingNotifications != null) ? (", pendingNotifications : " + pendingNotifications) : "") +
				((friendRequests != null) ? ", friendRequests : " + friendRequests : "") +
				'}';
	}

	public boolean unAppoint(SubForum subForum) {
		return false;
	}

	public boolean appoint(SubForum subForum) {
		return false;
	}

	public boolean banModerator() {
		return false;
	}
}
