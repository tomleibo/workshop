package content;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import users.User;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="thread")
public class Thread {
	@Id
    @GeneratedValue
	@Column(name="thread_id", nullable=false, unique=true)
	public int id;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="member_started")
    @Cascade({CascadeType.SAVE_UPDATE})
	private User memberStarted;
	@Column(name="date")
	@Temporal(TemporalType.DATE)
	private Date date;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="opening_message")
    @Cascade({CascadeType.ALL})
	private Message openingMessage;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="sub_forum")
    @Cascade({CascadeType.SAVE_UPDATE})
	private SubForum subForum;

	public Thread(){}

	public Thread(User user, Message openingMessage, SubForum subForum){
		this.memberStarted = user;
		this.date = new Date(System.currentTimeMillis());
		this.openingMessage = openingMessage;
		this.subForum = subForum;
		openingMessage.setThread(this);
	}
	
	public User getMemberStarted() {
		return memberStarted;
	}

	public Date getDate() {
		return date;
	}

	public Message getOpeningMessage() {
		return openingMessage;
	}
	
	public boolean deleteSelf() {
		return subForum.removeThread(this);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Thread)) {
			return false;
		}
		Thread obj = (Thread) o;
		return obj.id == id;
	}

    public int getNumberOfMessages() {
        return openingMessage.getNumberOfMessages();
    }

    public int getNumberOfMessagesForUser(User user) {
        return openingMessage.getNumberOfMessagesByUser(user);
    }

    public SubForum getSubForum() {
        return subForum;
    }

}
