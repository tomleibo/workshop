package users;


import content.Message;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="notification")
public class Notification {

	@Id
    @GeneratedValue
	@Column(name="notification_id")
	public int id;
	@Column(name="title")
	private String title;
	@Column(name="message")
	private String message;
	@Column(name="date")
	@Temporal(TemporalType.DATE)
	private Date date;
    @Column (name="viewed")
    private boolean viewed=false;

	public Notification(){

	}
	public Notification(String title, String message) {
		this.title = title;
		this.message = message;
		this.date = new java.sql.Date(System.currentTimeMillis());
        this.viewed = false;
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

    public static Notification newThreadNotification(content.Thread thread) {
        String title = "New thread published";
        String message = thread.getMemberStarted().getUsername() + " has published a new thread on your forum.";
        return new Notification(title, message);
    }

    public static Notification editMessageNotification(Message post) {
        String title = "Message was edited";
        String message =  post.getUser().getUsername() + " has edited a message you commented on: " + post.getTitle();
        return new Notification(title, message);
    }

    public static Notification deleteMessageNotification(Message post) {
        String title = "Message was deleted";
        String message =  post.getUser().getUsername() + " has deleted a message you commented on: " + post.getTitle() + "\nAs a result your comment was deleted.";
        return new Notification(title, message);
    }

    public static Notification repliedToMessageNotification(User user, Message addedTo) {
        String title = "Reply was published";
        String message =  user.getUsername() + " replied to message: " + addedTo.getTitle();
        return new Notification(title, message);
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public boolean isViewed() {
        return this.viewed;
    }
}
