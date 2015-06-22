package policy;

import javax.persistence.*;

@Entity
public class ForumPolicy{
	@Id
	@GeneratedValue
	public int id;
	@Column(name = "max_moderators")
	private int maxModerators;
	@Column(name = "password_regex")
	private String passwordRegex;
	@Enumerated(EnumType.STRING)
	private HashFunction hashFunction;
	@Column(name = "mail_verification")
	private boolean doUsersNeedMailVerification;
    @Column (name = "sessionTimeout")
    private long sessionTimeout; // Milliseconds.
    @Column (name = "idleTime")
    private int idleTime;
    @Column (name = "askIdentificationQuestion")
    private boolean askIdentificationQuestion;
    @Column (name = "passwordExpireDate")
    private long passwordMaxTime; // Milliseconds.
    @Column (name = "canModeratorEditPosts")
    private boolean canModeratorEditPosts;
    @Column (name = "moderatorMinimumNumberOfPosts")
    private int moderatorMinimumNumberOfPosts;
    @Column (name = "moderatorMinimumSeniority")
    private long moderatorMinimumSeniority; // Milliseconds.


    public enum HashFunction{
		MD5,SHA
	}

	public ForumPolicy() {
        this(1, ".+", HashFunction.MD5, false, 7 * 24 * 60 * 60 * 1000, 24 * 60 * 60 * 1000, false, -1, true, 0, 0);
    }

	public ForumPolicy(int maxModerators, String passwordRegex,HashFunction hash, boolean mailVeri) {
        this(maxModerators, passwordRegex, hash, mailVeri, 7 * 24 * 60 * 60 * 1000, 24 * 60 * 60 * 1000, false, -1, true, 0, 0);
    }
	
	public ForumPolicy(int maxModerators, String passwordRegex,HashFunction hash) {
		this(maxModerators, passwordRegex, hash, false, 7 * 24 * 60 * 60 * 1000, 24 * 60 * 60 * 1000, false, -1, true, 0, 0);
	}

    public ForumPolicy(int maxModerators, String passwordRegex, HashFunction hashFunction,
                       boolean doUsersNeedMailVerification, long sessionTimeout, int idleTime,
                       boolean askIdentificationQuestion, long passwordMaxTime, boolean canModeratorEditPosts,
                       int moderatorMinimumNumberOfPosts, long moderatorMinimumSeniority) {
        this.maxModerators = maxModerators;
        this.passwordRegex = passwordRegex;
        this.hashFunction = hashFunction;
        this.doUsersNeedMailVerification = doUsersNeedMailVerification;
        this.sessionTimeout = sessionTimeout;
        this.idleTime = idleTime;
        this.askIdentificationQuestion = askIdentificationQuestion;
        this.passwordMaxTime = passwordMaxTime;
        this.canModeratorEditPosts = canModeratorEditPosts;
        this.moderatorMinimumNumberOfPosts = moderatorMinimumNumberOfPosts;
        this.moderatorMinimumSeniority = moderatorMinimumSeniority;
    }

    public int getMaxModerators() {
		return maxModerators;
	}
	
	public boolean checkPassword(String password) {
		return password.matches(passwordRegex);
	}
	
	public HashFunction getHashFunction() {
		return hashFunction;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForumPolicy policy = (ForumPolicy) o;

        if (maxModerators != policy.maxModerators) return false;
        if (doUsersNeedMailVerification != policy.doUsersNeedMailVerification) return false;
        if (sessionTimeout != policy.sessionTimeout) return false;
        if (idleTime != policy.idleTime) return false;
        if (askIdentificationQuestion != policy.askIdentificationQuestion) return false;
        if (passwordMaxTime != policy.passwordMaxTime) return false;
        if (canModeratorEditPosts != policy.canModeratorEditPosts) return false;
        if (moderatorMinimumNumberOfPosts != policy.moderatorMinimumNumberOfPosts) return false;
        if (moderatorMinimumSeniority != policy.moderatorMinimumSeniority) return false;
        if (!passwordRegex.equals(policy.passwordRegex)) return false;
        return hashFunction == policy.hashFunction;

    }

    @Override
    public int hashCode() {
        int result = maxModerators;
        result = 31 * result + passwordRegex.hashCode();
        result = 31 * result + hashFunction.hashCode();
        result = 31 * result + (doUsersNeedMailVerification ? 1 : 0);
        result = 31 * result + (int) (sessionTimeout ^ (sessionTimeout >>> 32));
        result = 31 * result + idleTime;
        result = 31 * result + (askIdentificationQuestion ? 1 : 0);
        result = 31 * result + (int) (passwordMaxTime ^ (passwordMaxTime >>> 32));
        result = 31 * result + (canModeratorEditPosts ? 1 : 0);
        result = 31 * result + moderatorMinimumNumberOfPosts;
        result = 31 * result + (int) (moderatorMinimumSeniority ^ (moderatorMinimumSeniority >>> 32));
        return result;
    }

    public void setMaxModerators(int maxModerators) {
        this.maxModerators = maxModerators;
    }

    public String getPasswordRegex() {
        return passwordRegex;
    }

    public void setPasswordRegex(String passwordRegex) {
        this.passwordRegex = passwordRegex;
    }

    public void setHashFunction(HashFunction hashFunction) {
        this.hashFunction = hashFunction;
    }

    public boolean isDoUsersNeedMailVerification() {
        return doUsersNeedMailVerification;
    }

    public void setDoUsersNeedMailVerification(boolean doUsersNeedMailVerification) {
        this.doUsersNeedMailVerification = doUsersNeedMailVerification;
    }

    public long getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public int getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(int idleTime) {
        this.idleTime = idleTime;
    }

    public boolean isAskIdentificationQuestion() {
        return askIdentificationQuestion;
    }

    public void setAskIdentificationQuestion(boolean askIdentificationQuestion) {
        this.askIdentificationQuestion = askIdentificationQuestion;
    }

    public long getPasswordMaxTime() {
        return passwordMaxTime;
    }

    public void setPasswordMaxTime(int passwordExpireDate) {
        this.passwordMaxTime = passwordExpireDate;
    }

    public void setSessionTimeout(long sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public void setPasswordMaxTime(long passwordMaxTime) {
        this.passwordMaxTime = passwordMaxTime;
    }

    public boolean isCanModeratorEditPosts() {
        return canModeratorEditPosts;
    }

    public void setCanModeratorEditPosts(boolean canModeratorEditPosts) {
        this.canModeratorEditPosts = canModeratorEditPosts;
    }

    public int getModeratorMinimumNumberOfPosts() {
        return moderatorMinimumNumberOfPosts;
    }

    public void setModeratorMinimumNumberOfPosts(int moderatorMinimumNumberOfPosts) {
        this.moderatorMinimumNumberOfPosts = moderatorMinimumNumberOfPosts;
    }

    public long getModeratorMinimumSeniority() {
        return moderatorMinimumSeniority;
    }

    public void setModeratorMinimumSeniority(long moderatorMinimumSeniority) {
        this.moderatorMinimumSeniority = moderatorMinimumSeniority;
    }
}
