package users;

public class FriendRequest {

	private final User requestingMember;
	private final User receivingMember;
	private final String message;
	private final java.sql.Date date;
	
	public FriendRequest(User requestingMember, User receivingMember, String message) {
		this.requestingMember = requestingMember;
		this.receivingMember = receivingMember;
		this.message = message;
		this.date = new java.sql.Date(System.currentTimeMillis());
	}

	public User getRequestingMember() {
		return requestingMember;
	}

	public User getReceivingMember() {
		return receivingMember;
	}

	public String getMessage() {
		return message;
	}

	public java.sql.Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "FriendRequest{" +
				"requestingMember=" + requestingMember +
				", receivingMember=" + receivingMember +
				", message='" + message + '\'' +
				", date=" + date +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FriendRequest that = (FriendRequest) o;

		if (!requestingMember.equals(that.requestingMember)) return false;
		if (!receivingMember.equals(that.receivingMember)) return false;
		if (!message.equals(that.message)) return false;
		return date.equals(that.date);

	}

	@Override
	public int hashCode() {
		int result = requestingMember.hashCode();
		result = 31 * result + receivingMember.hashCode();
		result = 31 * result + message.hashCode();
		result = 31 * result + date.hashCode();
		return result;
	}
}
