package policy;

import policy.Policy;

public class ForumPolicy extends Policy {
	private int maxModerators;

	public ForumPolicy(int maxModerators) {
		this.maxModerators = maxModerators;
	}

	public int getMaxModerators() {
		return maxModerators;
	}
	
}
