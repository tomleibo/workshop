package users;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import content.Forum;
import users.userState.GuestState;
import users.userState.MemberState;
import users.userState.UserState;

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
	
	public User(Forum forum) {
		this.forum = forum;
		this.state = new GuestState();
		active = true;
		banned = false;
		loggedIn = false;
	}
	
	public User(Forum forum, String userName, String hashedPassword) {
		this.forum = forum;
		this.userName = userName;
		this.hashedPassword = hashedPassword;
		this.state = new MemberState();
		friends = new HashSet<User>();
		friendRequests = new  ArrayList<FriendRequest>();
		pendingNotifications = new  ArrayList<Notification>();
		sentReports = new ArrayList<Report>();
		active = true;
		banned = false;
		loggedIn = false;
	}
	
	public FriendRequest sendFriendRquest(User user, String message) {
		if (state.canSendFriendRequest()) {
			FriendRequest request = new FriendRequest(this, user, message);
			if (user.getFriendRequest(request))
				return request;
		}
		return null;
	}
	
	public boolean getFriendRequest(FriendRequest request) {
		if (state.canGetFriendRequest())
			return friendRequests.add(request);
		return false;			
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
}
