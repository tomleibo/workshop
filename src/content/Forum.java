package content;

import java.util.ArrayList;
import java.util.List;

import policy.ForumPolicy;
import users.Report;
import users.User;
import utils.IdGenerator;

public class Forum {
	public int id;
	private String name;
	private User admin;
	private List<SubForum> subForums;
	private List<User> members;
	private ForumPolicy properties;
	
	public Forum(User admin, ForumPolicy policy, String name) {
		this.id=IdGenerator.getId(IdGenerator.FORUM);
		this.name = name;
		this.admin = admin;
		members = new ArrayList<>();
		subForums = new ArrayList<>();
		this.properties = policy;
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
	
	public void setSuperAdmin(User superAdmin) {
		this.admin = superAdmin;
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
