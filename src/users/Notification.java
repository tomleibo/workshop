package users;


import java.sql.Date;

public class Notification {

	private final String title;
	private final String message;
	private final java.sql.Date date;
	
	public Notification(String title, String message) {
		this.title = title;
		this.message = message;
		this.date = new java.sql.Date(System.currentTimeMillis());
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Notification{" +
				"title='" + title + '\'' +
				", message='" + message + '\'' +
				", date=" + date +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Notification that = (Notification) o;

		if (!title.equals(that.title)) return false;
		if (!message.equals(that.message)) return false;
		return date.equals(that.date);
	}

	@Override
	public int hashCode() {
		int result = title.hashCode();
		result = 31 * result + message.hashCode();
		result = 31 * result + date.hashCode();
		return result;
	}
}
