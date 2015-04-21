package users;

import content.Forum;
import users.userState.GuestState;
import users.userState.MemberState;
import users.userState.SuperAdminState;
import users.userState.UserState;

import java.util.*;

public class User {
	
	private int id;
	private UserState state;
	private String sessionId;
	private String userName;
	private String hashedPassword;
	private final Forum forum;
	private boolean active;
	private boolean banned;
	private Set<User> friends;
	private List<Notification> pendingNotifications;
	private List<FriendRequest> friendRequests;
	private List<Report> sentReports;
	private boolean loggedIn;
	private String emailAddress;
	
	private static final Map<Forum, User> guests = new HashMap<>();
	private static final Object superAdminLock = new Object();
	private static User superAdmin;

	public User() {
		this.forum = null;
		this.state = new SuperAdminState();
		initializeFlags();
	}

	public User(Forum forum) {
		this.forum = forum;
		this.state = new GuestState();
		initializeFlags();
	}

	public User(Forum forum, String username, String hashedPassword, String emailAddress) {
		this.forum = forum;
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

	public static User getGuestUser(Forum forum) {
		if (!guests.containsKey(forum)) {
			synchronized (guests) {
				if (!guests.containsKey(forum)) {
					guests.put(forum, new User(forum));
				}
			}
		}
		return guests.get(forum);
	}

	public static User getSuperAdmin(Forum forum) {
		if (superAdmin == null) {
			synchronized (superAdminLock) {
				if (superAdmin == null) {
					superAdmin = new User();
				}
			}
		}
		return superAdmin;
	}
	
	public FriendRequest sendFriendRequest(User user, String message) {
		if (state.canSendFriendRequest()) {
			FriendRequest request = new FriendRequest(this, user, message);
			if (user.getFriendRequest(request))
				return request;
		}
		return null;
	}
	
	public boolean getFriendRequest(FriendRequest request) {
		return state.canGetFriendRequest() && friendRequests.add(request);
	}
	
	public boolean addFriend(User user) {
		return friends.add(user);
	}
	
	public boolean deleteFriend(User user) {
		return friends.remove(user);
	}
	
	public Report sendReport(User user, String title, String content) {
		if (state.canSendReport() && user.state.canGetReport()) {
			Report report = new Report(title, content, this, user);
			if (forum.addReport(report))
				return report;
		}
		return null;
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

	@Override
	public String toString() {
		return "User{" +
				"userName : '" + userName + '\'' +
				((forum != null) ? ", forum : " + forum : "") +
				", state : " + state +
				((pendingNotifications != null) ? (", pendingNotifications : " + pendingNotifications) : "") +
				((friendRequests != null) ? ", friendRequests : " + friendRequests : "") +
				'}';
	}
}
