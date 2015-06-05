package users;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="friendrequest")
public class FriendRequest {
	@Id
    @GeneratedValue
	@Column(name="friend_request_id")
	public int id;
    @Column(name="viewed")
    private boolean viewed=false;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="requesting_member")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private User requestingMember;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "receiving_member")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private User receivingMember;
	@Column(name="message")
	private String message;
	@Column(name="date")
	@Temporal(TemporalType.DATE)
	private java.util.Date date;

	public FriendRequest(){

	}
	public FriendRequest(User requestingMember, User receivingMember, String message) {
		this.requestingMember = requestingMember;
		this.receivingMember = receivingMember;
		this.message = message;
		this.date = new java.sql.Date(System.currentTimeMillis());
        this.viewed = false;
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

	public java.util.Date getDate() {
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
		return  (message.equals(that.message)) ;


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
