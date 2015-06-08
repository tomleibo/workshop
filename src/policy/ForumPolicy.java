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
    private long sessionTimeout;
    @Column (name = "idleTime")
    private int idleTime;
    @Column (name = "askIdentificationQuestion")
    private boolean askIdentificationQuestion;
    @Column (name = "passwordExpireDate")
    private long passwordMaxTime;

    public enum HashFunction{
		MD5,SHA
	}

	public ForumPolicy() {
        this.maxModerators = 1;
        this.passwordRegex = ".+";
        this.hashFunction = HashFunction.MD5;
        this.doUsersNeedMailVerification = false;
        this.passwordMaxTime = -1;
        this.askIdentificationQuestion = false;
        this.sessionTimeout = 7 * 24 * 60 * 60 * 1000;
        this.idleTime = 24 * 60 * 60 * 1000;
    }

	public ForumPolicy(int maxModerators, String passwordRegex,HashFunction hash, boolean mailVeri) {
		this.maxModerators = maxModerators;
		this.passwordRegex = passwordRegex;
		this.hashFunction = hash;
		this.doUsersNeedMailVerification = mailVeri;
        this.passwordMaxTime = -1;
        this.askIdentificationQuestion = false;
        this.sessionTimeout = 7 * 24 * 60 * 60 * 1000;
        this.idleTime = 24 * 60 * 60 * 1000;
	}
	
	public ForumPolicy(int maxModerators, String passwordRegex,HashFunction hash) {
		this.maxModerators = maxModerators;
		this.passwordRegex = passwordRegex;
		this.hashFunction=hash;
		this.doUsersNeedMailVerification=false;
        this.passwordMaxTime = -1;
        this.askIdentificationQuestion = false;
        this.sessionTimeout = 7 * 24 * 60 * 60 * 1000;
        this.idleTime = 24 * 60 * 60 * 1000;
	}

    public ForumPolicy(int maxModerators, String passwordRegex, HashFunction hashFunction, boolean doUsersNeedMailVerification, long sessionTimeout, int idleTime, boolean askIdentificationQuestion, long passwordMaxTime) {
        this.maxModerators = maxModerators;
        this.passwordRegex = passwordRegex;
        this.hashFunction = hashFunction;
        this.doUsersNeedMailVerification = doUsersNeedMailVerification;
        this.sessionTimeout = sessionTimeout;
        this.idleTime = idleTime;
        this.askIdentificationQuestion = askIdentificationQuestion;
        this.passwordMaxTime = passwordMaxTime;
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

        ForumPolicy that = (ForumPolicy) o;

        if (maxModerators != that.maxModerators) return false;
        if (doUsersNeedMailVerification != that.doUsersNeedMailVerification) return false;
        if (passwordRegex != null ? !passwordRegex.equals(that.passwordRegex) : that.passwordRegex != null)
            return false;
        return hashFunction == that.hashFunction;

    }

    @Override
    public int hashCode() {
        int result = maxModerators;
        result = 31 * result + (passwordRegex != null ? passwordRegex.hashCode() : 0);
        result = 31 * result + (hashFunction != null ? hashFunction.hashCode() : 0);
        result = 31 * result + (doUsersNeedMailVerification ? 1 : 0);
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
}
