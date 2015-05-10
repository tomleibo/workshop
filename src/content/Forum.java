package content;

import policy.ForumPolicy;
import users.Report;
import users.User;
import utils.ForumLogger;
import utils.IdGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name="Forum")
public class Forum {
	@Id
	@Column(name="forum_id", nullable = false, unique = true)
	public int id;
	@Column(name="name")
	private String name;
	@OneToOne
	@JoinColumn(name = "admin")
	private User admin;
	@OneToMany
	@JoinColumn(name="sub_forums")
	private List<SubForum> subForums;
	@OneToMany
	@JoinColumn(name="members")
	private List<User> members;
	@OneToOne
	@JoinColumn(name="policy")
	private ForumPolicy policy;

	public Forum() {

	}

	public Forum(User admin, ForumPolicy policy, String name) {
		this.id=IdGenerator.getId(IdGenerator.FORUM);
		this.name = name;
		this.admin = admin;
		members = new ArrayList<>();
		subForums = new ArrayList<>();
		this.policy = policy;
		//	addMember(SuperAdmin);
		addMember(admin);
	}

	public User getAdmin() {
		return admin;
	}

	public String getName() {
		return name;
	}

	public boolean hasSubForum(SubForum subForum) {
		return subForums.contains(subForum);
	}

	public boolean addSubForum(SubForum sub) {
		if (!subForums.contains(sub)){
			subForums.add(sub);
			return true;
		}
		return false;
	}

	public boolean deleteSubForum(SubForum subForum) {
		boolean b =subForums.remove(subForum);
		return b;

	}

	public boolean addMember(User user) {
		if(members.add(user)){
			ForumLogger.actionLog("The user " + user.getUsername() + "added to the forum " + getName());
			return true;
		}
		ForumLogger.errorLog("The user " + user.getUsername() + "could not be added to the forum " + getName());
		return false;

	}

	public boolean removeMember(User user) {
		members.remove(user);
		return true;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}

	public List<SubForum> getSubForums() {
		return subForums;
	}

	public void setSubForums(List<SubForum> subForums) {
		this.subForums = subForums;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public ForumPolicy getPolicy() {
		return policy;
	}

	public boolean setPolicy(ForumPolicy policy) {
		this.policy = policy;
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Forum)) {
			return false;
		}
		Forum obj = (Forum) o;
		return obj.id == id || name.equals(obj.getName());
	}

	public boolean addReport(Report report) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isSecured() {
		return false;
	}
}
