package content;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import users.User;
import utils.IdGenerator;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="thread")
public class Thread {
	@Id
    @GeneratedValue
	@Column(name="subforum_id", nullable=false, unique=true)
	public int id;
	@OneToOne
	@JoinColumn(name="member_started")
    @Cascade({CascadeType.SAVE_UPDATE})
	private User memberStarted;
	@Column(name="date")
	@Temporal(TemporalType.DATE)
	private Date date;
	@OneToOne
	@JoinColumn(name="opening_message")
    @Cascade({CascadeType.ALL})
	private Message openingMessage;
	@ManyToOne
	@JoinColumn(name="sub_forum")
    @Cascade({CascadeType.SAVE_UPDATE})
	private SubForum subForum;

	public Thread(){}

	public Thread(User user, Message openingMessage, SubForum subForum){
		this.id=IdGenerator.getId(IdGenerator.THREAD);
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
}
