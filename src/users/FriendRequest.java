package users;

public class FriendRequest {

	private User requestingMember;
	private User receivingMember;
	private String message;
	private java.sql.Date date;
	
	public FriendRequest(User requestingMember, User receivingMember, String message) {
		this.requestingMember = requestingMember;
		this.receivingMember = receivingMember;
		this.message = message;
		this.date = new java.sql.Date(System.currentTimeMillis());
	}

	public User getRequestingMember() {
		return requestingMember;
	}

	public void setRequestingMember(User requestingMember) {
		this.requestingMember = requestingMember;
	}

	public User getReceivingMember() {
		return receivingMember;
	}

	public void setReceivingMember(User receivingMember) {
		this.receivingMember = receivingMember;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public java.sql.Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}
}
