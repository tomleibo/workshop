package content;

import users.User;

public class Thread {
	public int id;
	private User memberStarted;
	private java.sql.Date date; 
	private Message openingMessage;
	private SubForum subForum;
	
	public Thread(User user, Message openingMessage, SubForum subForum){
		this.memberStarted = user;
		this.date = new java.sql.Date(System.currentTimeMillis());
		this.openingMessage = openingMessage;
		this.subForum = subForum;
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
		if (!(o instanceof Message)) {
			return false;
		}
		Message obj = (Message) o;
		return obj.id == id;
	}
}
