package content;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import users.User;
import utils.IdGenerator;
@Entity
@Table(name="Message")
public class Message {
	@Id
	@Column(name="message_id", nullable=false, unique=true)
	public int id;
	@Column(name="title")
	private String title="";
	@Column(name="body")
	private String body="";
	@Column(name="date")
	@Temporal(TemporalType.DATE)
	private java.util.Date date;

	//date last edited?
	@OneToOne
	@JoinColumn(name="publisher")
	private User publisher;
	@OneToMany(mappedBy = "enclosingMessage")
	private List<Message> comments;
	//add insertable=false | uodateable=false
	@ManyToOne()
	@JoinColumn(name="enclosing")
	private Message enclosingMessage;
	@OneToOne
	@JoinColumn(name="thread")
	private Thread thread;

	public Message() {

	}

	public Message(String title, String body, User publisher, Thread thread, Message parent) {
		this.id=IdGenerator.getId(IdGenerator.MESSAGE);
		this.title = title;
		this.body = body;
		this.date = new java.sql.Date(System.currentTimeMillis());
		this.publisher = publisher;
		this.comments = new ArrayList<>();
		this.enclosingMessage = parent;
		this.thread = thread;
	}

	public Message getEnclosingMessage() {
		return enclosingMessage;
	}

	public boolean edit(String body) {
		this.body=body;
		return true;
	}

	public boolean deleteSelf() {
		if (this.enclosingMessage == null) {
			return this.thread.deleteSelf();
		}
		else {
			return this.enclosingMessage.removeComment(this);
		}
	}

	private boolean removeComment(Message message) {
		return comments.remove(message);
	}

	public boolean addComment(Message comment) {
		return comments.add(comment);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Message)) {
			return false;
		}
		Message obj = (Message) o;
		return obj.id == id;
	}

	public String getBody() {
		return body;
	}

	public String getTitle() {
		return title;
	}

	public java.util.Date getDate() {
		return date;
	}

	public List<Message> getComments() {
		return comments;
	}

	public User getUser() {
		return publisher;
	}

	public boolean setThread(Thread threadAdd) {
		this.thread = threadAdd;
		return true;
	}

	public Thread getThread() {
		return thread;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", title=" + title + ", publisher="
				+ publisher + "]";
	}

}
