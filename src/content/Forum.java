package content;

import policy.ForumPolicy;
import users.Report;
import users.User;
import utils.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class Forum {
	public int id;
	private String name;
	private User admin;
	private List<SubForum> subForums;
	private List<User> members;
	private ForumPolicy policy;
	
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
	
	public User getSuperAdmin() {
		return admin;
	}
	
	public String getName() {
		return name;
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
		return members.add(user);
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
		return false;
	}
}
