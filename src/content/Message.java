package content;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import users.User;

public class Message {
	public int id;
	private String title="";
	private String body="";
	private java.sql.Date date;
	//date last edited?
	private User publisher;
	private List<Message> comments;
	private Message enclosingMessage;
	private Thread thread;
	
	
	public Message(String title, String body, User publisher, Thread thread, Message parent) {
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
	
	public boolean deleteSelf(Message comment) {
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
	
	public java.sql.Date getDate() {
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
	
	//test delete
}
