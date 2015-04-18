package content;

import java.util.List;

import users.Member;

@SuppressWarnings("unused")
public class SubForum {
	private List<Thread> threads;
	private List<Member> moderators;
	private List<Member> bannedModerators;
	
	public List<Thread> viewThreads() {
		//sort by date?
		return null;
	}
	
	public Thread openThread(Member member, String title, String content) {
		return null;
	}
	
	public boolean changeModerator(Member existingModerator, Member newModerator) {
		//by policy
		return false;
	}
	
	public boolean banModerator(Member moderator) {
		return false;
	}
}
