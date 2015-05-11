package content;

import org.hibernate.annotations.*;
import users.User;
import utils.IdGenerator;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="subforum")
public class SubForum {
	@Id
    @GeneratedValue
	@Column(name="subforum_id", nullable=false, unique=true)
	public int id;
	@Column(name="name")
	private String name;
	@OneToMany(mappedBy = "subForum")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
	private List<Thread> threads;
	@ManyToMany
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private List<User> moderators;
	@OneToMany
	@JoinColumn(name="banned_moderators")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private List<User> bannedModerators;
	@Column(name="max_moderators")
	private int maxModerators;

	public SubForum(){}

	public SubForum(String name, User mod, int maxModerators) {
		this.id=IdGenerator.getId(IdGenerator.SUBFORUM);
		this.name=name;
		this.threads = new ArrayList<>();
		this.moderators = new ArrayList<>();
		moderators.add(mod);
		bannedModerators = new ArrayList<>();
		this.maxModerators=maxModerators;
	}

	public List<User> getModerators() {
		return moderators;
	}

	public List<Thread> viewThreads() {
		return threads;
	}

	public String getName(){
		return this.name;
	}

	public boolean addThread(Thread thread) {
		return threads.add(thread);
	}

	public boolean removeThread(Thread thread) {
		return threads.remove(thread);
	}

	public boolean hasModerator(User moderator) {
		return moderators.contains(moderator);
	}

	public boolean addModerator(User moderator) {
		if (moderators.size() < maxModerators && !moderators.contains(moderator)) {
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
		if(moderators.contains(newModerator)) {
			return false;
		}
		moderators.add(newModerator);
		moderators.remove(existingModerator);
		return true;
	}

	public boolean banModerator(User moderator) {
		if (moderators.size() ==1 ){
			return false;
		}
		else {
			if (moderators.remove(moderator)) {
				bannedModerators.add(moderator);
				return true;
			}
			return false;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SubForum)) {
			return false;
		}
		SubForum obj = (SubForum) o;
		return obj.id == id  || name.equals(obj.getName());
	}

	public boolean didUserPostHere(User user) {
		for (Thread t : threads) {
			if (didUserPostPost(t.getOpeningMessage(),user)) {
				return true;
			}
		}
		return false;
	}

	private boolean didUserPostPost(Message openingMessage,User user) {
		if (openingMessage.getUser().equals(user)) {
			return true;
		}
		if (openingMessage.getComments()==null) {
			return false;
		}
		for (Message m : openingMessage.getComments()) {
			if(didUserPostPost(m,user)){
				return true;
			}
		}
		return false;
	}

    public int getNumberOfMessages() {
        int numOfMessages = 0;
        for (Thread thread : threads) {
            numOfMessages += thread.getNumberOfMessages();
        }
        return numOfMessages;
    }
}
