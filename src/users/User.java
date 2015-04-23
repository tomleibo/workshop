package users;

import content.SubForum;
import exceptions.UserAlreadyLoggedInException;
import exceptions.UserNotLoggedInException;
import exceptions.WrongPasswordException;
import users.userState.*;
import utils.IdGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {

	private static String guestUsername = "Guest";
	private static String guestPassword = "";
	private static String guestMail = "";

	private final int id;
	private final String username;
	private String emailAddress;
	private String hashedPassword;
	private java.sql.Date creationDate;

	private UserState state;

	private boolean active;
	private boolean banned;
	private boolean loggedIn;

	private Set<User> friends;
	private List<Notification> pendingNotifications;
	private List<FriendRequest> friendRequests;
	private List<Report> sentReports;

	/**
	 * Constructor for testing.
	 */
	public User(int i) {
		this.id = i;
		this.username = "" + id;
		this.state = new GuestState();
		initializeUser();
	}

	/**
	 * Creates a new User with given arguments.
	 * @param username String representing the username.
	 * @param hashedPassword String representing the hashed password.
	 * @param emailAddress String representing the user's email address.
	 * @param state UserState for the created user.
	 */
	private User(String username, String hashedPassword, String emailAddress, UserState state) {
		this.id = IdGenerator.getId(IdGenerator.USER);
		if (state.isGuest())
			this.username = username + id;
		else
			this.username = username;
		this.hashedPassword = hashedPassword;
		this.state = state;
		this.emailAddress = emailAddress;
		friends = new HashSet<>();
		friendRequests = new ArrayList<>();
		pendingNotifications = new  ArrayList<>();
		sentReports = new ArrayList<>();
		initializeUser();
	}

	private void initializeUser() {
		active = true;
		banned = false;
		loggedIn = false;
		creationDate = new java.sql.Date(System.currentTimeMillis());
	}

	/**
	 * Returns a new guest user.
	 * @return User with Guest state.
	 */
	public static User newGuest() {
		return new User(guestUsername, guestPassword, guestMail, UserStates.newState(UserStates.GUEST));
	}

	/**
	 * returns new super admin with given credentials.
	 * @param username String representing the username.
	 * @param hashedPassword String representing the hashed password.
	 * @param emailAddress String representing the user's email address.
	 * @return User with Member state.
	 */
	public static User newMember(String username, String hashedPassword, String emailAddress) {
		return new User(username, hashedPassword, emailAddress, UserStates.newState(UserStates.MEMBER));
	}

	/**
	 * returns new super admin with given credentials.
	 * @param username String representing the username.
	 * @param hashedPassword String representing the hashed password.
	 * @param emailAddress String representing the user's email address.
	 * @return User with Super-Admin state.
	 */
	public static User newSuperAdmin(String username, String hashedPassword, String emailAddress) {
		return new User(username, hashedPassword, emailAddress, UserStates.newState(UserStates.SUPER_ADMIN));
	}

	/**
	 * Login user and returns itself.
	 * @return user itself;
	 * @throws UserAlreadyLoggedInException if user already logged in, exception contains the logged in user.
	 */
	public User login(String hashedPassword) throws WrongPasswordException, UserAlreadyLoggedInException {
		if (!hashedPassword.equals(this.hashedPassword))
			throw new WrongPasswordException();
		if (isLoggedIn())
			throw new UserAlreadyLoggedInException(this);
		loggedIn = true;
		return this;
	}

	public User loginAsGuest() throws UserAlreadyLoggedInException, WrongPasswordException {
		return login(guestPassword);
	}

	/**
	 * Logout user and returns a new guest user which is logged in automatically.
	 * @return new logged in guest user.
	 * @throws UserNotLoggedInException if user is not logged in.
	 */
	public User logout() throws UserNotLoggedInException {
		if (!isLoggedIn())
			throw new UserNotLoggedInException();
		loggedIn = false;
		User guest = newGuest();
		try {
			return guest.loginAsGuest();
		} catch (UserAlreadyLoggedInException | WrongPasswordException e) {
			e.printStackTrace();
			return guest;
		}
	}

	public boolean addFriendRequest(FriendRequest request) {
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

	public boolean unAppoint(SubForum subForum) {
		if (state.isModerator()) {
			boolean result = state.removeManagedSubForum(subForum);
			if (result & (state.getNumberOfManagedSubForums() == 0))
				setState(UserStates.newState(UserStates.MEMBER));
			return result;
		}
		return false;
	}

	public boolean appoint(SubForum subForum) {
		if (state.isModerator()) {
			return state.addManagedSubForum(subForum);
		} else {
			setState(UserStates.newState(UserStates.MODERATOR));
			return state.addManagedSubForum(subForum);
		}
	}

	public boolean banModerator() {
		// TODO
		return false;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
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
		String status = this.state.getStatus();
		state.setStatus(status);
		this.state = state;
	}

	public boolean addSentReport(Report report) {
		return sentReports.add(report);
	}

	public Set<User> getFriends(){
		return friends;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id != user.id) return false;
		if (state != null ? !state.equals(user.state) : user.state != null) return false;
		if (username != null ? !username.equals(user.username) : user.username != null) return false;
		if (hashedPassword != null ? !hashedPassword.equals(user.hashedPassword) : user.hashedPassword != null)
			return false;
		return !(emailAddress != null ? !emailAddress.equals(user.emailAddress) : user.emailAddress != null);

	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (state != null ? state.hashCode() : 0);
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (hashedPassword != null ? hashedPassword.hashCode() : 0);
		result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "User{" +
				"username : '" + username + '\'' +
				", state : " + state +
				((pendingNotifications != null) ? (", pendingNotifications : " + pendingNotifications) : "") +
				((friendRequests != null) ? ", friendRequests : " + friendRequests : "") +
				'}';
	}

}
