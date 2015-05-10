package users;

import content.SubForum;
import exceptions.UserAlreadyLoggedInException;
import exceptions.UserNotLoggedInException;
import exceptions.WrongPasswordException;
import users.userState.*;
import utils.ForumLogger;
import utils.IdGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="user")
public class User {

	private static String guestUsername = "Guest";
	private static String guestPassword = "";
	private static String guestMail = "";
	@Id
	@Column(name="user_id")
	private int id;
	@Column(name="name")
	private String username;
	@Column(name="email_address")
	private String emailAddress;
	@Column(name="password")
	private String hashedPassword;
	@Column(name="date")
	@Temporal(TemporalType.DATE)
	private java.util.Date creationDate;
	@OneToOne
	@JoinColumn(name="state")
	private UserState state;
	@Column(name="is_active")
	private boolean active;
	@Column(name="is_banned")
	private boolean banned;
	@Column(name="is_logged_in")
	private boolean loggedIn;
	@ManyToMany
	private Set<User> friends;
	@OneToMany
	@JoinColumn(name="panding_notfications")
	private List<Notification> pendingNotifications;
	@OneToMany( mappedBy = "receivingMember")
	private List<FriendRequest> friendRequests;
	@OneToMany( mappedBy = "reporter")
	private List<Report> sentReports;
	@Column(name="seniority")
	private int  seniority;
	@Column(name="loginTime")
	private long loginTime;

	public User(){}
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
		creationDate = new java.util.Date(System.currentTimeMillis());
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
		if (!hashedPassword.equals(this.hashedPassword)) {
			ForumLogger.errorLog("The user " + username + " trying to login but he's password is incorrect");
			throw new WrongPasswordException();
		}
		if (isLoggedIn()) {
			ForumLogger.errorLog("The user " + username + " trying to login but ha is already logged in");
			throw new UserAlreadyLoggedInException(this);
		}
		loggedIn = true;
		ForumLogger.actionLog("The user " + username + " is now logged in!");
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
		if(friendRequests.add(request)) {
			ForumLogger.actionLog("The user " + getUsername() + "got friend request from "+request.getRequestingMember().getUsername());
			return true;
		}
		ForumLogger.errorLog("The user " + getUsername() + "could not get friend request from "+request.getRequestingMember().getUsername());
		return false;
	}

	public boolean addFriend(User user) {
		return friends.add(user);
	}

	public boolean deleteFriend(User user) {
		if(friends.remove(user)) {
			ForumLogger.actionLog("The user " + getUsername() + "deleted " + user.getUsername() + "from he's friends");
			return true;
		}
		ForumLogger.errorLog("The user " + getUsername() + "could not deleted " + user.getUsername() + "from he's friends");
		return false;
	}

	public boolean activate() {
		if (active)
			return false;
		active = true;
		return true;
	}

	public boolean deactivate() {
		if (!active) {
			ForumLogger.errorLog("The user " + getUsername() + "is already deactivate");
			return false;
		}
		ForumLogger.actionLog("The user " + getUsername() + "is now deactivate");
		active = false;
		return true;
	}

	public boolean unBanUser() {
		if (!banned) {
			ForumLogger.errorLog("The user " + getUsername() + "is already not banned");
			return false;
		}
		banned = false;
		ForumLogger.actionLog("The user " + getUsername() + "is now not banned");
		return true;
	}

	public boolean banUser() {
		if (banned) {
			ForumLogger.errorLog("The user " + getUsername() + "is already banned");
			return false;
		}
		banned = true;
		ForumLogger.actionLog("The user " + getUsername() + "is now banned");
		return true;
	}

	public boolean unAppoint(SubForum subForum) {
		if (state.isModerator()) {
			boolean result = state.removeManagedSubForum(subForum);
			if (result & (state.getNumberOfManagedSubForums() == 0)) {
				setState(UserStates.newState(UserStates.MEMBER));
				ForumLogger.actionLog("The user " + getUsername() + "is not a moderator anymore");
			}
			return result;
		}
		ForumLogger.errorLog("The user " + getUsername() + " can't unappoint from moderator position");
		return false;
	}

	public boolean appoint(SubForum subForum) {
		if (state.isModerator()) {
			ForumLogger.errorLog("The user " + getUsername() + " is already moderator");
			return state.addManagedSubForum(subForum);
		} else {
			setState(UserStates.newState(UserStates.MODERATOR));
			ForumLogger.actionLog("The user " + getUsername() + " is now moderator of the sub-forum " + subForum.getName());
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

	public boolean isActive() {
		return active;
	}

	public boolean isBanned() {
		return banned;
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

	public int getSeniority(){
		return seniority;
	}

	public long getLoginTime(){
		return loginTime;
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

	public List<FriendRequest> getFriendRequests() {
		return friendRequests;
	}
}
