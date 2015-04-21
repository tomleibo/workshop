package content;

import users.User;
import utils.IdGenerator;

public class Thread {
	public int id;
	private User memberStarted;
	private java.sql.Date date; 
	private Message openingMessage;
	private SubForum subForum;
	
	public Thread(User user, Message openingMessage, SubForum subForum){
		this.id=IdGenerator.getId(IdGenerator.THREAD);
		this.memberStarted = user;
		this.date = new java.sql.Date(System.currentTimeMillis());
		this.openingMessage = openingMessage;
		this.subForum = subForum;
		openingMessage.setThread(this);
	}
	
	public User getMemberStarted() {
		return memberStarted;
	}

	public java.sql.Date getDate() {
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
}
