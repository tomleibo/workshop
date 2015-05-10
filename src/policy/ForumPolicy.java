package policy;

import javax.persistence.*;

@Entity
public class ForumPolicy{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
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
}
