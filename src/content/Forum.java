package content;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import policy.ForumPolicy;
import users.Notification;
import users.Report;
import users.User;
import utils.ForumLogger;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="Forum")
public class Forum {
	@Id
    @GeneratedValue
	@Column(name="forum_id", nullable = false, unique = true)
	public int id;
	@Column(name="name")
	private String name;
    @Column (name="status_types")
    private String statusTypes;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "admin")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
	private User admin;
	@OneToMany
	@JoinColumn(name="containing_forum")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<SubForum> subForums;
	@OneToMany
	@JoinColumn(name="containing_forum")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<User> members;
    @OneToMany
    @JoinColumn(name="reports_of_forum")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Report> reports;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="policy")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
	private ForumPolicy policy;

	public Forum() {

	}

	public Forum(User admin, ForumPolicy policy, String name) {
		this.name = name;
		this.admin = admin;
        this.statusTypes = "Gold:20;Silver:10;Regular:0;";
		members = new ArrayList<>();
		subForums = new ArrayList<>();
        reports = new ArrayList<>();
		this.policy = policy;
		addMember(admin);
	}

    public Forum(int id,User admin, ForumPolicy policy, String name) {
        this.id=id;
        this.name = name;
        this.admin = admin;
        this.statusTypes = "Gold:20;Silver:10;Regular:0;";
        members = new ArrayList<>();
        subForums = new ArrayList<>();
        this.policy = policy;
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

    public boolean hasSubMember(User member) {
        return members.contains(member);
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

    public void sendNotificationToAllUsers(Notification notification) {
        for (User user : members) {
            user.sendNotification(notification);
        }
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

    public List<User> getNonGuestMembers() {
        List<User> result = new ArrayList<>();
        for (User user : members) {
            if (user.isMember()) {
                result.add(user);
            }
        }
        return result;
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
		return reports.add(report);
	}

	public boolean isSecured() {
		return policy.isDoUsersNeedMailVerification();
	}

    public int getNumberOfMessages() {
        int numOfMessages = 0;
        for (SubForum subForum : subForums) {
            numOfMessages += subForum.getNumberOfMessages();
        }
        return numOfMessages;
    }

    public int getNumberOfMessagesForUser(User user) {
        int numOfMessages = 0;
        for (SubForum subForum : subForums) {
            numOfMessages += subForum.getNumberOfMessagesForUser(user);
        }
        return numOfMessages;
    }

    public boolean addStatusType(String type, int numberOfMessages) {
        if (statusTypes.isEmpty()) {
            statusTypes = type + ":" + numberOfMessages + ";";
            return true;
        }
        String[] statuses = statusTypes.split(";");
        boolean found = false;
        for (String status : statuses) {
            if (status.substring(0, status.indexOf(":")).equalsIgnoreCase(type)) {
                found = true;
                break;
            }
        }
        if (found)
            return false;
        statusTypes = statusTypes + type + ":" + numberOfMessages + ";";
        return true;
    }

    public boolean removeStatusType(String type) {
        if (statusTypes.isEmpty())
            return false;
        String[] statuses = statusTypes.split(";");
        StringBuilder builder = new StringBuilder();
        boolean found = false;
        for (String status : statuses) {
            if (status.substring(0, status.indexOf(":")).equalsIgnoreCase(type)) {
                found = true;
            } else {
                builder.append(status + ";");
            }
        }
        statusTypes = builder.toString();
        return found;
    }

    public boolean hasStatusType(String type) {
        if (statusTypes.isEmpty())
            return false;
        String[] statuses = statusTypes.split(";");
        boolean found = false;
        for (String status : statuses) {
            if (status.substring(0, status.indexOf(":")).equalsIgnoreCase(type)) {
                found = true;
                break;
            }
        }
        return found;
    }

    public Map<Integer, String> getStatusTypes() {
        Map<Integer, String> result = new HashMap<>();
        try {
            List<String> statuses = Arrays.asList(statusTypes.split(";"));
            for (String status : statuses) {
                String statusName = status.substring(0, status.indexOf(":"));
                Integer statusNumber = Integer.valueOf(status.substring(status.indexOf(":") + 1));
                result.put(statusNumber, statusName);
            }
        }
        catch (Exception e) {
            ForumLogger.errorLog(e.getMessage());
        }
        return result;
    }

    public List<Report> getReports() {
        return reports;
    }
}
