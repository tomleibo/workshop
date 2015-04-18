package users;

import java.util.List;

import content.Notification;
import content.SubForum;


@SuppressWarnings(value = "all")
public class Member {
	
	
	private static final int TYPE_GUEST=0;
	private static final int TYPE_MEMBER=1;
	private static final int TYPE_MODERATOR=2;
	private static final int TYPE_ADMIN=3;
	
	private int id;
	private String sessionId;
	private String userName;
	private String hashedPassword;
	private int type;
	private boolean active; //add state with banned?
	private List<Member> friends;
	private List<Notification> pendingNotifications;
	private List<FriendRequest> friendRequests;
	private List<Report> reports;
	
	public boolean addFriend(Member member) {
		return false;
	}
	
	public boolean deleteFriend(Member member) {
		return false;
	}
	
	public Report report(Member member, String title, String content) {
		return null;
	}
	
	public boolean deactivate() {
		return false;
	}
	
	// equivalent to deactivate?
	public boolean banUser() { //not moderator
		return false;
	}
	
	
	
	
}
