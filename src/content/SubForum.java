package content;

import java.util.ArrayList;
import java.util.List;

import users.User;

@SuppressWarnings("unused")
public class SubForum {
	public int id;
	private String name;
	private List<Thread> threads;
	private List<User> moderators;
	private List<User> bannedModerators;
	private int maxModerators;
	
	public SubForum(String name, User mod, int maxModerators) {
		this.name=name;
		this.threads = new ArrayList<>();
		this.moderators = new ArrayList<>();
		moderators.add(mod);
		bannedModerators = new ArrayList<>();
		this.maxModerators=maxModerators;
	}
	
	
	
	public List<Thread> viewThreads() {
		return threads; 
	}
	
	public boolean addThread(Thread thread) {
		return threads.add(thread);
	}
	
	public boolean removeThread(Thread thread) {
		return threads.remove(thread); 
	}
	
	public boolean addModerator(User moderator) {
		if (moderators.size() < maxModerators) {	
			moderators.add(moderator);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean removeModerator(User moderator) {
		if (moderators.size() == 1) {
			return false;
		}
		else {
			moderators.remove(moderator);
			return true;
		}
	}
	
	public boolean changeModerator(User existingModerator, User newModerator) {
		moderators.add(newModerator);
		moderators.remove(existingModerator);
		return true;
	}
	
	public boolean banModerator(User moderator) {
		if (moderators.size() ==1 ){
			return false;
		}
		else {
			moderators.remove(moderator);
			bannedModerators.add(moderator);
			return true;
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Message)) {
			return false;
		}
		Message obj = (Message) o;
		return obj.id == id;
	}
}
