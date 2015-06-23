package users;

import content.SubForum;
import exceptions.WrongPasswordException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import utils.ForumLogger;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="user")
public class User {

	private static String guestUsername = "Guest";
	private static String guestPassword = "";
	private static String guestMail = "";

    public final static int GUEST = 0;
    public final static int MEMBER = 10;
    public final static int MODERATOR = 20;
    public final static int ADMIN = 30;
    public final static int SUPERADMIN = 40;


	@Id
    @GeneratedValue
	@Column(name="user_id")
	private int id;
	@Column(name="name")
	private String username;
	@Column(name="email_address")
	private String emailAddress;
	@Column(name="password")
	private String hashedPassword;
    @Column (name="oldPasswords")
    private String oldHashedPasswords;
    @Column(name="passwordSetDate")
    @Temporal(TemporalType.DATE)
    private java.util.Date passwordSetDate;
	@Column(name="date")
	@Temporal(TemporalType.DATE)
	private java.util.Date creationDate;
	@Column(name="is_active")
	private boolean active;
	@Column(name="is_banned")
	private boolean banned;
    @Column(name="state")
    private int state;
    @Column(name="status")
    private String status = "";
    @Column(name="question")
    private String question;
    @Column(name="answer")
    private String hashedAnswer;
	@ManyToMany()
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @LazyCollection(LazyCollectionOption.FALSE)
	private Set<User> friends;
	@OneToMany()
	@JoinColumn(name="panding_notfications")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<Notification> pendingNotifications;
	@OneToMany( mappedBy = "receivingMember")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<FriendRequest> friendRequests;
	@OneToMany( mappedBy = "reporter")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<Report> sentReports;
	@Column(name="seniority")
	private int  seniority;
	@Column(name="loginTime")
	private long loginTime;
    @OneToMany()
    @JoinColumn(name="managing_users")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SubForum> managedSubForums;

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User(){
        initializeUser();
    }

    public List<SubForum> getManagedSubForums() {
        return managedSubForums;
    }

    /**
	 * Constructor for testing.
	 */
	public User(int i) {
		this.id = i;
		this.username = "" + id;
		this.state=MEMBER;
		initializeUser();
	}

	/**
	 * Creates a new User with given arguments.
	 * @param username String representing the username.
	 * @param hashedPassword String representing the hashed password.
	 * @param emailAddress String representing the user's email address.
	 * @param state UserState for the created user.
	 */
	private User(String username, String hashedPassword, String emailAddress, int state) {
		this(username, hashedPassword, emailAddress, state, null, null);
	}

    public User(String username, String hashedPassword, String emailAddress, int state, String question, String hashedAnswer) {
        if (state==GUEST)
            this.username = username + id;
        else
            this.username = username;
        this.oldHashedPasswords = "";
        setHashedPassword(hashedPassword);
        this.state = state;
        this.emailAddress = emailAddress;
        this.question = question;
        this.hashedAnswer = hashedAnswer;
        friends = new HashSet<>();
        friendRequests = new ArrayList<>();
        pendingNotifications = new  ArrayList<>();
        sentReports = new ArrayList<>();
        initializeUser();
    }

    public boolean isGuest() {
        return state==GUEST;
    }

    public boolean isMember() {
        return state>=MEMBER;
    }

    public boolean isMod() {
        return state>=MODERATOR;
    }

    public boolean isAdmin() {
        return state>=ADMIN;
    }

    public boolean isSuperAdmin() {
        return state>=SUPERADMIN;
    }

    private void initializeUser() {
		active = true;
		banned = false;
		creationDate = new java.util.Date(System.currentTimeMillis());
        managedSubForums = new ArrayList<>();
        status = "Regular";
	}

	/**
	 * Returns a new guest user.
	 * @return User with Guest state.
	 */
	public static User newGuest() {
        return new User(guestUsername,guestPassword,guestMail,GUEST);
	}

	/**
	 * returns new super admin with given credentials.
	 * @param username String representing the username.
	 * @param hashedPassword String representing the hashed password.
	 * @param emailAddress String representing the user's email address.
	 * @return User with Member state.
	 */
	public static User newMember(String username, String hashedPassword, String emailAddress) {
		return new User(username, hashedPassword, emailAddress, MEMBER);
	}

    public static User newMember(String username, String hashedPassword, String emailAddress, String question, String hashedAnswer) {
        return new User(username, hashedPassword, emailAddress, MEMBER, question, hashedAnswer);
    }

	/**
	 * returns new super admin with given credentials.
	 * @param username String representing the username.
	 * @param hashedPassword String representing the hashed password.
	 * @param emailAddress String representing the user's email address.
	 * @return User with Super-Admin state.
	 */
	public static User newSuperAdmin(String username, String hashedPassword, String emailAddress) {
		return new User(username, hashedPassword, emailAddress, SUPERADMIN);
	}

	/**
	 * Login user and returns itself.
	 * @return user itself;
	 */
	public User login(String hashedPassword) throws WrongPasswordException {
		if (!isRightPassword(hashedPassword)) {
			ForumLogger.errorLog("The user " + username + " trying to login but he's password is incorrect");
			throw new WrongPasswordException();
		}
        loginTime = new Date().getTime();
		ForumLogger.actionLog("The user " + username + " is now logged in!");
		return this;
	}

    public boolean isRightPassword(String hashedPassword) {
        return hashedPassword.equals(this.hashedPassword);
    }

	public User loginAsGuest() throws WrongPasswordException {
		return login(guestPassword);
	}

	/**
	 * Logout user and returns a new guest user which is logged in automatically.
	 * @return new logged in guest user.
	 */
	public User logout() {
		User guest = newGuest();
		try {
			return guest.loginAsGuest();
		} catch (WrongPasswordException e) {
			e.printStackTrace();
			return guest;
		}
	}

	public boolean addFriendRequest(FriendRequest request) {
		if(friendRequests.add(request)) {
			ForumLogger.actionLog("The user " + getUsername() + "got friend request from " + request.getRequestingMember().getUsername());
			return true;
		}
		ForumLogger.errorLog("The user " + getUsername() + "could not get friend request from "+request.getRequestingMember().getUsername());
		return false;
	}

	public void sendNotification(Notification notification) {
        if (!pendingNotifications.contains(notification)) {
            pendingNotifications.add(notification);
        }
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
		if (isMod()) {
			boolean result = managedSubForums.remove(subForum);
			if (managedSubForums.size() == 0) {
				setState(MEMBER);
				ForumLogger.actionLog("The user " + getUsername() + "is not a moderator anymore");
			}
			return true;
		}
		ForumLogger.errorLog("The user " + getUsername() + " can't unappoint from moderator position");
		return false;
	}

	public boolean appoint(SubForum subForum) {
		if (isMod()) {
			ForumLogger.errorLog("The user " + getUsername() + " is already moderator");
			return managedSubForums.add(subForum);
		} else {
			setState(MODERATOR);
			ForumLogger.actionLog("The user " + getUsername() + " is now moderator of the sub-forum " + subForum.getName());
			return managedSubForums.add(subForum);
		}
	}

	public boolean banModerator() {
		// TODO
		return false;
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
        addToUsedPassword(hashedPassword);
        setPasswordSetDate(new Date());
	}

    public Date getPasswordSetDate() {
        return passwordSetDate;
    }

    public void setPasswordSetDate(Date passwordSetDate) {
        this.passwordSetDate = passwordSetDate;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public long getLoginTime(){
		return loginTime;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username.equals(user.username);

    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
	public String toString() {
		return "User{" + username + "}";
	}

	public List<FriendRequest> getFriendRequests() {
		return friendRequests;
	}

	public List<Notification> getPendingNotifications() {
		return pendingNotifications;
	}

    private void addToUsedPassword(String hashedPassword) {
        oldHashedPasswords = oldHashedPasswords + hashedPassword + ";";
    }

    public boolean alreadyUsedPassword(String hashedPassword) {
        return Arrays.asList(oldHashedPasswords.split(";")).contains(hashedPassword);
    }

	public String getStateName(){
		switch(this.state){
			case SUPERADMIN:
				return "Super-Admin";
			case ADMIN:
				return "Admin";
			case MODERATOR:
				return "Moderator";
			case MEMBER:
				return "Member";
			case GUEST:
				return "Guest";
		}
		return "";
	}

    public String getEmail() {
        return emailAddress;
    }
}
