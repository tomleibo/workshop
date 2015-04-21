package content;

import policy.ForumPolicy;
import users.Report;
import users.User;

import java.util.ArrayList;
import java.util.List;

public class Forum {
	public int id;
	private User superAdmin;
	private List<SubForum> subForums;
	private List<User> members;
	private ForumPolicy properties;
	
	public Forum(User admin, ForumPolicy policy) {
		superAdmin = admin;
		members = new ArrayList<>();
		subForums = new ArrayList<>();
		this.properties = policy;
		addMember(admin);
	}
	
	public User getSuperAdmin() {
		return superAdmin;
	}
	
	public boolean addSubForum(SubForum sub) {
		subForums.add(sub);
		return true;
	}
	
	public boolean deleteSubForum(SubForum subForum) {
		return subForums.remove(subForum);
		
	}
	
	public boolean addMember(User user) {
		members.add(user);
		return true;
	}
	
	public boolean removeMember(User user) {
		members.remove(user);
		return true;
	}
	
	public void setSuperAdmin(User superAdmin) {
		this.superAdmin = superAdmin;
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
	
	public ForumPolicy getProperties() {
		return properties;
	}
	
	public boolean setProperties(ForumPolicy policy) {
		this.properties = policy;
		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Message)) {
			return false;
		}
		Message obj = (Message) o;
		return obj.id == id;
	}

	public boolean addReport(Report report) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getName() {
		return null;
	}
}
