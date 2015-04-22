package policy;


public class ForumPolicy extends Policy {
	private int maxModerators;
	private String passwordRegex;
	private HashFunction hashFunction;
	private boolean doUsersNeedMailVerification;
	public enum HashFunction{
		MD5,SHA
	}
	
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
