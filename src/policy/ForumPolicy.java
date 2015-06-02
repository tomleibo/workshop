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
	public enum HashFunction{
		MD5,SHA
	}
	public ForumPolicy(){}
	public ForumPolicy(int maxModerators, String passwordRegex,HashFunction hash, boolean mailVeri) {
		this.maxModerators = maxModerators;
		this.passwordRegex = passwordRegex;
		this.hashFunction=hash;
		this.doUsersNeedMailVerification=mailVeri;
	}
	
	public ForumPolicy(int maxModerators, String passwordRegex,HashFunction hash) {
		this.maxModerators = maxModerators;
		this.passwordRegex = passwordRegex;
		this.hashFunction=hash;
		this.doUsersNeedMailVerification=false;
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
}
